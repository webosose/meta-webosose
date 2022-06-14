# Copyright (c) 2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Remove an executable flag in GNU_STACK
CFLAGS += "-Wa,--noexecstack"
