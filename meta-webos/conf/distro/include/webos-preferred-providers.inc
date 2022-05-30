# Copyright (c) 2012-2022 LG Electronics, Inc.

# Place distro-wide PREFERRED_PROVIDER settings here. Place MACHINE-specific
# settings (always with a _<MACHINE> override) in <MACHINE>.conf with a default
# for the emulator set here (with regular assignment, so that it can be
# overridden by higher layers whose distro .inc files are always included after
# this one) and "MACHINE" appended to their [vardeps] flags.

PREFERRED_PROVIDER_virtual/webruntime = "webruntime"
VIRTUAL-RUNTIME_webruntime = "webruntime"

WEBOS_PREFERRED_PROVIDER_FOR_BASH = "busybox"
PREFERRED_PROVIDER_bash = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'busybox', 'bash', d)}"
VIRTUAL-RUNTIME_bash = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_BASH', 'busybox', 'busybox', 'bash', d)}"

# Use the busybox Tar instead of GNU Tar
WEBOS_PREFERRED_PROVIDER_FOR_TAR = "busybox"
PREFERRED_PROVIDER_tar = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'busybox', 'tar', d)}"
VIRTUAL-RUNTIME_tar = "${@oe.utils.conditional('WEBOS_PREFERRED_PROVIDER_FOR_TAR', 'busybox', 'busybox', 'tar', d)}"

# Prefer libjpeg-turbo from meta-oe as it provides SIMD optimizations
PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

PREFERRED_PROVIDER_stat = "busybox"
VIRTUAL-RUNTIME_stat = "busybox"
VIRTUAL-RUNTIME_webos-ime = "imemanager"

VIRTUAL-RUNTIME_bluetooth_service = "com.webos.service.bluetooth2"

# Options are: bluez4, bluez5. This will only be used if "bluetooth" appears in
# DISTRO_FEATURES.
VIRTUAL-RUNTIME_bluetooth_stack = "bluez5"
BLUETOOTH_SIL_NAME = "bluez5"

BLUEZ = "${VIRTUAL-RUNTIME_bluetooth_stack}"
BLUEZ:class-native = "bluez5"
BLUEZ:class-nativesdk = "bluez5"

# Just for documentation; these values are the defaults set in the recipes that
# use them.
VIRTUAL-RUNTIME_cpushareholder = "cpushareholder-stub"

# Use Enactbrowser by default, except for armv45
VIRTUAL-RUNTIME_com.webos.app.browser ?= "com.webos.app.enactbrowser"
VIRTUAL-RUNTIME_com.webos.app.browser:armv4 = ""
VIRTUAL-RUNTIME_com.webos.app.browser:armv5 = ""

VIRTUAL-RUNTIME_webappmanager = "wam"
VIRTUAL-RUNTIME_webappmanager:armv4 = ""
VIRTUAL-RUNTIME_webappmanager:armv5 = ""

VIRTUAL-RUNTIME_initscripts = "webos-initscripts"
PREFERRED_RPROVIDER_initd-functions = "webos-initscripts"

PREFERRED_RPROVIDER_ssh = "dropbear"

# Options are: sysvinit, upstart, systemd
VIRTUAL-RUNTIME_init_manager = "systemd"

# With upstart we don't need update-rc.d, as bonus fixes following avahi issue for us:
# http://lists.openembedded.org/pipermail/openembedded-core/2013-November/086901.html
BAD_RECOMMENDATIONS += "update-rc.d"
UPDATERCD:class-target = ""

# Don't install big /boot/vmlinux* file by default in qemux86 images (included in oprofile's RRECOMMENDS)
# Use --no-vmlinux option to opcontrol or install it manually if you need to see samples from kernel
BAD_RECOMMENDATIONS:append:qemux86 = " kernel-vmlinux"

PREFERRED_PROVIDER_virtual/docker = "docker-moby"

# Define which modules should be built by nyx-modules by default.
#
# "webOS OSE" (defined by the meta-webos layer) builds only for the qemux86 emulator, therefore
# include all the emulator modules.
#
NYX_MODULES_REQUIRED ??= "\
    NYXMOD_OW_OSINFO;\
    NYXMOD_OW_DEVICEINFO;\
    NYXMOD_OW_SYSTEM;\
    NYXMOD_OW_DISPLAY;\
    NYXMOD_QEMU_BATTERY;\
    NYXMOD_OW_SECURITY;\
    NYXMOD_OW_SECURITY2;\
    NYXMOD_QEMU_CHARGER;\
    NYXMOD_QEMU_KEYS;\
    NYXMOD_QEMU_TOUCHPANEL;\
"

VIRTUAL-RUNTIME_nyx_modules_providers ??= "\
    nyx-modules \
    nyx-modules-qemux86 \
"

# Make wam preferred provider for webappmanager-webos
PREFERRED_PROVIDER_webappmanager-webos ?= "wam"

VIRTUAL-RUNTIME_ntp = "ntp sntp"

# Options: luna-surface-manager, auto-luna-surface-manager
VIRTUAL-RUNTIME_surface-manager = "luna-surfacemanager-base"
VIRTUAL-RUNTIME_surface-manager-conf = "auto-luna-surface-manager"
VIRTUAL-RUNTIME_surface-manager-extension = "auto-luna-surface-manager"
VIRTUAL-RUNTIME_surface-manager-tests = "luna-surfacemanager-base-tests"

# Options: com.webos.service.pdm, physical-device-manager, physical-device-manager2
VIRTUAL-RUNTIME_pdm = "com.webos.service.pdm"

