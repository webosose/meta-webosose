# Copyright (c) 2018-2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
RDEPENDS_${PN}_remove_class-target = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'bash', '', d)}"

# Replace runtime dependency on bash with ${VIRTUAL-RUNTIME_bash}
PACKAGECONFIG[vm] = ",,libcap,libgcc ${VIRTUAL-RUNTIME_bash}"

# cannot use bpf for kernels older than 4.10
# WARNING: lib32-kernel-selftest-1.0-r0 do_compile: clang >= 6.0  with bpf support is needed with kernel 4.18+ so either install it and add it to HOSTTOOLS, or add clang-native from meta-clang to dependency
PACKAGECONFIG_remove = "bpf"
