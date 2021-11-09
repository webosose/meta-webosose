# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://wpa-supplicant.sh \
            file://wpa-supplicant.service \
            file://0001-Add-p2p-changes.patch \
"
# Replace the wpa_supplicant.service from wpa-supplicant source with our own version (for some unknown reason)
SYSTEMD_SERVICE_${PN}_remove = "wpa_supplicant.service"
SYSTEMD_SERVICE_${PN}_append = " wpa-supplicant.service"

do_configure_append() {
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

}

do_install_append() {
    # systemd service files
    install -d ${D}${sysconfdir}/systemd/system
    install -v -m 644 ${WORKDIR}/wpa-supplicant.service ${D}${sysconfdir}/systemd/system/wpa-supplicant.service
    # Remove the wpa_supplicant.service from upstream, but be aware that we're still
    # keeping upstream wpa_supplicant-nl80211@.service wpa_supplicant@.service  wpa_supplicant-wired@.service
    rm -vf ${D}${systemd_unitdir}/system/wpa_supplicant.service
    install -d ${D}${sysconfdir}/systemd/system/scripts
    install -v -m 755 ${WORKDIR}/wpa-supplicant.sh ${D}${sysconfdir}/systemd/system/scripts/

    # Replace the removed wpa_supplicant.service from upstream with our =wpa-supplicant.service
    sed -i 's/SystemdService=wpa_supplicant.service/SystemdService=wpa-supplicant.service/g' ${D}/${datadir}/dbus-1/system-services/*service
}

FILES_${PN} += "${systemd_unitdir}"
