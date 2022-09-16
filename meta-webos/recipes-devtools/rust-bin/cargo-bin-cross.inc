# Copyright (c) 2022 LG Electronics, Inc.
#
# Authors = "the meta-rust-bin authors"
# Github = "https://github.com/rust-embedded/meta-rust-bin"
# License = "Apache-2.0 | MIT"
#

DESCRIPTION = "Cargo build tool for Rust (pre-compiled binary version)"
HOMEPAGE = "https://rust-lang.org"
LICENSE = "Apache-2.0 | MIT"
SECTION = "devel"

inherit cross
inherit webos_rust-common

PN = "cargo-bin-cross-${TARGET_ARCH}"

CARGO_HOST_TARGET = "${@rust_target(d, 'HOST')}"

SYSROOT_DIRS_NATIVE += "${prefix}"
SYSROOT_DIRS_IGNORE += "\
    ${prefix}/share \
    ${prefix}/etc \
"

# Stripping fails because of mixed arch types (host and target)
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"

fakeroot do_install() {
    ${S}/install.sh --destdir="${D}" --prefix="${prefix}"
    rm -f ${D}${prefix}/lib/rustlib/uninstall.sh
    rm -f ${D}${prefix}/lib/rustlib/install.log
    rm -f ${D}${prefix}/lib/rustlib/components
    rm -f ${D}${prefix}/lib/rustlib/rust-installer-version
}
do_install[depends] += "virtual/fakeroot-native:do_populate_sysroot"

python () {
    pv = d.getVar("PV", True)

    # only specified nightly needs another branch
    nightly = pv.startswith("nightly-")

    if nightly:
        pv = "nightly"

    target = d.getVar("CARGO_HOST_TARGET", True)
    cargo_uri = ("%s;md5sum=%s;sha256sum=%s;downloadname=%s" %
                 (cargo_url(target), cargo_md5(target), cargo_sha256(target),
                  d.getVar("PN", True) + "-" + pv + "-" + target + ".tar.gz"))
    src_uri = d.getVar("SRC_URI", True).split()
    cargo_extract_path = cargo_url(target).split('/')[-1].replace('.tar.gz', '')
    d.setVar("SRC_URI", ' '.join(src_uri + [cargo_uri]))
    d.setVar("S", "${{WORKDIR}}/{}".format(cargo_extract_path))
    d.appendVarFlag("S", "vardeps", " cargo_url")
    d.appendVarFlag("S", "vardepsexclude", " WORKDIR")
}
