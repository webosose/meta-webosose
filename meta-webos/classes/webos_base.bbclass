# Copyright (c) 2013-2024 LG Electronics, Inc.
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

    bom_exception_list = [
        "lib32-lsb-release",
        "lsb-release",
        "cve-update-nvd2-native",
        "python3-asttokens-native",
        "python3-checksec-py-native",
        "python3-docopt-native",
        "python3-icontract-native",
        "python3-lief-native",
        "python3-pylddwrap-native",
        "python3-rich-native",
        "python3-pip-native",
        "python3-poetry-core-native"
    ]

    jsondata = {}
    jsondata["section"] = d.getVar("SECTION")
    src_uri = d.getVar("SRC_URI")
    src_uri = src_uri.replace(d.getVar("TOPDIR"), "TOPDIR")
    jsondata["src_uri"] = src_uri
    jsondata["srcrev"] = "".join(d.getVar("SRCREV").split())
    jsondata["recipe"] = d.getVar("PN")
    jsondata["file"] = d.getVar("FILE")[len(d.getVar("TOPDIR")):]
    jsondata["arch"] = d.getVar("PACKAGE_ARCH")
    jsondata["author"] = d.getVar("AUTHOR")
    jsondata["rp_maintainer"] = d.getVar("WEBOS_RP_MAINTAINER")
    license = d.getVar("LICENSE")
    license_flags = d.getVar("LICENSE_FLAGS")
    packages = d.getVar("PACKAGES")
    jsondata["license"] = license
    jsondata["license_flags"] = license_flags
    jsondata["packageconfig"] = d.getVar("PACKAGECONFIG")
    jsondata["packages"] = packages
    pkg_lic = {}
    if packages:
        for pkg in packages.split():
            lic = d.getVar("LICENSE:%s" % pkg)
            if lic and lic != license:
                pkg_lic[pkg] = lic
    jsondata["pkg_lic"] = pkg_lic
    jsondata["pe"] = d.getVar("PE")
    jsondata["pv"] = d.getVar("PV")
    jsondata["pr"] = d.getVar("PR")
    jsondata["extendprauto"] = d.getVar("EXTENDPRAUTO")
    jsondata["extendpkgv"] = d.getVar("EXTENDPKGV")
    jsondata["webos_version"] = d.getVar("WEBOS_VERSION")
    jsondata["webos_component_version"] = d.getVar("WEBOS_COMPONENT_VERSION")
    jsondata["webos_submission"] = d.getVar("WEBOS_SUBMISSION")
    jsondata["webos_prebuilt_binaries"] = bb.data.inherits_class('webos_prebuilt_binaries', d)

    if jsondata["recipe"] not in bom_exception_list:
        datafile = os.path.join(d.getVar("TOPDIR"), "webos-bom.json")

        lock = bb.utils.lockfile(datafile + '.lock')
        with open(datafile, "a") as f:
            json.dump(jsondata, f, sort_keys=True, cls=WebosBomJSONEncoder)
            f.write(',\n')
        bb.utils.unlockfile(lock)

    full_datafile = os.path.join(d.getVar("TOPDIR"), "webos-bom-full.json")

    lock = bb.utils.lockfile(full_datafile + '.lock')
    with open(full_datafile, "a") as f:
        json.dump(jsondata, f, sort_keys=True, cls=WebosBomJSONEncoder)
        f.write(',\n')
    bb.utils.unlockfile(lock)

    write_compile_option(d)
}

do_write_abi_xml_data[nostamp] = "1"
addtask write_abi_xml_data
python do_write_abi_xml_data() {
    pn = d.getVar("PN")
    solibs = d.getVar("SOLIBS")
    solibsdev = d.getVar("SOLIBSDEV")
    sstate_manmach = d.getVar("SSTATE_MANMACH")
    datafile = os.path.join(d.getVar("TOPDIR"), "abi", "%s.xml" % pn)
    bb.utils.mkdirhier(os.path.join(d.getVar("TOPDIR"), "abi"))
    manifest = os.path.join(d.getVar("SSTATE_MANIFESTS"), "manifest-%s-%s.populate_sysroot" % (sstate_manmach, pn))
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
        f.write("<version>%s</version>\n" % d.getVar("PV"))
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
    compile_data["arch"] = d.getVar('PACKAGE_ARCH')
    compile_data["recipe"] = d.getVar('PN')
    CFLAGS = d.getVar('CFLAGS')
    CXXFLAGS = d.getVar('CXXFLAGS')
    if CFLAGS.find('none') == -1:
        compile_data["cflags"] = pruneString(CFLAGS).replace(d.getVar("TOPDIR"), "TOPDIR")
        compile_data["cc"] = pruneString(d.getVar('CC')).replace(d.getVar("TOPDIR"), "TOPDIR")

    if CXXFLAGS.find('none') == -1:
        compile_data["cxxflags"] = pruneString(CXXFLAGS).replace(d.getVar("TOPDIR"), "TOPDIR")
        compile_data["cxx"] = pruneString(d.getVar('CXX')).replace(d.getVar("TOPDIR"), "TOPDIR")

    if compile_data.get("cc") != None or compile_data.get("cxx") != None:
        compile_data["ldflags"] = pruneString(d.getVar('LDFLAGS')).replace(d.getVar("TOPDIR"), "TOPDIR")

    if compile_data.get("cc") is None and compile_data.get("cxx") is None:
        return

    compile_datafile = os.path.join(d.getVar("TOPDIR"), "webos-compile-option.json")
    lock = bb.utils.lockfile(compile_datafile + '.lock')
    with open(compile_datafile, "a") as fp:
        json.dump(compile_data, fp, sort_keys=True)
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

        result_file = os.path.join(d.getVar("TOPDIR"), "webos-common-compile-option.json")
        with open(result_file,'w') as rfp:
            json.dump(common_options,rfp,sort_keys=True)
        fp.close()

# BBINCLUDED is changed after RecipeParsed fired so we need to refer it with event handler.
# See the bitbake/lib/bb/parse/ast.py
addhandler pkg_add_extra_metadata
pkg_add_extra_metadata[eventmask] = "bb.event.RecipeParsed"
python pkg_add_extra_metadata() {
    pn             = d.getVar('BPN')
    extra_meta     = 'Recipes: {}'.format(os.path.relpath(d.getVar('FILE'), d.getVar('TOPDIR')))
    bbappend_files = d.getVar('BBINCLUDED').split()
    # If recipe name is aa, we need to match files like aa.bbappend and aa_1.1.bbappend
    # Files like aa1.bbappend or aa1_1.1.bbappend must be excluded.
    import re
    bbappend_re = re.compile( r".*/%s_[^/]*\.bbappend$" % re.escape(pn))
    bbappend_re1 = re.compile( r".*/%s\.bbappend$" % re.escape(pn))
    for file in bbappend_files:
        if bbappend_re.match(file) or bbappend_re1.match(file):
            extra_meta += ' ' + os.path.relpath(file, d.getVar('TOPDIR'))

    d.setVar('PACKAGE_ADD_RECIPES_METADATA', extra_meta)

    # Remove email address from author and rp maintainer
    d.setVar('PACKAGE_ADD_AUTHOR_METADATA', re.sub(' <.+@.+>', '', d.getVar('AUTHOR')))
    d.setVar('PACKAGE_ADD_RPMAINTAINER_METADATA', re.sub(' <.+@.+>', '', d.getVar('WEBOS_RP_MAINTAINER')))
}
