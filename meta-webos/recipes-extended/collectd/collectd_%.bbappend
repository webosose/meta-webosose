# Copyright (c) 2018-2023 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Fix QA Issue: collectd rdepends on protobuf-c, but it isn't a build dependency, missing protobuf-c in DEPENDS or PACKAGECONFIG? [build-deps]
PACKAGECONFIG[write_riemann] = "--enable-write_riemann,--disable-write_riemann,protobuf-c-native protobuf-c"
