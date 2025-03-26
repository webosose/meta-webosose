# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895292
# ERROR: QA Issue: File /usr/src/debug/bpftrace/0.20.1+git/location.hh in package bpftrace-src contains reference to TMPDIR
# File /usr/src/debug/bpftrace/0.20.1+git/parser.tab.cc in package bpftrace-src contains reference to TMPDIR
# File /usr/src/debug/bpftrace/0.20.1+git/parser.tab.hh in package bpftrace-src contains reference to TMPDIR
# File /usr/src/debug/bpftrace/0.20.1+git/lex.yy.cc in package bpftrace-src contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/lib/bpftrace/ptest/tests/bpftrace_test in package bpftrace-ptest contains reference to TMPDIR
# File /usr/lib/bpftrace/ptest/tests/testprogs/CTestTestfile.cmake in package bpftrace-ptest contains reference to TMPDIR
# File /usr/lib/bpftrace/ptest/tests/testprogs/cmake_install.cmake in package bpftrace-ptest contains reference to TMPDIR
# File /usr/lib/bpftrace/ptest/tests/testlibs/CTestTestfile.cmake in package bpftrace-ptest contains reference to TMPDIR
# File /usr/lib/bpftrace/ptest/tests/testlibs/cmake_install.cmake in package bpftrace-ptest contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
