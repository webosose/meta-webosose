# Copyright (c) 2018-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN}:append:class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS:${PN}:remove:class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# Replace runtime dependency on bash with ${VIRTUAL-RUNTIME_bash}
PACKAGECONFIG[vm] = ",,libcap,libgcc ${VIRTUAL-RUNTIME_bash}"

# cannot use bpf for kernels older than 4.10
# WARNING: lib32-kernel-selftest-1.0-r0 do_compile: clang >= 6.0  with bpf support is needed with kernel 4.18+ so either install it and add it to HOSTTOOLS, or add clang-native from meta-clang to dependency
PACKAGECONFIG:remove = "bpf"

# http://caprica.lgsvl.com:8080/Errors/Details/1534060
# userfaultfd.c:126:2: error: format not a string literal and no format arguments [-Werror=format-security]
#  fprintf(stderr, examples);
#  ^~~~~~~
SECURITY_STRINGFORMAT = ""

# http://caprica.lgsvl.com:8080/Errors/Details/1534060
# kernel-selftest/1.0-r0/recipe-sysroot/usr/include/bits/fcntl2.h:50:4: error: call to '__open_missing_mode' declared with attribute error: open with O_CREAT or O_TMPFILE in second argument needs 3 arguments
#     __open_missing_mode ();
#     ^~~~~~~~~~~~~~~~~~~~~~
lcl_maybe_fortify = ""
