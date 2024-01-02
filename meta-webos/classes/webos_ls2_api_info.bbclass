# Copyright (c) 2021-2024 LG Electronics, Inc.

LS2_DIR = "${IMAGE_ROOTFS}${datadir}/luna-service2"

webos_ls2_api_info_data () {
    if [ -d "${LS2_DIR}" -a -n "${BUILDHISTORY_DIR_IMAGE}" ] ; then
        cd ${LS2_DIR}
        if [ -d ${BUILDHISTORY_DIR_IMAGE}/ls2_api ] ; then
            rm -rf ${BUILDHISTORY_DIR_IMAGE}/ls2_api
        fi
        mkdir -p ${BUILDHISTORY_DIR_IMAGE}/ls2_api
        cp -r ./* ${BUILDHISTORY_DIR_IMAGE}/ls2_api/
        cd -
    fi
}

ROOTFS_POSTPROCESS_COMMAND += "webos_ls2_api_info_data ;"
