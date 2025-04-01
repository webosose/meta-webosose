# Copyright (c) 2020-2025 LG Electronics, Inc.
#
# webos_oss_pkg_info
#
# This class adds write_oss_pkg_info task.
# write_oss_pkg_info can be run by bitbake -c write_oss_pkg_info <image>
# collected oss-pkg-info.yaml files of image

# This is
# meta/classes-global/license.bbclass:LICENSE_DIRECTORY ??= "${DEPLOY_DIR}/licenses"
OSS_DEPLOY_DIR ?= "${DEPLOY_DIR}/licenses"
OSS_FILENAME   ?= "oss-pkg-info.yaml"

# We need license.manifest of image
addtask write_oss_pkg_info after do_rootfs before do_build
do_write_oss_pkg_info[doc] = "Collects oss package information of the image"
do_write_oss_pkg_info[nostamp] = "1"

python do_write_oss_pkg_info() {
    imagename    = d.getVar("IMAGE_NAME")
    oss_filename = d.getVar("OSS_FILENAME")
    manifest     = oe.path.join(d.getVar("OSS_DEPLOY_DIR"), imagename, "license.manifest")
    default_oss  = oe.path.join(d.getVar("TOPDIR"), 'build-templates', "%s-%s" % (imagename, oss_filename))
    target_oss   = oe.path.join(d.getVar("DEPLOY_DIR_IMAGE"), "%s-%s" % (d.getVar("IMAGE_BASENAME"), oss_filename))

    if os.path.isfile(manifest):
        with open(target_oss, "w") as output:
            """ Paste the default oss-pkg-input.yaml contents at the front of final oss file """
            if os.path.isfile(default_oss):
                with open(default_oss) as input:
                    oss_string = input.read()
                    output.write(oss_string)

            """ Extract recipe names from license manifest """
            tmp = []
            with open(manifest) as input:
                for line in input:
                    line = line.rstrip()
                    if line.startswith("RECIPE NAME: "):
                        tmp.append(line.split(":")[1].strip())

            """ Remove duplicates """
            pkgs = list(dict.fromkeys(tmp))

            for pkg in pkgs:
                oss = oe.path.join(d.getVar("OSS_DEPLOY_DIR"), pkg, oss_filename)
                if os.path.isfile(oss):
                    with open(oss) as input:
                        output.write('%s:\n' % pkg)
                        oss_string = input.read()
                        if oss_string.startswith("Open Source Software Package:"):
                            output.write("\n".join(oss_string.split("\n")[1:]))
                            """ if the oss-pkg-file.yaml doesn't have 0x0a(EOF), we should handle it """
                            if not oss_string.endswith("\n"):
                                output.write("\n")
                        else:
                            bb.warn("There is no OSS item in the yaml file. :%s" % pkg)
                            output.write(oss_string)
    else:
        bb.fatal("There is no %s" % manifest)
}
