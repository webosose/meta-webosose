# Copyright (c) 2023 LG Electronics, Inc.

PR = "r0"

require chromium-stdlib.inc

require webruntime-repo_94.inc

do_install:append() {
    cp -R --no-dereference --preserve=mode,links -v ${S}/src/buildtools/third_party/libc++/__config_site ${D}${includedir}/c++/v1
}
