# Copyright (c) 2012-2021 LG Electronics, Inc.

DESCRIPTION = "meta-webos components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS_${PN} variable.
PR = "r39"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep
inherit webos_prerelease_dep

VIRTUAL-RUNTIME_ai ?= "com.webos.service.ai"
# The same restriction as libgoogleassistant (snowboy doesn't support i686;x86)
VIRTUAL-RUNTIME_ai_i686 = ""
VIRTUAL-RUNTIME_appinstalld ?= "appinstalld2"
VIRTUAL-RUNTIME_event-monitor-network ?= "event-monitor-network"
VIRTUAL-RUNTIME_initscripts ?= "initscripts"
VIRTUAL-RUNTIME_memorymanager ?= "com.webos.service.memorymanager"
VIRTUAL-RUNTIME_surface-manager ?= "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf ?= "luna-surfacemanager-conf"
VIRTUAL-RUNTIME_surface-manager-extension ?= ""
VIRTUAL-RUNTIME_tts ?= "com.webos.service.tts"
VIRTUAL-RUNTIME_webappmanager ?= ""
VIRTUAL-RUNTIME_webos-ime ?= ""

VIRTUAL-RUNTIME_nyx_modules_providers ??= "\
    nyx-modules \
    nyx-modules-qemux86 \
"

# Restricted to only these 3 MACHINEs by COMPATIBLE_MACHINE
VIRTUAL-RUNTIME_com.webos.service.mediacontroller ?= ""
VIRTUAL-RUNTIME_com.webos.service.mediacontroller_qemux86 = "com.webos.service.mediacontroller"
VIRTUAL-RUNTIME_com.webos.service.mediacontroller_raspberrypi4 = "com.webos.service.mediacontroller"
VIRTUAL-RUNTIME_com.webos.service.mediacontroller_raspberrypi4-64 = "com.webos.service.mediacontroller"

VIRTUAL-RUNTIME_g-media-pipeline ?= ""
VIRTUAL-RUNTIME_g-media-pipeline_raspberrypi3 = "g-media-pipeline"
VIRTUAL-RUNTIME_g-media-pipeline_raspberrypi3-64 = "g-media-pipeline"
VIRTUAL-RUNTIME_g-media-pipeline_raspberrypi4 = "g-media-pipeline"
VIRTUAL-RUNTIME_g-media-pipeline_raspberrypi4-64 = "g-media-pipeline"
VIRTUAL-RUNTIME_g-media-pipeline_qemux86 = "g-media-pipeline"

VIRTUAL-RUNTIME_g-camera-pipeline ?= ""
VIRTUAL-RUNTIME_g-camera-pipeline_raspberrypi4 = "g-camera-pipeline"
VIRTUAL-RUNTIME_g-camera-pipeline_raspberrypi4-64 = "g-camera-pipeline"
VIRTUAL-RUNTIME_g-camera-pipeline_qemux86 = "g-camera-pipeline"

VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"

# We're not using VIRTUAL-RUNTIME because VIRTUAL-RUNTIME is usually used for only
# one item and changing that in <distro>-preferred-providers.inc would require
# .bbappend in meta-<distro> to do PR/PRINC/PR_append bump anyway so it's easier
# to change this variable in .bbappend together with bump.
#
WEBOS_PACKAGESET_TESTAPPS = " \
    bareapp \
    com.webos.app.test.enact \
    com.webos.app.test.webosose \
    com.webos.app.test.webrtc \
    com.webos.app.test.youtube \
"

MEDIA = " \
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    ${VIRTUAL-RUNTIME_g-media-pipeline} \
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

VIRTUAL-RUNTIME_com.example.app.iotivity ?= "com.example.app.iotivity"
VIRTUAL-RUNTIME_com.example.app.iotivity_armv4 = ""
VIRTUAL-RUNTIME_com.example.app.iotivity_armv5 = ""

VIRTUAL-RUNTIME_com.example.service.iotivity ?= "com.example.service.iotivity"
VIRTUAL-RUNTIME_com.example.service.iotivity_armv4 = ""
VIRTUAL-RUNTIME_com.example.service.iotivity_armv5 = ""

