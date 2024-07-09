# Copyright (c) 2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Make sure to have 'duktape' in PACKAGECONFIG even for a newer recipe that introduces it in PACKAGECONFIG
PACKAGECONFIG:append = " ${@'duktape' if 'duktape' in d.getVarFlags('PACKAGECONFIG') else ''}"
