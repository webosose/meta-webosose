# Copyright (c) 2013-2022 LG Electronics, Inc.
#
# webos_base
#
# This class adds write_bom_data and write_abi_xml_data,
# Each of them can be run by bitake --runall option.
# They are useful to verify build output specification.

do_write_bom_data[nostamp] = "1"
addtask write_bom_data
python do_write_bom_data() {
    import json
    # We want one recipe per line, starting with arch and recipe and section keys,
    # so that it's easy to sort and compare them
    class WebosBomJSONEncoder(json.JSONEncoder):
        def iterencode(self, obj, _one_shot=True):
            if isinstance(obj, dict):
                output = []
                if "arch" in obj.keys() and "recipe" in obj.keys() and "section" in obj.keys():
                    output.append(json.dumps("arch") + ": " + self.encode(obj["arch"]))
                    output.append(json.dumps("recipe") + ": " + self.encode(obj["recipe"]))
                    output.append(json.dumps("section") + ": " + self.encode(obj["section"]))
                for key, value in sorted(obj.items()):
                    if key == "arch" or key == "recipe" or key == "section":
                        continue
                    output.append(json.dumps(key) + ": " + self.encode(value))
                return "{" + ",".join(output) + "}"
            else:
                return json.JSONEncoder().iterencode(obj, _one_shot)

    jsondata = {}
    jsondata["section"] = d.getVar("SECTION", True)
    jsondata["src_uri"] = d.getVar("SRC_URI", True)
    jsondata["srcrev"] = "".join(d.getVar("SRCREV", True).split())
    jsondata["recipe"] = d.getVar("PN", True)
    jsondata["file"] = d.getVar("FILE", True)[len(d.getVar("TOPDIR", True)):]
    jsondata["arch"] = d.getVar("PACKAGE_ARCH", True)
    jsondata["author"] = d.getVar("AUTHOR", True)
    license = d.getVar("LICENSE", True)
    license_flags = d.getVar("LICENSE_FLAGS", True)
    packages = d.getVar("PACKAGES", True)
    jsondata["license"] = license
    jsondata["license_flags"] = license_flags
    jsondata["packages"] = packages
    pkg_lic = {}
    if packages:
        for pkg in packages.split():
            lic = d.getVar("LICENSE:%s" % pkg, True)
            if lic and lic != license:
                pkg_lic[pkg] = lic
    jsondata["pkg_lic"] = pkg_lic
    jsondata["pe"] = d.getVar("PE", True)
    jsondata["pv"] = d.getVar("PV", True)
    jsondata["pr"] = d.getVar("PR", True)
    jsondata["extendprauto"] = d.getVar("EXTENDPRAUTO", True)
    jsondata["extendpkgv"] = d.getVar("EXTENDPKGV", True)
    jsondata["webos_version"] = d.getVar("WEBOS_VERSION", True)
    jsondata["webos_component_version"] = d.getVar("WEBOS_COMPONENT_VERSION", True)
    jsondata["webos_submission"] = d.getVar("WEBOS_SUBMISSION", True)
    jsondata["webos_prebuilt_binaries"] = bb.data.inherits_class('webos_prebuilt_binaries', d)

    datafile = os.path.join(d.getVar("TOPDIR", True), "webos-bom.json")
    lock = bb.utils.lockfile(datafile + '.lock')
    with open(datafile, "a") as f:
        json.dump(jsondata, f, sort_keys=True, cls=WebosBomJSONEncoder)
        f.write(',\n')
    bb.utils.unlockfile(lock)

    write_compile_option(d)
}

do_write_abi_xml_data[nostamp] = "1"
addtask write_abi_xml_data
python do_write_abi_xml_data() {
    pn = d.getVar("PN", True)
    solibs = d.getVar("SOLIBS", True)
    solibsdev = d.getVar("SOLIBSDEV", True)
    sstate_manmach = d.getVar("SSTATE_MANMACH", True)
    datafile = os.path.join(d.getVar("TOPDIR", True), "abi", "%s.xml" % pn)
    bb.utils.mkdirhier(os.path.join(d.getVar("TOPDIR", True), "abi"))
    manifest = os.path.join(d.getVar("SSTATE_MANIFESTS", True), "manifest-%s-%s.populate_sysroot" % (sstate_manmach, pn))
    lock = bb.utils.lockfile(datafile + '.lock')
    if not os.path.isfile(manifest):
        # nothing was staged, we don't need to run ABI checker on this
        return
    import re
    re_headers = re.compile(r"\.h$")
    re_solibs = re.compile(r"\.so\..*$")
    # in some libraries we don't set proper SONAME and versioning
    re_solibsdev = re.compile(r"\.so$")
    with open(datafile, "w") as f:
        f.write("<!-- generated from %s -->\n" % manifest)
        f.write("<version>%s</version>\n" % d.getVar("PV", True))
        f.write("<headers>\n")
        with open(manifest, "r") as manfile:
            for line in manfile:
                if re.search(re_headers, line):
                    f.write(line)
        f.write("</headers>\n")
        f.write("<libs>\n")
        with open(manifest, "r") as manfile:
            for line in manfile:
                if re.search(re_solibs, line):
                    f.write(line)
                if re.search(re_solibsdev, line):
                    f.write(line)
        f.write("</libs>\n")
    bb.utils.unlockfile(lock)
}

