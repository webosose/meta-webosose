# Copyright (c) 2018-2024 LG Electronics, Inc.

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

EXTENDPRAUTO:append = "webos24"
SYSTEMD_SERVICE:${PN}:remove = "connman.service"
# connman.service is provided by connman-conf
RDEPENDS:${PN} += "connman-conf"

WEBOS_VERSION = "1.41-11_829ccdc4b0ced34982c1cdc7dec06d8b4c26cac3"
WEBOS_REPO_NAME = "connman-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

inherit webos_component
inherit webos_public_repo
inherit webos_enhanced_submissions

do_install:append() {
    rm -vf ${D}${systemd_unitdir}/system/connman.service
}

# http://gecko.lge.com:8000/Errors/Details/821746
# connman-1.42/src/util.c:160:17: error: implicit declaration of function 'snprintf' [-Wimplicit-function-declaration]
# connman-1.42/src/util.c:185:13: error: implicit declaration of function 'toupper' [-Wimplicit-function-declaration]
# connman-1.42/plugins/gadget.c:328:35: error: initialization of 'int (*)(struct connman_technology *, const char *, const char *, const char *, _Bool)' from incompatible pointer type 'int (*)(struct connman_technology *, const char *, _Bool)' [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:928:64: error: passing argument 3 of 'callbacks_pointer->p2p_prov_disc_fail' makes integer from pointer without a cast [-Wint-conversion]
# connman-1.42/src/service.c:5168:28: error: passing argument 1 of 'g_get_current_time' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:3878:85: error: passing argument 2 of 'g_timeout_add' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/plugins/bluetooth.c:919:27: error: initialization of 'int (*)(struct connman_technology *, const char *, const char *, const char *, _Bool)' from incompatible pointer type 'int (*)(struct connman_technology *, const char *, _Bool)' [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:359:47: error: implicit declaration of function '__connman_get_connected_peer' [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:359:45: error: assignment to 'struct connman_peer *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/plugins/wifi.c:644:51: error: passing argument 1 of 'remove_persistent_groups_elements' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:819:17: error: implicit declaration of function 'g_supplicant_interface_p2p_remove_persistent_group'; did you mean 'g_supplicant_interface_p2p_remove_all_persistent_groups'? [-Wimplicit-function-declaration]
# connman-1.42/gsupplicant/supplicant.c:4002:25: error: implicit declaration of function '__connman_util_byte_to_string' [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:844:25: error: implicit declaration of function '__connman_util_remove_colon_from_mac_addr'; did you mean '__connman_util_insert_colon_to_mac_addr'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:844:23: error: assignment to 'const char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/plugins/wifi.c:1410:44: error: passing argument 2 of 'g_list_foreach' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:1876:9: error: implicit declaration of function '__connman_sd_cleanup'; did you mean '__connman_nat_cleanup'? [-Wimplicit-function-declaration]
# connman-1.42/gsupplicant/supplicant.c:4228:41: error: implicit declaration of function '__connman_util_ipaddr_binary_to_string' [-Wimplicit-function-declaration]
# connman-1.42/gsupplicant/supplicant.c:4228:39: error: assignment to 'char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/gsupplicant/supplicant.c:4236:39: error: assignment to 'char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/gsupplicant/supplicant.c:4244:42: error: assignment to 'char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/plugins/wifi.c:2537:17: error: implicit declaration of function '__connman_sd_init'; did you mean '__connman_nat_init'? [-Wimplicit-function-declaration]
# connman-1.42/gsupplicant/supplicant.c:4686:63: error: passing argument 2 of 'callbacks_pointer->p2p_invitation_received' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:4697:63: error: passing argument 2 of 'callbacks_pointer->p2p_invitation_received' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:4739:43: error: assignment to 'g_supplicant_p2p_network_signal_func' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, struct _GSupplicantP2PSProvisionSignalParams *)'} from incompatible pointer type 'void (*)(GSupplicantInterface *, GSupplicantPeer *, void *)' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, void *)'} [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:4221:19: error: assignment to 'GSList *' {aka 'struct _GSList *'} from incompatible pointer type 'GList *' {aka 'struct _GList *'} [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5026:41: error: assignment to 'g_supplicant_p2p_prov_dics_signal_func' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, void *)'} from incompatible pointer type 'void (*)(GSupplicantInterface *, GSupplicantPeer *, const char *)' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, const char *)'} [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5032:41: error: assignment to 'g_supplicant_p2p_prov_dics_signal_func' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, void *)'} from incompatible pointer type 'void (*)(GSupplicantInterface *, GSupplicantPeer *, const char *)' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, const char *)'} [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5040:54: error: passing argument 3 of 'fire_p2p_signal_when_network_present' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/src/sd.c:162:33: error: implicit declaration of function '__connman_util_remove_colon_from_mac_addr'; did you mean '__connman_util_insert_colon_to_mac_addr'? [-Wimplicit-function-declaration]
# connman-1.42/src/sd.c:162:31: error: assignment to 'const char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/gsupplicant/supplicant.c:5129:54: error: passing argument 3 of 'callback_p2p_prov_disc_fail' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/src/sd.c:247:31: error: assignment to 'const char *' from 'int' makes pointer from integer without a cast [-Wint-conversion]
# connman-1.42/gsupplicant/supplicant.c:5800:16: error: returning 'struct peer_device_data *' from a function with incompatible return type 'GSupplicantP2PNetwork *' {aka 'struct _GSupplicantP2PNetwork *'} [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5818:24: error: returning 'GSupplicantPeer *' {aka 'struct _GSupplicantPeer *'} from a function with incompatible return type 'const char *' [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5844:24: error: returning 'GSupplicantPeer *' {aka 'struct _GSupplicantPeer *'} from a function with incompatible return type 'const char *' [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5848:24: error: returning 'GSupplicantPeer *' {aka 'struct _GSupplicantPeer *'} from a function with incompatible return type 'const char *' [-Wincompatible-pointer-types]
# connman-1.42/gsupplicant/supplicant.c:5891:21: error: assignment to 'struct peer_device_data *' from incompatible pointer type 'GSupplicantP2PNetwork *' {aka 'struct _GSupplicantP2PNetwork *'} [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:4933:9: error: implicit declaration of function 'connman_technology_tethering_add_station'; did you mean 'connman_technology_tethering_notify'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:4936:9: error: implicit declaration of function '__connman_technology_sta_count_changed'; did you mean '__connman_technology_set_connected'? [-Wimplicit-function-declaration]
# connman-1.42/src/peer.c:1158:33: error: implicit declaration of function '__connman_p2p_set_dhcp_pool' [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:4943:9: error: implicit declaration of function 'connman_technology_tethering_remove_station'; did you mean 'connman_technology_remove_station'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:4981:9: error: implicit declaration of function '__connman_sd_response_from_p2p_peer' [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5055:9: error: implicit declaration of function 'connman_peer_set_strength'; did you mean 'connman_peer_set_state'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5056:9: error: implicit declaration of function 'connman_peer_set_config_methods'; did you mean 'g_supplicant_peer_get_config_methods'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5057:9: error: implicit declaration of function 'connman_peer_set_pri_dev_type'; did you mean 'connman_peer_set_sub_device'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5212:28: error: assignment to 'GList *' {aka 'struct _GList *'} from incompatible pointer type 'GSList *' {aka 'struct _GSList *'} [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:5225:22: error: passing argument 1 of 'g_slist_free' from incompatible pointer type [-Wincompatible-pointer-types]
# connman-1.42/plugins/wifi.c:5273:25: error: implicit declaration of function '__connman_storage_get_p2p_persistents_count'; did you mean '__connman_storage_get_p2p_persistents'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5456:25: error: implicit declaration of function 'connman_peer_dhcpclient_cb' [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5595:25: error: implicit declaration of function 'g_supplicant_interface_set_p2p_persistent_group'; did you mean 'g_supplicant_interface_get_p2p_persistent_group'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5605:17: error: implicit declaration of function '__connman_p2p_go_set_bridge'; did you mean '__connman_tethering_get_bridge'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5609:25: error: implicit declaration of function '__connman_p2p_go_set_enabled'; did you mean '__connman_bridge_enable'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:5672:25: error: implicit declaration of function '__connman_p2p_go_set_disabled'; did you mean '__connman_bridge_disable'? [-Wimplicit-function-declaration]
# connman-1.42/plugins/wifi.c:6024:36: error: initialization of 'void (*)(GSupplicantInterface *, GSupplicantP2PNetwork *, const char *, connman_bool_t)' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantP2PNetwork *, const char *, int)'} from incompatible pointer type 'void (*)(GSupplicantInterface *, GSupplicantPeer *, const char *, _Bool)' {aka 'void (*)(struct _GSupplicantInterface *, struct _GSupplicantPeer *, const char *, _Bool)'} [-Wincompatible-pointer-types]
CFLAGS += "-Wno-error=incompatible-pointer-types -Wno-error=int-conversion -Wno-error=implicit-function-declaration"
