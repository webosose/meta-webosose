# https://git.openembedded.org/openembedded-core/commit/?h=styhead&id=f08df9adf290fb6cbebff24df6bbbbe8e5ce95e0
do_install:append() {
    sed -e 's@--sysroot=${STAGING_DIR_HOST}@@g' \
        -e "s@${DEBUG_PREFIX_MAP}@@g" \
        -e 's@${STAGING_DIR_HOST}@@g' \
        -e 's@${STAGING_DIR_NATIVE}@@g' \
        -e 's@^\(sys_lib_search_path_spec="\).*@\1${libdir} ${base_libdir}"@' \
        -e 's@^\(compiler_lib_search_dirs="\).*@\1${libdir} ${base_libdir}"@' \
        -e 's@^\(compiler_lib_search_path="\).*@\1${libdir} ${base_libdir}"@' \
        -e 's@^\(predep_objects="\).*@\1"@' \
        -e 's@^\(postdep_objects="\).*@\1"@' \
        -e "s@${HOSTTOOLS_DIR}/@@g" \
        -i ${D}${bindir}/libtool
}
