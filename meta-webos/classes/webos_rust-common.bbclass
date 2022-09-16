# Copyright (c) 2022 LG Electronics, Inc.
#
# Authors = "the meta-rust-bin authors"
# Github = "https://github.com/rust-embedded/meta-rust-bin"
# License = "Apache-2.0 | MIT"
#
# webos_rust-common
#
def rust_target(d, spec_type):
    '''
    Convert BitBake system specs into Rust target.
    `spec_type` is one of BUILD, TARGET, or HOST
    '''
    import re
    spec_type = spec_type.upper()

    arch = d.getVar('%s_ARCH' % spec_type, True)
    os = d.getVar('%s_OS' % spec_type, True)

    # Make sure that tasks properly recalculate after ARCH or OS change
    d.appendVarFlag("rust_target", "vardeps", " %s_ARCH" % spec_type)
    d.appendVarFlag("rust_target", "vardeps", " %s_OS" % spec_type)

    # Sometimes bitbake mixes the calling convention into the OS, sometimes it
    # doesn't... let's just take the first part
    os = os.split('-')[0]

    # The bitbake vendor won't ever match the Rust specs
    vendor = "unknown"

    tclibc = d.getVar("TCLIBC", True)
    callconvention = "gnu"
    # Only install the musl target toolchain for rust
    # versions 1.35.0 and above
    if spec_type == "TARGET" and tclibc == "musl":
        pnre = re.compile("rust-bin-cross")
        m = pnre.match(d.getVar("PN", True))
        if m:
            pv = d.getVar("PV", True)
            if pv >= "1.35.0":
                callconvention = "musl"
        else:
            callconvention = "musl"

    # TUNE_FEATURES are always only for the TARGET
    if spec_type == "TARGET":
        tune = d.getVar("TUNE_FEATURES", True).split()
        tune += d.getVar("MACHINEOVERRIDES", True).split(":")
    else:
        tune = []

    if not os == 'linux':
        bb.fatal("Unsupported OS: %s. Only Linux is supported." % os)

    if arch in ["i386", "i486", "i586", "i686", "i786", "x86"]:
        arch = "i686"
    elif arch in ["x86_64", "x86-64", "x64", "amd64"]:
        arch = "x86_64"
    elif arch in ["xscale", "arm", "armv6l", "armv7l"]:
        # Rust requires NEON/VFP in order to build for armv7, else fall back to v6
        tune_armv7 = any(t.startswith("armv7") for t in tune)
        tune_neon = "neon" in tune
        tune_cchard = "callconvention-hard" in tune
        if all([tune_armv7, tune_neon, tune_cchard]):
            arch = "armv7"
            callconvention += "eabihf"
        elif any(t.startswith("armv5") for t in tune):
            arch = "armv5te"
            callconvention += "eabi"
        else:
            arch = "arm"
            if tune_cchard:
                callconvention += "eabihf"
            else:
                callconvention += "eabi"
    elif arch in ["aarch64"]:
        arch = "aarch64"
    elif arch in ["ppc"]:
        arch = "powerpc"
    elif arch in ["ppc64"]:
        arch = "powerpc64"
    elif arch in ["ppc64le"]:
        arch = "powerpc64le"
    else:
        bb.fatal("Unknown architecture: %s" % arch)

    target = "%s-%s-%s-%s" % (arch, vendor, os, callconvention)

    return target

# Yocto adds a vardep to rust_target on "rust_target[vardeps]"... it really
# doesn't like us generating ARCH and OS dynamically. We don't actually care
# about the "rust_target[vardeps]" string, we care about the vardeps that
# string specifies (*_ARCH/*_OS), so it is safe to exclude.
rust_target[vardepsexclude] += "rust_target[vardeps]"

RUST_BASE_URI := "https://static.rust-lang.org"
