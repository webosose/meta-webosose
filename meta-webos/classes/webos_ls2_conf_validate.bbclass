# Copyright (c) 2023-2024 LG Electronics, Inc.
#
# LS2 security configuration validation
#

inherit webos_filesystem_paths

WEBOS_LS2_CONF_VALIDATE_ERROR_ON_WARNING ?= "0"
WEBOS_LS2_CONF_VALIDATE_SKIP_GROUP ?= ""

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

fakeroot python do_validate_ls2_acg() {
    import os
    import json

    rootfs_groups_d = d.getVar("IMAGE_ROOTFS") + d.getVar("webos_sysbus_groupsdir")
    rootfs_api_perms_d = d.getVar("IMAGE_ROOTFS") + d.getVar("webos_sysbus_apipermissionsdir")
    rootfs_clientperms_dir = d.getVar("IMAGE_ROOTFS") + d.getVar("webos_sysbus_permissionsdir")

    # There can be no sysbus directories in a tiny image
    if not os.path.isdir(rootfs_groups_d):
        bb.note("Directory '%s' is missing, skipping validation." % rootfs_groups_d)
        return
    if not os.path.isdir(rootfs_api_perms_d):
        bb.note("Directory '%s' is missing, skipping validation." % rootfs_api_perms_d)
        return
    if not os.path.isdir(rootfs_clientperms_dir):
        bb.note("Directory '%s' is missing, skipping validation." % rootfs_clientperms_dir)
        return

    # List of group names to skip checking
    skip_group = d.getVar("WEBOS_LS2_CONF_VALIDATE_SKIP_GROUP").split()
    if len(skip_group) > 0:
        msg = "=== LIST BEGIN: Groups considered as exception ===\n"
        for group in sorted(skip_group):
            msg += "  %s\n" % group
        msg += "=== LIST END ===\n"
        bb.warn(msg)
    # Always skip 'allowedNames' which is being used a key in old-style groups.json
    skip_group.append("allowedNames")

    # Returns a set of group names defined in 'dir'.
    # Json files in 'dir' are expected to have groups as keys.
    def read_groups(dir):
        msg = "Reading groups from %s\n" % dir
        groups = set()
        with os.scandir(dir) as it:
            for entry in it:
                if entry.is_file():
                    msg += "  %s\n" % entry.name
                    with open(entry.path) as fp:
                        groups_json = json.load(fp)
                        groups.update(filter(lambda x: x not in skip_group, groups_json.keys()))
        msg += "Done reading groups from %s\n" % dir
        bb.debug(1, msg)
        return groups

    # Returns a set of group names used in 'perm_entry' but not in 'groups'.
    # 'groups' is a set of group names to match and 'perm_entry' is an
    # iterator entry of a file that refers groups in an array form.
    def get_missing_groups_in_perm(groups, perm_entry):
        msg = "Checking groups in %s\n" % perm_entry.name
        missing_groups = set()
        with open(perm_entry.path) as fp:
            perm_json = json.load(fp)
            for groups_used in perm_json.values():
                for group in groups_used:
                    if not group in groups:
                        missing_groups.add(group)
            for group in sorted(missing_groups):
                msg += "  %s%s" % (group, " => missing" if not group in groups else "\n")
        msg += "Done checking groups in %s\n" % perm_entry.name
        bb.debug(1, msg)
        return missing_groups

    # First, we build a set of groups defined in "groups.d".
    groups_defined = read_groups(rootfs_groups_d)
    msg = "=== LIST BEGIN: Groups defined in groups.d(%s) ===\n" % rootfs_groups_d
    for group in sorted(groups_defined):
        msg += "  %s\n" % group
    msg += "=== LIST END ===\n"

    # Second, get groups from "api-permissions.d".
    # Those groups are also considered as valid.
    groups_defined2 = read_groups(rootfs_api_perms_d)
    msg += "=== LIST BEGIN: Groups used in api-permissions.d(%s) ===\n" % rootfs_api_perms_d
    for group in sorted(groups_defined2):
        msg += "  %s\n" % group
    msg += "=== LIST END ===\n"
    bb.debug(2, msg)

    # Merge groups from "groups.d" and "api-permissions.d" with showing differences.
    # Those differences are recommended to define in "groups.d".
    groups_defined2.difference_update(groups_defined)
    cnt = len(groups_defined2)
    if cnt > 0:
        msg = "Found %d group(s) that appear only in api-permissions.d, consider define them in groups.d\n" % cnt
        msg += "=== LIST BEGIN: Groups used in api-permissions.d but not defined in groups.d ===\n"
        for group in sorted(groups_defined2):
            msg += "  %s\n" % group
        msg += "=== LIST END ===\n"
        bb.warn(msg)
    groups_valid = groups_defined.union(groups_defined2)
    msg = "=== LIST BEGIN: Groups considered as valid ===\n"
    for group in sorted(groups_valid):
        msg += "  %s\n" % group
    msg += "=== LIST END ===\n"
    bb.note(msg)

    # Iterate files in "client-permissions.d" and list up groups
    # which don't appear in the set built above.
    groups_missing = {}
    with os.scandir(rootfs_clientperms_dir) as it:
        for entry in it:
            if entry.is_file():
                for group in get_missing_groups_in_perm(groups_valid, entry):
                    if group not in skip_group:
                        if group in groups_missing:
                            groups_missing[group] += [entry.name]
                        else:
                            groups_missing[group] = [entry.name]

    # Raise a warning or error(if enabled) if any missing group is found.
    cnt = len(groups_missing)
    if cnt > 0:
        msg = "Found %d group(s) used in client-permissions.d but not defined\n" % cnt
        msg += "=== LIST BEGIN ===\n"
        for group in sorted(groups_missing):
            msg += "'%s' being used in:\n" % group
            for entry in sorted(groups_missing[group]):
                msg += "  %s\n" % entry
        msg += "=== LIST END =====\n"
        bb.warn(msg)
        if d.getVar("WEBOS_LS2_CONF_VALIDATE_ERROR_ON_WARNING") != "0":
            bb.fatal("Fatal error while checking groups, aborting!")
}

addtask do_validate_ls2_security_conf after do_rootfs before do_image
addtask do_validate_ls2_acg after do_validate_ls2_security_conf before do_image
do_validate_ls2_security_conf[depends] += "libpbnjson-native:do_populate_sysroot"
