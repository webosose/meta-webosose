# Copyright (c) 2013-2020 LG Electronics, Inc.

# A blacklisted recipe is ignored by bitbake. The reason it is useful to be able
# to have bitbake ignore a recipe is because, although PREFERRED_PROVIDER can be
# used to select which recipe is used to fulfill a build-time dependency, bitbake
# does not have a PREFERRED_RPROVIDER to allow specification of which package
# should be used to satisfy a run-time dependency. When two recipes both provide
# common packages, but one recipe provides additional packages that the other
# doesn't, then the only way to guarantee that the first recipe will never be
# used is to blacklist it.

PNBLACKLIST[vboxguestdrivers] ?= "BROKEN: fails to build with newer kernels and not used anymore: error: macro 'access_ok' passed 3 arguments, but takes just 2 http://caprica.lgsvl.com:8080/Errors/Details/1451808"

# Use webOS qt5 configuration
PNBLACKLIST[qwt-qt5] ?= "ERROR: Unknown module(s) in QT: printsupport opengl http://caprica.lgsvl.com:8080/Errors/Details/1509145"
PNBLACKLIST[libconnman-qt5] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509142"
PNBLACKLIST[libqofono] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 http://caprica.lgsvl.com:8080/Errors/Details/1509141"
PNBLACKLIST[qtwebkit] ?= "Could not find a package configuration file provided by Qt5Widgets, because widgets are disabled in our builds http://caprica.lgsvl.com:8080/Errors/Details/1509126"
PNBLACKLIST[qtwebengine] ?= "We use own webruntime based on chromium"
PNBLACKLIST[qtwebview] ?= "Depends on blacklisted qtwebengine"
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
# since 5.12:
PNBLACKLIST[qmllive] ?= "requires widgets module enabled in qtbase, which was disabled in PLAT-18645 http://caprica.lgsvl.com:8080/Errors/Details/1509138"
PNBLACKLIST[qtconnectivity] ?= "requires dbus module enabled in qtbase, which was disabled in PLAT-18268 ERROR: Feature 'bluez' was enabled, but the pre-condition 'libs.bluez && features.concurrent && features.dbus' failed. http://caprica.lgsvl.com:8080/Errors/Details/1509143"

PNBLACKLIST[kernel-devsrc] ?= "BROKEN for aarch64: make[4]: *** No rule to make target `kernel-devsrc/1.0-r0/image/usr/src/kernel/arch/arm64/boot/dts/broadcom/../overlays/Makefile'.  Stop http://caprica.lgsvl.com:8080/Errors/Details/1509158"
PNBLACKLIST[glmark2] ?= "broken: we don't build libGL with mesa: cannot find -lGL http://caprica.lgsvl.com:8080/Errors/Details/1509155"
PNBLACKLIST[sysstat] ?= "BROKEN: install: invalid group ‘man’ http://caprica.lgsvl.com:8080/Errors/Details/1509136"
PNBLACKLIST[man-db] ?= "BROKEN: without --disable-cache-owner: chown: invalid user: ?man:man? our passwd doesn't have man user and since http://git.openembedded.org/openembedded-core/commit/?id=7080df4b6bc50440eed600e81f2c6fa76a80623d it's needed http://caprica.lgsvl.com:8080/Errors/Details/1509154"
PNBLACKLIST[hplip] ?= "BROKEN: configure: error: could not force soft-float and chgrp: invalid group: ‘lp’ http://caprica.lgsvl.com:8080/Errors/Details/1509153"
PNBLACKLIST[gst-plugins-gl] ?= "GL/glew.h:1205:14: fatal error: GL/glu.h: No such file or directory http://caprica.lgsvl.com:8080/Errors/Details/1509152"
PNBLACKLIST[poppler] ?= "fails to generate GIR http://caprica.lgsvl.com:8080/Errors/Details/1509140"
PNBLACKLIST[s-suite] ?= "rdepends on blacklisted sysstat"
PNBLACKLIST[mongodb] ?= "src/mongo/util/net/ssl_manager.cpp:557:70: error: invalid conversion from 'size_t*' {aka 'unsigned int*'} to 'long unsigned int*' [-fpermissive] http://caprica.lgsvl.com:8080/Errors/Details/1509767"
PNBLACKLIST[netkit-rusers] ?= "BROKEN: fails to build rup.c:57:45: error: unknown type name 'statstime'; did you mean 'si_stime'? http://caprica.lgsvl.com:8080/Errors/Details/1530992"
PNBLACKLIST[android-tools] ?= "Fails to build with thumb enabled http://caprica.lgsvl.com:8080/Errors/Details/1532133"
PNBLACKLIST[paho-mqtt-c] ?= "Sometimes fails with cannot create build/VersionInfo.h: Directory nonexistent http://caprica.lgsvl.com:8080/Errors/Details/1530992"

