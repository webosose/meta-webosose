# Copyright (c) 2021 LG Electronics, Inc.

# Write package dependency data as a json file
python create_pkg_dependency_data () {
    from oe.rootfs import image_list_installed_packages
    import subprocess
    import json

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
        output[pkg].update({"version":val["ver"]})
        output[pkg].update({"arch":val["arch"]})
        output[pkg].update({"ipk":val["filename"]})
        for dep in val["deps"]:
            if '[REC]' in dep:
                if output.get(dep.split()[0]):
                    output[dep.split()[0]]["requiredby"].append(pkg+' [REC]')
                else:
                    output[dep.split()[0]]={"requires":[],"requiredby":[pkg+' [REC]'],"rprovides":[],"version":'',"arch":'',"ipk":''}
            else:
                if output.get(dep):
                    output[dep]["requiredby"].append(pkg)
                else:
                    output[dep]={"requires":[],"requiredby":[pkg],"rprovides":[],"version":'',"arch":'',"ipk":''}

    file=open(os.path.join(d.getVar('IMGDEPLOYDIR',True),'{}-dependency.json'.format(d.getVar('IMAGE_BASENAME',True))),'w')
    file.write(json.dumps(output))
    file.close()
}

ROOTFS_POSTPROCESS_COMMAND += "create_pkg_dependency_data ;"
