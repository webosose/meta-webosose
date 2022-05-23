HOMEPAGE = "https://github.com/opencontainers/runc"
SUMMARY = "runc container cli tools"
DESCRIPTION = "runc is a CLI tool for spawning and running containers according to the OCI specification."

# Apache-2.0 for containerd
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/import/LICENSE;md5=435b266b3899aa8a959f17d41c56def8"

S = "${WORKDIR}/git"

PV = "${RUNC_VERSION}+git${SRCPV}"

inherit go
inherit goarch
inherit pkgconfig

PACKAGECONFIG[seccomp] = "seccomp,,libseccomp"
PACKAGECONFIG[selinux] = "selinux,,libselinux"
# This PACKAGECONFIG serves the purpose of whether building runc as static or not
PACKAGECONFIG[static] = ""

PACKAGECONFIG ??= "static \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'seccomp', 'seccomp', '', d)} \
                   ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', 'selinux', '', d)} \
	          "


PROVIDES += "virtual/runc"
RPROVIDES:${PN} = "virtual-runc"

GO_IMPORT = "import"

LIBCONTAINER_PACKAGE="github.com/opencontainers/runc/libcontainer"

do_configure[noexec] = "1"


DISTRO_BUILDTAGS ?= "${@bb.utils.contains('DISTRO_FEATURES', 'seccomp', 'seccomp', '', d)} \
                     ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', 'selinux', '', d)}"

EXTRA_OEMAKE="BUILDTAGS='${PACKAGECONFIG_CONFARGS} ${DISTRO_BUILDTAGS}' GO=${GO}"

do_compile() {
	# Set GOPATH. See 'PACKAGERS.md'. Don't rely on
	# docker to download its dependencies but rather
	# use dependencies packaged independently.
	cd ${S}/src/import
	rm -rf .gopath
	dname=`dirname "${LIBCONTAINER_PACKAGE}"`
	bname=`basename "${LIBCONTAINER_PACKAGE}"`
	mkdir -p .gopath/src/${dname}

	(cd .gopath/src/${dname}; ln -sf ../../../../../${bname} ${bname})
	export GOPATH="${S}/src/import/.gopath:${S}/src/import/vendor:${STAGING_DIR_TARGET}/${prefix}/local/go"

	# Fix up symlink for go-cross compiler
	rm -f ${S}/src/import/vendor/src
	ln -sf ./ ${S}/src/import/vendor/src

	# Pass the needed cflags/ldflags so that cgo
	# can find the needed headers files and libraries
	export CGO_ENABLED="1"
	export CGO_CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_TARGET}"
	export CGO_LDFLAGS="${LDFLAGS} --sysroot=${STAGING_DIR_TARGET}"
	export GO=${GO}

	export CFLAGS=""
	export LDFLAGS=""

	if ${@bb.utils.contains('PACKAGECONFIG', 'static', 'true', 'false', d)}; then
		oe_runmake static
	else
		oe_runmake runc
	fi
}

do_install() {
	mkdir -p ${D}/${bindir}

	cp ${S}/src/import/runc ${D}/${bindir}/runc
	ln -sf runc ${D}/${bindir}/docker-runc
}

