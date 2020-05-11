# Copyright (c) 2020 LG Electronics, Inc.
#
# webos_oss_pkg_info
#
# This class adds populate_oss_pkg_info and write_oss_pkg_info task,
# write_oss_pkg_info can be run by bitbake --runall option to get
# constructed oss-pkg-info.yaml files of each components

OSS_DEPLOY_DIR ?= "${DEPLOY_DIR}/oss-pkg-info/"
OSS_DESTDIR    ?= "${WORKDIR}/osspkginfo-destdir/"
OSS_FILENAME   ?= "oss-pkg-info.yaml"

addtask populate_oss_pkg_info after do_patch before do_preconfigure do_configure do_build
do_populate_oss_pkg_info[doc] = "Writes oss package information for the recipe that is collected later when the image is constructed"
do_populate_oss_pkg_info[dirs] = "${OSS_DESTDIR}/${PN}"
do_populate_oss_pkg_info[cleandirs] = "${OSS_DESTDIR}"
do_populate_oss_pkg_info[vardeps] = "${OSS_DEPLOY_DIR} ${OSS_DESTDIR} ${OSS_FILENAME}"

# Copy oss package information file into deploy path
do_populate_oss_pkg_info () {
    if [ -f "${S}/${OSS_FILENAME}" ] ; then
        install -m 644 ${S}/${OSS_FILENAME} ${OSS_DESTDIR}/${PN}/
    fi
}

SSTATETASKS += "do_populate_oss_pkg_info"
do_populate_oss_pkg_info[sstate-inputdirs] = "${OSS_DESTDIR}"
do_populate_oss_pkg_info[sstate-outputdirs] = "${OSS_DEPLOY_DIR}"

python do_populate_oss_pkg_info_setscene () {
    sstate_setscene(d)
}
addtask do_populate_oss_pkg_info_setscene

addtask write_oss_pkg_info after do_populate_oss_pkg_info
do_write_oss_pkg_info[doc] = "Constructs oss package information of the recipe"
do_write_oss_pkg_info[nostamp] = "1"

python do_write_oss_pkg_info() {
    pn           = d.getVar("PN")
    oss_filename = d.getVar("OSS_FILENAME")
    oss_path     = os.path.join(os.path.join(d.getVar("OSS_DEPLOY_DIR"), pn), oss_filename)

    if os.path.isfile(oss_path):
        with open(oss_path) as src:
            datafile = os.path.join(d.getVar("TOPDIR", True), oss_filename)
            lock = bb.utils.lockfile(datafile + '.lock')
            with open(datafile, "a") as dest:
                dest.write('%s:\n' % pn)
                for l in src.readlines():
                    dest.write(l)
            bb.utils.unlockfile(lock)
}
