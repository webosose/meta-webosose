# Copyright (c) 2025 LG Electronics, Inc.

# https://lists.openembedded.org/g/openembedded-devel/message/115007
PACKAGECONFIG[cblas] = "-DCBLAS=ON,-DCBLAS=OFF"

# https://lists.openembedded.org/g/openembedded-devel/message/114844 (https://git.openembedded.org/meta-openembedded/commit/?id=b617496fb08950c155e75c8f21bafb10e301095c)
# https://lists.openembedded.org/g/openembedded-devel/message/115008

# The `xerbla.o` file contains an absolute path in `xerbla.f.o`, but the options
# `-fdebug-prefix-map` and `-ffile-prefix-map` cannot be used because gfortran does
# not support them. And we cannot easily change CMake to use relative paths, because
# it will convert them to absolute paths when generating Unix Makefiles or Ninja:
# https://gitlab.kitware.com/cmake/community/-/wikis/FAQ#why-does-cmake-use-full-paths-or-can-i-copy-my-build-tree
# https://gitlab.kitware.com/cmake/cmake/-/issues/13894
#
# To address this issue, we manually replace the absolute path with a relative path
# in the generated `build.make` file.
#
# An issue has been reported: https://github.com/Reference-LAPACK/lapack/issues/1087,
# requesting a fix in the source code.
#
# This workaround resolves the TMPDIR [buildpaths] issue by converting the absolute path
# of `xerbla.f` to a relative path. The steps are as follows:
#
# 1. Locate all `build.make` files after the `do_configure` step is completed.
# 2. Compute the relative path for various `*.f` files based on the current build directory.
# 3. Replace the absolute path with the calculated relative path in the `build.make` files
#
# Additionally, when ptests are enabled, apply a simpler workaround for ptest code:
# - Replace occurrences of `${WORKDIR}` in all `build.make` files under the TESTING directory, excluding
#   the MATGEN subdirectory, with a relative path prefix of `"../../.."`.
do_configure:append(){
    for file in `find ${B} -name build.make`; do
        # Replacing all .f files found with:
        # for f in $(find ${S} -name \*.f -printf " %f" | sort -u); do
        # would be more reliable with other optional PACKAGECONFIGs, but also very slow as there are
        # ~ 3500 of them and this loop takes around 20 minutes
        for f in xerbla c_cblat1 c_cblat2 c_cblat3 c_dblat1 c_dblat2 c_dblat3 c_sblat1 c_sblat2 c_sblat3 c_zblat1 c_zblat2 c_zblat3; do
            sed -i -e "s#\(.*-c \).*\(/$f\.f \)#\1$(grep "\-c .*$f\.f" $file | awk -F'cd ' '{print $2}'| \
                awk "{src=\$1; sub(/.*-c /, \"\"); sub(/$f\.f.*/, \"\"); obj=\$0; print src, obj}" | \
                while read src obj; do echo "$(realpath --relative-to="$src" "$obj")"; done)\2#g" $file
         done
    done
    if ${@bb.utils.contains('PTEST_ENABLED', '1', 'true', 'false', d)} ; then
        for file in `find . -name build.make -path '*TESTING*' -not -path '*MATGEN*'`; do
            sed -i -e "s#\(.*-c \)\(${WORKDIR}\)\(.*.[f|F] \)#\1../../..\3#g" $file
        done
    fi
}
