# Copyright (c) 2014-2023 LG Electronics, Inc.
#
# webos_nyx_module_provider

# This class is to be inherited by the recipe for every component that provides
# Nyx modules
#

# If no modules from this provider are needed for an image, then it might
# produce an empty package
ALLOW_EMPTY:${PN} = "1"

# Pass in the list of required modules
#
# CMake requires that a list contain no embedded whitespace, so we clean
# it up first.

def webos_clean_nyxrequired(s):
    return  ";".join(filter(None,map(str.strip,s.split(';'))))

NYX_MODULES_REQUIRED ??= "\
    NYXMOD_OW_OSINFO;\
    NYXMOD_OW_DEVICEINFO;\
    NYXMOD_OW_SYSTEM;\
    NYXMOD_OW_DISPLAY;\
    NYXMOD_QEMU_BATTERY;\
    NYXMOD_OW_SECURITY;\
    NYXMOD_OW_SECURITY2;\
    NYXMOD_QEMU_CHARGER;\
    NYXMOD_QEMU_KEYS;\
    NYXMOD_QEMU_TOUCHPANEL;\
    NYXMOD_OW_GPS;\
"

CLEAN_MODULE_LIST = "${@ webos_clean_nyxrequired('${NYX_MODULES_REQUIRED}')}"
EXTRA_OECMAKE += "-DNYX_MODULES_REQUIRED:STRING='${CLEAN_MODULE_LIST}'"

# Pass in the build's timestamp so it is available for any implementors
# of an OSInfo module.
EXTRA_OECMAKE += "-DWEBOS_BUILD_DATETIME:STRING='${DATETIME}'"
EXTRA_OECMAKE[vardepsexclude] += "DATETIME"

# Ensure any nyx-module provider cites a dependency on nyx-lib
DEPENDS += "nyx-lib"

# Add any built modules that are installed
FILES:${PN} += "${libdir}/nyx/modules/*"
