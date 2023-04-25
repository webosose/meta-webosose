# Copyright (c) 2013-2023 LG Electronics, Inc.

# A blacklisted recipe is ignored by bitbake. The reason it is useful to be able
# to have bitbake ignore a recipe is because, although PREFERRED_PROVIDER can be
# used to select which recipe is used to fulfill a build-time dependency, bitbake
# does not have a PREFERRED_RPROVIDER to allow specification of which package
# should be used to satisfy a run-time dependency. When two recipes both provide
# common packages, but one recipe provides additional packages that the other
# doesn't, then the only way to guarantee that the first recipe will never be
# used is to blacklist it.

# Use webOS qt5 configuration
SKIP_RECIPE[qwt-qt5] ?= "ERROR: Unknown module(s) in QT: printsupport opengl http://caprica.lgsvl.com:8080/Errors/Details/1509145"
SKIP_RECIPE[libconnman-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509142"
SKIP_RECIPE[libqofono] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
SKIP_RECIPE[libqofonoext] ?= "depends on blacklisted libqofono which requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
SKIP_RECIPE[qtwebkit] ?= "Could not find a package configuration file provided by Qt5Widgets, because widgets are disabled in our builds http://caprica.lgsvl.com:8080/Errors/Details/1509126"
SKIP_RECIPE[qtwebengine] ?= "We use own webruntime based on chromium"
SKIP_RECIPE[qtwebview] ?= "Depends on blacklisted qtwebengine"
SKIP_RECIPE[qt-kiosk-browser] ?= "Depends on blacklisted qtwebengine"
SKIP_RECIPE[maliit-framework-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509125"
SKIP_RECIPE[maliit-plugins-qt5] ?= "depends on blacklisted maliit-framework-qt5"
SKIP_RECIPE[qt5-creator] ?= "depends on blacklisted qtwebkit"
# since 5.9:
SKIP_RECIPE[qtopcua] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509148"
SKIP_RECIPE[qtmqtt] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509146"
SKIP_RECIPE[qtsmarthome] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509144"
# since 5.11:
SKIP_RECIPE[qt3d-runtime] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509147"
SKIP_RECIPE[python-pyqt5] ?= "needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509139"
SKIP_RECIPE[python3-pyqt5] ?= "needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509149"
SKIP_RECIPE[python3-pyqtchart] ?= "depends on blacklisted python3-pyqt5 which needs newer sip than 4.19.12 what we have in thud, This version of PyQt5 requires sip 4.19.14 or later. http://caprica.lgsvl.com:8080/Errors/Details/1509149"
# since 5.12:
SKIP_RECIPE[qmllive] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509138"
SKIP_RECIPE[qtconnectivity] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 ERROR: Feature 'bluez' was enabled, but the pre-condition 'libs.bluez && features.concurrent && features.dbus' failed. http://caprica.lgsvl.com:8080/Errors/Details/1509143"
# since 6.2:
SKIP_RECIPE[packagegroup-qt6-commercial-modules] ?= "depends on Commercial Qt, can be dropped once https://codereview.qt-project.org/c/yocto/meta-qt6/+/470280 is merged"
SKIP_RECIPE[packagegroup-qt6-addons] ?= "depends on blacklisted qtconnectivity, qtmqtt, qtopcua"
SKIP_RECIPE[packagegroup-qt6-modules] ?= "depends on blacklisted packagegroup-qt6-addons"
SKIP_RECIPE[qtdeviceutilities] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200304"
SKIP_RECIPE[qtcharts] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200305"
SKIP_RECIPE[qtdatavis3d] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com/Errors/Details/200303"
SKIP_RECIPE[qtpositioning] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://gecko.lge.com/Errors/Details/200302"
SKIP_RECIPE[qt3d] ?= "fails to build with assimp enabled, Feature qt3d_system_assimp: Forcing to ON breaks its condition: QT_FEATURE_qt3d_assimp AND TEST_assimp, probably related to https://lists.openembedded.org/g/openembedded-core/message/155219"

# meta-oe/recipes-support/vboxguestdrivers/vboxguestdrivers_6.1.18.bb:do_packagedata
SKIP_RECIPE[vboxguestdrivers] ?= "BROKEN: kernel-module-vboxguest conflicts in pkgdata with the vboxguest kernel modules we have enabled in linux-yocto"
# meta/recipes-kernel/linux/kernel-devsrc.bb:do_install
SKIP_RECIPE[kernel-devsrc] ?= "BROKEN for aarch64: make[4]: *** No rule to make target `kernel-devsrc/1.0-r0/image/usr/src/kernel/arch/arm64/boot/dts/broadcom/../overlays/Makefile'.  Stop http://caprica.lgsvl.com:8080/Errors/Details/1509158"
# meta/recipes-extended/sysstat/sysstat_12.4.0.bb:do_install
SKIP_RECIPE[sysstat] ?= "BROKEN: install: invalid group ‘man’ http://caprica.lgsvl.com:8080/Errors/Details/1509136"
# meta/recipes-extended/man-db/man-db_2.9.4.bb:do_install
SKIP_RECIPE[man-db] ?= "BROKEN: without --disable-cache-owner: chown: invalid user: ?man:man? our passwd doesn't have man user and since http://git.openembedded.org/openembedded-core/commit/?id=7080df4b6bc50440eed600e81f2c6fa76a80623d it's needed http://caprica.lgsvl.com:8080/Errors/Details/1509154"

# meta-oe/recipes-core/packagegroups/packagegroup-meta-oe.bb
SKIP_RECIPE[packagegroup-meta-oe] ?= "depends on blacklisted gradm, scsirastools, tomoyo-tools, s-suite, .."
# meta-oe/recipes-support/libssh2/libssh2_1.9.0.bb
SKIP_RECIPE[libssh2] ?= "Depends on blacklisted man-db since https://git.openembedded.org/meta-openembedded/commit/?id=b3e9b51c9ba58b01e55c5b0a9a9bec3f7f06d07a"
# meta-oe/recipes-support/libgit2/libgit2_1.0.1.bb
SKIP_RECIPE[libgit2] ?= "Depends on blacklisted libssh2 which depends on blacklisted man-db since https://git.openembedded.org/meta-openembedded/commit/?id=b3e9b51c9ba58b01e55c5b0a9a9bec3f7f06d07a"
# meta-oe/recipes-benchmark/s-suite/s-suite_git.bb
SKIP_RECIPE[s-suite] ?= "rdepends on blacklisted sysstat"

# meta-virtualization/recipes-containers/cri-o/cri-o_git.bb:do_compile
SKIP_RECIPE[cri-o] ?= "ERROR: cri-o-1.12.0+git774a29ecf6855f2dff266dc2aa2fe81d7d964465-r0 do_compile: Function failed: do_compile: | Makefile:112: recipe for target conmon/config.h failed"
# meta-virtualization/recipes-core/kata-containers/kata-agent_git.bb:do_package_qa
SKIP_RECIPE[kata-agent] ?= "ERROR: kata-agent-git-r0 do_package_qa: QA Issue: ELF binary kata-agent/git-r0/packages-split/kata-agent/usr/bin/kata-agent has relocations in .text [textrel]"
# meta-virtualization/recipes-core/kata-containers/kata-proxy_git.bb:do_package_qa
SKIP_RECIPE[kata-proxy] ?= "ERROR: kata-proxy-git-r0 do_package_qa: QA Issue: ELF binary kata-proxy/git-r0/packages-split/kata-proxy/usr/libexec/kata-containers/kata-proxy has relocations in .text [textrel]"
# meta-virtualization/recipes-core/kata-containers/kata-runtime_git.bb:do_compile
SKIP_RECIPE[kata-runtime] ?= "ERROR: kata-runtime-git-r0 do_compile: oe_runmake failed: make: *** No rule to make target arch/386-options.mk.  Stop."
# meta-virtualization/recipes-core/kata-containers/kata-shim_git.bb:do_package_qa
SKIP_RECIPE[kata-shim] ?= "ERROR: kata-shim-git-r0 do_package_qa: QA Issue: ELF binary kata-shim/git-r0/packages-split/kata-shim/usr/libexec/kata-containers/kata-shim has relocations in .text [textrel]"
# meta-virtualization/recipes-core/runv/runv_git.bb:do_compile
SKIP_RECIPE[runv] ?= "ERROR: runv-0.4.0+gitb360a686abc6c6e896382990ef1b93ef07c7a677-r0 do_compile: Function failed: do_compile: src/import/vendor/src/github.com/hyperhq/runv/hypervisor/qemu/qemu.go:45:28: undefined: QEMU_SYSTEM_EXE"
# meta-virtualization/recipes-extended/seabios/seabios_1.11.2.bb:do_compile
SKIP_RECIPE[seabios] ?= "ERROR: seabios-1.11.2-r0 do_compile: Function failed: do_compile: i586-webos-linux-ld: error: out/rom16.strip.o: unsupported ELF file type 2"
# meta-virtualization/recipes-extended/vgabios/biossums_0.7a.bb:do_package_qa
SKIP_RECIPE[biossums] ?= "ERROR: biossums-0.7a-r0 do_package_qa: QA Issue: No GNU_HASH in the ELF binary biossums/0.7a-r0/packages-split/biossums/usr/bin/biossums, didnt pass LDFLAGS? [ldflags]"

# These are reproducible only with rpi MACHINEs, most likely caused by usrmerge DISTRO_FEATURE enabled with sota from meta-updater
# meta-openembedded/meta-networking/recipes-connectivity/ufw/ufw_0.33.bb:do_package_qa
SKIP_RECIPE[ufw] ?= "ERROR: ufw-0.33-r0 do_package_qa: QA Issue: ufw package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-networking/recipes-filter/arno-iptables-firewall/arno-iptables-firewall_2.0.1g.bb:do_install
SKIP_RECIPE[arno-iptables-firewall] ?= "ERROR: arno-iptables-firewall-2.0.1g-r0 do_install: Function failed: do_install: install: cannot stat tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/arno-iptables-firewall/2.0.1g-r0/arno-iptables-firewall_2.0.1g//usr/lib/systemd/system/arno-iptables-firewall.service: No such file or directory"
# meta-openembedded/meta-networking/recipes-kernel/wireguard/wireguard-module_0.0.20171221.bb:do_package
SKIP_RECIPE[wireguard-module] ?= "ERROR: wireguard-module-0.0.20171221-r0 do_package: QA Issue: wireguard-module: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-protocols/babeld/babeld_1.8.3.bb:do_package
SKIP_RECIPE[babeld] ?= "ERROR: babeld-1.8.3-r0 do_package: QA Issue: babeld: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-support/cifs/cifs-utils_git.bb:do_package
SKIP_RECIPE[cifs-utils] ?= "ERROR: cifs-utils-6.7-r0 do_package: QA Issue: cifs-utils: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-networking/recipes-support/linux-atm/linux-atm_2.5.2.bb:do_package_qa
SKIP_RECIPE[linux-atm] ?= "ERROR: linux-atm-2.5.2-r0 do_package_qa: QA Issue: linux-atm package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-oe/recipes-connectivity/lirc/lirc_0.9.4d.bb:do_package
SKIP_RECIPE[lirc] ?= "ERROR: lirc-0.9.4d-r0 do_package: SYSTEMD_SERVICE:lirc value lircmd.service does not exist"
# meta-openembedded/meta-oe/recipes-extended/boinc/boinc-client_7.12.bb:do_package
SKIP_RECIPE[boinc-client] ?= "ERROR: boinc-client-7.12-r0 do_package: SYSTEMD_SERVICE:boinc-client value boinc-client.service does not exist"
# meta-openembedded/meta-oe/recipes-extended/scsirastools/scsirastools_1.6.6.bb:do_package
SKIP_RECIPE[scsirastools] ?= "ERROR: scsirastools-1.6.6-r0 do_package: QA Issue: scsirastools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-graphics/dietsplash/dietsplash_git.bb:do_package
SKIP_RECIPE[dietsplash] ?= "ERROR: dietsplash-0.3-r1 do_package: QA Issue: dietsplash: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-security/tomoyo-tools/tomoyo-tools_2.5.0.bb:do_package
SKIP_RECIPE[tomoyo-tools] ?= "ERROR: tomoyo-tools-2.5.0-r0 do_package: QA Issue: tomoyo-tools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-shells/tcsh/tcsh_6.20.00.bb:do_install
SKIP_RECIPE[tcsh] ?= "ERROR: tcsh-6.20.00-r0 do_install: Function failed: do_install: ln: failed to create symbolic link tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/tcsh/6.20.00-r0/image/usr/bin/tcsh: File exists"
# meta-openembedded/meta-oe/recipes-support/gradm/gradm_3.1.bb:do_package
SKIP_RECIPE[gradm] ?= "ERROR: gradm-3.1-r0 do_package: QA Issue: gradm: Files/directories were installed but not shipped in any package"
# meta-raspberrypi/recipes-multimedia/omxplayer/omxplayer_git.bb:do_compile
SKIP_RECIPE[omxplayer] ?= "ERROR: omxplayer-git-r4 do_compile: Function failed: do_compile: DllAvFormat.h:117:51: error: ::avio_feof has not been declared"
# meta-virtualization/recipes-containers/lxcfs/lxcfs_3.0.1.bb:do_package
SKIP_RECIPE[lxcfs] ?= "ERROR: lxcfs-3.0.1-r0 do_package: SYSTEMD_SERVICE:lxcfs value lxcfs.service does not exist"

# meta-networking/recipes-kernel/wireguard/wireguard-tools_1.0.20200319.bb
SKIP_RECIPE[wireguard-tools] ?= "Depends on blacklisted wireguard-module"
# meta-oe/dynamic-layers/meta-python/recipes-extended/lcdproc/lcdproc_git.bb
SKIP_RECIPE[lcdproc] ?= "Depends on blacklisted lirc"

# And few more in dunfell
# meta-virtualization/recipes-extended/xen/xen-tools_git.bb
SKIP_RECIPE[xen-tools] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Fails to build with gold, details in https://lists.yoctoproject.org/g/meta-virtualization/message/5202', '', d)}"

SKIP_RECIPE[sysdig] ?= "Doesn't build with jsoncpp with exceptions disabled, meta-webos/recipes-devtools/jsoncpp/jsoncpp_%.bbappend adds -DJSON_USE_EXCEPTION=0"

# meta-virtualization/recipes-extended/xen/xen-python2.bb
SKIP_RECIPE[xen-python2] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Depends on blacklisted xen-tools', '', d)}"
# meta-virtualization/recipes-extended/xen/xen_git.bb
SKIP_RECIPE[xen] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Depends on blacklisted xen-tools', '', d)}"
# meta-virtualization/recipes-extended/xen/xtf_git.bb
SKIP_RECIPE[xtf] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Rdepends on xen-tools-xl package provided by blacklisted xen-tools', '', d)}"
