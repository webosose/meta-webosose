# Fluent Bit - Yocto / Bitbake
# ============================
# The following Bitbake package the latest Fluent Bit stable release.

SUMMARY = "Fast Log processor and Forwarder"
DESCRIPTION = "Fluent Bit is a data collector, processor and  \
forwarder for Linux. It supports several input sources and \
backends (destinations) for your data. \
"

HOMEPAGE = "http://fluentbit.io"
BUGTRACKER = "https://github.com/fluent/fluent-bit/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"
SECTION = "net"

PR = "r1"
PV = "1.7.4"

SRCREV = "d638a11725ecdaf683be6167709c4712559cde79"
SRC_URI = "git://github.com/fluent/fluent-bit.git;branch=1.7;protocol=https \
    file://0001-mbedtls-Fix-build-failure-on-gcc-11.patch \
    file://0002-mbedtls-Fix-GCC-warning-in-ssl_calc_finished_tls_sha.patch \
"

S = "${WORKDIR}/git"
DEPENDS = "zlib bison-native flex-native"
INSANE_SKIP:${PN}-dev += "dev-elf"

# Use CMake 'Unix Makefiles' generator
OECMAKE_GENERATOR ?= "Unix Makefiles"

# Fluent Bit build options
# ========================

# Host related setup
EXTRA_OECMAKE += "-DGNU_HOST=${HOST_SYS} "

# Disable LuaJIT and filter_lua support
EXTRA_OECMAKE += "-DFLB_LUAJIT=Off -DFLB_FILTER_LUA=Off "

# Disable Library and examples
EXTRA_OECMAKE += "-DFLB_SHARED_LIB=Off -DFLB_EXAMPLES=Off "

# Systemd support (optional)
DEPENDS += "systemd"
EXTRA_OECMAKE += "-DFLB_IN_SYSTEMD=On "

# Kafka Output plugin (disabled by default): note that when
# enabling Kafka output plugin, the backend library librdkafka
# requires 'openssl' as a dependency.
#
# DEPENDS += "openssl "
# EXTRA_OECMAKE += "-DFLB_OUT_KAFKA=On "

inherit cmake systemd

SYSTEMD_SERVICE:${PN} = "fluent-bit.service"
TARGET_CC_ARCH:append = " ${SELECTED_OPTIMIZATION}"

# bundled monkey in fluentbit/1.7.4/lib/monkey
# fails to build without fcommon as shown in:
# http://gecko.lge.com:8000/Errors/Details/822527
# https://bugs.gentoo.org/707642
CFLAGS += "-fcommon"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894430
# ERROR: QA Issue: File /usr/src/debug/fluentbit/1.7.4/src/stream_processor/parser/sql_lex.c in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/stream_processor/parser/sql_lex.h in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/stream_processor/parser/sql_parser.c in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/stream_processor/parser/sql_parser.h in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/record_accessor/ra_parser.h in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/record_accessor/ra_parser.c in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/record_accessor/ra_lex.c in package fluentbit-src contains reference to TMPDIR
# File /usr/src/debug/fluentbit/1.7.4/src/record_accessor/ra_lex.h in package fluentbit-src contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/bin/fluent-bit in package fluentbit contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/bin/.debug/fluent-bit in package fluentbit-dbg contains reference to TMPDIR
# File /usr/lib/fluent-bit/.debug/libfluent-bit.so in package fluentbit-dbg contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/include/fluent-bit/flb_info.h in package fluentbit-dev contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
