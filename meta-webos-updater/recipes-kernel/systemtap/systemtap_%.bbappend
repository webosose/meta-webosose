# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webosupdater1"

# Workaround to resolve 'do_install' failure when using 'usrmerge' feature
# because of do_install:append's wrong work on oe-core's systemtap_git.bb
systemd_unitdir:sota = "${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/tmp/systemd', '${nonarch_base_libdir}/systemd', d)}"
do_install:append:sota() {
    if ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', 'true', 'false', d)}; then
        install -d ${D}${nonarch_base_libdir}
        mv ${D}${systemd_unitdir} ${D}${nonarch_base_libdir}/systemd
        rmdir `dirname ${D}${systemd_unitdir}` --ignore-fail-on-non-empty
    fi
}
