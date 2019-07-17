# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosupdater1"

# Workaround to resolve 'do_install' failure when using 'usrmerge' feature
# because of do_install_append's wrong work on oe-core's systemtap_git.bb
systemd_unitdir_sota = "${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/tmp/systemd', '${nonarch_base_libdir}/systemd', d)}"
do_install_append_sota() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${systemd_unitdir} ${D}${nonarch_base_libdir}/systemd
        rmdir `dirname ${D}${systemd_unitdir}` --ignore-fail-on-non-empty
    fi
}