PNBLACKLIST[packagegroup-meta-oe] ?= "depends on blacklisted ndctl, .."

# ERROR: QA Issue: ELF binary 'TOPDIR/BUILD/work/qemux86-webos-linux/x264/r2854+gitAUTOINC+e9a5903edf-r0/packages-split/x264/usr/lib/libx264.so.152' has relocations in .text [textrel] http://caprica.lgsvl.com:8080/Errors/Details/1509766
INSANE_SKIP_${PN}_pn-x264 = "textrel"
# ERROR: QA Issue: ELF binary 'TOPDIR/BUILD/work/qemux86-webos-linux/x264/r2854+gitAUTOINC+e9a5903edf-r0/packages-split/x264-bin/usr/bin/x264' has relocations in .text [textrel] http://caprica.lgsvl.com:8080/Errors/Details/1530991
INSANE_SKIP_${PN}-bin_pn-x264 = "textrel"

# QA Issue: ELF binary 'work/raspberrypi3-webos-linux-gnueabi/mpeg2dec/0.5.1-r0/packages-split/libmpeg2/usr/lib/libmpeg2.so.0.1.0' has relocations in .text
INSANE_SKIP_libmpeg2_pn-mpeg2dec += "textrel"

PNBLACKLIST[nagios-core] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on apache2 from meta-webserver which is not included', d)}"
PNBLACKLIST[nagios-plugins] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
PNBLACKLIST[nagios-nrpe] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Depends on nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
PNBLACKLIST[nagios-nsca] ?= "${@bb.utils.contains('BBFILE_COLLECTIONS', 'webserver', '', 'Rdepends on nagios-base provided by nagios-core which depends on apache2 from meta-webserver which is not included', d)}"
PNBLACKLIST[ipxe] ?= "Doesn't match the COMPATIBLE_HOST restriction from syslinux: syslinux was skipped: incompatible with host arm-webos-linux-gnueabi (not in COMPATIBLE_HOST)"

