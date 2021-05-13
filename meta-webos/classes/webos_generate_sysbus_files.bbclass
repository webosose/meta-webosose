# Copyright (c) 2021 LG Electronics, Inc.


inherit webos_system_bus
inherit webos_filesystem_paths

WEBOS_SYSTEM_BUS_CONFIGURE_FILES ??= "TRUE"

def webos_service_schema_validation_checker(service_info):
    import json
    import jsonschema
    from jsonschema import validate
    service_schema = {
        "id": "ServiceDescription",
        "type": "object",
        "properties": {
            "id": {
                "type": "string",
                "description": "ID of the service array., e.g., \"com.webos.sync.service\" . Every service array has a unique ID."
            },
            "description": {
                "type": "string",
                "description": "It provides description of the service array."
            },
            "version": {
                "type": "string",
                "description": "It shows the version of the service."
            },
            "schemaVersion": {
                "type": "string",
                "description": "It  just describes the  version of the schema."
            },
            "type": {
                "type": "string",
                "description": "it can be  either JS service or Native service",
                "enum": ["JS", "Native"]
            },
            "isDynamicService": {
                "type": "string",
                "default": "no",
                "description": "If it sets to  yes, then serviceType is  dynamic else serviceType is static."
            },
            "acgTrustLevel": {
                "type": "string",
                "enum": [
                    "oem",
                    "dev",
                    "part"
                ],
                "description": "service trust level"
            },
            "services": {
                "type": "array",
                "items": {
                    "type": "object",
                    "properties": {
                        "name": {
                            "type": "string",
                            "description": "It descrbies the service name"
                        },
                        "description": {
                            "type": "string",
                            "description": "service description"
                        },
                        "allowedNames": {
                            "type": "array",
                            "items": {
                                "type": "string"
                            },
                            "description": "It list out the multiple service names ."
                        },
                        "outbound": {
                            "type": "array",
                            "items": {
                                "type": "string"
                            },
                            "description": "It describes Array of services that this service is allowed to send requests to."
                        },
                        "isPrivileged": {
                            "type": "string",
                            "default": "no",
                            "description": "If it sets to yes, then type is prilieged else  type is regular."
                        },
                        "requiredPermissions": {
                            "type": "array",
                            "items": {
                                "type": "string"
                            },
                            "description": "It specifies the security groups required to run the service."
                        },
                        "Commands": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "name": {
                                        "type": "string",
                                         "description": "it gives absolute path of api of the service"
                                    },
                                    "description": {
                                        "type": "string",
                                        "description": "it gives the api description"
                                    },
                                    "groups": {
                                        "type": "array",
                                        "items": {
                                            "type": "string"
                                        },
                                        "description": "each api is placed into set of groups. it just list out the group names"
                                    }
                                },
                                "description": "Pair of name  and groups",
                                "required": ["name", "groups"]
                            },
                            "description": "An object array of functions of that service"
                        },
                        "groups": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "name": {
                                        "type": "string",
                                        "description": "it descibes group name"
                                    },
                                    "description": {
                                        "type": "string",
                                        "description": "it describes the group information"
                                    },
                                    "acgTrustLevel": {
                                        "type": "array",
                                        "items": {
                                            "type": "string"
                                        },
                                        "description": "it descibes group trust level"
                                    }
                                },
                                "description": "Pair of name and acgTrustLevel",
                                "required": ["name", "acgTrustLevel"]
                            },
                            "description": "An object array of  groups"
                        }
                    },
                    "description": "set of name, requiredPermissions, Commands and groups",
                    "required": ["name",  "requiredPermissions", "Commands", "groups"]
                },
                "description": "An object array of services"
            }
        },
        "required": [
            "id",
            "services"
        ]
    }

    try:
        validate(instance=service_info, schema=service_schema)
    except jsonschema.exceptions.ValidationError as err:
        return False
    return True



def webos_service_generate_security_files_get_immediate_subdirectories(root):
    import os
    return [name for name in os.listdir(root)
            if os.path.isdir(os.path.join(root, name))]

def webos_service_generate_api_file(d, service_info):
    import os
    import json
    bb.note("permFile: Start generating api.json file")
    service_id = service_info["id"]

    apiList = {}
    for service in service_info["services"]:
        for command in service["Commands"]:
            for group in command["groups"]:
                if service["name"] + "." + group not in apiList:
                    apiList[service["name"] + "." + group] = list()
                    apiList[service["name"] + "." + group].append(command["name"])
                else:
                    apiList[service["name"] + "." + group].append(command["name"])
    dst_dir = d.getVar("D", True)
    api_file_dir = d.getVar("webos_sysbus_apipermissionsdir", True)
    api_file = api_file_dir + "/" + service_id + ".api" + ".json"
    if not os.path.exists(dst_dir + api_file_dir):
        os.makedirs(dst_dir + api_file_dir)


    with open(dst_dir + api_file, "w") as f:
        json.dump(apiList, f, indent=4)
        f.write("\n")

    return api_file

