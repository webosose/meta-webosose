#!/usr/bin/env python2

# @@@LICENSE
#
# Copyright (c) 2015-2023 LG Electronics, Inc.
#
# Unless otherwise specified or set forth in the NOTICE file, all content,
# including all source code files and documentation files in this repository are:
# Confidential computer software. Valid license from LG required for
# possession, use or copying.
#
# LICENSE@@@

import os, sys
import json
import itertools
import copy
import re
import configparser
from subprocess import call


for n in ("acg_dirty", "acg_json", "acg_api", "acg_perm", "ls2_role",
          "ls2_schema"):
    locals()[n] = n.replace('_', '-')

def handle_error(c, m):
    sys.stderr.write("[%s] %s\n" % (c, m))


def concat_map(fun, it):
    return itertools.chain.from_iterable(itertools.imap(fun, it))


def ValidateJson(json_path, schema_path, prefix='/'):
    '''Validate JSON file against JSON schema'''
    status = call(["pbnjson_validate", "-s", schema_path, "-f", json_path])
    if status != 0:
        handle_error(ls2_schema, "/%s: doesn't match JSON schema %s" %
                                 (os.path.relpath(json_path, prefix),
                                  os.path.basename(schema_path)))


class CommentSripper:

    TOKEN_RE = re.compile(
            r'''
             "[^"\\]*(\\.[^"\\]*)*"        # string in quotes
             |
             //[^\n]*                      # c++-style comments
             |
             /\*[^*]*(\*+[^/*][^*]*)*\*+/  # c-style comments
             ''',
            re.DOTALL | re.MULTILINE | re.VERBOSE
            )

    @classmethod
    def _walk_non_comments(cls, text):
        i = 0
        for m in cls.TOKEN_RE.finditer(text):
            j = m.start()
            assert j < m.end() # all matches have non-zero length
            if text[j] == '/':
                yield text[i:j]
                i = m.end()
        yield text[i:]

    @classmethod
    def strip(cls, text):
        return "".join(cls._walk_non_comments(text))


def LoadJson(path):
    '''
    Load json file with given path, first remove comments, then report if
    there are duplicate keys, and return parsed JSON object.

    Comments look like :
        // ...
    or
        /*
        ...
        */
    '''

    def parse_props(props):
        obj = dict(props)
        if len(obj) < len(props) and not parse_props.reported:
            handle_error(acg_json, "%s: file contains multiple props with same name" % (path))
            parse_props.reported = True
        return obj
    parse_props.reported = False

    with open(path, 'r') as f:
        content = ''.join(f.readlines())
        content = CommentSripper.strip(content)
        return json.loads(content, object_pairs_hook = parse_props)

def ReadGroupDeclarations(path, groups):
    ''' Read group declarations file into a map {group: is_public}. '''
    data = LoadJson(path)
    for group, v in data.items():
        groups[group] = v['public']

def ReadApiPermissions(path, api_permissions):
    ''' Process API permission files '''

    data = LoadJson(path)

    for group, methods in data.items():
        if not group in api_permissions:
            api_permissions[group] = set()

        # Populate the api_permissions map, ensuring method uniqueness within a group
        for method in methods:
            if method in api_permissions[group]:
                handle_error(acg_api, "%s: duplicate method %s" % (group, method))
            else:
                api_permissions[group].add(method)

def ReadClientPermissions(path, client_permissions):
    ''' Process client permission files '''

    data = LoadJson(path)

    for service, groups in data.items():
        if not service in client_permissions:
            client_permissions[service] = set()

        for group in groups:
            if group in client_permissions[service]:
                handle_error(acg_perm, "%s: duplicate group %s in client permissions" % (service, group))
            else:
                client_permissions[service].add(group)


class LSConf:
    def __init__(self, path, rootfs='/'):
        self._conf = None
        self._rootfs = rootfs
        full_path = self.resolve_path(path)
        if os.path.exists(full_path):
            self._conf = ConfigParser.ConfigParser()
            self._conf.read(full_path)

    def _dirs(self, group, key):
        return map(self.resolve_path, self._conf.get(group, key).split(';'))

    def resolve_path(self, path):
        return os.path.join(self._rootfs, os.path.relpath(path, '/'))

    @property
    def valid(self): return self._conf is not None

    @property
    def schemas_dir(self):
        return self.resolve_path("/etc/palm/schemas/luna-service2")

    @property
    def manifests_dirs(self):
        return self._dirs('Security', 'ManifestsDirectories')

    @property
    def containers_dirs(self):
        return self._dirs('Security', 'ContainersDirectories')

    def schema_path(self, name):
        return os.path.join(self.schemas_dir, name + ".schema")


def ListFiles(directory):
    ''' Yield files in directory and check if they are not executable '''
    if not os.path.isdir(directory):
        handle_error(acg_dirty, "%s: no such directory" % (directory,))
        return

    for name in os.listdir(directory):
        path = os.path.join(directory, name)
        if os.access(path, os.X_OK):
            handle_error(acg_dirty, "%s: file is executable" % path)
        yield path

def ReadGroups(rootfs):
    ''' Read group files '''

    group_declarations = {}

    gdir = os.path.join(rootfs, "usr/share/luna-service2/groups.d")
    if os.path.isdir(gdir):
        for gfile in ListFiles(gdir):
            ReadGroupDeclarations(gfile, group_declarations)

    return group_declarations


