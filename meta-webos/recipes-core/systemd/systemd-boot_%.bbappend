# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Undo
# meta-webos/conf/distro/include/webos.inc:SECURITY_STACK_PROTECTOR = "-fstack-protector-all"
# and use the default:
# meta/conf/distro/include/security_flags.inc:SECURITY_STACK_PROTECTOR ?= "-fstack-protector-strong"
# to avoid:
# http://gecko.lge.com:8000/Errors/Details/922859
# Checking if "linker supports -static-pie" : links: NO
# git/src/boot/efi/meson.build:231:8: ERROR: Problem encountered: Linker does not support -static-pie.
SECURITY_STACK_PROTECTOR = "-fstack-protector-strong"
