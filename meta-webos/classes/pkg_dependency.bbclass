# Copyright (c) 2021-2025 LG Electronics, Inc.

# Write package dependency data as a json file
python create_pkg_dependency_data () {
    from oe.rootfs import image_list_installed_packages
    import os
    import subprocess
    import json

    rootfs_dir = d.getVar('IMAGE_ROOTFS')
    ipkconf_target = d.getVar('IPKGCONF_TARGET')

    def initOpkgInfoFromIpkConf():
        with open(ipkconf_target, 'r', encoding='utf-8') as ipkconf_fd:
            ipk_info_dir = ''
            for var in ipkconf_fd:
                if var.startswith('option info_dir'):
                    opkg_info_path = rootfs_dir + var.strip().split()[-1]
                    break
            if not os.path.exists(opkg_info_path):
                bb.fatal("[ERROR] Cannot find opkg info directory while creating package info data.")
            return opkg_info_path

    opkg_info_path = initOpkgInfoFromIpkConf()

    def getInfo(pkg, key):
        key_match_val=dict({'recipe': 'Recipes', 'license': 'License', 'section': 'Section', 'author': 'Author', 'rp_maintainer': 'RP-Maintainer', 'description': 'Description'})
        pkg_control = os.path.join(opkg_info_path, f'{pkg}.control')
        if not os.path.exists(pkg_control):
            bb.warn(f"[WARN] There isn\'t {pkg}.control, couldn\'t get {key} of {pkg}")
        else:
            with open(pkg_control, 'r', encoding='utf-8') as pkg_control_fd:
                for var in pkg_control_fd:
                    if var.startswith(key_match_val[key]):
                        return var.strip().split(':')[-1].strip()

    def getInstall_files(pkg):
        pkg_list = os.path.join(opkg_info_path, f'{pkg}.list')
        if not os.path.exists(pkg_list):
            bb.warn(f"[WARN] There isn\'t {pkg}.list, couldn\'t get install_files from {pkg}")
        else:
            pkg_list_fd = open(pkg_list, 'r', encoding='utf-8')
            file_list=list()
            for var in pkg_list_fd:
                file_list.append(var.strip().split()[0])
            pkg_list_fd.close()
            return file_list

    output = dict()
    installed_packages = image_list_installed_packages(d)

    for pkg, val in installed_packages.items():
        if "deps" not in val:
            val.update({"deps":[]})
        if "provs" not in val:
            val.update({"provs":[]})
        if "ver" not in val:
            val.update({"ver":''})
        if "arch" not in val:
            val.update({"arch":''})
        if "filename" not in val:
            val.update({"filename":''})

        if output.get(pkg):
            output[pkg]["requires"].extend(val["deps"])
            output[pkg]["rprovides"].extend(val["provs"])
        else:
            output[pkg]={"requires":val["deps"],"requiredby":[],"rprovides":val["provs"]}
        output[pkg].update({"description":getInfo(pkg,'description')})
        output[pkg].update({"version":val["ver"]})
        output[pkg].update({"arch":val["arch"]})
        output[pkg].update({"ipk":val["filename"]})

        output[pkg]['recipe'] = getInfo(pkg,'recipe').split()
        output[pkg]['license'] = getInfo(pkg,'license')
        output[pkg]['section'] = getInfo(pkg,'section')
        output[pkg]['author'] = getInfo(pkg,'author')
        output[pkg]['rp_maintainer'] = getInfo(pkg,'rp_maintainer')
        output[pkg]['install_file'] = getInstall_files(pkg)
        for dep in val["deps"]:
            if '[REC]' in dep:
                if output.get(dep.split()[0]):
                    output[dep.split()[0]]["requiredby"].append(pkg+' [REC]')
                else:
                    output[dep.split()[0]]={"requires":[],"requiredby":[pkg+' [REC]'],"rprovides":[],"description":'',"version":'',"arch":'',"ipk":'',"recipe":[],"license":'',"section":'',"author":'',"rp_maintainer":'',"install_file":[]}
            else:
                if output.get(dep):
                    output[dep]["requiredby"].append(pkg)
                else:
                    output[dep]={"requires":[],"requiredby":[pkg],"rprovides":[],"description":'',"version":'',"arch":'',"ipk":'',"recipe":[],"license":'',"section":'',"author":'',"rp_maintainer":'',"install_file":[]}

    file=open(os.path.join(d.getVar('IMGDEPLOYDIR'),'{}-dependency.json'.format(d.getVar('IMAGE_BASENAME'))),'w')
    file.write(json.dumps(dict(sorted(output.items())), indent='\t'))
    file.close()
}

ROOTFS_POSTPROCESS_COMMAND += "create_pkg_dependency_data ;"
