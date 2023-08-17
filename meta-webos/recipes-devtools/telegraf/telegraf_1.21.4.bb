# Copyright (c) 2022-2023 LG Electronics, Inc.

HOMEPAGE = "https://github.com/influxdata/telegraf"
SUMMARY = "Telegraf is the open source server agent to help you collect metrics from your stacks, sensors, and systems."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/import/LICENSE;md5=c5d3aeddd4f7a4c4993bbdc4a41aec44"

SRCREV = "583ee20a093acaa0781508b7840deb97a0fda622"
SRC_URI = "git://github.com/influxdata/telegraf.git;protocol=https;branch=release-1.21 \
    file://0001-Modify-the-Makefile-for-v1.21.4.patch;patchdir=src/${GO_IMPORT} \
    file://0002-Remove-unused-plugins-for-v1.21.4.patch;patchdir=src/${GO_IMPORT} \
    file://0003-Apply-inputs.socket_listener-plugin-for-sdkagent.patch;patchdir=src/${GO_IMPORT} \
"

PR = "r1"

GO_IMPORT = "import"

S = "${WORKDIR}/git"

inherit goarch
inherit go

EXTRA_OEMAKE = "GO='${GO}'"

TELEGRAF_OUT = "${WORKDIR}/build/out"

CGO_ENABLED ?= "1"
CGO_ENABLED:x86-64 = "0"

#   cannot find package runtime/cgo (using -importcfg)
#   ...go/pkg/tool/linux_amd64/link: cannot open file : open : no such file or directory
STATIC_FLAGS ?= ""
STATIC_FLAGS:x86-64 = "-a -pkgdir dontusecurrentpkgs"

do_compile[network] = "1"

do_compile() {
    export GOROOT="${STAGING_LIBDIR_NATIVE}/${TARGET_SYS}/go"
    export GOPATH="${S}/src"

    # Pass the needed cflags/ldflags so that cgo
    # can find the needed headers files and libraries
    export CFLAGS=""
    export LDFLAGS=""
    export CGO_CFLAGS="${BUILDSDK_CFLAGS} --sysroot=${STAGING_DIR_TARGET}"
    export CGO_LDFLAGS="${BUILDSDK_LDFLAGS} --sysroot=${STAGING_DIR_TARGET}"

    export STATIC_FLAGS="${STATIC_FLAGS}"
    export CGO_ENABLED="${CGO_ENABLED}"

    export GOARM="7"
    export DESTDIR="${TELEGRAF_OUT}"
    export buildbin="${WORKDIR}/build/bin/telegraf"
    export LDFLAGS="-w"
    export glibc_version="${GLIBCVERSION}"

    cd ${S}/src/${GO_IMPORT}
    oe_runmake install

    # Golang forces permissions to 0500 on directories and 0400 on files in
    # the module cache which prevents us from easily cleaning up the build
    # directory. Let's just fix the permissions here so we don't have to
    # hack the clean tasks.
    chmod -R u+w ${S}/src/pkg/mod
}

do_install() {
    # /etc
    install -d ${D}${sysconfdir}/logrotate.d
    install -d ${D}${sysconfdir}/telegraf
    install -d ${D}${sysconfdir}/telegraf/telegraf.d

    install -m 0644 ${TELEGRAF_OUT}/etc/logrotate.d/telegraf ${D}${sysconfdir}/logrotate.d/
    install -m 0644 ${TELEGRAF_OUT}/etc/telegraf/telegraf.conf ${D}${sysconfdir}/telegraf/

    # /usr/bin
    install -d ${D}${bindir}

    install -m 0755 ${TELEGRAF_OUT}/usr/bin/telegraf ${D}${bindir}/

    # /usr/lib
    install -d ${D}${systemd_unitdir}/system
    sed -i 's/User=telegraf/User=root/g' ${TELEGRAF_OUT}/usr/lib/telegraf/scripts/telegraf.service
    install -m 0644 ${TELEGRAF_OUT}/usr/lib/telegraf/scripts/telegraf.service ${D}${systemd_unitdir}/system

    #install -m 0644 ${TELEGRAF_OUT}/usr/lib/telegraf/scripts/init.sh ${D}${libdir}/telegraf/scripts/
    #install -m 0644 ${TELEGRAF_OUT}/usr/lib/telegraf/scripts/telegraf.service ${D}${libdir}/telegraf/scripts/

    # /var
    #install -d ${D}${localstatedir}/log/telegraf
}

inherit systemd
SYSTEMD_SERVICE:${PN} = "telegraf.service"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"
