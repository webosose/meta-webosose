# Copyright (c) 2015-2018 LG Electronics, Inc.

WEBOS_SYSTEM_BUS_MANIFEST_TYPE ??= "PACKAGE"

inherit webos_filesystem_paths

def webos_configure_manifest_template():
    manifest = {}
    manifest["id"] = ""
    manifest["version"] = "1.0.0"

    return manifest

def webos_configure_manifest_warn(d, message):
    pn = d.getVar("BPN", True)
    bb.warn("webos_configure_manifest: warning in package: %s, with message: %s" % (pn, message))

def webos_configure_manifest_lookup_files_by_ext(d, dir_var, ext):
    ret = []

    dst = d.getVar("D", True)
    rel_dir = d.getVar(dir_var, True)
    abs_dir = dst + rel_dir

    if not os.path.exists(abs_dir):
        return ret

    for f in os.listdir(abs_dir):
        if f.endswith(ext):
            ret.append(os.path.join(rel_dir, f))

    return sorted(ret)

def webos_configure_manifest_lookup_file_by_name(d, dir_name, srv_name):
    dst = d.getVar("D", True)
    rel_dir = d.getVar(dir_name, True)
    abs_dir = dst + rel_dir

    if not os.path.exists(abs_dir):
        return None

    for f in os.listdir(abs_dir):
        if srv_name in f:
            return os.path.join(rel_dir, f)

    return None

def webos_configure_manifest_find_file_by_name_or_pn(d, dirpath, name, ext):
    f = webos_configure_manifest_lookup_file_by_name(d, dirpath, name + ext)
    return f

def webos_configure_manifest_service(d):
    import os.path

    dirs = [ "webos_sysbus_servicedir", "webos_sysbus_pubservicesdir", "webos_sysbus_prvservicesdir" ]

    def generate_manifests(d, dirpath):
        found_srvs = webos_configure_manifest_lookup_files_by_ext(d, dirpath, ".service")

        manifests = []
        for srv in found_srvs:
            srv_name = os.path.splitext(os.path.basename(srv))[0]

            manifest = webos_configure_manifest_template()
            manifest["id"] = srv_name
            manifest["serviceFiles"] = [srv]

            role = None
            if "pub" in dirpath:
                role = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_pubrolesdir", srv_name, ".role.json")
                if role: manifest["roleFilesPub"] = [role]
            elif "prv" in dirpath:
                role = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_prvrolesdir", srv_name, ".role.json")
                if role: manifest["roleFilesPrv"] = [role]
            else:
                role = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_rolesdir", srv_name, ".role.json")
                if role: manifest["roleFiles"] = [role]

                provides = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_apipermissionsdir", srv_name, ".api.json")
                if provides: manifest["apiPermissionFiles"] = [provides]

                requires = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_permissionsdir", srv_name, ".perm.json")
                if requires: manifest["clientPermissionFiles"] = [requires]

            if role:
                manifests.append(manifest)
            else:
                webos_configure_manifest_warn(d, "Can not distinguish role file for service %s" % srv_name)

        return manifests

    manifests = generate_manifests(d, dirs[0])
    if len(manifests) != 0:
        return manifests

    manifests_pub = generate_manifests(d, dirs[1]) # public directory
    manifests_prv = generate_manifests(d, dirs[2]) # private direcotry

    # merge public and private to one manifest
    manifests = []

    # some packages don't provide both files
    for manifest_pub in manifests_pub:
        manifest =  webos_configure_manifest_template()
        manifest["id"] = manifest_pub["id"]

        if "serviceFiles" in manifest_pub:
            manifest["serviceFiles"] = manifest_pub["serviceFiles"]
        if "roleFilesPub" in manifest_pub:
            manifest["roleFilesPub"] = manifest_pub["roleFilesPub"]

        manifests.append(manifest)

    for manifest_prv in manifests_prv:
        manifest = None
        for manifest in manifests:
            if manifest["id"] == manifest_prv["id"]:
                break

        if manifest is None:
            # There is not any public manifest, generate new
            manifest = webos_configure_manifest_template()
            manifest["id"] = manifest_prv["id"]
            manifests.append(manifest)

        if "serviceFiles" in manifest_prv:
            if "serviceFiles" in manifest:
                manifest["serviceFiles"].extend(manifest_prv["serviceFiles"])
            else:
                manifest["serviceFiles"] = manifest_prv["serviceFiles"]

        if "roleFilesPrv" in manifest_prv:
            manifest["roleFilesPrv"] = manifest_prv["roleFilesPrv"]

    return manifests

