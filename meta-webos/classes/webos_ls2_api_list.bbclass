# Copyright (c) 2022-2024 LG Electronics, Inc.
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

    root_image_path =d.getVar('IMAGE_ROOTFS');
    bb.note("perform do_write_ls2_api_list on ROOTFS : " + root_image_path)
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


    manifests_directories = read_ls2_manifests_directories(root_image_path)
    bb.note('Manifests directories: %s' % manifests_directories)
    ls_api_info_data = []
    # Iterate over the manifests directories and check for  manifest files
    for manifests_dir in manifests_directories:
        # make absolute manifest path to search for files ending with .manifest.json'
        manifest_files =search_for_manifest_files (root_image_path,manifests_dir)

        for manifest_file in manifest_files:
            bb.note('Gathering info on api permission file from manifest_file %s' % manifest_file)
            apis_details= get_api_permissions_for_manifest_file(root_image_path,manifest_file)
            if apis_details:
                ls_api_info_data.append({os.path.basename(manifest_file).replace(".manifest.json", ""): apis_details})
    bb.note("webOS Release Code Name ::: " + d.getVar('WEBOS_DISTRO_RELEASE_CODENAME'))
    bb.note("webOS Distro Name ::: " + d.getVar('DISTRO'))
    ls_api_info= {'ls2_api_list': ls_api_info_data, 'release_code_name': d.getVar('WEBOS_DISTRO_RELEASE_CODENAME'), 'distro': d.getVar('DISTRO')}

    #write ls_api_info to file
    ls2_output_file = d.getVar("LS2_API_LIST_FILENAME")
    output = os.path.join(d.getVar('BUILDHISTORY_DIR_IMAGE'), ls2_output_file)
    json_info_as_string = json.dumps(ls_api_info).replace("'", '"')
    with open(output, 'w') as f:
        f.write(json_info_as_string)
    bb.note("BUILD Path of ls2_api_list.json file : " + output)
}
