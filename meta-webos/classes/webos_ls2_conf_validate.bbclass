# Copyright (c) 2023 LG Electronics, Inc.
#
# LS2 security configuration validation
#

inherit webos_filesystem_paths

# For some reason, using expr directly doesn't work
accumulate() {
    echo $(expr $1 + $2)
}

scan_and_validate() {
    # $1: directory to scan
    # $2: schema to use

    if [ ! -d "$1" ]; then
        bbnote "Skipping non-existent directory $1"
        echo 0; return
    fi

    local errors=0
    local schema="${IMAGE_ROOTFS}${webos_sysconfdir}/schemas/luna-service2/$2"
    local schema_old_group="${IMAGE_ROOTFS}${webos_sysconfdir}/schemas/luna-service2/old_groups.schema"

    bbnote "Scanning directory $1"
    if [ -f "$schema" ]; then
        bbnote "    with schema: $schema"
    else
        bbwarn "    without schema: $schema (missing)"
    fi

    for ff in $(find "$1" -type f -name "*.json"); do
        local errcode=0
        if [ ! -f "$schema" ]; then
            ${STAGING_BINDIR_NATIVE}/pbnjson_validate -f "$ff" > /dev/null 2>&1 || errcode=1
        else
            ${STAGING_BINDIR_NATIVE}/pbnjson_validate -f "$ff" -s "$schema" > /dev/null 2>&1 || errcode=2
            if [ $errcode -ne 0 -a "$2" = "groups.schema" -a -f $schema_old_group ]; then
                # additional handling with old_groups.schema
                errcode=0
                # Prepare allowed_names.schema if necessary
                if [ ! -f "${T}/allowed_names.schema" ]; then
                    echo '{"type":"object","properties":{"allowedNames":{"type":"array","items":{"type":"string"},"minItems":1,"uniqueItems":true}},"required":["allowedNames"]}' > "${T}/allowed_names.schema"
                fi
                if ( ${STAGING_BINDIR_NATIVE}/pbnjson_validate -f "$ff" -s "${T}/allowed_names.schema" > /dev/null 2>&1 ); then
                    ${STAGING_BINDIR_NATIVE}/pbnjson_validate -f "$ff" -s "$schema_old_group" > /dev/null 2>&1 || errcode=3
                else
                    ${STAGING_BINDIR_NATIVE}/pbnjson_validate -f "$ff" > /dev/null 2>&1 || errcode=1
                fi
            fi
        fi

        case $errcode in
            1)
                bberror "\t$(basename $ff) isn't a valid json"
                errors=$(accumulate $errors 1)
                ;;
            2)
                bberror "\t$(basename $ff) isn't valid as per $2"
                errors=$(accumulate $errors 1)
                ;;
            3)
                bberror "\t$(basename $ff) isn't valid as per old_groups.schema"
                errors=$(accumulate $errors 1)
                ;;
            *)
                bbnote "\t$(basename $ff) looks ok"
                ;;
        esac
    done

    bbnote "    Found $errors error(s)"
    bbnote "    Done for directory $1"

    # returns the number of validation errors
    echo $errors; return
}

fakeroot do_validate_ls2_security_conf() {
    local errors=0

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_manifestsdir} \
            "manifest.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_apipermissionsdir} \
            "api_permissions.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_permissionsdir} \
            "client_permissions.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_containersdir} \
            "container.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_rolesdir} \
            "role.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_pubrolesdir} \
            "old_role.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_prvrolesdir} \
            "old_role.schema" ))

    errors=$(accumulate $errors \
        $(scan_and_validate \
            ${IMAGE_ROOTFS}${webos_sysbus_groupsdir} \
            "groups.schema" ))

    if [ $errors -gt 0 ]; then
        bbfatal_log "Found $errors validation error(s). See details in log messages above."
    fi
}

addtask do_validate_ls2_security_conf after do_rootfs before do_image
do_validate_ls2_security_conf[depends] += "libpbnjson-native:do_populate_sysroot"