def webos_configure_manifest_comment_remover(text):
    import re

    def replacer(match):
        s = match.group(0)
        return "" if s.startswith('/') else s

    pattern = re.compile(r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"', re.DOTALL | re.MULTILINE)
    return re.sub(pattern, replacer, text)

def webos_configure_manifest_application_from_appinfo(d, app_info_file):
    import json
    import os.path

    manifest = webos_configure_manifest_template()

    with open(app_info_file, "r") as f:
        app_info = json.loads(webos_configure_manifest_comment_remover(f.read()))


        def is_valid_version(version):
            """
                This function checks that string is valid version string
                according to semver.org.
            """
            import re
            pattern = re.compile("^(\d+\.\d+\.\d+)"
                                 "(?:-([0-9A-Za-z-]+(?:\.[0-9A-Za-z-]+)*))?"
                                 "(?:\+([0-9A-Za-z-]+(?:\.[0-9A-Za-z-]+)*))?$")
            return re.match(pattern, version)

        manifest["id"] = app_info["id"]
        manifest["version"] = app_info["version"]

        if not is_valid_version(manifest["version"]):
            webos_configure_manifest_warn(d, "Incompatible version string found in %s" % app_info_file)

        dst = d.getVar("D", True)

        # Possible behaviuors:
        # 1. There is native or native_builtin type
        # 1.1. The role is merged to service file
        # 1.2. The role is installed in place
        # 2. There is not native or native_builtin type, role files are generated by webos_app_configure_security
        if "native" in app_info["type"]:
            def warn_mismatch(d, dirpath):
                files = webos_configure_manifest_lookup_files_by_ext(d, dirpath, ".json")
                if len(files) == 0:
                    return False

                webos_configure_manifest_warn(d, "Can not determinate role file for application %s" % manifest["id"])
                webos_configure_manifest_warn(d, "Possible name mismatch required %s but found %s"
                    % (manifest["id"], ', '.join(files)))

                return True

            role_file = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_rolesdir", app_info["id"], ".json")
            if role_file:
                manifest["roleFile"] = [role_file]

                provides = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_apipermissionsdir", app_info["id"], ".json")
                if provides: manifest["apiPermissionFiles"]= [provides]

                requires = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_permissionsdir", app_info["id"], ".json")
                if requires: manifest["clientPermissionFiles"] = [requires]
                return manifest

            if warn_mismatch(d, "webos_sysbus_rolesdir"):
                return None

            role_file_pub = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_pubrolesdir", app_info["id"], ".json")
            role_file_prv = webos_configure_manifest_find_file_by_name_or_pn(d, "webos_sysbus_prvrolesdir", app_info["id"], ".json")

            if not role_file_pub and not role_file_prv:
                warn_mismatch(d, "webos_sysbus_pubrolesdir")
                warn_mismatch(d, "webos_sysbus_prvrolesdir")
                return None

            if role_file_pub: manifest["roleFilesPub"] = [role_file_pub]
            if role_file_prv: manifest["roleFilesPrv"] = [role_file_prv]
        else:
            role_dir_rel = d.getVar("webos_sysbus_rolesdir", True)
            role_file = os.path.join(role_dir_rel, manifest["id"] + ".app.json")
            if not os.path.exists(dst + role_file):
                webos_configure_manifest_warn(d, "Can not determinate role file for application %s" % manifest["id"])
                return None

            perm_dir = d.getVar("webos_sysbus_permissionsdir", True)
            perm_file = os.path.join(perm_dir, manifest["id"] + ".app.json")
            if not os.path.exists(dst + perm_file):
                webos_configure_manifest_warn(d, "Can not determinate client permissions file for application %s" % manifest["id"])
                return None

            manifest["roleFiles"] = [role_file]
            manifest["clientPermissionFiles"] = [perm_file]

    return manifest

def webos_configure_manifest_application(d):
    import os

    manifests = []
    def get_immediate_subdirectories(root):
        return [name for name in os.listdir(root)
            if os.path.isdir(os.path.join(root, name))]

    dst = d.getVar("D", True)
    app_dir = dst + d.getVar("webos_applicationsdir", True)
    if not os.path.exists(app_dir):
        return manifests

    apps_dir = get_immediate_subdirectories(app_dir)
    if len(apps_dir) == 0:
        webos_configure_manifest_warn(d, "There aren't any app in application dir")

    for app in apps_dir:
        app_info_file = os.path.join(app_dir, os.path.join(app, 'appinfo.json'))

        if not os.path.exists(app_info_file):
            # ignore application template
            if app_dir == d.getVar("BPN", True) and len(apps_dir) != 1:
                webos_configure_manifest_warn(d, "There is no application info for %s" % app)
            continue

        manifest = webos_configure_manifest_application_from_appinfo(d, app_info_file)
        if not manifest is None:
            manifests.append(manifest)
    return manifests

def webos_configure_manifest_package(d):
    import os

    pn = d.getVar("BPN", True)

    manifest = webos_configure_manifest_template()

    manifest["id"] = pn

    role = webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_rolesdir", ".json")
    if role: manifest["roleFiles"] = role

    role_pub = webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_pubrolesdir", ".json")
    if role_pub: manifest["roleFilesPub"] = role_pub

    role_prv = webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_prvrolesdir", ".json")
    if role_prv: manifest["roleFilesPrv"] = role_prv

    srv = []
    srv.extend(webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_servicedir", ".service"))
    srv.extend(webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_pubservicesdir", ".service"))
    srv.extend(webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_prvservicesdir", ".service"))
    if srv: manifest["serviceFiles"] = srv

    provides = webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_apipermissionsdir", ".json")
    if provides: manifest["apiPermissionFiles"] = provides

    requires = webos_configure_manifest_lookup_files_by_ext(d, "webos_sysbus_permissionsdir", ".json")
    if requires: manifest["clientPermissionFiles"] = requires

    return [manifest]

fakeroot python do_configure_manifest() {
    import os, json

    manifests = []

    manifest_type = d.getVar("WEBOS_SYSTEM_BUS_MANIFEST_TYPE", True)
    if manifest_type == "SERVICE":
        manifests.extend(webos_configure_manifest_service(d))
    elif manifest_type == "APPLICATION":
        manifests.extend(webos_configure_manifest_application(d))
    elif manifest_type == "PACKAGE":
        manifests.extend(webos_configure_manifest_package(d))
    elif manifest_type == "PASS":
        return
    else:
        webos_configure_manifest_warn(d, "Unrecognized manifest type %s" % manifest_type)

    if len(manifests) == 0:
        webos_configure_manifest_warn(d, "No manifests were configured")
        return

    man_dir = d.getVar("D", True) + d.getVar("webos_sysbus_manifestsdir", True)
    if not os.path.exists(man_dir):
        os.makedirs(man_dir)

    for manifest in manifests:
        name = os.path.join(man_dir, manifest["id"] + ".manifest.json")
        with open(name, "w+") as f:
            json.dump(manifest, f, indent = 4, sort_keys = False)
            f.write("\n")
}

addtask configure_manifest after do_install do_configure_security before do_package
