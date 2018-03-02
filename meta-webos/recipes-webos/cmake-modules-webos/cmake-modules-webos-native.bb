# Copyright (c) 2012-2018 LG Electronics, Inc.

DESCRIPTION = "CMake modules used by webOS"
LICENSE = "Apache-2.0"
SECTION = "webos/devel/tools"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.6.3-1_4ca5fd1f785d045bd43cccaf7ce65429698abef8"
PR = "r1"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_arch_indep
inherit webos_cmake
inherit native

WEBOS_CMAKE_DEPENDS = ""

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

do_compile() {
     :
}

# Keep in sync with classes/webos_pro_filesystem_paths.bbclass
do_install_append() {
    # The location of webOS.cmake depends on CMAKE_ROOT, which isn't accessible
    # to OE, but it's got to be somewhere under ${D} (assume there's only one):
    local webos_cmake=$(find ${D} -name webOS.cmake)

    # Insert an invocation of _webos_init_install_vars_pro() just before
    # the endmacro() of _webos_init_install_vars(). Output to a temporary file
    # so that do_install() can be re-executed.
    awk '/^macro\(_webos_init_install_vars\)/,/^endmacro\(\)/ { \
            if ($0 == "endmacro()") { \
                printf "\t_webos_init_install_vars_pro()\n"
            } \
         }; \
         { print }' $webos_cmake > ${WORKDIR}/webOS.cmake

    # Use tr to generate tabs and to prevent OE from trying to expand the
    # WEBOS_INSTALL_* just in case they're defined in OE somehow.
    tr '~@' '\t$' <<! >> ${WORKDIR}/webOS.cmake

macro(_webos_init_install_vars_pro)
~_webos_set_from_env(WEBOS_INSTALL_WEBOS_CUSTOMIZATIONDIR              webos_customizationdir                  @{WEBOS_INSTALL_PREFIX}/palm/customization)
~_webos_set_from_env(WEBOS_INSTALL_DEVELOPERDIR                        webos_developerdir                      @{WEBOS_INSTALL_MEDIADIR}/developer)
~_webos_set_from_env(WEBOS_INSTALL_EMULATORSHAREDDIR                   webos_emulatorshareddir                 @{WEBOS_INSTALL_MEDIADIR}/shared)
~_webos_set_from_env(WEBOS_INSTALL_FIRSTUSESENTINELFILE                webos_firstusesentinelfile              @{WEBOS_INSTALL_SYSMGR_LOCALSTATEDIR}/preferences/ran-firstuse)
~_webos_set_from_env(WEBOS_INSTALL_SETTINGSSERVICE_ERRORSENTINELFILE   webos_settingsservice_errorsentinelfile @{WEBOS_INSTALL_WEBOS_LOCALSTATEDIR}/settingsservice_critical_error)
endmacro()
!

    mv -f ${WORKDIR}/webOS.cmake $webos_cmake
}
