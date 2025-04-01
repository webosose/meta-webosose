# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos10"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
    file://0001-Add-p2p-changes.patch \
"
# Replace the wpa_supplicant.service from wpa-supplicant source with our own version (for some unknown reason)
SYSTEMD_SERVICE:${PN}:remove = "wpa_supplicant.service"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "wpa-supplicant.service"
WEBOS_SYSTEMD_SCRIPT = "wpa-supplicant.sh"

do_configure:append() {
    # Enable DBus Introspection for easier debugging
    echo "CONFIG_CTRL_IFACE_DBUS_INTRO=y" >> ${B}/wpa_supplicant/.config

    # Enable debugging output to a file
    echo "CONFIG_DEBUG_FILE=y" >> ${B}/wpa_supplicant/.config

    # Redirect log to syslog instead of stdout
    echo "CONFIG_DEBUG_SYSLOG=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_DEBUG_SYSLOG_FACILITY=LOG_DAEMON" >> ${B}/wpa_supplicant/.config

    # P2P config
    echo "bss_max_count=400" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "max_num_sta=2" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "manufacturer=LGE" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "model_name=webOS" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "model_number=webOS" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "device_name=webOS" >> ${WORKDIR}/wpa_supplicant.conf-sane
    echo "serial_number=webOS" >> ${WORKDIR}/wpa_supplicant.conf-sane

    # Enable P2P (aka WiFi direct) support
    echo "CONFIG_P2P=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_AP=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_WPS=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_WPS2=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_WIFI_DISPLAY=y" >> ${B}/wpa_supplicant/.config
    echo "CONFIG_IEEE80211N=y" >> ${B}/wpa_supplicant/.config

    #Enable WEP Security
    echo "CONFIG_WEP=y" >> ${B}/wpa_supplicant/.config

}

do_install:append() {
    # Remove the wpa_supplicant.service from upstream, but be aware that we're still
    # keeping upstream wpa_supplicant-nl80211@.service wpa_supplicant@.service  wpa_supplicant-wired@.service
    rm -vf ${D}${systemd_system_unitdir}/wpa_supplicant.service

    # Replace the removed wpa_supplicant.service from upstream with our =wpa-supplicant.service
    sed -i 's/SystemdService=wpa_supplicant.service/SystemdService=wpa-supplicant.service/g' ${D}/${datadir}/dbus-1/system-services/*service
}

FILES:${PN} += "${systemd_unitdir}"