VIRTUAL-RUNTIME_org.ocf.webossample ?= "org.ocf.webossample.occlientbasicops org.ocf.webossample.ocserverbasicops"
VIRTUAL-RUNTIME_org.ocf.webossample_armv4 = ""
VIRTUAL-RUNTIME_org.ocf.webossample_armv5 = ""
VIRTUAL-RUNTIME_org.ocf.webossample_x86-64 = ""

VIRTUAL-RUNTIME_nodejs-module-node-red ?= "nodejs-module-node-red"
VIRTUAL-RUNTIME_nodejs-module-node-red_armv4 = ""
VIRTUAL-RUNTIME_nodejs-module-node-red_armv5 = ""

# The same restrition as nodejs (and nodejs-module-node-red and com.webos.service.contextintentmgr)
VIRTUAL-RUNTIME_contextintentmgr ?= "com.webos.service.contextintentmgr"
VIRTUAL-RUNTIME_contextintentmgr_armv4 = ""
VIRTUAL-RUNTIME_contextintentmgr_armv5 = ""
VIRTUAL-RUNTIME_contextintentmgr_mips64 = ""

VIRTUAL-RUNTIME_mojoservicelauncher ?= "mojoservicelauncher"
VIRTUAL-RUNTIME_mojoservicelauncher_armv4 = ""
VIRTUAL-RUNTIME_mojoservicelauncher_armv5 = ""

VIRTUAL-RUNTIME_com.webos.service.flowmanager ?= "com.webos.service.flowmanager"
VIRTUAL-RUNTIME_com.webos.service.flowmanager_armv4 = ""
VIRTUAL-RUNTIME_com.webos.service.flowmanager_armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.home ?= "com.webos.app.home"
VIRTUAL-RUNTIME_com.webos.app.home_armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.home_armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.notification ?= "com.webos.app.notification"
VIRTUAL-RUNTIME_com.webos.app.notification_armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.notification_armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.volume ?= "com.webos.app.volume"
VIRTUAL-RUNTIME_com.webos.app.volume_armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.volume_armv5 = ""

VIRTUAL-RUNTIME_bluetooth_service ?= "com.webos.service.bluetooth2"

VIRTUAL-RUNTIME_com.webos.app.browser ?= "com.webos.app.enactbrowser"

VIRTUAL-RUNTIME_unifiedsearch ?= "com.webos.service.unifiedsearch com.webos.service.unifiedsearch-plugins"

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
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', '${VIRTUAL-RUNTIME_bluetooth_service}', '', d)} \
    bootd \
    configd \
    configurator \
    com.palm.service.devmode \
    com.webos.app.mediagallery\
    com.webos.service.intent \
    event-monitor \
    filecache \
    fluentbit \
    fontconfig-utils \
    ilib-qml-plugin \
    ilib-webapp \
    luna-downloadmgr \
    luna-init \
    luna-sysservice \
    ${VIRTUAL-RUNTIME_mojoservicelauncher} \
    ${VIRTUAL-RUNTIME_nodejs-module-node-red} \
    nodejs-module-webos-service \
    notificationmgr \
    pacrunner \
    pmklogd \
    pmlogctl \
    pmlogdaemon \
    qml-app-components \
    sam \
    settingsservice \
    sleepd \
    webos-connman-adapter \
    webos-fluentbit-plugins \
    webos-fontconfig-files \
    webos-nettools \
    ${MEDIA} \
    ${VIRTUAL-RUNTIME_appinstalld} \
    ${VIRTUAL-RUNTIME_browser_fonts} \
    ${VIRTUAL-RUNTIME_com.example.app.iotivity} \
    ${VIRTUAL-RUNTIME_com.example.service.iotivity} \
    ${VIRTUAL-RUNTIME_com.webos.app.browser} \
    ${VIRTUAL-RUNTIME_com.webos.app.home} \
    ${VIRTUAL-RUNTIME_com.webos.app.notification} \
    ${VIRTUAL-RUNTIME_com.webos.app.volume} \
    ${VIRTUAL-RUNTIME_event-monitor-network} \
    ${VIRTUAL-RUNTIME_initscripts} \
    ${VIRTUAL-RUNTIME_iotivity-node} \
    ${VIRTUAL-RUNTIME_memorymanager} \
    ${VIRTUAL-RUNTIME_nyx_modules_providers} \
    ${VIRTUAL-RUNTIME_org.ocf.webossample} \
    ${VIRTUAL-RUNTIME_pdm} \
    ${VIRTUAL-RUNTIME_settingsapp} \
    ${VIRTUAL-RUNTIME_surface-manager} \
    ${VIRTUAL-RUNTIME_surface-manager-conf} \
    ${VIRTUAL-RUNTIME_surface-manager-extension} \
    ${VIRTUAL-RUNTIME_umediaserver} \
    ${VIRTUAL-RUNTIME_unifiedsearch} \
    ${VIRTUAL-RUNTIME_webappmanager} \
    ${VIRTUAL-RUNTIME_webos-ime} \
    ${@ '' if '${WEBOS_DISTRO_PRERELEASE}' == '' else '${WEBOS_PACKAGESET_TESTAPPS}'} \
    ${WEBOS_PACKAGESET_TZDATA} \
    ${WEBOS_FOSS_MISSING_FROM_RDEPENDS} \
"

