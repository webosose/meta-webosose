# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

do_configure_prepend() {
    # work around for broken CUDA cmake support
    sed -i 's/^find_package(CUDA/# find_package(CUDA/g' ${S}/unsupported/test/CMakeLists.txt
}
