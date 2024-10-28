# Copyright (c) 2024 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Add missing library dependency to the prebuilt libduktape.so.207.20700
DEPENDS += "patchelf-native"
do_install:append() {
    patchelf --add-needed libm.so.6 ${D}${libdir}/libduktape.so.207.20700
}
