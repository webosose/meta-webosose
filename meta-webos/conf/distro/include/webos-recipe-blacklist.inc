# Copyright (c) 2013-2022 LG Electronics, Inc.

# A blacklisted recipe is ignored by bitbake. The reason it is useful to be able
# to have bitbake ignore a recipe is because, although PREFERRED_PROVIDER can be
# used to select which recipe is used to fulfill a build-time dependency, bitbake
# does not have a PREFERRED_RPROVIDER to allow specification of which package
# should be used to satisfy a run-time dependency. When two recipes both provide
# common packages, but one recipe provides additional packages that the other
# doesn't, then the only way to guarantee that the first recipe will never be
# used is to blacklist it.

# Use webOS qt5 configuration
PNBLACKLIST[qwt-qt5] ?= "ERROR: Unknown module(s) in QT: printsupport opengl http://caprica.lgsvl.com:8080/Errors/Details/1509145"
PNBLACKLIST[libconnman-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509142"
PNBLACKLIST[libqofono] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
PNBLACKLIST[libqofonoext] ?= "depends on blacklisted libqofono which requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
PNBLACKLIST[qtwebkit] ?= "Could not find a package configuration file provided by Qt5Widgets, because widgets are disabled in our builds http://caprica.lgsvl.com:8080/Errors/Details/1509126"
PNBLACKLIST[qtwebengine] ?= "We use own webruntime based on chromium"
PNBLACKLIST[qtwebview] ?= "Depends on blacklisted qtwebengine"
PNBLACKLIST[qt-kiosk-browser] ?= "Depends on blacklisted qtwebengine"
PNBLACKLIST[maliit-framework-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509125"
PNBLACKLIST[maliit-plugins-qt5] ?= "depends on blacklisted maliit-framework-qt5"
PNBLACKLIST[qt5-creator] ?= "depends on blacklisted qtwebkit"
# since 5.9:
PNBLACKLIST[qtopcua] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509148"
PNBLACKLIST[qtmqtt] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509146"
PNBLACKLIST[qtsmarthome] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509144"
# since 5.11:
PNBLACKLIST[qt3d-runtime] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509147"
PNBLACKLIST[python-pyqt5] ?= "needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509139"
PNBLACKLIST[python3-pyqt5] ?= "needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509149"
PNBLACKLIST[python3-pyqtchart] ?= "depends on blacklisted python3-pyqt5 which needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509149"
# since 5.12:
PNBLACKLIST[qmllive] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509138"
PNBLACKLIST[qtconnectivity] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 ERROR: Feature 'bluez' was enabled, but the pre-condition 'libs.bluez && features.concurrent && features.dbus' failed. http://caprica.lgsvl.com:8080/Errors/Details/1509143"
# since 6.2:
PNBLACKLIST[packagegroup-qt6-addons] ?= "depends on blacklisted qtconnectivity, qtmqtt, qtopcua"
PNBLACKLIST[packagegroup-qt6-modules] ?= "depends on blacklisted packagegroup-qt6-addons"
PNBLACKLIST[qtdeviceutilities] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200304"
PNBLACKLIST[qtcharts] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200305"
PNBLACKLIST[qtdatavis3d] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200303"
PNBLACKLIST[qtpositioning] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://gecko.lge.com/Errors/Details/200302"

# meta-oe/recipes-support/vboxguestdrivers/vboxguestdrivers_6.1.18.bb:do_packagedata
PNBLACKLIST[vboxguestdrivers] ?= "BROKEN: kernel-module-vboxguest conflicts in pkgdata with the vboxguest kernel modules we have enabled in linux-yocto"
# meta/recipes-kernel/linux/kernel-devsrc.bb:do_install
PNBLACKLIST[kernel-devsrc] ?= "BROKEN for aarch64: make[4]: *** No rule to make target `kernel-devsrc/1.0-r0/image/usr/src/kernel/arch/arm64/boot/dts/broadcom/../overlays/Makefile'.  Stop http://caprica.lgsvl.com:8080/Errors/Details/1509158"
# meta/recipes-extended/sysstat/sysstat_12.4.0.bb:do_install
PNBLACKLIST[sysstat] ?= "BROKEN: install: invalid group ‘man’ http://caprica.lgsvl.com:8080/Errors/Details/1509136"
# meta/recipes-extended/man-db/man-db_2.9.4.bb:do_install
PNBLACKLIST[man-db] ?= "BROKEN: without --disable-cache-owner: chown: invalid user: ?man:man? our passwd doesn't have man user and since http://git.openembedded.org/openembedded-core/commit/?id=7080df4b6bc50440eed600e81f2c6fa76a80623d it's needed http://caprica.lgsvl.com:8080/Errors/Details/1509154"

# meta-oe/recipes-core/packagegroups/packagegroup-meta-oe.bb
PNBLACKLIST[packagegroup-meta-oe] ?= "depends on blacklisted libsmi, phonet-utils, gradm, scsirastools, tomoyo-tools, s-suite, .."
# meta-oe/recipes-support/libssh2/libssh2_1.9.0.bb
PNBLACKLIST[libssh2] ?= "Depends on blacklisted man-db since https://git.openembedded.org/meta-openembedded/commit/?id=b3e9b51c9ba58b01e55c5b0a9a9bec3f7f06d07a"
# meta-oe/recipes-support/libgit2/libgit2_1.0.1.bb
PNBLACKLIST[libgit2] ?= "Depends on blacklisted libssh2 which depends on blacklisted man-db since https://git.openembedded.org/meta-openembedded/commit/?id=b3e9b51c9ba58b01e55c5b0a9a9bec3f7f06d07a"
# meta-oe/recipes-benchmark/s-suite/s-suite_git.bb
PNBLACKLIST[s-suite] ?= "rdepends on blacklisted sysstat"

# nagios-* PNBLACKLIST upstreamed in:
# https://lists.yoctoproject.org/g/meta-virtualization/message/6301
# will probably get merged in hardknott
# meta-virtualization/recipes-extended/nagios/nagios-core_4.4.6.bb
PNBLACKLIST[nagios-core] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on apache2 from meta-webserver which is not included', d)}"
# meta-virtualization/recipes-extended/nagios/nagios-plugins_2.2.1.bb
PNBLACKLIST[nagios-plugins] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
# meta-virtualization/recipes-extended/nagios/nagios-nrpe_4.0.2.bb
PNBLACKLIST[nagios-nrpe] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
# meta-virtualization/recipes-extended/nagios/nagios-nsca_2.9.2.bb
PNBLACKLIST[nagios-nsca] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Rdepends on nagios-base provided by nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
# ipxe fixes sent upstream in:
# https://lists.yoctoproject.org/g/meta-virtualization/message/6302
# https://lists.yoctoproject.org/g/meta-virtualization/message/6304
# https://lists.yoctoproject.org/g/meta-virtualization/message/6305
# will probably get merged in hardknott
# meta-virtualization/recipes-extended/ipxe/ipxe_git.bb:do_compile
PNBLACKLIST[ipxe] ?= "Doesn't match the COMPATIBLE_HOST restriction from syslinux: syslinux was skipped: incompatible with host arm-webos-linux-gnueabi (not in COMPATIBLE_HOST) and also fails with gold: ipxe-gitrAUTOINC+64b4452bca-r0 do_compile: Function failed: do_compile: Makefile.housekeeping:111: GNU gold is unsuitable for building iPXE"

# meta-virtualization/recipes-containers/cri-o/cri-o_git.bb:do_compile
PNBLACKLIST[cri-o] ?= "ERROR: cri-o-1.12.0+git774a29ecf6855f2dff266dc2aa2fe81d7d964465-r0 do_compile: Function failed: do_compile: | Makefile:112: recipe for target conmon/config.h failed"
# meta-virtualization/recipes-core/kata-containers/kata-agent_git.bb:do_package_qa
PNBLACKLIST[kata-agent] ?= "ERROR: kata-agent-git-r0 do_package_qa: QA Issue: ELF binary kata-agent/git-r0/packages-split/kata-agent/usr/bin/kata-agent has relocations in .text [textrel]"
# meta-virtualization/recipes-core/kata-containers/kata-proxy_git.bb:do_package_qa
PNBLACKLIST[kata-proxy] ?= "ERROR: kata-proxy-git-r0 do_package_qa: QA Issue: ELF binary kata-proxy/git-r0/packages-split/kata-proxy/usr/libexec/kata-containers/kata-proxy has relocations in .text [textrel]"
# meta-virtualization/recipes-core/kata-containers/kata-runtime_git.bb:do_compile
PNBLACKLIST[kata-runtime] ?= "ERROR: kata-runtime-git-r0 do_compile: oe_runmake failed: make: *** No rule to make target arch/386-options.mk.  Stop."
# meta-virtualization/recipes-core/kata-containers/kata-shim_git.bb:do_package_qa
PNBLACKLIST[kata-shim] ?= "ERROR: kata-shim-git-r0 do_package_qa: QA Issue: ELF binary kata-shim/git-r0/packages-split/kata-shim/usr/libexec/kata-containers/kata-shim has relocations in .text [textrel]"
# meta-virtualization/recipes-core/runv/runv_git.bb:do_compile
PNBLACKLIST[runv] ?= "ERROR: runv-0.4.0+gitb360a686abc6c6e896382990ef1b93ef07c7a677-r0 do_compile: Function failed: do_compile: src/import/vendor/src/github.com/hyperhq/runv/hypervisor/qemu/qemu.go:45:28: undefined: QEMU_SYSTEM_EXE"
# dev86 fixes sent upstream in:
# https://lists.yoctoproject.org/g/meta-virtualization/message/6306
# https://lists.yoctoproject.org/g/meta-virtualization/message/6307
# will probably get merged in hardknott
# meta-virtualization/recipes-extended/dev86/dev86_0.16.20.bb:do_install
PNBLACKLIST[dev86] ?= "ERROR: dev86-0.16.21-r0 do_package_qa: QA Issue: File * in package dev86 doesn't have GNU_HASH (didn't pass LDFLAGS?)"
# kvmtool can be dropped in hardknott
# meta-virtualization/recipes-extended/kvmtool/kvmtool.bb:do_compile
PNBLACKLIST[kvmtool] ?= "ERROR: kvmtool-4.14.0+gitAUTOINC+0e1882a49f-r0 do_patch: Fuzz detected: Applying patch 0003-kvmtool-Werror-disabled.patch"
# meta-virtualization/recipes-extended/seabios/seabios_1.11.2.bb:do_compile
PNBLACKLIST[seabios] ?= "ERROR: seabios-1.11.2-r0 do_compile: Function failed: do_compile: i586-webos-linux-ld: error: out/rom16.strip.o: unsupported ELF file type 2"
# meta-virtualization/recipes-extended/vgabios/biossums_0.7a.bb:do_package_qa
PNBLACKLIST[biossums] ?= "ERROR: biossums-0.7a-r0 do_package_qa: QA Issue: No GNU_HASH in the ELF binary biossums/0.7a-r0/packages-split/biossums/usr/bin/biossums, didnt pass LDFLAGS? [ldflags]"

# These are reproducible only with rpi MACHINEs, most likely caused by usrmerge DISTRO_FEATURE enabled with sota from meta-updater
# meta-openembedded/meta-networking/recipes-connectivity/ufw/ufw_0.33.bb:do_package_qa
PNBLACKLIST[ufw] ?= "ERROR: ufw-0.33-r0 do_package_qa: QA Issue: ufw package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-networking/recipes-filter/arno-iptables-firewall/arno-iptables-firewall_2.0.1g.bb:do_install
PNBLACKLIST[arno-iptables-firewall] ?= "ERROR: arno-iptables-firewall-2.0.1g-r0 do_install: Function failed: do_install: install: cannot stat tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/arno-iptables-firewall/2.0.1g-r0/arno-iptables-firewall_2.0.1g//usr/lib/systemd/system/arno-iptables-firewall.service: No such file or directory"
# meta-openembedded/meta-networking/recipes-kernel/wireguard/wireguard-module_0.0.20171221.bb:do_package
PNBLACKLIST[wireguard-module] ?= "ERROR: wireguard-module-0.0.20171221-r0 do_package: QA Issue: wireguard-module: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-protocols/babeld/babeld_1.8.3.bb:do_package
PNBLACKLIST[babeld] ?= "ERROR: babeld-1.8.3-r0 do_package: QA Issue: babeld: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-support/cifs/cifs-utils_git.bb:do_package
PNBLACKLIST[cifs-utils] ?= "ERROR: cifs-utils-6.7-r0 do_package: QA Issue: cifs-utils: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-support/linux-atm/linux-atm_2.5.2.bb:do_package_qa
PNBLACKLIST[linux-atm] ?= "ERROR: linux-atm-2.5.2-r0 do_package_qa: QA Issue: linux-atm package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-oe/recipes-connectivity/lirc/lirc_0.9.4d.bb:do_package
PNBLACKLIST[lirc] ?= "ERROR: lirc-0.9.4d-r0 do_package: SYSTEMD_SERVICE:lirc value lircmd.service does not exist"
# phonet-utils removal sent in:
# https://lists.openembedded.org/g/openembedded-devel/message/90533
# will probably get merged in hardknott
# meta-openembedded/meta-oe/recipes-connectivity/phonet-utils/phonet-utils_git.bb:do_package_qa
PNBLACKLIST[phonet-utils] ?= "ERROR: phonet-utils-0.0.0+gitrAUTOINC+4acfa720fd-r2 do_package_qa: QA Issue: phonet-utils package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-oe/recipes-extended/boinc/boinc-client_7.12.bb:do_package
PNBLACKLIST[boinc-client] ?= "ERROR: boinc-client-7.12-r0 do_package: SYSTEMD_SERVICE:boinc-client value boinc-client.service does not exist"
# meta-openembedded/meta-oe/recipes-extended/scsirastools/scsirastools_1.6.6.bb:do_package
PNBLACKLIST[scsirastools] ?= "ERROR: scsirastools-1.6.6-r0 do_package: QA Issue: scsirastools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-graphics/dietsplash/dietsplash_git.bb:do_package
PNBLACKLIST[dietsplash] ?= "ERROR: dietsplash-0.3-r1 do_package: QA Issue: dietsplash: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-security/tomoyo-tools/tomoyo-tools_2.5.0.bb:do_package
PNBLACKLIST[tomoyo-tools] ?= "ERROR: tomoyo-tools-2.5.0-r0 do_package: QA Issue: tomoyo-tools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-shells/tcsh/tcsh_6.20.00.bb:do_install
PNBLACKLIST[tcsh] ?= "ERROR: tcsh-6.20.00-r0 do_install: Function failed: do_install: ln: failed to create symbolic link tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/tcsh/6.20.00-r0/image/usr/bin/tcsh: File exists"
# meta-openembedded/meta-oe/recipes-support/gradm/gradm_3.1.bb:do_package
PNBLACKLIST[gradm] ?= "ERROR: gradm-3.1-r0 do_package: QA Issue: gradm: Files/directories were installed but not shipped in any package"
# Fix sent upstream in:
# https://lists.openembedded.org/g/openembedded-devel/message/90578
# will probably get merged in hardknott
# meta-openembedded/meta-oe/recipes-support/libsmi/libsmi_0.5.0.bb:do_package_qa
PNBLACKLIST[libsmi] ?= "ERROR: libsmi-0.5.0-r0 do_package_qa: QA Issue: /usr/bin/smistrip contained in package libsmi requires /usr/bin/sh, but no providers found in RDEPENDS:libsmi? [file-rdeps]"
# meta-raspberrypi/recipes-multimedia/omxplayer/omxplayer_git.bb:do_compile
PNBLACKLIST[omxplayer] ?= "ERROR: omxplayer-git-r4 do_compile: Function failed: do_compile: DllAvFormat.h:117:51: error: ::avio_feof has not been declared"
# meta-virtualization/recipes-containers/lxcfs/lxcfs_3.0.1.bb:do_package
PNBLACKLIST[lxcfs] ?= "ERROR: lxcfs-3.0.1-r0 do_package: SYSTEMD_SERVICE:lxcfs value lxcfs.service does not exist"

# meta-networking/recipes-kernel/wireguard/wireguard-tools_1.0.20200319.bb
PNBLACKLIST[wireguard-tools] ?= "Depends on blacklisted wireguard-module"
# meta-oe/dynamic-layers/meta-python/recipes-extended/lcdproc/lcdproc_git.bb
PNBLACKLIST[lcdproc] ?= "Depends on blacklisted lirc"

# And few more in dunfell
# meta-openembedded/meta-oe/recipes-support/libgusb/libgusb_git.bb:do_configure
PNBLACKLIST[libgusb] ?= "ERROR: libgusb-0.3.0+gitAUTOINC+636efc0624-r0 do_configure: meson failed: gusb/meson.build:128:6: ERROR: Unknown variable libgusb_girtarget."
# meta-openembedded/meta-oe/recipes-gnome/libpeas/libpeas_1.25.3.bb:do_configure
PNBLACKLIST[libpeas] ?= "ERROR: libpeas-1.25.3-r0 do_configure: meson failed: tests/libpeas/introspection/meson.build:52:0: ERROR: Unknown variable libpeas_gir."
# meta-virtualization/recipes-extended/xen/xen-tools_git.bb
PNBLACKLIST[xen-tools] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Fails to build with gold, details in https://lists.yoctoproject.org/g/meta-virtualization/message/5202', '', d)}"

PNBLACKLIST[sysdig] ?= "Doesn't build with jsoncpp with exceptions disabled, meta-webos/recipes-devtools/jsoncpp/jsoncpp_%.bbappend adds -DJSON_USE_EXCEPTION=0"

# meta-virtualization/recipes-extended/xen/xen-python2.bb
PNBLACKLIST[xen-python2] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Depends on blacklisted xen-tools', '', d)}"
# meta-virtualization/recipes-extended/xen/xen_git.bb
PNBLACKLIST[xen] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Depends on blacklisted xen-tools', '', d)}"

# meta-oe/meta-multimedia/recipes-multimedia/packagegroups/packagegroup-meta-multimedia.bb
PNBLACKLIST[packagegroup-meta-multimedia] ?= "ERROR: depends on x265 without respecting LICENSE_FLAGS, fixed in kirkstone https://lists.openembedded.org/g/openembedded-devel/message/94141"

# meta-openembedded/meta-oe/recipes-multimedia/pipewire/pipewire_git.bb
PNBLACKLIST[pipewire] ?= "Doesn't like our old pulseaudio from meta-webos: meson.build:234:2: ERROR: Invalid version of dependency, need 'libpulse' ['>= 11.1'] found '9.0-14-gdf81'."
