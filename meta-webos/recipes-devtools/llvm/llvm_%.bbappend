# Copyright (c) 2014-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

PACKAGECONFIG[libedit] = "-DLLVM_ENABLE_LIBEDIT=ON,-DLLVM_ENABLE_LIBEDIT=OFF,libedit"