RDEPENDS_${PN}_append_webos = " \
    com.webos.app.camera \
    com.webos.app.mediaviewer \
    com.webos.app.imageviewer \
    com.webos.app.videoplayer \
    com.webos.service.storageaccess \
    com.webos.service.swupdater \
    com.webos.service.uwb \
    gssdp \
    gupnp \
    ${VIRTUAL-RUNTIME_ai} \
    ${VIRTUAL-RUNTIME_com.webos.service.mediacontroller} \
    ${VIRTUAL-RUNTIME_com.webos.service.flowmanager} \
    ${VIRTUAL-RUNTIME_contextintentmgr} \
    ${VIRTUAL-RUNTIME_g-camera-pipeline} \
    ${VIRTUAL-RUNTIME_tts} \
    wireless-regdb-static \
"

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
    lsb-release \
    makedevs \
    ncurses \
    openssl \
    procps \
    psmisc \
    sqlite3 \
    ${@oe.utils.conditional('VIRTUAL-RUNTIME_init_manager', 'systemd', 'systemd-analyze', 'sysvinit-pidof', d)} \
"

# These packages that are installed in the qemux86 image only.
RRECOMMENDS_${PN}_append_qemux86 = " \
    fuse-utils \
    kernel-module-ac97-bus \
    kernel-module-bluetooth \
    kernel-module-btbcm \
    kernel-module-btintel \
    kernel-module-btusb \
    kernel-module-configs \
    kernel-module-evdev \
    kernel-module-fuse \
    kernel-module-hci-uart \
    kernel-module-media \
    kernel-module-rfcomm \
    kernel-module-snd-ac97-codec \
    kernel-module-snd-intel8x0 \
    kernel-module-snd-pcm \
    kernel-module-snd-usb-audio \
    kernel-module-snd-usbmidi-lib \
    kernel-module-uinput \
    kernel-module-uvcvideo \
    kernel-module-v4l2-common \
    kernel-module-vboxguest \
    kernel-module-videobuf2-core \
    kernel-module-videobuf2-memops \
    kernel-module-videobuf2-v4l2 \
    kernel-module-videobuf2-vmalloc \
    kernel-module-videodev \
    ntfs-3g \
"

RDEPENDS_${PN}_append_qemux86 = " \
    com.webos.service.audiofocusmanager \
    com.webos.service.audiooutput \
    com.webos.service.camera \
    com.webos.service.hfp \
    com.webos.service.location \
    com.webos.service.mediaindexer \
    com.webos.service.power2 \
    com.webos.service.storageaccess \
    dhcp-client \
    ofono \
    v4l-utils \
    vmwgfx-layout \
"

RDEPENDS_${PN} += "${MACHINE_EXTRA_RDEPENDS}"
RRECOMMENDS_${PN} += "${MACHINE_EXTRA_RRECOMMENDS}"

# Unused meta-webos components:
# - libtinyxml
