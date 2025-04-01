# Copyright (c) 2015-2025 LG Electronics, Inc.
#
# webos_app_generate_security_files
#
# This class is to be inherited by the recipe for every application that needs
# to generate permission and role files from its appinfo.json.
# This will happen implicitly, as all such applications will inherit from
# webos_app, which inherits this class.
#
# Keep this code in sync with that in appinstalld that does the same thing
# until [DRD-4417] is implemented.
#

inherit webos_system_bus

WEBOS_SYSTEM_BUS_CONFIGURE_FILES ??= "TRUE"
WEBOS_SYSTEM_BUS_ACG_ENABLED ??= "FALSE"

def webos_app_generate_security_files_write_permission_file(d, app_info):
    import os
    import json

    app_id = app_info["id"]
    key    = app_id + "-*"
    type   = app_info["type"]

    if type == "native":
        key = app_id

    permission = {}

    if "requiredPermissions" in app_info:
        permission[key] = app_info["requiredPermissions"]
        if d.getVar("WEBOS_SYSTEM_BUS_ACG_ENABLED") == "FALSE":
            pub_bus = False
            prv_bus = False
            trust_level = app_info.get("trustLevel", "default")
            if trust_level == "default":
                pub_bus = True
            elif trust_level == "trusted":
                pub_bus = True
                prv_bus = True
            elif trust_level == "netcast":
                # According to https://wiki.lgsvl.com/display/webOSDocs/Security+Level+for+web+applications
                # netcast level dosn't have access to public and private bus
                pass
            else:
                bb.fatal("Unexpected trustLevel: " + trust_level)

            if type == "web":
                if "com.palm." in app_id or "com.webos." in app_id:
                    prv_bus = True
            elif type == "qml":
                prv_bus = True
                pub_bus = True
            elif type == "native":
                pub_bus = True

            if prv_bus:
                if "private" not in permission[key]:
                    permission[key].append("private")
                pub_bus = True

            if pub_bus:
                if "public" not in permission[key]:
                    permission[key].append("public")
    else:
        if d.getVar("WEBOS_SYSTEM_BUS_ACG_ENABLED") == "TRUE":
            permission[key] = []
        else:
            permission[key] = []
            pub_bus = False
            prv_bus = False
            trust_level = app_info.get("trustLevel", "default")
            if trust_level == "default":
                pub_bus = True
            elif trust_level == "trusted":
                pub_bus = True
                prv_bus = True
            elif trust_level == "netcast":
                # According to https://wiki.lgsvl.com/display/webOSDocs/Security+Level+for+web+applications
                # netcast level dosn't have access to public and private bus
                pass
            else:
                bb.fatal("Unexpected trustLevel: " + trust_level)

            if type == "web":
                if "com.palm." in app_id or "com.webos." in app_id:
                    prv_bus = True
            elif type == "qml":
                prv_bus = True
                pub_bus = True
            elif type == "native":
                pub_bus = True

            if prv_bus:
                permission[key].append("private")
                pub_bus = True

            if pub_bus:
                permission[key].append("public")

    if d.getVar("DISTRO") == "webos" or d.getVar("DISTRO") == "webos-auto":
        if type == "qml":
            permission[key].append("application.operation")

    dst_dir         = d.getVar("D")
    permissions_dir = d.getVar("webos_sysbus_permissionsdir")
    permission_file = permissions_dir + "/" + app_id + ".app.json"

    if not os.path.exists(dst_dir + permissions_dir):
        os.makedirs(dst_dir + permissions_dir)

    with open(dst_dir + permission_file, "w") as f:
        json.dump(permission, f, indent=4)
        f.write("\n")

    return permission_file

def webos_app_generate_security_files_write_role_file(d, app_info):
    import os
    import json

    app_id = app_info["id"]
    type = app_info["type"]
    trustLevelKey = "oem"

    role = {}
    if type == "native":
        exe_name = app_info["main"]
        app_dir = d.getVar("webos_applicationsdir")
        role["exeName"] = app_dir + "/" + d.getVar("BPN") + "/" + exe_name
        role["type"]  = "regular"
        role["allowedNames"] = [app_id + "*"]
        role["permissions"] = [{"service": app_id, "outbound": ["*"] }]
        role["trustLevel"] = trustLevelKey
    else:
        role["appId"] = app_id
        role["type"]  = "regular"
        role["allowedNames"] = [app_id + "-*"]
        role["permissions"] = [{"service": app_id + "-*", "outbound": ["*"] }]
        role["trustLevel"] = trustLevelKey

    dst_dir   = d.getVar("D")
    roles_dir = d.getVar("webos_sysbus_rolesdir")
    role_file = roles_dir + "/" + app_id + ".app.json"

    if not os.path.exists(dst_dir + roles_dir):
        os.makedirs(dst_dir + roles_dir)

    with open(dst_dir + role_file, "w") as f:
        json.dump(role, f, indent=4)
        f.write("\n")

    return role_file

def webos_app_generate_security_files_get_immediate_subdirectories(root):
    import os
    return [name for name in os.listdir(root)
            if os.path.isdir(os.path.join(root, name))]

def webos_app_generate_security_files_delete_comments(text):
    import re

    def replacer(match):
        s = match.group(0)
        return "" if s.startswith('/') else s

    pattern = re.compile(r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
        re.DOTALL | re.MULTILINE
    )
    return re.sub(pattern, replacer, text)

def webos_app_generate_security_files_read_json(file):
    """ Read a JSON file with comments: //, /**/ """

    import json

    with open(file, "r") as f:
        content = f.read()

    content = webos_app_generate_security_files_delete_comments(content)
    return json.loads(content)

fakeroot python do_configure_security() {
    import json
    import os.path

    if d.getVar("WEBOS_SYSTEM_BUS_CONFIGURE_FILES") != "TRUE":
        return

    dst_dir = d.getVar("D")
    app_dir = dst_dir + d.getVar("webos_applicationsdir")

    # ignore component that isn't app
    if not os.path.exists(app_dir):
        return

    roles_dir     = dst_dir + d.getVar("webos_sysbus_rolesdir")
    pub_roles_dir = dst_dir + d.getVar("webos_sysbus_pubrolesdir")
    prv_roles_dir = dst_dir + d.getVar("webos_sysbus_prvrolesdir")

    apps = webos_app_generate_security_files_get_immediate_subdirectories(app_dir)

    pkg_name = d.getVar("PN")
    for app in apps:
        app_info_file = app_dir + "/" + app + "/appinfo.json"

        # ignore app that doesn't have appinfo.json
        if not os.path.exists(app_info_file):
            continue

        # ignore app that already has role file
        role_file = roles_dir + "/" + app + ".role.json"
        if os.path.exists(role_file):
            continue

        # ignore app that already has public role file
        pub_role_file = pub_roles_dir + "/" + app + ".json"
        if os.path.exists(pub_role_file):
            continue

        # ignore app that already has private role file
        prv_role_file = prv_roles_dir + "/" + app + ".json"
        if os.path.exists(prv_role_file):
            continue

        app_info = webos_app_generate_security_files_read_json(app_info_file)

        type = app_info["type"]
        if type in ["qml", "web", "native"]:
            role_file       = webos_app_generate_security_files_write_role_file(d, app_info)
            permission_file = webos_app_generate_security_files_write_permission_file(d, app_info)
}

addtask configure_security after do_install before do_package
