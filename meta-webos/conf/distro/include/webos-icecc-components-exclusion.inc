# Copyright (c) 2014-2023 LG Electronics, Inc.

# These packages fail to build when icecc is used

ICECC_RECIPE_DISABLE:append = " \
    busybox \
    gmp-native \
    linux-libc-headers \
    llvm3.3 \
    nss \
"

ICECC_RECIPE_DISABLE:append:arm = " \
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
