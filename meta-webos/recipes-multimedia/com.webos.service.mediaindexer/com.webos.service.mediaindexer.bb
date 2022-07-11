# Copyright (c) 2020-2022 LG Electronics, Inc.

SUMMARY = "Media indexer service"
AUTHOR = "Kalaimani K <kalaimani.k@lge.com>"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSE;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://oss-pkg-info.yaml;md5=2bdfe040dcf81b4038370ae96036c519 \
"

WEBOS_VERSION = "1.0.0-22_db4e8b7cdb20f3cfccafa572f0aac3f97e058957"
PR = "r8"

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
FILES:${PN} += "${libdir}/*.so"

