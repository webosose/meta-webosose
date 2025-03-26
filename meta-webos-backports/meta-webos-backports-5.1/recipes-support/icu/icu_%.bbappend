# https://git.openembedded.org/openembedded-core/commit/?h=styhead&id=dc6306883cc2c7d4d98d595442e5bf4037a160c5
do_compile:prepend:class-nativesdk() {
    # Make sure certain build host references do not end up being compiled
    # in the image. This only affects libicutu and icu-dbg
    sed  \
        -e 's,DU_BUILD=,DU_BUILD_unused=,g' \
        -e '/^CPPFLAGS.*/ s,--sysroot=${STAGING_DIR_TARGET},,g' \
        -i ${B}/tools/toolutil/Makefile
}
do_install:append:class-nativesdk() {
    sed -i  \
        -e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
        -e 's|${DEBUG_PREFIX_MAP}||g' \
        -e 's:${HOSTTOOLS_DIR}/::g' \
        ${D}/${libdir}/${BPN}/${@icu_install_folder(d)}/Makefile.inc \
        ${D}/${libdir}/${BPN}/${@icu_install_folder(d)}/pkgdata.inc
}
