# Copyright (c) 2019-2024 LG Electronics, Inc.

EXTENDPRAUTO = "webos1"

# enable cblas
EXTRA_OECMAKE += "-DCBLAS=ON"

# fix configure error during FortranCInterface detect
OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/893035
# File /usr/lib/lapack/ptest/bin/xlintsts in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstc in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstrfc in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat3z in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xeigtstd in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat1d in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat1s in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstz in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xdcblat2 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstd in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat2c in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat3d in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xdcblat3 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xzcblat1 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xzcblat2 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstrfd in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat3s in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstrfz in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat2s in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat2z in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xeigtsts in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xccblat2 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xeigtstc in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xzcblat3 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstzc in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xeigtstz in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstrfs in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat3c in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xlintstds in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xccblat1 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xscblat3 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat2d in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat1c in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xblat1z in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xdcblat1 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xscblat1 in package lapack-ptest contains reference to TMPDIR
# File /usr/lib/lapack/ptest/bin/xccblat3 in package lapack-ptest contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