# meta-openembedded/meta-multimedia/recipes-multimedia/gerbera/gerbera_git.bb:do_compile
PNBLACKLIST[gerbera] ?= "ERROR: gerbera-1.0+gitAUTOINC+4435b165d1-r0 do_compile: Function failed: do_compile: 8.2.0/ld: error: gerbera/1.0+gitAUTOINC+4435b165d1-r0/recipe-sysroot-native/usr/lib/libnsl.so: incompatible target"
# meta-updater/recipes-test/big-update/big-update_2.0.bb:do_populate_lic
PNBLACKLIST[big-update] ?= "ERROR: big-update-2.0-r0 do_populate_lic: QA Issue: big-update: Recipe file fetches files and does not have license file information (LIC_FILES_CHKSUM) [license-checksum]"
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
# meta-virtualization/recipes-devtools/python/python-m2crypto_0.22.3.bb:do_configure
PNBLACKLIST[python-m2crypto] ?= "ERROR: python-m2crypto-0.22.3-r0 do_configure: Function failed: do_configure: Error: CPP #error __WORDSIZE is not defined. Use the -cpperraswarn option to continue swig processing."
# meta-virtualization/recipes-extended/dev86/dev86_0.16.20.bb:do_fetch
PNBLACKLIST[dev86-native] ?= "ERROR: dev86-native-0.16.20-r0 do_fetch: Fetcher failure: Connecting to v3.sk (v3.sk)|68.183.70.209|:80... connected. HTTP request sent, awaiting response... 503 Service Unavailable"
PNBLACKLIST[dev86] ?= "ERROR: dev86-native-0.16.20-r0 do_fetch: Fetcher failure: Connecting to v3.sk (v3.sk)|68.183.70.209|:80... connected. HTTP request sent, awaiting response... 503 Service Unavailable"
# meta-virtualization/recipes-extended/ipxe/ipxe_git.bb:do_compile
PNBLACKLIST[ipxe] ?= "ERROR: ipxe-gitrAUTOINC+64b4452bca-r0 do_compile: Function failed: do_compile: Makefile.housekeeping:111: GNU gold is unsuitable for building iPXE"
# meta-virtualization/recipes-extended/kvmtool/kvmtool.bb:do_compile
PNBLACKLIST[kvmtool] ?= "ERROR: kvmtool-4.14.0+git0e1882a49f81cb15d328ef83a78849c0ea26eecc-r0 do_compile: Function failed: do_compile: virtio/net.c:336:47: error: argument to sizeof in strncpy call is the same expression as the source; did you mean to use the size of the destination? [-Werror=sizeof-pointer-memaccess]"
# meta-virtualization/recipes-extended/seabios/seabios_1.11.2.bb:do_compile
PNBLACKLIST[seabios] ?= "ERROR: seabios-1.11.2-r0 do_compile: Function failed: do_compile: i586-webos-linux-ld: error: out/rom16.strip.o: unsupported ELF file type 2"
# meta-virtualization/recipes-extended/vgabios/biossums_0.7a.bb:do_package_qa
PNBLACKLIST[biossums] ?= "ERROR: biossums-0.7a-r0 do_package_qa: QA Issue: No GNU_HASH in the ELF binary biossums/0.7a-r0/packages-split/biossums/usr/bin/biossums, didnt pass LDFLAGS? [ldflags]"
# meta-virtualization/recipes-networking/cni/cni_git.bb:do_compile
PNBLACKLIST[cni] ?= "ERROR: cni-0.6.0+gitcc562d1b44b3d00b8c341250d49a8a0823a1c085-r0 do_compile: Function failed: do_compile: | ../../../src/github.com/containernetworking/plugins/vendor/github.com/safchain/ethtool/ethtool.go:205:35: undefined: syscall.SYS_SOCKET"
# meta-webosose/meta-webos-backports/meta-webos-backports-3.0/recipes-multimedia/gstreamer/gst-validate_1.16.0.bb:do_configure
PNBLACKLIST[gst-validate] ?= "ERROR: gst-validate-1.16.0-r0 do_configure: Function failed: do_configure: configure: Requested gstreamer-1.0 >= 1.16.0 but version of GStreamer is 1.14.4"
# meta-webosose/meta-webos-backports/meta-webos-backports-3.0/recipes-multimedia/gstreamer/gstreamer1.0-rtsp-server_1.16.0.bb:do_configure
PNBLACKLIST[gstreamer1.0-rtsp-server] ?= "ERROR: gstreamer1.0-rtsp-server-1.16.0-r0 do_configure: Function failed: do_configure: | configure: Requested gstreamer-1.0 >= 1.16.0 but version of GStreamer is 1.14.4"
# meta-webosose/meta-webos-backports/meta-webos-backports-3.0/recipes-multimedia/gstreamer/gstreamer1.0-vaapi_1.16.0.bb:do_configure
PNBLACKLIST[gstreamer1.0-vaapi] ?= "ERROR: gstreamer1.0-vaapi-1.16.0-r0 do_configure: Function failed: do_configure: configure: Requested gstreamer-1.0 >= 1.16.0 but version of GStreamer is 1.14.4"

PNBLACKLIST[openlmi-tools] ?= "Depends on blacklisted python-m2crypto"
PNBLACKLIST[gstd] ?= "Depends on blacklisted gstreamer1.0-rtsp-server"
PNBLACKLIST[vgabios] ?= "Depends on blacklisted dev86-native"
PNBLACKLIST[xen] ?= "Depends on blacklisted dev86-native"
PNBLACKLIST[python-pywbem] ?= "Depends on blacklisted python-m2crypto"
PNBLACKLIST[kubernetes] ?= "Depends on blacklisted cni"

