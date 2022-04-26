# Copyright (c) 2014-2022 LG Electronics, Inc.

# These packages fail to build when icecc is used

ICECC_USER_PACKAGE_BL:append = " \
    busybox \
    gmp-native \
    linux-libc-headers \
    llvm3.3 \
    nss \
"

ICECC_USER_PACKAGE_BL:append:arm = " \
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
