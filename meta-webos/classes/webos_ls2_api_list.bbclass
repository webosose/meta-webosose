# Copyright (c) 2022-2025 LG Electronics, Inc.
#
# write_ls2_api_list
#
# This class adds write_ls2_api_list task.
# write_ls2_api_list can be run by bitbake -c write_ls2_api_list <image>
# ls2_api_list.json files of build

LS2_API_LIST_FILENAME   ?= "ls2_api_list.json"

# We need ls2_api_list.json image
addtask write_ls2_api_list after do_rootfs before do_image
do_write_ls2_api_list[doc] = "Collects ls2 api information of the image"

python do_write_ls2_api_list() {
    import os,json,glob,re

    def read_from_json(json_file):
        with open(json_file, 'r') as file:
            raw_lines = file.read()
        try:
            parsed_json = json.loads(raw_lines)
            return parsed_json
        except json.JSONDecodeError as e:
            bb.note('Error parsing JSON: {e}')
            return None

    def remove_comments_from_json(json_file):
        with open(json_file, 'r') as file:
            lines = file.readlines()

        clean_lines = []
        for line in lines:
            # Remove single-line comments
            line = re.sub(r'//.*', '', line)
            # Remove multi-line comments
            line = re.sub(r'/\*.*?\*/', '', line, flags=re.DOTALL)
            clean_lines.append(line)

        # Combine the cleaned lines back into a single string
        clean_json = ''.join(clean_lines)
        # Parse the cleaned JSON
        try:
            parsed_json = json.loads(clean_json)
            return parsed_json
        except json.JSONDecodeError as e:
            bb.note('Error parsing JSON: {e}')
            return None

    def read_ls2_manifests_directories(image_rootfs):
        manifests_directories = []
        with open(os.path.join(image_rootfs, 'etc', 'luna-service2', 'ls-hubd.conf'), 'r') as f:
            ls_hubd_conf = f.read()
        for line in ls_hubd_conf.splitlines():
            line = line.strip()
            #ignore empty lines and comments
            if not line or line.startswith('#'):
                continue
            #ignore section likes [Security]
            elif line.startswith('[') and line.endswith(']'):
                continue
            else:
                key, value = line.split('=', 1)
                key = key.strip()
                value = value.strip()
                if key == 'ManifestsDirectories':
                    manifests_directories = value.split(';')
                    break
        return manifests_directories

    def search_for_manifest_files(root_image_path,manifest_dir_path):
        manifest_file_path=os.path.join(root_image_path,manifest_dir_path.lstrip('/'),'*.manifest.json')
        bb.debug(1,'Gathering info on api permission file from manifest_file_path %s' % manifest_file_path)
        return glob.glob(manifest_file_path)

    def get_api_permissions_for_manifest_file(root_image_path,manifest_file):
        bb.debug(1,'Gathering info on api permission file from manifest_file %s' % manifest_file)
        apis_details=[];
        with open(manifest_file, 'r') as f:
            manifest_data = json.load(f)
        if 'apiPermissionFiles' in manifest_data:
            for api_permission_file in manifest_data['apiPermissionFiles']:
                api_permission_file_path = os.path.join(root_image_path, api_permission_file.lstrip('/'))
                if not os.path.isfile(api_permission_file_path) or 'compat.' in api_permission_file_path:
                    bb.note("File not found or Compat files excluded : " + api_permission_file_path)
                    continue
                # Remove comments and parse JSON
                api_permission_data = remove_comments_from_json(api_permission_file_path)
                if api_permission_data is None:
                    bb.note('Failed to parse JSON due to comment removal.')
                for key, value in api_permission_data.items():
                    apis_details.extend(value)
        return apis_details

    def process_ls2_group_files(file_path):
        groups = []
        groups_data = read_from_json(file_path)
        if groups_data is None:
            groups_data = remove_comments_from_json(file_path)
        if groups_data is None:
            bb.note('Failed to read JSON or commented JSON in file %s'%(file_path))
            return None
        for key, value in groups_data.items():
            if key == "allowedNames": #skip old groups file key
                continue
            if isinstance(value, dict):
                groups.append({key:value})
            elif (len(value) == 1):
                group_new_format_value = {"privacy":False, "trust" :  value[0], "description":""}
                groups.append({key:group_new_format_value})
            else:
                bb.note('skipped %s due to unknown format or multiple ACG groups there'%(key))
        return groups

    def process_ls2_role_files(file_path):
        trustLevelDetails = []
        role_data = read_from_json(file_path)
        if role_data is None:
            role_data = remove_comments_from_json(file_path)
        if role_data is None:
            bb.note('Failed to read JSON or commented JSON in file %s'%(file_path))
            return None
        # convert to {file name : content} object
        file_name = os.path.basename(file_path).replace(".role.json", "").replace(".app.json", "")
        trustLevelDetails.append({file_name: role_data})
        return trustLevelDetails

    def process_ls2_client_permissions_files(file_path):
        client_permissions = []
        permission_data = read_from_json(file_path)
        if permission_data is None:
            permission_data = remove_comments_from_json(file_path)
        if permission_data is None:
            bb.note('Failed to parse JSON due to comment removal.')
            return None
        for key, value in permission_data.items():
            client_permissions.append({key: value})
        return client_permissions

    def get_ls2_metadata_from_manifest_file(meta_file_type, root_image_path,manifest_file):
        bb.debug(1,'Gathering info on api permission file from manifest_file %s' % manifest_file)
        ls2_meta_datas=[];
        with open(manifest_file, 'r') as f:
            manifest_data = json.load(f)
        if meta_file_type in manifest_data:
            for ls2_meta_file in manifest_data[meta_file_type]:
                ls2_meta_file_path = os.path.join(root_image_path, ls2_meta_file.lstrip('/'))
                if not os.path.isfile(ls2_meta_file_path) or 'compat.' in ls2_meta_file_path:
                    bb.note("File not found or Compat files excluded : " + ls2_meta_file_path)
                    continue
                if meta_file_type in ["clientPermissionFiles", "apiPermissionFiles"]:
                   temp_permissions_data =  process_ls2_client_permissions_files(ls2_meta_file_path)
                   if temp_permissions_data:
                        ls2_meta_datas.extend(temp_permissions_data)
                elif (meta_file_type == 'groupsFiles'):
                    temp_groups_data = process_ls2_group_files(ls2_meta_file_path)
                    if temp_groups_data :
                        ls2_meta_datas.extend(temp_groups_data)
                elif (meta_file_type == 'roleFiles'):
                    temp_groups_data = process_ls2_role_files(ls2_meta_file_path)
                    if temp_groups_data :
                        ls2_meta_datas.extend(temp_groups_data)
                else:
                    bb.note('skipped ls2 meta file Type %s in Manifest File '%(meta_file_type, manifest_file))
        return ls2_meta_datas

    # Actual Business Logic Starts Here
    root_image_path =d.getVar('IMAGE_ROOTFS');
    bb.note("perform do_write_ls2_api_list on ROOTFS : " + root_image_path)
    manifests_directories = read_ls2_manifests_directories(root_image_path)
    bb.note('Manifests directories: %s' % manifests_directories)
    ls_api_info_data = []
    ls_permissions_info_data = []
    ls_groups_info_data = []
    ls_acg_methods_info_data = []
    ls_role_info_data = [] # for storing ACG Details and Outbound Detals
    # Iterate over the manifests directories and check for manifest files
    for manifests_dir in manifests_directories:
        # Make absolute manifest path to search for files ending with .manifest.json
        manifest_files = search_for_manifest_files(root_image_path, manifests_dir)
        for manifest_file in manifest_files:
            bb.note('Gathering info on permissions from manifest file %s' % manifest_file)
            perm_info = {}
            # Get API permissions
            apis_details = get_api_permissions_for_manifest_file(root_image_path, manifest_file)
            if apis_details:
                ls_api_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): apis_details})
            # Get group permissions
            groups_details = get_ls2_metadata_from_manifest_file('groupsFiles', root_image_path, manifest_file)
            if groups_details:
                ls_groups_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): groups_details})
            # Get client permissions
            client_permissions_details = get_ls2_metadata_from_manifest_file('clientPermissionFiles', root_image_path, manifest_file)
            if client_permissions_details:
                ls_permissions_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): client_permissions_details})
            # Get methods of ACG Groups
            acg_method_details = get_ls2_metadata_from_manifest_file('apiPermissionFiles', root_image_path, manifest_file)
            if client_permissions_details:
                ls_acg_methods_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): acg_method_details})
            # Get Trust Level & Outbound permissions from role file
            role_info_data = get_ls2_metadata_from_manifest_file('roleFiles', root_image_path, manifest_file)
            if role_info_data:
                ls_role_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): role_info_data})
    bb.note("webOS Release Code Name ::: " + d.getVar('WEBOS_DISTRO_RELEASE_CODENAME'))
    bb.note("webOS Distro Name ::: " + d.getVar('DISTRO'))
    ls_api_info = {
        'ls2_api_list': ls_api_info_data,
        'ls2_client_permissions_list': ls_permissions_info_data,
        'ls2_groups_list': ls_groups_info_data,
        'ls2_methods_list': ls_acg_methods_info_data,
        'ls2_role_list' : ls_role_info_data,
        'release_code_name': d.getVar('WEBOS_DISTRO_RELEASE_CODENAME'),
        'distro': d.getVar('DISTRO')}

    ls2_output_file = d.getVar("LS2_API_LIST_FILENAME")
    if not (d.getVar('BUILDHISTORY_DIR_IMAGE') and os.path.isdir(d.getVar('BUILDHISTORY_DIR_IMAGE'))):
        bb.note("BUILDHISTORY_DIR_IMAGE '%s' is empty or isn't a directory (probably buildhistory isn't enabled), will not create %s file there" % (d.getVar('BUILDHISTORY_DIR_IMAGE'), ls2_output_file))
        return
    output = os.path.join(d.getVar('BUILDHISTORY_DIR_IMAGE'), ls2_output_file)
    json_info_as_string = json.dumps(ls_api_info)
    with open(output, 'w') as f:
        f.write(json_info_as_string)
    bb.note("BUILD Path of ls2_api_list.json file : " + output)
}
