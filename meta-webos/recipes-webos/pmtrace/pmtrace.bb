# Copyright (c) 2013-2025 LG Electronics, Inc.

SUMMARY = "lttng-ust tracepoints wrapper library and performance tools"
AUTHOR = "Sukil Hong <sukil.hong@lge.com>"
SECTION = "webos/libs"

LICENSE = "LGPL-2.1-only & MIT & Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL;md5=e2aa4f66375a24019b0ff5e99cec40ad \
    file://LICENSE.MIT;md5=19b5d9061141f7ab05cfcfdd4404ed08 \
    file://README.md;md5=35108c1521572d2a526926333b233cd7 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "libpbnjson pmloglib glib-2.0"

WEBOS_VERSION = "1.0.0-18_97c256a74ef2249929b0366bf7ac5c972894a197"
PR = "r16"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_lttng
inherit webos_public_repo
inherit webos_prerelease_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# The libmemtracker, libpmtrace, pmctl (library/header/binary files) will be installed in all builds except RELEASE mode.
# Only libpmtrace header files need to install in all builds for other modules that are referring to the header files.
EXTRA_OECMAKE += "-DENABLE_LIBPMTRACE:BOOLEAN=${@'False' if ('${WEBOS_DISTRO_PRERELEASE}' == '') else 'True'}"
EXTRA_OECMAKE += "-DDEFAULT_LOGGING:STRING=${@'' if ('${WEBOS_DISTRO_PRERELEASE}' == '') else 'pmlog'}"
