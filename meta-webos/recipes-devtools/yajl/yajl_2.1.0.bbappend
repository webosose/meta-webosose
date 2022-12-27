# Copyright (c) 2016-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

EXTRA_OECMAKE:append = " -DCMAKE_AR:FILEPATH=${AR}"

BBCLASSEXTEND += "native"
