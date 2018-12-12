# Copyright (c) 2014-2019 LG Electronics, Inc.

# These packages fail to build when icecc is used

ICECC_USER_PACKAGE_BL_append = " \
    busybox \
    webruntime \
    gmp-native \
    linux-libc-headers \
    llvm3.3 \
    nss \
    wam \
"

ICECC_USER_PACKAGE_BL_append_arm = " \
    binutils \
    e2fsprogs \
    gcc \
    gmp \
    libcap \
    libgpg-error \
    ncurses \
    perl \
    nettle \
"