def webos_service_generate_service_file(d, service_info, package_info):
    import os.path
    import json
    bb.note("service: Start generating service file")
    dst_dir = d.getVar("D", True)
    services_dir = d.getVar("webos_sysbus_servicedir", True)
    service_file = services_dir + "/" + service_info["id"] + ".service"
    if not os.path.exists(dst_dir + services_dir):
        os.makedirs(dst_dir + services_dir)

    if "isDynamicService" in service_info:
        Type = service_info["isDynamicService"]
        if (Type == "yes"):
            Type = "dynamic"
        else:
            Type = "static"
    else:
        Type = "dynamic"

    with open(dst_dir + service_file, "w") as f:
        f.write("[D-BUS Service] \n")
        f.write("Name=")
        f.write(service_info["id"])
        f.write("\n")
        f.write("Exec=/usr/bin/run-js-service  -n  /usr/palm/services/")
        f.write(service_info["id"])
        f.write("\n")
        f.write("Type=")
        f.write(Type)
        f.write("\n")
    return service_file


def webos_service_generate_perm_file(d, service_info):
    import os
    import json

    bb.note("permFile: Start generating perm.json file")
    service_id = service_info["id"]
    bb.note("permFile: service_id in services.json : ", service_id)

    permission = {}

    for perm in service_info["services"]:
        if "requiredPermissions" in perm:
            permission[service_id] = perm["requiredPermissions"]

    dst_dir = d.getVar("D", True)
    permissions_dir = d.getVar("webos_sysbus_permissionsdir", True)
    permission_file = permissions_dir + "/" + service_id + ".perm" + ".json"
    if not os.path.exists(dst_dir + permissions_dir):
        os.makedirs(dst_dir + permissions_dir)


    with open(dst_dir + permission_file, "w") as f:
        json.dump(permission, f, indent=4)
        f.write("\n")

    return permission_file

def webos_service_generate_role_file(d, service_info):
    import os
    import json
    bb.note("roleFile: Start generating role.json file")
    service_id = service_info["id"]
    bb.note("roleFile: service_id in services.json : ", service_id)

    role = {}

    role["appId"] = service_id
    role["type"]  = "regular"
    role["allowedNames"] = [service_id]
    role["permissions"] = [{"service": service_id, "outbound": ["*"] }]

    dst_dir   = d.getVar("D", True)
    roles_dir = d.getVar("webos_sysbus_rolesdir", True)
    role_file = roles_dir + "/" + service_id + ".role" + ".json"
    if not os.path.exists(dst_dir + roles_dir):
        os.makedirs(dst_dir + roles_dir)

    with open(dst_dir + role_file, "w") as f:
        json.dump(role, f, indent=4)
        f.write("\n")

    return role_file

def webos_service_generate_group_file(d, service_info):
    import os
    import json
    bb.note("groupFile: Start generating group.json file")
    service_id = service_info["id"]

    groupsList = {}
    groupsList["allowedNames"] = [service_id]
    for service  in service_info["services"]:
        for groups in service["groups"]:
            group =  service["name"] + "." + groups [ "name"]
            if group not in groupsList:
                groupsList[group] = list()
                groupsList[group]=groups["acgTrustLevel"]

    dst_dir = d.getVar("D", True)
    group_file_dir = d.getVar("webos_sysbus_groupsdir", True)
    group_file = group_file_dir + "/" + service_id + ".group" + ".json"
    if not os.path.exists(dst_dir + group_file_dir):
        os.makedirs(dst_dir + group_file_dir)

    with open(dst_dir + group_file, "w") as f:
        json.dump(groupsList, f, indent=4)
        f.write("\n")

    return group_file

def webos_service_generate_security_files_comment_remover(text):
    import re

    def replacer(match):
        s = match.group(0)
        return "" if s.startswith('/') else s

    pattern = re.compile(r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
        re.DOTALL | re.MULTILINE
    )
    return re.sub(pattern, replacer, text)

def webos_service_generate_sysbus_files_read_json(file):
    """ Read a JSON file with comments: //, /**/ """

    import json

    with open(file, "r") as f:
        content = f.read()

    content = webos_service_generate_security_files_comment_remover(content)
    return json.loads(content)

fakeroot python do_configure_sysbus_files() {
    import json
    import os.path

    bb.note("Start creating sysbus files")

    if d.getVar("WEBOS_SYSTEM_BUS_CONFIGURE_FILES", True) != "TRUE":
        return

    dst_dir = d.getVar("D", True)
    service_dir = dst_dir + d.getVar("webos_servicesdir",True)

    if not os.path.exists(service_dir):
        return

    builtInServices = webos_service_generate_security_files_get_immediate_subdirectories(service_dir)

    for builtInService in builtInServices:
        service_json_file = service_dir + "/" + builtInService + "/services.json"
        package_json_file = service_dir + "/" + builtInService + "/package.json"
        if not (os.path.exists(service_json_file) and os.path.exists(package_json_file)):
            continue

        service_info = webos_service_generate_sysbus_files_read_json(service_json_file)
        package_info = webos_service_generate_sysbus_files_read_json(package_json_file)
        isValid = webos_service_schema_validation_checker(service_info)
        if isValid:
            bb.note("Schema Validation is success: ", service_json_file)
            rolefile = webos_service_generate_role_file(d, service_info)
            permFile = webos_service_generate_perm_file(d,service_info)
            groupFile = webos_service_generate_group_file(d, service_info)
            apiFile = webos_service_generate_api_file(d, service_info)
            servicefile= webos_service_generate_service_file(d, service_info , package_info)
        else:
            bb.note("Schema Validation is failed : ", service_json_file)
}

addtask configure_sysbus_files after do_install do_configure_security before do_package
