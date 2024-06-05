# Copyright (c) 2012-2024 LG Electronics, Inc.

SUMMARY = "webOS preferences manager"
AUTHOR = "Rajesh Gopu I.V <rajeshgopu.iv@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "luna-service2 json-c sqlite3 glib-2.0 nyx-lib"
RDEPENDS:${PN} = "luna-prefs-data"

WEBOS_VERSION = "3.0.0-17_2162ebe4a03df228d60b5f517a8c05beacc9cc11"
PR = "r17"

inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_program
inherit webos_library
inherit webos_system_bus

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# http://gecko.lge.com:8000/Errors/Details/821703
# luna-prefs/3.0.0-17/git/luna-prefs-service/accesschecker.c:39:39: error: initialization of 'LSMessage *' from incompatible pointer type 'LSMessage **' [-Wincompatible-pointer-types]
# luna-prefs/3.0.0-17/git/luna-prefs-service/accesschecker.c:85:24: error: assignment to 'LSMessage **' from incompatible pointer type 'LSMessage *' [-Wincompatible-pointer-types]
# luna-prefs/3.0.0-17/git/luna-prefs-service/main.c:487:48: error: passing argument 4 of 'checkAccess' from incompatible pointer type [-Wincompatible-pointer-types]
# luna-prefs/3.0.0-17/git/luna-prefs-service/main.c:766:48: error: passing argument 4 of 'checkAccess' from incompatible pointer type [-Wincompatible-pointer-types]
# luna-prefs/3.0.0-17/git/luna-prefs-service/main.c:959:48: error: passing argument 4 of 'checkAccess' from incompatible pointer type [-Wincompatible-pointer-types]
# luna-prefs/3.0.0-17/git/luna-prefs-service/main.c:1072:49: error: passing argument 4 of 'checkAccess' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types"
