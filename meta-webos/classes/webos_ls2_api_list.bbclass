# Copyright (c) 2022 LG Electronics, Inc.
inherit python3native

# A hook function to support extract_ls2_apis IMAGE_FEATURES
do_webos_extract_ls2_apis_hook () {
    bbnote "Path of Rootfs added ######  :  "+${IMAGE_ROOTFS}
    install -m 644 ${IMAGE_ROOTFS}/etc/luna-service2/ls-hubd.conf ${WORKDIR}/temp/
    ManifestsDirectories="Manifests"
    file_data="["
    manifestsArr=$(cat ${WORKDIR}/temp/ls-hubd.conf | sed -r '/[^=]+=[^=]+/!d' | sed -r 's/\s+=\s/=/g' | grep ${ManifestsDirectories})
    #while read line;
    for line in $manifestsArr
    do
        ManifestsDirectoryStr=$(echo $line | cut -d '=' -f 2)
        manifestsDirectoryarr=$(echo $ManifestsDirectoryStr | sed -e 's/;/\n/g')
        for ManifestsDirectoryPath in $manifestsDirectoryarr
        do
            echo "PATH is :::: " + $ManifestsDirectoryPath
            local jsonfiles
            ROOTFS_MANIFEST_DIR_PATH="${IMAGE_ROOTFS}${ManifestsDirectoryPath}"
            #jsonfiles=`find $ROOTFS_MANIFEST_DIR_PATH -not -name "*app*" -name "*.json"`
            MANIFESTKEY="apiPermissionFiles"
            echo "ROOTFS_MANIFEST_DIR_PATH :::::::::::::::::::::::::::::::::::: "+$ROOTFS_MANIFEST_DIR_PATH
            if [ ! -d "$ROOTFS_MANIFEST_DIR_PATH" ]; then
                continue
            fi
            jsonfiles=$(grep -rlw ${MANIFESTKEY} ${ROOTFS_MANIFEST_DIR_PATH})
            local jsonfile
            for jsonfile in $jsonfiles
            do
                newkey=$(basename $jsonfile .manifest.json)
                RESULT=$(cat $jsonfile | ${PYTHON} -c 'import json,sys;obj=json.load(sys.stdin);file_list=obj["apiPermissionFiles"];print(",".join(file_list))')
                strarr=$(echo $RESULT | sed -e 's/,/\n/g')
                local singleline
                for singleline in $strarr
                do
                    singlelinefullpath="${IMAGE_ROOTFS}${singleline}"
                    if [ ! -f "$singlelinefullpath" ] || [[ $singlelinefullpath == *compat.*.api.json ]] ; then
                        echo "File not found or Compat files excluded  :  "+${singlelinefullpath}
                        continue
                    fi
                    file_data="${file_data} {'$newkey':"

                    value=$(cat $singlelinefullpath | sed -e '/^\/\//d' -e 's@\(.*\)[[:blank:]]\{1,\}//.*@\1@' | ${PYTHON} -c 'import json,sys,os;obj=json.load(sys.stdin);json_list=[];[json_list.extend(obj[x]) for x in obj];print(json.dumps(json_list));')
                    file_data="${file_data} $value},"
                done
            done
        done
    done
    file_data_final=$(echo ${file_data} | sed 's/.$/]/')
    final_json_data="{'ls2_api_list':$file_data_final,'release_code_name':'${WEBOS_DISTRO_RELEASE_CODENAME}','distro':'${DISTRO}'}"
    #Create output file, override if already present
    final_result=$(echo $final_json_data | sed "s/'/\"/g")
    echo "webOS Release Code Name ::: " + ${WEBOS_DISTRO_RELEASE_CODENAME}
    echo "webOS Distro Name ::: " + ${DISTRO}
    output="${TOPDIR}/BUILD/final_json_data_file.json"
    #output="${LS2_JSON_PATH}/final_json_data_file.json"
    echo $final_result | tee $output
}

addtask do_webos_extract_ls2_apis_hook after do_rootfs before do_image
