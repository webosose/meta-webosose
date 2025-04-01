# Copyright (c) 2019-2025 LG Electronics, Inc.

SUMMARY = "Camera service framework to control camera devices"
AUTHOR = "Sungho Lee <shl.lee@lge.com>"
SECTION = "webos/services"

require com.webos.service.camera.inc

PR = "${INC_PR}.0"

DEPENDS = "glib-2.0 luna-service2 json-c alsa-lib pmloglib udev nlohmann-json"

# depends on edgeai-vision
PACKAGECONFIG ??= " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'webos-aiframework', d)}\
"

PACKAGECONFIG[webos-aiframework] = "-DWITH_AIFRAMEWORK=ON,-DWITH_AIFRAMEWORK=OFF,edgeai-vision"
inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.camera.service camera-registry.service"
WEBOS_SYSTEMD_SCRIPT = "camera-registry.sh"

# To scan the plugins used by the camera service in /usr/lib/camera.
FILES:${PN}-dev += "${libdir}/camera/lib*${SOLIBSDEV}"
FILES:${PN} += "${libdir}/camera/lib*${SOLIBS}"
