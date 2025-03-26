# Copyright (c) 2025 LG Electronics, Inc.

require media-codec-interface.inc

WEBOS_REPO_NAME = "media-codec-interface"

PR = "${INC_PR}.0"

EXTRA_OECMAKE += "-DWRAPPER_BUILD=ON"

FILES:${PN} += "${LIBCBE_DIR}/lib*${SOLIBS}"
FILES:${PN}-dev += "${LIBCBE_DIR}/lib*${SOLIBSDEV}"
