# Copyright (c) 2012-2024 LG Electronics, Inc.

DESCRIPTION = "webOS component for managing network connections using connman"
AUTHOR = "Muralidhar N <muralidhar.n@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=2763f3ed850f8412903ea776e0526bea \
    file://oss-pkg-info.yaml;md5=b0cf0d697c8340cbfa56b94bdc2539fb \
"

SECTION = "webos/services"

DEPENDS = "luna-service2 libpbnjson glib-2.0 luna-prefs openssl glib-2.0-native wca-support-api wca-support nyx-lib"
RDEPENDS:${PN} = "connman connman-client"

WEBOS_VERSION = "1.1.0-43_282a9ab89a024c24c1a602ccd2f11e22995b3b96"
PR = "r16"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_daemon
inherit webos_system_bus
inherit webos_machine_dep

# Set EXTRA_OECMAKE in webos-connman-adapter.bbappend to override default value for wifi and wired interfaces, for eg.
# EXTRA_OECMAKE += "-DWIFI_IFACE_NAME=wlan0 -DWIRED_IFACE_NAME=eth1"

EXTRA_OECMAKE += "-DENABLE_SCAN_ON_SOFTAP=true"

PACKAGECONFIG[enable-multiple-routing-table] = "-DMULTIPLE_ROUTING_TABLE:BOOL=true,-DMULTIPLE_ROUTING_TABLE:BOOL=false,"
PACKAGECONFIG = "enable-multiple-routing-table"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
SRC_URI:append:raspberrypi4 = " file://blacklistcdc_ether.conf"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "webos-connman-adapter.service"
WEBOS_SYSTEMD_SCRIPT = "webos-connman-adapter.sh"

do_install:append:raspberrypi4 () {
    install -d  ${D}${sysconfdir}/modprobe.d
    install -m 644 ${UNPACKDIR}/blacklistcdc_ether.conf  ${D}${sysconfdir}/modprobe.d/blacklistcdc_ether.conf
}

FILES:${PN}:append:raspberrypi4 = " ${sysconfdir}/modprobe.d/*"

# http://gecko.lge.com:8000/Errors/Details/821711
# webos-connman-adapter/1.1.0-43/git/src/lunaservice_utils.c:244:59: error: passing argument 2 of 'json_convert_to_native_valist' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wan_service.c:587:14: error: implicit declaration of function 'cellular_technology_status_check_with_subscription'; did you mean 'p2p_technology_status_check_with_subscription'? [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/connman_service.c:2021:49: error: passing argument 1 of 'g_dbus_proxy_set_default_timeout' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_p2p_service.c:839:43: error: initialization of 'gboolean' {aka 'int'} from 'gpointer' {aka 'void *'} makes integer from pointer without a cast [-Wint-conversion]
# webos-connman-adapter/1.1.0-43/git/src/wifi_p2p_service.c:994:73: error: passing argument 3 of 'g_timeout_add' makes pointer from integer without a cast [-Wint-conversion]
# webos-connman-adapter/1.1.0-43/git/src/wifi_p2p_service.c:3313:70: error: passing argument 3 of 'jobject_get_exists' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_p2p_service.c:3315:54: error: passing argument 1 of 'jstring_get' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_p2p_service.c:4034:13: error: implicit declaration of function 'connman_technology_set_go_intent'; did you mean 'connman_technology_set_powered'? [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/wifi_setting.c:892:44: error: passing argument 1 of 'g_strv_length' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_setting.c:897:60: error: passing argument 1 of 'strlen' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_setting.c:897:45: error: passing argument 2 of 'strncat' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/connman_manager.c:1769:33: error: assignment to 'gboolean *' {aka 'int *'} from 'int' makes pointer from integer without a cast [-Wint-conversion]
# webos-connman-adapter/1.1.0-43/git/src/connman_manager.c:2031:76: error: passing argument 2 of 'connman_update_callbacks->services_changed' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/connman_manager.c:2063:25: error: passing argument 2 of 'connman_update_callbacks->saved_services_changed' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/connectionmanager_service.c:284:25: error: implicit declaration of function 'send_peer_information_to_subscribers'; did you mean 'send_tethering_state_to_subscribers'? [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/connectionmanager_service.c:1410:74: error: passing argument 4 of 'change_network_ipv6' makes pointer from integer without a cast [-Wint-conversion]
# webos-connman-adapter/1.1.0-43/git/src/connectionmanager_service.c:1563:93: error: passing argument 3 of 'change_network_dns' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/connectionmanager_service.c:2469:47: error: passing argument 1 of 'connman_counter_new' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_scan.c:113:44: error: implicit declaration of function 'is_connected_peer' [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/wifi_scan.c:160:18: error: implicit declaration of function 'is_wifi_tethering' [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/wifi_scan.c:434:40: error: passing argument 1 of 'g_strv_length' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_scan.c:444:64: error: passing argument 1 of 'strlen' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_scan.c:444:48: error: passing argument 2 of 'strncat' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:1841:33: error: implicit declaration of function 'setPropertyUpdateCallback' [-Wimplicit-function-declaration]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:2723:48: error: passing argument 1 of 'wifi_scan_now_with_option' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:2723:54: error: passing argument 2 of 'wifi_scan_now_with_option' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:2896:72: error: passing argument 2 of 'check_service_security' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:4153:56: error: passing argument 2 of 'LSSubscriptionSetCancelFunction' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_service.c:4162:82: error: passing argument 5 of 'g_bus_watch_name' from incompatible pointer type [-Wincompatible-pointer-types]
# webos-connman-adapter/1.1.0-43/git/src/wifi_tethering_service.c:752:42: error: passing argument 2 of 'jboolean_get' from incompatible pointer type [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types -Wno-error=int-conversion -Wno-error=implicit-function-declaration"