def CheckRole(full_path, prefix='/'):
    '''
    Check role files with given path for generic errors:
      * JSON error (collateral)
      * No permissions for a declared allowed name
      * Duplicate permissions for a declared allowed name
      * Permissions for undeclared allowed name
    '''
    path = os.path.join('/', os.path.relpath(full_path, prefix))
    try:
        role = LoadJson(full_path)
        if "role" in role:
            allowedNames = role["role"]["allowedNames"]
        else:
            allowedNames = role["allowedNames"]
        permissions = role["permissions"]
        for name in allowedNames:
            service = [x for x in permissions if x["service"] == name]
            if not service:
                handle_error(ls2_role, "%s: No permissions for allowed name '%s'" % (path, name))
            if len(service) > 1:
                handle_error(ls2_role, "%s: Duplicate permissions for allowed name '%s'" % (path, name))
            for s in service:
                permissions.remove(s)
        for p in permissions:
            handle_error(ls2_role, "%s: Permissions without allowed name '%s'" % (path, p["service"]))
    except ValueError as err:   # General JSON decoder error
        handle_error(ls2_role, "%s: %s" % (path, err))


def ReadManifests(rootfs, ls_hub_conf):
    ''' Read permission files from manifests '''

    resolve_path = ls_hub_conf.resolve_path
    manifest_schema = ls_hub_conf.schema_path("manifest")
    role_schema = ls_hub_conf.schema_path("role")
    old_role_schema = ls_hub_conf.schema_path("old_role")
    api_schema = ls_hub_conf.schema_path("api_permissions")
    perm_schema = ls_hub_conf.schema_path("client_permissions")

    # Map {group: set of methods} as in API permission files
    api_permissions = {}

    # Map {service: set of groups}
    client_permissions = {}

    for mdir in ls_hub_conf.manifests_dirs:
        if not os.path.isdir(mdir):
            continue
        for mfile in ListFiles(mdir):
            ValidateJson(mfile, manifest_schema, rootfs)
            manifest = LoadJson(mfile)
            if 'apiPermissionFiles' in manifest:
                for api in map(resolve_path, manifest['apiPermissionFiles']):
                    ValidateJson(api, api_schema, rootfs)
                    ReadApiPermissions(api, api_permissions)
            if 'clientPermissionFiles' in manifest:
                for perm in map(resolve_path, manifest['clientPermissionFiles']):
                    ValidateJson(perm, perm_schema, rootfs)
                    ReadClientPermissions(perm, client_permissions)

            def check_role(key, schema_path):
                if key in manifest:
                    for role in map(resolve_path, manifest.get(key, [])):
                        ValidateJson(role, schema_path, rootfs)
                        CheckRole(role, rootfs)

            check_role('roleFiles', role_schema)
            check_role('roleFilesPub', old_role_schema)
            check_role('roleFilesPrv', old_role_schema)

    return (api_permissions, client_permissions)

def CheckGroupsDeclared(groups, api):
    ''' Yield groups that are defined in API but miss declaration '''
    for group in api.keys():
        if not group in groups:
            yield group

def CheckGroupsDefined(groups, api):
    ''' Yield groups that weren't defined in API '''
    for group in groups:
        if not group in api:
            yield group

def Verify(rootfs):
    ''' Do various checks of the security data '''

    ls_hub_conf = LSConf("/etc/luna-service2/ls-hubd.conf", rootfs)
    if not ls_hub_conf.valid:
        return False

    container_schema = ls_hub_conf.schema_path("container")
    for container_file in concat_map(ListFiles, ls_hub_conf.containers_dirs):
        ValidateJson(container_file, container_schema, prefix=rootfs)

    group_declarations = ReadGroups(rootfs)
    api_permissions, client_permissions = ReadManifests(rootfs, ls_hub_conf)

    for group in CheckGroupsDeclared(group_declarations, api_permissions):
        handle_error(acg_api, "%s: missing group declarations" % group)
    for group in CheckGroupsDefined(group_declarations.keys(), api_permissions):
        handle_error(acg_api, "%s: missing group definitions" % group)

    # Verify that all referenced groups exist
    for service, groups in client_permissions.items():
        for g in CheckGroupsDefined(groups, api_permissions):
            handle_error(acg_perm, "%s: use of undefined group %s" % (service, g))

    legacy_groups = {'private', 'public'}

    # Verify that client permissions don't contain sub/supersets
    for service, groups in client_permissions.items():
        # warning is already issued for groups not in api, just remove them
        groups = filter(lambda x: x in api_permissions, groups)
        for g1, g2 in itertools.combinations(groups, 2):
            methods1 = api_permissions[g1]
            methods2 = api_permissions[g2]
            if methods1.issubset(methods2) and not g2 in legacy_groups:
                handle_error(acg_perm, "%s: client permissions redundancy %s is subset of %s" % (service, g1, g2))
            elif methods2.issubset(methods1) and not g1 in legacy_groups:
                handle_error(acg_perm, "%s: client permissions redundancy %s is subset of %s" % (service, g2, g1))

    # Verify that client permissions either contain legacy groups or non-legacy groups
    for service, groups in client_permissions.items():
        if groups.intersection(legacy_groups) and groups.difference(legacy_groups):
            handle_error(acg_perm, "%s: client permissions have both new ang legacy groups" % service)

    # Verify that all methods from the compatibility group `public' are published otherwise.
    # Whenever `public' goes away, the method should still be available to market applications.
    # TODO: Collect baseline groups to compare with
    #published_methods = copy.deepcopy(api_permissions["public"])
    #for group, methods in api_permissions.items():
    #    if not group_declarations[group]:
    #        continue
    #    published_methods = published_methods.difference(methods)
    #for method in published_methods:
    #    handle_error(acg_api, "%s: was public, but not not accessbile via public groups any more" % method)

    return True

if __name__ == "__main__":
    p = sys.argv[1] if len(sys.argv) > 1 else "/"
    if not Verify(p):
        sys.stderr.write("LS2 hub config not found\n")
