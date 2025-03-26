#!/bin/sh
# @@@LICENSE
#
# Copyright (c) 2024-2025 LG Electronics, Inc.
#
# Confidential computer software. Valid license from LG required for
# possession, use or copying. Consistent with FAR 12.211 and 12.212,
# Commercial Computer Software, Computer Software Documentation, and
# Technical Data for Commercial Items are licensed to the U.S. Government
# under vendor's standard commercial license.
#
# LICENSE@@@
#
if [[ -z ${CAMERA_REGISTRY_PATH} ]]; then
    REGISTRY_PATH="/mnt/lg/cmn_data/camera/camera_plugin_registry.json"
else
    REGISTRY_PATH="${CAMERA_REGISTRY_PATH}"
fi

eval $(nyx-cmd OSInfo query --format=shell webos_release webos_build_id)
CAMERA_REGISTRY_STAMP="${REGISTRY_PATH}.done.$webos_release-$webos_build_id"

logger "Checking camera plugins registry file: ${REGISTRY_PATH}"
if [ ! -f ${REGISTRY_PATH} ] || [ ! -f ${CAMERA_REGISTRY_STAMP} ] ; then
    logger "Building camera plugins registry file"
    /usr/bin/camera-plugin-inspect > /var/tmp/camera-plugin-inspect.log
    chmod 644 ${REGISTRY_PATH}
fi

logger "Checking webos version for camera plugins registry"
if [ ! -f ${CAMERA_REGISTRY_STAMP} ] ; then
    logger -s "Removing camera plugins registry flag file"
    rm -f ${REGISTRY_PATH}.done.*
    touch ${CAMERA_REGISTRY_STAMP}
fi

logger "Finish checking camera plugins registry"

