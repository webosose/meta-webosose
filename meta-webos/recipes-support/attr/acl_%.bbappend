# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

RDEPENDS_${PN}-ptest_append_class-target = " ${VIRTUAL-RUNTIME_bash} perl"
RDEPENDS_${PN}-ptest_remove = "bash"