# analogous to base_get_metadata_git_branch from metadata_scm.bbclass
def webos_base_get_metadata_git_describe(path, d):
    import bb.process

    try:
        description, _ = bb.process.run('git describe', cwd=path)
    except bb.process.ExecutionError:
        description = '<unknown>'
    return description.strip()

def write_compile_option(d):
    import json
    def pruneString(string: str, plist=None, isFullPath=False):
        if plist is None:
            plist = ['-isystem', 'sysroot', 'prefix-map']
        if not isFullPath:
            for pword in plist:
                string = ' '.join([x for x in string.strip().split() if x.find(pword) == -1])
        else:
            for pword in (plist.split()):
                string = string.replace(pword,'').strip()

        return string

    def getSameOption(opt1: str, opt2: str):
        same_opt = list()
        for o1 in (opt1.split()):
            for o2 in (opt2.split()):
                if o1 == o2:
                    same_opt.append(o1)
                    break
        if len(same_opt) == 0:
            return ''
        return ' '.join(same_opt)

    # Write Compile option data
    compile_data = {}
    compile_data["arch"] = d.getVar('PACKAGE_ARCH', True)
    compile_data["recipe"] = d.getVar('PN', True)
    CFLAGS = d.getVar('CFLAGS',True)
    CXXFLAGS = d.getVar('CXXFLAGS',True)
    if CFLAGS.find('none') == -1:
        compile_data["cflags"] = pruneString(CFLAGS)
        compile_data["cc"] = pruneString(d.getVar('CC',True))

    if CXXFLAGS.find('none') == -1:
        compile_data["cxxflags"] = pruneString(CXXFLAGS)
        compile_data["cxx"] = pruneString(d.getVar('CXX', True))

    if compile_data.get("cc") != None or compile_data.get("cxx") != None:
        compile_data["ldflags"] = pruneString(d.getVar('LDFLAGS', True))

    if compile_data.get("cc") is None and compile_data.get("cxx") is None:
        return

    compile_datafile = os.path.join(d.getVar("TOPDIR", True), "webos-compile-option.json")
    lock = bb.utils.lockfile(compile_datafile + '.lock')
    with open(compile_datafile, "a") as fp:
        json.dump(compile_data, fp)
        fp.write(',\n')
    bb.utils.unlockfile(lock)

    if bb.data.inherits_class('image',d):
        # image recipe is usually has all depends, so that it will be build at the end.
        fp = open(compile_datafile, 'r')
        FIELD = ['cc', 'cflags', 'cxx', 'cxxflags', 'ldflags']
        common_options = dict()
        try:
            jsondata = json.load(fp)
        except json.decoder.JSONDecodeError:
            fp.seek(os.SEEK_SET)
            while True:
                try:
                    line = fp.readline().strip()
                except:
                    break
                if not line:
                    break
                if line[-1] == ',':
                    line = line[:-1]
                line = json.loads(line)
                ARCH = line['arch']
                if common_options.get(ARCH) is None:
                    common_options.update({ARCH: {'cc': None, 'cflags': None, 'cxx': None, 'cxxflags': None, 'ldflags': None}})

                for field in FIELD:
                    if line.get(field) is not None:
                        if common_options[ARCH][field] == None:
                            common_options[ARCH][field] = line[field]
                        elif common_options[ARCH][field] == '':
                            continue
                        else:
                            common_options[ARCH][field] = getSameOption(common_options[ARCH][field], line[field])
        cnt = 0
        for arch in common_options.keys():
            try:
                for field in common_options[arch]:
                    if common_options[arch][field] is None:
                        cnt +=1
                    if len(FIELD) == cnt:
                        common_options.pop(arch)
            except:
                pass
            cnt = 0

        result_file = os.path.join(d.getVar("TOPDIR", True), "webos-common-compile-option.json")
        with open(result_file,'w') as rfp:
            json.dump(common_options,rfp)
        fp.close()