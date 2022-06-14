# Copyright (c) 2017-2020 LG Electronics, Inc.

# workaround for the upstream branch name change from master to main.
# inherit line below can be removed when the request is applied upstream.
# : https://lists.openembedded.org/g/openembedded-devel/message/97510
inherit fix_branch_name

EXTENDPRAUTO:append = "webos1"

PACKAGECONFIG:append = " snappy"

# Needed for db8-native
BBCLASSEXTEND = "native"