# These are reproducible only with rpi MACHINEs, most likely caused by usrmerge DISTRO_FEATURE enabled with sota from meta-updater
# meta-openembedded/meta-filesystems/recipes-utils/xfsdump/xfsdump_3.1.8.bb:do_configure
PNBLACKLIST[xfsdump] ?= "ERROR: xfsdump-3.1.8-r0 do_configure: Function failed: do_configure: | FATAL ERROR: could not find a current XFS handle library."
# meta-openembedded/meta-filesystems/recipes-utils/xfsprogs/xfsprogs_4.18.0.bb:do_package
PNBLACKLIST[xfsprogs] ?= "ERROR: xfsprogs-4.18.0-r0 do_package: QA Issue: xfsprogs: Files/directories were installed but not shipped in any package:"
# meta-openembedded/meta-multimedia/recipes-connectivity/rygel/rygel_0.34.0.bb:do_install
PNBLACKLIST[rygel] ?= "ERROR: rygel-0.34.0-r0 do_install: Function failed: do_install: rmdir: failed to remove tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/rygel/0.34.0-r0/image/usr/lib/systemd: Directory not empty "
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
PNBLACKLIST[lirc] ?= "ERROR: lirc-0.9.4d-r0 do_package: SYSTEMD_SERVICE_lirc value lircmd.service does not exist"
# meta-openembedded/meta-oe/recipes-connectivity/phonet-utils/phonet-utils_git.bb:do_package_qa
PNBLACKLIST[phonet-utils] ?= "ERROR: phonet-utils-0.0.0+gitrAUTOINC+4acfa720fd-r2 do_package_qa: QA Issue: phonet-utils package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-openembedded/meta-oe/recipes-extended/boinc/boinc-client_7.12.bb:do_package
PNBLACKLIST[boinc-client] ?= "ERROR: boinc-client-7.12-r0 do_package: SYSTEMD_SERVICE_boinc-client value boinc-client.service does not exist"
# meta-openembedded/meta-oe/recipes-extended/fluentbit/fluentbit_0.12.1.bb:do_package
PNBLACKLIST[fluentbit] ?= "ERROR: fluentbit-0.12.1-r0 do_package: SYSTEMD_SERVICE_fluentbit value td-agent-bit.service does not exist"
# meta-openembedded/meta-oe/recipes-extended/scsirastools/scsirastools_1.6.6.bb:do_package
PNBLACKLIST[scsirastools] ?= "ERROR: scsirastools-1.6.6-r0 do_package: QA Issue: scsirastools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-extended/uml-utilities/uml-utilities_20040406.bb:do_package
PNBLACKLIST[uml-utilities] ?= "ERROR: uml-utilities-20040406-r1 do_package: QA Issue: uml-utilities: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-graphics/dietsplash/dietsplash_git.bb:do_package
PNBLACKLIST[dietsplash] ?= "ERROR: dietsplash-0.3-r1 do_package: QA Issue: dietsplash: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-security/tomoyo-tools/tomoyo-tools_2.5.0.bb:do_package
PNBLACKLIST[tomoyo-tools] ?= "ERROR: tomoyo-tools-2.5.0-r0 do_package: QA Issue: tomoyo-tools: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-shells/tcsh/tcsh_6.20.00.bb:do_install
PNBLACKLIST[tcsh] ?= "ERROR: tcsh-6.20.00-r0 do_install: Function failed: do_install: ln: failed to create symbolic link tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/tcsh/6.20.00-r0/image/usr/bin/tcsh: File exists"
# meta-openembedded/meta-oe/recipes-support/gradm/gradm_3.1.bb:do_package
PNBLACKLIST[gradm] ?= "ERROR: gradm-3.1-r0 do_package: QA Issue: gradm: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-oe/recipes-support/libsmi/libsmi_0.5.0.bb:do_package_qa
PNBLACKLIST[libsmi] ?= "ERROR: libsmi-0.5.0-r0 do_package_qa: QA Issue: /usr/bin/smistrip contained in package libsmi requires /usr/bin/sh, but no providers found in RDEPENDS_libsmi? [file-rdeps]"
# meta-openembedded/meta-oe/recipes-support/usb-modeswitch/usb-modeswitch_2.5.0.bb:do_package
PNBLACKLIST[usb-modeswitch] ?= "ERROR: usb-modeswitch-2.5.0-r0 do_package: QA Issue: usb-modeswitch: Files/directories were installed but not shipped in any package"
# meta-openembedded/meta-python/recipes-devtools/python/python-pyrex_0.9.9.bb:do_package
PNBLACKLIST[python-pyrex] ?= "ERROR: python-pyrex-0.9.9-r4 do_package: QA Issue: python-pyrex: Files/directories were installed but not shipped in any package"
# meta-raspberrypi/recipes-multimedia/omxplayer/omxplayer_git.bb:do_compile
PNBLACKLIST[omxplayer] ?= "ERROR: omxplayer-git-r4 do_compile: Function failed: do_compile: DllAvFormat.h:117:51: error: ::avio_feof has not been declared"
# meta-virtualization/recipes-containers/cgroup-lite/cgroup-lite_1.15.bb:do_package
PNBLACKLIST[cgroup-lite] ?= "ERROR: cgroup-lite-1.15-r0 do_package: QA Issue: cgroup-lite: Files/directories were installed but not shipped in any package"
# meta-virtualization/recipes-containers/lxcfs/lxcfs_3.0.1.bb:do_package
PNBLACKLIST[lxcfs] ?= "ERROR: lxcfs-3.0.1-r0 do_package: SYSTEMD_SERVICE_lxcfs value lxcfs.service does not exist"
# meta-virtualization/recipes-extended/diod/diod_1.0.24.bb:do_package
PNBLACKLIST[diod] ?= "ERROR: diod-1.0.24+gitAUTOINC+0ea3fe3d82-r0 do_package: QA Issue: diod: Files/directories were installed but not shipped in any package"
# meta-webosose/meta-webos/recipes-upstreamable/bootchart2/bootchart2_git.bb:do_package
PNBLACKLIST[bootchart2] ?= "ERROR: bootchart2-0.14.8+gitAUTOINC+42509aa0c9-r10 do_package: SYSTEMD_SERVICE_bootchart2 value bootchart2.service does not exist"
# openembedded-core/meta/recipes-connectivity/avahi/avahi-ui_0.7.bb:do_install
PNBLACKLIST[avahi-ui] ?= "ERROR: avahi-ui-0.7-r0 do_install: Function failed: do_install: rm: cannot remove tmp-glibc/work/raspberrypi4-webos-linux-gnueabi/avahi-ui/0.7-r0/image/usr/lib/libavahi-c*: No such file or directory"
# openembedded-core/meta/recipes-devtools/systemd-bootchart/systemd-bootchart_233.bb:do_package
PNBLACKLIST[systemd-bootchart] ?= "ERROR: systemd-bootchart-233+gitAUTOINC+fe1c5e41e6-r0 do_package: SYSTEMD_SERVICE_systemd-bootchart value systemd-bootchart.service does not exist"

