# Copyright (c) 2020-2021 LG Electronics, Inc.

SUMMARY = "Media indexer service"
AUTHOR = "Jaehoon Lee <jaehoon85.lee@lge.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

WEBOS_VERSION = "1.0.0-9_0ca6e4344066a16811eb16ab12ee98e30c18b3e6"
PR = "r5"

inherit webos_component
inherit webos_enhanced_submissions
inherit webos_cmake
inherit webos_system_bus
inherit webos_public_repo

# third party libraries
DEPENDS = "glib-2.0 libmtp libupnp libedit gstreamer1.0 gstreamer1.0-plugins-base taglib libpng libjpeg-turbo gdk-pixbuf jpeg libexif giflib"
# webos dependencies
DEPENDS += "luna-service2 pmloglib libpbnjson"
# webos runtime dependencies
VIRTUAL-RUNTIME_pdm ?= "com.webos.service.pdm"
RDEPENDS_${PN} = "${VIRTUAL-RUNTIME_pdm} db8"

SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
S = "${WORKDIR}/git"

# uncomment next line to use mediaindexer in shell/interactive mode
#EXTRA_OECMAKE += " -DSTANDALONE=1"

# configure the maximum number of parallel meta data extractions
#EXTRA_OECMAKE += " -DPARALLEL_META_EXTRACTION=10"

# configure the folders on local storage which shall be scanned from
# storage plugin, the format is <path>,<name>,<description>;...
#EXTRA_OECMAKE += " -DSTORAGE_DEVS:string='/media/local,Media,Local Media Storage'"

# media indexer client library
FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so"

