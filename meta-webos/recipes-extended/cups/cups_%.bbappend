# Copyright (c) 2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Enable stack buffer overflow protection
CFLAGS += "${SECURITY_STACK_PROTECTOR}"