PNBLACKLIST[wireguard-tools] ?= "Depends on blacklisted wireguard-module"
PNBLACKLIST[python-snimpy] ?= "Depends on blacklisted libsmi"
PNBLACKLIST[lcdproc] ?= "Depends on blacklisted lirc"
PNBLACKLIST[usb-modeswitch-data] ?= "Depends on blacklisted usb-modeswitch"

# Recipes failing because of svn fetcher issue
# http://lists.openembedded.org/pipermail/openembedded-devel/2020-February/205012.html
# it was fixed in dunfell:
# https://github.com/openembedded/meta-openembedded/commit/6902dcd2edc3a08f6929e19e9a9c18493883f9d2
# and then all of these were removed:
# https://github.com/openembedded/meta-openembedded/commit/7449a3e08389a0c50cdf17bc16995ef1678dc2d2
# meta-openembedded/meta-oe/recipes-support/samsung-soc-utils/s3c24xx-gpio_svn.bb:do_populate_lic
# meta-openembedded/meta-oe/recipes-support/samsung-soc-utils/s3c24xx-gpio_svn.bb:do_compile
PNBLACKLIST[s3c24xx-gpio] ?= "svn://svn.openmoko.org is automatically redirected to https://github.com/openmoko/openmoko-svn where it's fetched, but into different directory, S does not point to correct directory and build fails"
# meta-openembedded/meta-oe/recipes-support/samsung-soc-utils/s3c64xx-gpio_svn.bb:do_populate_lic
# meta-openembedded/meta-oe/recipes-support/samsung-soc-utils/s3c64xx-gpio_svn.bb:do_compile
PNBLACKLIST[s3c64xx-gpio] ?= "svn://svn.openmoko.org is automatically redirected to https://github.com/openmoko/openmoko-svn where it's fetched, but into different directory, S does not point to correct directory and build fails"
# meta-openembedded/meta-oe/recipes-support/wmiconfig/wmiconfig_svn.bb:do_patch
PNBLACKLIST[wmiconfig] ?= "svn://svn.openmoko.org is automatically redirected to https://github.com/openmoko/openmoko-svn where it's fetched, but into different directory, S does not point to correct directory and build fails"
# meta-openembedded/meta-oe/recipes-support/usbpath/usbpath_svn.bb:do_patch
PNBLACKLIST[usbpath] ?= "svn://svn.openmoko.org is automatically redirected to https://github.com/openmoko/openmoko-svn where it's fetched, but into different directory, S does not point to correct directory and build fails"

