# Copyright (c) 2017-2023 LG Electronics, Inc.
#
# QML syntax error and problematic pattern checker
#

DEPENDS:append = " qtdeclarative-native libxml2-native"

inherit qt6-paths

WEBOS_QMLLINT_EXTRA_VALIDATION ?= "0"
WEBOS_QMLLINT_ERROR_ON_WARNING ?= "0"
WEBOS_QMLLINT_ERROR_LOG ?= "${T}/qmllint_error.log"
WEBOS_QMLLINT_OPTIONS ?= ""

get_qmllint_options() {
    local options=""
    if [ "${WEBOS_QMLLINT_OPTIONS}" = "" ]; then
        if [ "${QT_VERSION}" = "5" ]; then
            options=""
        else
            version=$(${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint -v | awk '{print $2}')
            version_check=$(echo "${version}\n6.5.0" | sort -V | head -n 1)
            if [ "${version_check}" != "6.5.0" ]; then
                # older than 6.5.0
                options=" \
                    --alias info \
                    --deprecated info \
                    --import info \
                    --inheritance-cycle info \
                    --multiline-strings info \
                    --property info \
                    --required info \
                    --signal info \
                    --type info \
                    --unqualified info \
                    --unused-imports info \
                    --with info \
                    -I ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QML} \
                "
            else
                options=" \
                    --alias-cycle info \
                    --deprecated info \
                    --import info \
                    --incompatible-type info \
                    --inheritance-cycle info \
                    --multiline-strings info \
                    --missing-property info \
                    --missing-type info \
                    --non-list-property info \
                    --prefixed-import-type info \
                    --read-only-property info \
                    --required info \
                    --restricted-type info \
                    --signal-handler-parameters info \
                    --unqualified info \
                    --unresolved-alias info \
                    --unresolved-type info \
                    --unused-imports info \
                    --with info \
                    -I ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QML} \
                "
            fi
        fi
    else
        options="${WEBOS_QMLLINT_OPTIONS}"
    fi
    echo "${options}"
}

do_compile:prepend () {
    bbnote "Checking QML syntax error and problematic pattern (Step 1): .qml or .js files stored as qresource"
    rm -f ${WEBOS_QMLLINT_ERROR_LOG}
    find ${S} -type f -name "*.qrc" | while read qrc; do
        local _dirname_=$(dirname $qrc)
        ${STAGING_BINDIR_NATIVE}/xmllint --xpath '//RCC/qresource/file' $qrc | sed 's/<file>//g' | sed 's/<\/file>/\n/g' | grep -E "*.qml$|*.js$" | while read file; do
            if [ -s "${_dirname_}/${file}" ]; then
                ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "${_dirname_}/${file}" $(get_qmllint_options) \
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

do_install:append () {
    bbnote "Checking QML syntax error and problematic pattern (Step 2): .qml or .js files to be installed"
    rm -f ${WEBOS_QMLLINT_ERROR_LOG}
    find ${D} -type f -not -empty -name "*.qml" -o -name "*.js" | while read file; do
        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "${file}" $(get_qmllint_options) \
            || echo "Found syntax error in ${file}" >> ${WEBOS_QMLLINT_ERROR_LOG}
        if [ "${WEBOS_QMLLINT_EXTRA_VALIDATION}" = "1" ]; then
            PATH=${STAGING_BINDIR_NATIVE}/python3-native:${PATH} \
            WEBOS_QMLLINT_ERROR_ON_WARNING=${WEBOS_QMLLINT_ERROR_ON_WARNING} \
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
