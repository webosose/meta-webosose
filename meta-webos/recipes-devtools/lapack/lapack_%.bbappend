# Copyright (c) 2019-2023 LG Electronics, Inc.

EXTENDPRAUTO = "webos1"

# enable cblas
EXTRA_OECMAKE += "-DCBLAS=ON"

# fix configure error during FortranCInterface detect
OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"
