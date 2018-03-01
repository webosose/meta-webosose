# This is originally taken from https://github.com/imyller/meta-nodejs/blob/master/recipes-devtools/nodejs/nodejs_6.inc
DESCRIPTION = "Node.js is a JavaScript runtime built on Chrome's V8 JavaScript engine"
HOMEPAGE = "http://nodejs.org"

LICENSE = "MIT"

COMPATIBLE_MACHINE_armv4 = "(!.*armv4).*"
# COMPATIBLE_MACHINE_armv5 = "(!.*armv5).*"
COMPATIBLE_MACHINE_mips64 = "(!.*mips64).*"

INC_PR = "r2"
PR = "${INC_PR}.1"

PROVIDES = "node"
RPROVIDES_${PN} = "node"
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}_append_class-target = " ${VIRTUAL-RUNTIME_bash}"

SRC_URI = "https://nodejs.org/dist/v${PV}/node-v${PV}.tar.gz;name=src \
	file://0003-try-arm-cache-flush-until-success.patch \
	file://0004-no-registry.patch \
	file://0006-implement-function-chroot-in-process-module.patch \
	file://0007-Adds-noexec-option-for-fork-and-spawn-APIs-of-nodejs.patch \
	file://0008-Fixes-process-pid-getter-to-always-get-actual-pid.patch \
	file://0009-Flush-processor-instruction-cash.patch \
	file://0010-Implement-initial-test-suit-for-forked-node.js.patch \
	file://0011-Optimize-V8-s-JSON-parser.patch \
	file://0012-Optimize-V8-s-JSON-parser-second-part.patch \
	file://0013-Provide-option-compile-js-to-node-for-.js-compilatio.patch \
	file://0014-Add-instanceof-check-for-all-Timeout-methods.patch \
	file://0015-Fix-threadpool-fork-safety.patch \
	file://0016-Implement-libuv-s-basic-fork-safety.patch \
	file://0017-Fix-possible-out-of-resources-in-init_once-function.patch \
	file://0018-Prevent-creating-usr-etc-in-native-sysroot.patch"

S = "${WORKDIR}/node-v${PV}"

# v8 errors out if you have set CCACHE
CCACHE = ""

# Helper utilities for building Node.js

def nodejs_map_dest_cpu(target_arch, d):
    import re
    if   re.match('i.86$', target_arch): return 'ia32'
    elif re.match('x86_64$', target_arch): return 'x64'
    elif re.match('aarch64$', target_arch): return 'arm64'
    elif re.match('powerpc64$', target_arch): return 'ppc64'
    elif re.match('powerpc$', target_arch): return 'ppc'
    return target_arch

ARCHFLAGS_arm = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', '--with-arm-float-abi=hard', '--with-arm-float-abi=softfp', d)} \
                 ${@bb.utils.contains('TUNE_FEATURES', 'neon', '--with-arm-fpu=neon', \
                    bb.utils.contains('TUNE_FEATURES', 'vfpv3d16', '--with-arm-fpu=vfpv3-d16', \
                    bb.utils.contains('TUNE_FEATURES', 'vfpv3', '--with-arm-fpu=vfpv3', \
                    '--with-arm-fpu=vfp', d), d), d)}"
ARCHFLAGS ?= ""

GYP_DEFINES_append_mipsel = " mips_arch_variant='r1' "

PACKAGECONFIG ??= "zlib openssl"

PACKAGECONFIG[zlib] = "--shared-zlib,,zlib,"
PACKAGECONFIG[openssl] = "--shared-openssl,,openssl,"
PACKAGECONFIG[v8-inspector] = ",--without-inspector,,"

DISABLE_STATIC = ""

do_configure () {
  export LD="${CXX}"
  GYP_DEFINES="${GYP_DEFINES}" export GYP_DEFINES
  ./configure	--prefix="${prefix}" \
		--dest-cpu="${@nodejs_map_dest_cpu(d.getVar('TARGET_ARCH', True), d)}" \
		--dest-os=linux ${ARCHFLAGS} \
		--without-snapshot \
		--with-intl=none \
		${PACKAGECONFIG_CONFARGS}
}

do_compile () {
  export LD="${CXX}"
  oe_runmake BUILDTYPE=Release
}

do_install () {
  oe_runmake install DESTDIR="${D}"
}

do_install_append_class-native() {
  # make sure we use node from PATH instead of absolute path to sysroot
  sed "1s^.*^#\!/usr/bin/env node^g" -i ${D}${exec_prefix}/lib/node_modules/npm/bin/npm-cli.js
}

do_install_append_class-nativesdk() {
  # make sure we use node from PATH instead of absolute path to sysroot
  sed "1s^.*^#\!/usr/bin/env node^g" -i ${D}${exec_prefix}/lib/node_modules/npm/bin/npm-cli.js
  sed "1s^.*^#\!/usr/bin/env python^g" -i ${D}${exec_prefix}/lib/node_modules/npm/node_modules/node-gyp/gyp/samples/samples
}

do_install_append_class-target() {
  # make sure we use node from PATH instead of absolute path to sysroot
  sed "1s^.*^#\!${bindir}/env node^g" -i ${D}${exec_prefix}/lib/node_modules/npm/bin/npm-cli.js
}

PACKAGES =+ "${PN}-npm"
FILES_${PN}-npm = "${exec_prefix}/lib/node_modules ${bindir}/npm"
RDEPENDS_${PN}-npm = "${VIRTUAL-RUNTIME_bash} python-compiler python-shell python-datetime python-subprocess python-multiprocessing python-crypt python-textutils python-netclient python-misc"

PACKAGES =+ "${PN}-dtrace"
FILES_${PN}-dtrace = "${exec_prefix}/lib/dtrace"

PACKAGES =+ "${PN}-systemtap"
FILES_${PN}-systemtap = "${datadir}/systemtap"

BBCLASSEXTEND = "native nativesdk"
VIRTUAL-RUNTIME_bash ?= "bash"
RDEPENDS_${PN}-npm_append_class-target = " ${VIRTUAL-RUNTIME_bash}"
