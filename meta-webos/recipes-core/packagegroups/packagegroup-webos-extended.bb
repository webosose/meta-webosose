# Copyright (c) 2012-2019 LG Electronics, Inc.

DESCRIPTION = "meta-webos components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS_${PN} variable.
PR = "r29"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep

VIRTUAL-RUNTIME_appinstalld ?= "appinstalld2"
VIRTUAL-RUNTIME_event-monitor-network ?= "event-monitor-network"
VIRTUAL-RUNTIME_memorymanager ?= "com.webos.service.memorymanager"
VIRTUAL-RUNTIME_webappmanager ?= ""
VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_librdx ?= "rdxd"
VIRTUAL-RUNTIME_surface-manager ?= "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf ?= "luna-surfacemanager-conf"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_webos-ime ?= ""
VIRTUAL-RUNTIME_nyx_modules_providers ??= "\
    nyx-modules \
    nyx-modules-qemux86 \
"
VIRTUAL-RUNTIME_com.webos.app.browser ?= "com.webos.app.browser"

# We're not using VIRTUAL-RUNTIME because VIRTUAL-RUNTIME is usually used for only
# one item and changing that in <distro>-preferred-providers.inc would require
# .bbappend in meta-<distro> to do PR/PRINC/PR_append bump anyway so it's easier
# to change this variable in .bbappend together with bump.
#
WEBOS_PACKAGESET_TESTAPPS = " \
    bareapp \
    com.webos.app.test.enact \
    com.webos.app.test.webosose \
    com.webos.app.test.youtube \
"

# Fonts used by the browser
VIRTUAL-RUNTIME_browser_fonts ?= "webos-fonts"

VIRTUAL-RUNTIME_settingsapp ?= "com.webos.app.settings"
VIRTUAL-RUNTIME_settingsapp_armv4 = ""
VIRTUAL-RUNTIME_settingsapp_armv5 = ""

VIRTUAL-RUNTIME_umediaserver ?= "umediaserver"
VIRTUAL-RUNTIME_umediaserver_armv4 = ""
VIRTUAL-RUNTIME_umediaserver_armv5 = ""

VIRTUAL-RUNTIME_iotivity-node ?= "iotivity-node"
VIRTUAL-RUNTIME_iotivity-node_armv4 = ""
VIRTUAL-RUNTIME_iotivity-node_armv5 = ""

# This packageset controls which time zone packages should be included in webOS.
# Since any application that uses localtime will indirectly depend on presence of
# time zone data, we pull in those packages as a top-level dependency. By
# assigning the list to its own variable, we have the option to only include a
# subset should there be a device that will only be used within some region.
WEBOS_PACKAGESET_TZDATA ?= " \
    tzdata \
    tzdata-africa \
    tzdata-americas \
    tzdata-antarctica \
    tzdata-arctic \
    tzdata-asia \
    tzdata-atlantic \
    tzdata-australia \
    tzdata-europe \
    tzdata-misc \
    tzdata-pacific \
    tzdata-posix \
    tzdata-right \
"

RDEPENDS_${PN} = " \
    activitymanager \
    audiod \
    bootd \
    configd \
    configurator \
    com.palm.service.devmode \
    event-monitor \
    filecache \
    fontconfig-utils \
    ilib-qml-plugin \
    ilib-webapp \
    luna-downloadmgr \
    luna-init \
    luna-sysservice \
    mojoservicelauncher \
    nodejs-module-node-red \
    nodejs-module-webos-service \
    notificationmgr \
    pacrunner \
    pmklogd \
    pmlogctl \
    pmlogdaemon \
    sam \
    settingsservice \
    sleepd \
    webos-connman-adapter \
    webos-fontconfig-files \
    ${VIRTUAL-RUNTIME_appinstalld} \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    ${VIRTUAL-RUNTIME_com.webos.app.browser} \
    ${VIRTUAL-RUNTIME_event-monitor-network} \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${VIRTUAL-RUNTIME_librdx} \
    ${VIRTUAL-RUNTIME_memorymanager} \
    ${VIRTUAL-RUNTIME_nyx_modules_providers} \
    ${VIRTUAL-RUNTIME_settingsapp} \
    ${VIRTUAL-RUNTIME_surface-manager} \
    ${VIRTUAL-RUNTIME_surface-manager-conf} \
    ${VIRTUAL-RUNTIME_surface-manager-extension} \
    ${VIRTUAL-RUNTIME_umediaserver} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${VIRTUAL-RUNTIME_webos-ime} \
    ${WEBOS_PACKAGESET_TESTAPPS} \
    ${WEBOS_PACKAGESET_TZDATA} \
    ${WEBOS_FOSS_MISSING_FROM_RDEPENDS} \
"

RDEPENDS_${PN}_append_hardware = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', '${VIRTUAL-RUNTIME_bluetooth_service}', '', d)} \
"

RDEPENDS_${PN}_append_webos = " node-inspector ${VIRTUAL-RUNTIME_iotivity-node}"

# XXX These FOSS components must be explicitly added because they are missing
# from the RDEPENDS lists of the components that expect them to be present at
# runtime (or perhaps some are in fact top-level components and some others
# aren't actually needed).
WEBOS_FOSS_MISSING_FROM_RDEPENDS = " \
    bzip2 \
    curl \
    e2fsprogs \
    hunspell \
    icu \
    iproute2 \
    lsb \
    makedevs \
    ncurses \
    openssl \
    procps \
    psmisc \
    sqlite3 \
    ${@'systemd-analyze' if '${VIRTUAL-RUNTIME_init_manager}' == 'systemd' else 'sysvinit-pidof'} \
"

# These packages that are installed in the qemux86 image only.
RDEPENDS_${PN}_append_qemux86 = " \
   kernel-module-uvcvideo \
   kernel-module-videobuf2-core \
   kernel-module-media \
   kernel-module-videodev \
   kernel-module-videobuf2-v4l2 \
   kernel-module-v4l2-common \
   kernel-module-videobuf2-vmalloc \
   kernel-module-videobuf2-memops \
   v4l-utils \
   dhcp-client \
"

RDEPENDS_${PN} += "${MACHINE_EXTRA_RDEPENDS}"
RRECOMMENDS_${PN} += "${MACHINE_EXTRA_RRECOMMENDS}"

# Unused meta-webos components:
# - libtinyxml
