# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

do_compile() {
    # LG next line is the only change here, we need to export NSPR_INCLUDE_DIR after do_compile_prepend_class-native
    # from upstream recipe, that's why we cannot use another do_compile_prepend in .bbappend
    export NSPR_INCLUDE_DIR=${STAGING_INCDIR}/nspr

    export CROSS_COMPILE=1
    export NATIVE_CC="${BUILD_CC}"
    export NATIVE_FLAGS="${BUILD_CFLAGS}"
    export BUILD_OPT=1

    export FREEBL_NO_DEPEND=1
    export FREEBL_LOWHASH=1

    export LIBDIR=${libdir}
    export MOZILLA_CLIENT=1
    export NS_USE_GCC=1
    export NSS_USE_SYSTEM_SQLITE=1
    export NSS_ENABLE_ECC=1

    export OS_RELEASE=3.4
    export OS_TARGET=Linux
    export OS_ARCH=Linux

    if [ "${TARGET_ARCH}" = "powerpc" ]; then
        OS_TEST=ppc
    elif [ "${TARGET_ARCH}" = "powerpc64" ]; then
        OS_TEST=ppc64
    elif [ "${TARGET_ARCH}" = "mips" -o "${TARGET_ARCH}" = "mipsel" -o "${TARGET_ARCH}" = "mips64" -o "${TARGET_ARCH}" = "mips64el" ]; then
        OS_TEST=mips
    elif [ "${TARGET_ARCH}" = "aarch64_be" ]; then
        OS_TEST="aarch64"
    else
        OS_TEST="${TARGET_ARCH}"
    fi

    if [ "${SITEINFO_BITS}" = "64" ]; then
        export USE_64=1
    elif [ "${TARGET_ARCH}" = "x86_64" -a "${SITEINFO_BITS}" = "32" ]; then
        export USE_X32=1
    fi

    export NSS_DISABLE_GTESTS=1

    # We can modify CC in the environment, but if we set it via an
    # argument to make, nsinstall, a host program, will also build with it!
    #
    # nss pretty much does its own thing with CFLAGS, so we put them into CC.
    # Optimization will get clobbered, but most of the stuff will survive.
    # The motivation for this is to point to the correct place for debug
    # source files and CFLAGS does that.  Nothing uses CCC.
    #
    export CC="${CC} ${CFLAGS}"
    make -C ./nss CCC="${CXX} -g" \
        OS_TEST=${OS_TEST} \
        RPATH="${RPATH}"
}
