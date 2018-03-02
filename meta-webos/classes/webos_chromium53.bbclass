# Copyright (c) 2016-2018 LG Electronics, Inc.

inherit webos_lttng
inherit webos_machine_dep

CHROMIUM53_SUBMISSION = "1"
CHROMIUM53_SUBMISSION_HASH = "48a4dd6bb384ce53bd894ca0707e28244a03d9ab"

CHROMIUM_DEBUG_FLAGS = "-g1"

GYP_DEFINES = "\
    chromeos=0\
    clang=0\
    component=static_library\
    disable_ftp_support=1\
    disable_nacl=1\
    enable_media_router=0\
    enable_plugin_installation=1\
    enable_plugins=1\
    enable_printing=0\
    enable_webrtc=1\
    ffmpeg_branding=Chrome\
    host_clang=0\
    no_gc_sections=1\
    ozone_auto_platforms=0\
    ozone_platform_caca=0\
    ozone_platform_dri=0\
    ozone_platform_drm=0\
    ozone_platform_eglfs=1\
    ozone_platform_egltest=0\
    ozone_platform_gbm=0\
    ozone_platform_ozonex=0\
    ozone_platform_test=0\
    ozone_platform_wayland_external=1\
    proprietary_codecs=1\
    python_ver=2.7\
    qt_patch=1\
    release_extra_cflags=' ${CHROMIUM_DEBUG_FLAGS} '\
    remoting=0\
    remove_webcore_debug_symbols=0\
    strip_tests=1\
    swig_defines=-DOS_LINUX\
    sysroot=${STAGING_DIR_HOST}\
    system_libdir=lib\
    use_ash=0\
    use_aura=1\
    use_cups=0\
    use_gnome_keyring=0\
    use_ibus=0\
    use_kerberos=0\
    use_nss_certs=0\
    use_nss_verifier=0\
    use_openssl=1\
    use_openssl_certs=1\
    use_ozone=1\
    use_pulseaudio=0\
    use_self_signed_certificates=1\
    use_system_icu=0\
    use_xkbcommon=1\
    webos=1\
    webos_ewam=0\
    werror=''\
"

GYP_DEFINES += "${@bb.utils.contains('WEBOS_LTTNG_ENABLED', '1', ' enable_lttng=1', '', d)}"

# Respect ld-is-gold in DISTRO_FEATURES when enabling gold
# Similar patch applied in meta-browser
# http://patchwork.openembedded.org/patch/77755/
EXTRA_OEGYP_GOLD = "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'linux_use_gold_binary=1 linux_use_gold_flags=1', 'linux_use_gold_binary=0 linux_use_gold_flags=0', d)}"

# Use system binutils, bundled binutils (GNU gold (GNU Binutils 2.24) 1.11)
# aren't compatible with (GNU gold (GNU Binutils for Ubuntu 2.25.90.20160101) 1.11) included in Ubuntu-16.04 Alpha
# and building native libvpx fails with:
# chromium38/38.0.2125.122-107-r11.3/git/src/third_party/binutils/Linux_x64/Release/bin/ld: error: /usr/lib/gcc/x86_64-linux-gnu/5/../../../x86_64-linux-gnu/crti.o: unsupported reloc 42 against global symbol __gmon_start__
GYP_DEFINES_append = " linux_use_bundled_gold=0"
GYP_DEFINES_append = " linux_use_bundled_binutils=0"

GYP_DEFINES += "${EXTRA_OEGYP_GOLD}"

GYP_DEFINES_append_arm = " target_arch=arm arm_float_abi=${TUNE_CCARGS_MFLOAT}"
GYP_DEFINES_append_aarch64 = " target_arch=arm64"
GYP_DEFINES_append_qemux86 = " target_arch=ia32"
# workaround to fix emulator issue with latest chromium
# replace with proper fix when available
# follow: https://bugreports.qt.io/browse/QTBUG-57705
GYP_DEFINES_append_qemux86 = " generate_character_data=0"
GYP_DEFINES_append_armv7a = " arm_version=7"
GYP_DEFINES_append_armv7ve = " arm_version=7 arm_override_arch='armv7ve'"
GYP_DEFINES_append_hardware = " use_directmedia2=1"

# umediaserver interface for hardware
GYP_DEFINES_append_hardware = " use_umediaserver=1 use_webos_media_focus_extension=1"
DEPENDS_append_hardware = " umediaserver"

# libndl-directmedia2 is still broken for aarch64 PLAT-45700
GYP_DEFINES_remove_aarch64 = "use_directmedia2=1 use_umediaserver=1 use_webos_media_focus_extension=1"
DEPENDS_remove_aarch64 = "umediaserver libndl-directmedia2"

# Doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

# The text relocations are intentional -- see comments in [GF-52468]
INSANE_SKIP_${PN} = "textrel"
