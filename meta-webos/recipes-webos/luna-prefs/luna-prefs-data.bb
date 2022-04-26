# Copyright (c) 2015-2022 LG Electronics, Inc.

# WARNING: This recipe appears in the SIGGEN_EXCLUDERECIPES_ABISAFE list in
# conf/layer.conf which means that changes to it will not be used in the generation
# of sstate signatures. This is unlikely to cause problems, unless luna-prefs is
# changed to no longer use the files under /etc/prefs/properties to determine the
# properties its exposes and their values.

SUMMARY = "Preferences Manager data for ${MACHINE}"
AUTHOR = "Oleksandr Ivanov <oleksandr.ivanov@lge.com>"
SECTION = "webos/base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PV = "3.0.0"
PR = "r3"

inherit webos_machine_dep

PRODUCT_DEVICE_NAME ?= "${DISTRO_NAME} Device"
PRODUCT_DEVICE_NAME_BRANDED ?= "LGE ${DISTRO_NAME} Device"
PRODUCT_DEVICE_NAME_SHORT ?= "${@ '${DISTRO_NAME}'.split()[1] if len('${DISTRO_NAME}'.split()) >= 2 else '${DISTRO_NAME}'} Device"
PRODUCT_DEVICE_NAME_SHORT_BRANDED ?= "LGE ${DISTRO_NAME} Device"
PRODUCT_DEVICE_NAME_PRODUCT_LINE_NAME ?= "${DISTRO_NAME} Device"
PRODUCT_DEVICE_NAME_PRODUCT_CLASS ?= "${@ '${DISTRO_NAME}'.split()[1] if len('${DISTRO_NAME}'.split()) >= 2 else '${DISTRO_NAME}'}"
PRODUCT_DEVICE_NAME_PRODUCT_BROWSER_OS_NAME ?= "${DISTRO_NAME}"

do_install() {
    install -d ${D}${sysconfdir}/prefs/properties

    echo -n "${PRODUCT_DEVICE_NAME}"                         > ${D}${sysconfdir}/prefs/properties/deviceName
    echo -n "${PRODUCT_DEVICE_NAME_BRANDED}"                 > ${D}${sysconfdir}/prefs/properties/deviceNameBranded
    echo -n "${PRODUCT_DEVICE_NAME_SHORT}"                   > ${D}${sysconfdir}/prefs/properties/deviceNameShort
    echo -n "${PRODUCT_DEVICE_NAME_SHORT_BRANDED}"           > ${D}${sysconfdir}/prefs/properties/deviceNameShortBranded
    echo -n "${MACHINE}"                                     > ${D}${sysconfdir}/prefs/properties/machineName
    echo -n "${PRODUCT_DEVICE_NAME_PRODUCT_LINE_NAME}"       > ${D}${sysconfdir}/prefs/properties/productLineName
    echo -n "${PRODUCT_DEVICE_NAME_PRODUCT_CLASS}"           > ${D}${sysconfdir}/prefs/properties/productClass
    echo -n "${PRODUCT_DEVICE_NAME_PRODUCT_BROWSER_OS_NAME}" > ${D}${sysconfdir}/prefs/properties/browserOsName
}

