# Copyright (c) 2023 LG Electronics, Inc.

DESCRIPTION = "Components for diagnostics added to webOS"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PR = "r2"

inherit packagegroup
inherit features_check
inherit webos_machine_impl_dep

REQUIRED_DISTRO_FEATURES = "webos-diagnostics"

VIRTUAL-RUNTIME_event-monitor-network ?= "event-monitor-network"

RDEPENDS:${PN} = " \
    com.palm.service.devmode \
    pmklogd \
    pmlogctl \
    pmlogdaemon \
    webos-fluentbit-plugins \
    ${VIRTUAL-RUNTIME_event-monitor-network} \
"
