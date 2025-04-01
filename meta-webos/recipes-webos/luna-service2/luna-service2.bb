# Copyright (c) 2012-2025 LG Electronics, Inc.

SUMMARY = "webOS Luna System Bus library, daemon, and utilities"
AUTHOR = "Yogish S <yogish.s@lge.com>"
SECTION = "webos/base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

DEPENDS = "libpbnjson pmloglib glib-2.0 gtest systemd"
VIRTUAL-RUNTIME_cpushareholder ?= "cpushareholder-stub"
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS:${PN} = "luna-service2-security-conf ${VIRTUAL-RUNTIME_cpushareholder} ${VIRTUAL-RUNTIME_bash}"

WEBOS_VERSION = "3.21.2-44_f116a5088f4d6918c50ad90eb7f8b0aa3decd12d"
PR = "r36"

EXTRA_OECMAKE += "${@ '-DWEBOS_DISTRO_PRERELEASE:STRING="devel"' \
                  if d.getVar('WEBOS_DISTRO_PRERELEASE') != '' else ''}"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_library
inherit webos_daemon
inherit webos_program
inherit webos_system_bus
inherit webos_core_os_dep
inherit webos_machine_impl_dep
inherit webos_prerelease_dep
inherit webos_lttng
inherit webos_test_provider

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "ls-hubd.service"

PACKAGECONFIG ?= ""
# Disable LTTng tracepoints explicitly.
# LTTng tracepoints in LS2 can cause out of memory, because LS2 is used by many components.
# To enable tracepoints back use WEBOS_LTTNG_ENABLED:pn-luna-service2 = "1"
WEBOS_LTTNG_ENABLED = "0"
EXTRA_OECMAKE += " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"

WEBOS_DISABLE_LS2_SECURITY ?= "0"
EXTRA_OECMAKE += '${@oe.utils.conditional("WEBOS_DISABLE_LS2_SECURITY", "1", "-DWEBOS_LS2_SECURE:BOOLEAN=False", "" ,d)}'

PACKAGES += "${PN}-perf"

FILES:${PN}-perf += "${webos_testsdir}/${BPN}-perf"

# Disable QA checks which are now triggered after moving the webos_testsdir from /opt to /usr/opt
# luna-service2-ptest: found library in wrong location: /usr/opt/webos/tests/luna-service2/lib/libls-hublib-test.so
INSANE_SKIP:${PN}-ptest += "libdir"
# luna-service2-dbg: found library in wrong location: /usr/opt/webos/tests/luna-service2/lib/.debug/libls-hublib-test.so
INSANE_SKIP:${PN}-dbg += "libdir"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894434
# ERROR: QA Issue: File /usr/opt/webos/tests/luna-service2/integration/test_monitor in package luna-service2-ptest contains reference to TMPDIR [buildpaths]
# ERROR: QA Issue: File /usr/opt/webos/tests/luna-service2/integration/.debug/luna-send-q in package luna-service2-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"

# http://gecko.lge.com:8000/Errors/Details/984690
# luna-service2/3.21.2-44/git/src/libluna-service2/transport_message.c:1249:12: error: implicit declaration of function '_LSTransportSendQueryPid'; did you mean '_LSTransportSendReply'? [-Wimplicit-function-declaration]
CFLAGS += "-Wno-error=implicit-function-declaration"
