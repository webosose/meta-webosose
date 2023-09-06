# Copyright (c) 2020-2023 LG Electronics, Inc.

SUMMARY = "Media indexer service"
AUTHOR = "Sujeet Nayak <Sujeet.nayak@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

WEBOS_VERSION = "1.0.0-25_41eadb20de0db9950251567680e83f4d218d9110"
PR = "r11"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_pkgconfig
inherit webos_system_bus
inherit webos_public_repo

# third party libraries
DEPENDS = "glib-2.0 libmtp libupnp libedit gstreamer1.0 gstreamer1.0-plugins-base taglib libpng libjpeg-turbo gdk-pixbuf jpeg libexif giflib"
# webos dependencies
DEPENDS += "luna-service2 pmloglib libpbnjson"
# webos runtime dependencies
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"
RDEPENDS:${PN} = "${VIRTUAL-RUNTIME_pdm} db8"

# http://gpro.lge.com/c/webosose/com.webos.service.mediaindexer/+/365247 CMakeLists.txt: respect libdir setting
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE} \
    file://0001-CMakeLists.txt-respect-libdir-setting.patch \
"
S = "${WORKDIR}/git"

inherit webos_systemd
WEBOS_SYSTEMD_SERVICE = "com.webos.service.mediaindexer.service"

# All service files will be managed in meta-lg-webos.
# The service file in the repository is not used, so please delete it.
# See the page below for more details.
# http://collab.lge.com/main/pages/viewpage.action?pageId=2031668745
do_install:append() {
    rm ${D}${sysconfdir}/systemd/system/com.webos.service.mediaindexer.service
}

#EXTRA_OECMAKE += " -DSTANDALONE=1"

# configure the maximum number of parallel meta data extractions
#EXTRA_OECMAKE += " -DPARALLEL_META_EXTRACTION=10"

# configure the folders on local storage which shall be scanned from
# storage plugin, the format is <path>,<name>,<description>;...
#EXTRA_OECMAKE += " -DSTORAGE_DEVS:string='/media/local,Media,Local Media Storage'"

# media indexer client library
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/*.so"

