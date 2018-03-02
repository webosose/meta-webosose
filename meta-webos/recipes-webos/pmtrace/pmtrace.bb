# Copyright (c) 2013-2018 LG Electronics, Inc.

SUMMARY = "lttng-ust tracepoints wrapper library and performance tools"
AUTHOR = "Andre Rosa <andre.rosa@lge.com>"
SECTION = "webos/libs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "lttng-ust libpbnjson pmloglib glib-2.0"

RDEPENDS_${PN} += " \
    babeltrace \
    lttng-tools \
    lttng-modules \
    python-argparse \
    python-compression \
    python-json \
    python-logging \
    python-multiprocessing \
    python-subprocess \
"

WEBOS_VERSION = "1.0.0-1_310573aee32c3bee62a27122d2fc4cf66d3b70e6"
PR = "r8"

inherit webos_component
inherit webos_cmake
inherit webos_enhanced_submissions
inherit webos_library
inherit webos_lttng
inherit webos_public_repo

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# The libmemtracker, libpmtrace, pmctl (library/header/binary files) will be installed in all builds except RELEASE mode.
# Only libpmtrace header files need to install in all builds for other modules that are referring to the header files.
EXTRA_OECMAKE += "-DENABLE_LIBPMTRACE:BOOLEAN=${@'False' if ('${WEBOS_DISTRO_PRERELEASE}' == '') else 'True'}"
EXTRA_OECMAKE += "-DDEFAULT_LOGGING:STRING=${@'' if ('${WEBOS_DISTRO_PRERELEASE}' == '') else 'pmlog'}"
