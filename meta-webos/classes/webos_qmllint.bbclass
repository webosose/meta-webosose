# Copyright (c) 2017-2020 LG Electronics, Inc.
#
# QML syntax error and problematic pattern checker
#

DEPENDS_append = " qtdeclarative-native libxml2-native"

inherit qmake5_paths

WEBOS_QMLLINT_EXTRA_VALIDATION ?= "0"
WEBOS_QMLLINT_ERROR_LOG ?= "${T}/qmllint_error.log"

do_compile_prepend () {
    bbnote "Checking QML syntax error and problematic pattern (Step 1): .qml or .js files stored as qresource"
    rm -f ${WEBOS_QMLLINT_ERROR_LOG}
    find ${S} -type f -name "*.qrc" | while read qrc; do
        local _dirname_=$(dirname $qrc)
        ${STAGING_BINDIR_NATIVE}/xmllint --xpath '//RCC/qresource/file' $qrc | sed 's/<file>//g' | sed 's/<\/file>/\n/g' | grep -E "*.qml$|*.js$" | while read file; do
            if [ -f "${_dirname_}/${file}" ]; then
                ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "${_dirname_}/${file}" \
                    || echo "Found syntax error in ${_dirname_}/${file}" >> ${WEBOS_QMLLINT_ERROR_LOG}
                if [ "${WEBOS_QMLLINT_EXTRA_VALIDATION}" = "1" ]; then
                    PATH=${STAGING_BINDIR_NATIVE}/python3-native:${PATH} \
                        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint-supplement.py "${_dirname_}/${file}" \
                        || echo "Found problematic code pattern in ${_dirname_}/${file}" >> ${WEBOS_QMLLINT_ERROR_LOG}
                fi
            fi
        done
    done
    if [ -e ${WEBOS_QMLLINT_ERROR_LOG} ]; then
        bbfatal_log "Found QML errors, see ${WEBOS_QMLLINT_ERROR_LOG} or BitBake logs for detail (Step 1)"
    else
        bbnote "Done validation, no QML errors found (Step 1)"
    fi
}

do_install_append () {
    bbnote "Checking QML syntax error and problematic pattern (Step 2): .qml or .js files to be installed"
    rm -f ${WEBOS_QMLLINT_ERROR_LOG}
    find ${D} -type f -name "*.qml" -o -name "*.js" | while read file; do
        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "${file}" \
            || echo "Found syntax error in ${file}" >> ${WEBOS_QMLLINT_ERROR_LOG}
        if [ "${WEBOS_QMLLINT_EXTRA_VALIDATION}" = "1" ]; then
            PATH=${STAGING_BINDIR_NATIVE}/python3-native:${PATH} \
                ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint-supplement.py "${file}" \
                || echo "Found problematic code pattern in ${file}" >> ${WEBOS_QMLLINT_ERROR_LOG}
        fi
    done
    if [ -e ${WEBOS_QMLLINT_ERROR_LOG} ]; then
        bbfatal_log "Found QML errors, see ${WEBOS_QMLLINT_ERROR_LOG} or BitBake logs for detail (Step 2)"
    else
        bbnote "Done validation, no QML errors found (Step 2)"
    fi
}
