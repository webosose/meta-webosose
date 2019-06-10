# Copyright (c) 2013-2019 LG Electronics, Inc.
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
    # We want one recipe per line, starting with arch and recipe keys,
    # so that it's easy to sort and compare them
    class WebosBomJSONEncoder(json.JSONEncoder):
        def iterencode(self, obj, _one_shot=True):
            if isinstance(obj, dict):
                output = []
                if "arch" in obj.keys() and "recipe" in obj.keys():
                    output.append(json.dumps("arch") + ": " + self.encode(obj["arch"]))
                    output.append(json.dumps("recipe") + ": " + self.encode(obj["recipe"]))
                for key, value in sorted(obj.items()):
                    if key == "arch" or key == "recipe":
                        continue
                    output.append(json.dumps(key) + ": " + self.encode(value))
                return "{" + ",".join(output) + "}"
            else:
                return json.JSONEncoder().iterencode(obj, _one_shot)

    jsondata = {}
    jsondata["src_uri"] = d.getVar("SRC_URI", True)
    jsondata["srcrev"] = "".join(d.getVar("SRCREV", True).split())
    jsondata["recipe"] = d.getVar("PN", True)
    jsondata["file"] = d.getVar("FILE", True)[len(d.getVar("TOPDIR", True)):]
    jsondata["arch"] = d.getVar("PACKAGE_ARCH", True)
    jsondata["author"] = d.getVar("AUTHOR", True)
    license = d.getVar("LICENSE", True)
    packages = d.getVar("PACKAGES", True)
    jsondata["license"] = license
    jsondata["packages"] = packages
    pkg_lic = {}
    if packages:
        for pkg in packages.split():
            lic = d.getVar("LICENSE_%s" % pkg, True)
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
    re_headers = re.compile("\.h$")
    re_solibs = re.compile("\.so\..*$")
    # in some libraries we don't set proper SONAME and versioning
    re_solibsdev = re.compile("\.so$")
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