# Few more failing in warrior:
# meta-virtualization/recipes-networking/netns/netns_git.bb:do_compile_ptest_base
PNBLACKLIST[netns] ?= "ERROR: netns-0.5.3-r0 do_compile_ptest_base: Function failed: do_compile_ptest_base: | bridge.go:10:2: cannot find package "github.com/genuinetools/netns/netutils""
# meta-openembedded/meta-multimedia/recipes-multimedia/dvb-apps/dvb-apps_1.1.1.bb:do_package_qa
PNBLACKLIST[dvb-apps] ?= "ERROR: dvb-apps-1.1.1-r0 do_package_qa: QA Issue: dvb-apps package is not obeying usrmerge distro feature. /lib should be relocated to /usr. [usrmerge]"
# meta-updater/recipes-support/slcand-start/slcand-start.bb:do_populate_lic
PNBLACKLIST[slcand-start] ?= "ERROR: slcand-start-1.0-r0 do_populate_lic: QA Issue: slcand-start: The LIC_FILES_CHKSUM does not match for file://openembedded-core/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

PNBLACKLIST[tvheadend] ?= "Depends on blacklisted dvb-apps"

# Few more in zeus, but we can blacklist them in advance in thud branch, to keep it simple
# meta-openembedded/meta-networking/recipes-support/wireshark/wireshark_3.0.6.bb:do_configure
PNBLACKLIST[wireshark] ?= "ERROR: wireshark-1_3.0.6-r0 do_configure: Could not find a package configuration file provided by Qt5PrintSupport"
# meta-openembedded/meta-oe/recipes-connectivity/telepathy/telepathy-glib_0.24.1.bb:do_configure
PNBLACKLIST[telepathy-glib] ?= "ERROR: telepathy-glib-0.24.1-r0 do_configure: configure failed: configure: error: GObject-Introspection must be enabled for Vala bindings"
# meta-openembedded/meta-oe/recipes-graphics/yad/yad_4.1.bb:do_compile
PNBLACKLIST[yad] ?= "ERROR: yad-4.1-r0 do_compile: oe_runmake failed: git/src/yad.h:28:10: fatal error: gdk/gdkx.h: No such file or directory"
# meta-openembedded/meta-oe/recipes-support/xmlsec1/xmlsec1_1.2.28.bb:do_configure
PNBLACKLIST[xmlsec1] ?= "ERROR: xmlsec1-1.2.28-r0 do_configure: configure failed: checking for nspr library installation in qemux86-webos-linux/xmlsec1/1.2.28-r0/recipe-sysroot/usr/lib/../.. folder... configure: error: not found: qemux86-webos-linux/xmlsec1/1.2.28-r0/recipe-sysroot/usr/lib/../../usr/include/nspr.h and/or qemux86-webos-linux/xmlsec1/1.2.28-r0/recipe-sysroot/usr/lib/../..//usr/lib/libnspr4.so files don't exist), typo?"

PNBLACKLIST[remmina] ?= "Depends on blacklisted avahi-ui"
PNBLACKLIST[telepathy-idle] ?= "Depends on blacklisted telepathy-glib"
PNBLACKLIST[packagegroup-meta-multimedia] ?= "Depends on blacklisted gstd, gerbera, rygel"

ERROR_QA_remove_pn-uthash = " src-uri-bad"
ERROR_QA_remove_pn-libuv = " src-uri-bad"
ERROR_QA_remove_pn-uhubctl = " src-uri-bad"
ERROR_QA_remove_pn-firewalld = " src-uri-bad"
ERROR_QA_remove_pn-python-matplotlib = " src-uri-bad"
ERROR_QA_remove_pn-arno-iptables-firewall = " src-uri-bad"
ERROR_QA_remove_pn-nana = " src-uri-bad"
ERROR_QA_remove_pn-dumb-init = " src-uri-bad"
ERROR_QA_remove_pn-crash = " src-uri-bad"
