# Copyright (c) 2023-2025 LG Electronics, Inc.

SUMMARY = "GStreamer Rust Plugins common for webos"
AUTHOR = "Seungwook Cha <seungwook.cha@lge.com>"

LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=6762ed442b3822387a51c92d928ead0d"

inherit cargo cargo-update-recipe-crates
INC_PR = "r0"
DEPENDS = "gstreamer1.0 gstreamer1.0-plugins-base"

WEBOS_REPO_NAME = "gst-plugins-webos"
SRC_URI = "${WEBOSOSE_GIT_REPO_COMPLETE}"
WEBOS_VERSION = "1.18.5-rust.11_cac1c661e3f79a8ec407d5e8140022ba47c0c1fe"
S = "${WORKDIR}/git"

require gstreamer1.0-plugins-webosrs-crates.inc

export gst_libdir = "${libdir}/gstreamer-1.0"
do_install() {
    install -d ${D}${gst_libdir}
    install ${B}/target/${CARGO_TARGET_SUBDIR}/*.so ${D}${gst_libdir}
}
FILES:${PN} +=  "${gst_libdir}"
