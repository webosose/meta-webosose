# Copyright (c) 2012-2023 LG Electronics, Inc.

DESCRIPTION = "meta-webos components used in webOS OSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# You don't need to change this value when you're changing just RDEPENDS:${PN} variable.
PR = "r45"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit webos_machine_impl_dep

VIRTUAL-RUNTIME_ai ?= "com.webos.service.ai"
# The same restriction as libgoogleassistant (snowboy doesn't support i686;x86)
VIRTUAL-RUNTIME_ai:i686 = ""
VIRTUAL-RUNTIME_ai:arm = ""
VIRTUAL-RUNTIME_ai:rpi = "com.webos.service.ai"
VIRTUAL-RUNTIME_appinstalld ?= "appinstalld2"
VIRTUAL-RUNTIME_memorymanager ?= "com.webos.service.memorymanager"
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"

# We're not using VIRTUAL-RUNTIME because VIRTUAL-RUNTIME is usually used for only
# one item and changing that in <distro>-preferred-providers.inc would require
# .bbappend in meta-<distro> to do PR/PRINC/PR_append bump anyway so it's easier
# to change this variable in .bbappend together with bump.
#

VIRTUAL-RUNTIME_settingsapp ?= "com.webos.app.settings"
VIRTUAL-RUNTIME_settingsapp:armv4 = ""
VIRTUAL-RUNTIME_settingsapp:armv5 = ""

VIRTUAL-RUNTIME_nodejs-module-node-red ?= "nodejs-module-node-red"
VIRTUAL-RUNTIME_nodejs-module-node-red:armv4 = ""
VIRTUAL-RUNTIME_nodejs-module-node-red:armv5 = ""

# The same restrition as nodejs (and nodejs-module-node-red and com.webos.service.contextintentmgr)
VIRTUAL-RUNTIME_contextintentmgr ?= "com.webos.service.contextintentmgr"
VIRTUAL-RUNTIME_contextintentmgr:armv4 = ""
VIRTUAL-RUNTIME_contextintentmgr:armv5 = ""
VIRTUAL-RUNTIME_contextintentmgr:mips64 = ""

VIRTUAL-RUNTIME_mojoservicelauncher ?= "mojoservicelauncher"
VIRTUAL-RUNTIME_mojoservicelauncher:armv4 = ""
VIRTUAL-RUNTIME_mojoservicelauncher:armv5 = ""

VIRTUAL-RUNTIME_com.webos.service.flowmanager ?= "com.webos.service.flowmanager"
VIRTUAL-RUNTIME_com.webos.service.flowmanager:armv4 = ""
VIRTUAL-RUNTIME_com.webos.service.flowmanager:armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.home ?= "com.webos.app.home"
VIRTUAL-RUNTIME_com.webos.app.home:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.home:armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.notification ?= "com.webos.app.notification"
VIRTUAL-RUNTIME_com.webos.app.notification:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.notification:armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.volume ?= "com.webos.app.volume"
VIRTUAL-RUNTIME_com.webos.app.volume:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.volume:armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.statusbar ?= "com.webos.app.statusbar"
VIRTUAL-RUNTIME_com.webos.app.statusbar:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.statusbar:armv5 = ""

VIRTUAL-RUNTIME_com.webos.app.browser ?= "com.webos.app.enactbrowser"

VIRTUAL-RUNTIME_unifiedsearch ?= "com.webos.service.unifiedsearch com.webos.service.unifiedsearch-plugins"

VIRTUAL-RUNTIME_com.webos.service.intent ?= "com.webos.service.intent"

VIRTUAL-RUNTIME_com.webos.app.mediagallery ?= "com.webos.app.mediagallery"

RDEPENDS:${PN} = " \
    activitymanager \
    configurator \
    event-monitor \
    filecache \
    fluentbit \
    luna-downloadmgr \
    luna-init \
    nodejs-module-webos-service \
    notificationmgr \
    pacrunner \
    qml-app-components \
    sleepd \
    webos-fluentbit-plugins \
    ${VIRTUAL-RUNTIME_appinstalld} \
    ${VIRTUAL-RUNTIME_com.webos.app.browser} \
    ${VIRTUAL-RUNTIME_com.webos.app.home} \
    ${VIRTUAL-RUNTIME_com.webos.app.mediagallery} \
    ${VIRTUAL-RUNTIME_com.webos.app.notification} \
    ${VIRTUAL-RUNTIME_com.webos.app.volume} \
    ${VIRTUAL-RUNTIME_com.webos.service.intent} \
    ${VIRTUAL-RUNTIME_memorymanager} \
    ${VIRTUAL-RUNTIME_mojoservicelauncher} \
    ${VIRTUAL-RUNTIME_nodejs-module-node-red} \
    ${VIRTUAL-RUNTIME_pdm} \
    ${VIRTUAL-RUNTIME_settingsapp} \
    ${VIRTUAL-RUNTIME_unifiedsearch} \
    ${WEBOS_FOSS_MISSING_FROM_RDEPENDS} \
"

RDEPENDS:${PN}:append:webos = " \
    com.webos.app.mediaviewer \
    com.webos.app.imageviewer \
    com.webos.app.videocall \
    com.webos.service.sdkagent \
    com.webos.service.storageaccess \
    ebd \
    event-monitor-pdm \
    gssdp \
    gupnp \
    ${VIRTUAL-RUNTIME_ai} \
    ${VIRTUAL-RUNTIME_com.webos.app.statusbar} \
    ${VIRTUAL-RUNTIME_com.webos.service.flowmanager} \
    ${VIRTUAL-RUNTIME_contextintentmgr} \
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
    ncurses \
    openssl \
    psmisc \
    sqlite3 \
    ${@oe.utils.conditional('VIRTUAL-RUNTIME_init_manager', 'systemd', 'systemd-analyze', 'sysvinit-pidof', d)} \
"

# These packages that are installed in the qemux86 image only.
RRECOMMENDS:${PN}:append:qemux86 = " \
    fuse-utils \
    kernel-module-8021q \
    kernel-module-ac97-bus \
    kernel-module-configs \
    kernel-module-evdev \
    kernel-module-fuse \
    kernel-module-hci-uart \
    kernel-module-rfcomm \
    kernel-module-snd-ac97-codec \
    kernel-module-snd-intel8x0 \
    kernel-module-snd-pcm \
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

RRECOMMENDS:${PN}:append:qemux86-64 = " \
    fuse-utils \
    kernel-module-8021q \
    kernel-module-ac97-bus \
    kernel-module-configs \
    kernel-module-evdev \
    kernel-module-fuse \
    kernel-module-hci-uart \
    kernel-module-rfcomm \
    kernel-module-snd-ac97-codec \
    kernel-module-snd-intel8x0 \
    kernel-module-snd-pcm \
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

RDEPENDS:${PN}:append:qemux86 = " \
    com.webos.service.hfp \
    com.webos.service.location \
    com.webos.service.power2 \
    com.webos.service.storageaccess \
    dhcpcd \
    event-monitor-pdm \
    ofono \
    v4l-utils \
    vmwgfx-layout \
"

RDEPENDS:${PN}:append:qemux86-64 = " \
    com.webos.service.hfp \
    com.webos.service.location \
    com.webos.service.power2 \
    com.webos.service.storageaccess \
    dhcpcd \
    event-monitor-pdm \
    ofono \
    v4l-utils \
    vmwgfx-layout \
"

RDEPENDS:${PN} += "${MACHINE_EXTRA_RDEPENDS}"
RRECOMMENDS:${PN} += "${MACHINE_EXTRA_RRECOMMENDS}"

# Unused meta-webos components:
# - libtinyxml
