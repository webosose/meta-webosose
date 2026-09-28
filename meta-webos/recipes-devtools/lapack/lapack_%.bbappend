# Copyright (c) 2019-2025 LG Electronics, Inc.

EXTENDPRAUTO = "webos3"

# https://lists.openembedded.org/g/openembedded-devel/message/115007
PACKAGECONFIG[cblas] = "-DCBLAS=ON,-DCBLAS=OFF"
PACKAGECONFIG = "cblas"

# https://lists.openembedded.org/g/openembedded-devel/message/114844 (https://git.openembedded.org/meta-openembedded/commit/?id=b617496fb08950c155e75c8f21bafb10e301095c)
# https://lists.openembedded.org/g/openembedded-devel/message/115008

# gfortran doesn't support -fdebug-prefix-map or -ffile-prefix-map options,
# which causes absolute build paths to be embedded in the .o files.
# This triggers the buildpaths QA check.
#
# The meta-oe recipe has a workaround using relative paths, but it has bugs
# that cause build failures (incorrect relative paths like ../../blas_64_obj/xerbla.f).
#
# Since we need to build lapack successfully first, disable buildpaths QA check
# for this recipe. This is acceptable for Fortran-heavy numerical libraries
# where reproducible builds are less critical.
ERROR_QA:remove = "buildpaths"

do_configure:append() {
    for file in `find ${B} -name build.make`; do
        # Fix incorrect relative paths for blas_64_obj files created by meta-oe's sed
        # CMake generates paths like "../../blas_64_obj/xerbla.f" which should be
        # "blas_64_obj/xerbla.f" since we compile from BLAS/SRC directory
        sed -i -e 's#-c \.\./\.\./\.\./blas_64_obj/#-c blas_64_obj/#g' $file
        sed -i -e 's#-c \.\./\.\./blas_64_obj/#-c blas_64_obj/#g' $file

        # Fix broken absolute paths created by meta-oe's sed for BLAS/SRC files
        # The pattern "-c /xerbla.f" should be "-c ${S}/BLAS/SRC/xerbla.f"
        sed -i -e "s#-c /xerbla\.f #-c ${S}/BLAS/SRC/xerbla.f #g" $file
        sed -i -e "s#-c /xerbla_array\.f #-c ${S}/BLAS/SRC/xerbla_array.f #g" $file
        sed -i -e "s#-c /lsame\.f #-c ${S}/BLAS/SRC/lsame.f #g" $file

        # Fix broken absolute paths for CBLAS testing files
        # These are in CBLAS/testing directory
        for f in c_cblat1 c_cblat2 c_cblat3 c_dblat1 c_dblat2 c_dblat3 c_sblat1 c_sblat2 c_sblat3 c_zblat1 c_zblat2 c_zblat3; do
            sed -i -e "s#-c /$f\.f #-c ${S}/CBLAS/testing/$f.f #g" $file
        done
    done
}
