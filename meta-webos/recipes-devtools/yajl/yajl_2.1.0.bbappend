# Copyright (c) 2016 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

EXTRA_OECMAKE_append = " -DCMAKE_AR:FILEPATH=${AR}"

BBCLASSEXTEND += "native"
