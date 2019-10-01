# Copyright (c) 2013-2019 LG Electronics, Inc.

SUMMARY = "Bootd single-shot launching service"
DESCRIPTION = "Bootd is a simplified upstart-like component. It provides automatic single-shot launching at boot time"
AUTHOR = "Sangwoo Kang <sangwoo82.kang@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "luna-service2 libpbnjson pmloglib glib-2.0 boost gtest pmtrace"

WEBOS_VERSION = "2.0.0-7_96c6a269a1c34aae0a0a1775893248acb93c4b1e"
PR = "r10"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_lttng
inherit webos_machine_dep
inherit webos_distro_dep
inherit webos_distro_variant_dep
inherit webos_public_repo
inherit webos_prerelease_dep

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# LTTNG option
# WEBOS_LTTNG_ENABLED = "0"
EXTRA_OECMAKE += " ${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', '-DWEBOS_LTTNG_ENABLED:BOOLEAN=True', '', d)}"
EXTRA_OECMAKE += "-DWEBOS_DISTRO_PRERELEASE:STRING='${WEBOS_DISTRO_PRERELEASE}'"

# gtest option
PACKAGES =+ "${PN}-tests"
FILES_${PN}-tests = "${libexecdir}/tests/*"

# http://caprica.lgsvl.com:8080/Errors/Details/1092091
# bootd/2.0.0-142-r10/git/src/bootd/util/Logger.cpp:210:40: error: format not a string literal and no format arguments [-Werror=format-security]
#          PmLogInfo(m_context, msgid, 0, m_msgBuffer);
#                                         ^~~~~~~~~~~
# 2.0.0-142-r10/git/src/bootd/util/Logger.cpp:231:31: error: format not a string literal and no format arguments [-Werror=format-security]
#          PmLogDebug(m_context, m_msgBuffer);
#                                ^~~~~~~~~~~
# 2.0.0-142-r10/git/src/bootd/util/Logger.cpp:252:40: error: format not a string literal and no format arguments [-Werror=format-security]
#          PmLogInfo(m_context, msgid, 0, m_msgBuffer);
#                                         ^~~~~~~~~~~
# 2.0.0-142-r10/git/src/bootd/util/Logger.cpp:273:43: error: format not a string literal and no format arguments [-Werror=format-security]
#          PmLogWarning(m_context, msgid, 0, m_msgBuffer);
#                                            ^~~~~~~~~~~
# 2.0.0-142-r10/git/src/bootd/util/Logger.cpp:294:41: error: format not a string literal and no format arguments [-Werror=format-security]
#          PmLogError(m_context, msgid, 0, m_msgBuffer);
#                                          ^~~~~~~~~~~
SECURITY_STRINGFORMAT = ""
