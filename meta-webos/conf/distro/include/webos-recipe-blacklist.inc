# Copyright (c) 2013-2023 LG Electronics, Inc.

# A blacklisted recipe is ignored by bitbake. The reason it is useful to be able
# to have bitbake ignore a recipe is because, although PREFERRED_PROVIDER can be
# used to select which recipe is used to fulfill a build-time dependency, bitbake
# does not have a PREFERRED_RPROVIDER to allow specification of which package
# should be used to satisfy a run-time dependency. When two recipes both provide
# common packages, but one recipe provides additional packages that the other
# doesn't, then the only way to guarantee that the first recipe will never be
# used is to blacklist it.

# meta-qt6
SKIP_RECIPE[packagegroup-qt6-addons] ?= "rdepends on blacklisted qtcharts, qtdeviceutilities, qtapplicationmanager, qtinterfaceframework, qtconnectivity-tools, qtmultimedia"
SKIP_RECIPE[packagegroup-qt6-modules] ?= "rdepends on blacklisted packagegroup-qt6-addons"
SKIP_RECIPE[packagegroup-qt6-commercial-modules] ?= "depends on Commercial Qt, can be dropped once https://codereview.qt-project.org/c/yocto/meta-qt6/+/470280 is merged"
SKIP_RECIPE[qtapplicationmanager] ?= "fails to build: Qt6::QWaylandEglPlatformIntegrationPlugi references non-existent qtapplicationmanager/6.4.1-r0/recipe-sysroot/usr/lib/plugins/platforms/libqwayland-egl.so http://gecko.lge.com:8000/Errors/Details/565731"
SKIP_RECIPE[qtdeviceutilities] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565732"
SKIP_RECIPE[qtcharts] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565734"
SKIP_RECIPE[qtconnectivity] ?= "fails to build with dbus and widgets disabled in qtbase: Failed to find optional Qt component DBus which is needed for bluetooth The DBus target is mentioned as a dependency for Bluetooth, but not declared.. http://gecko.lge.com:8000/Errors/Details/565738"
SKIP_RECIPE[qtdatavis3d] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565733"
SKIP_RECIPE[qtmultimedia] ?= "fails to build: qtmultimedia/6.0.0-r1/git/src/qtmultimediaquicktools/qsgvideonode_texture.cpp:56:30: error: 'class QSGRhiSupport' has no member named 'isRhiEnabled'. http://gecko.lge.com:8000/Errors/Details/565741"
SKIP_RECIPE[qtpdf] ?= "fails to build with widgets disabled in qtbase: Failed to find required Qt component Widgets. http://gecko.lge.com:8000/Errors/Details/565740"
SKIP_RECIPE[qtwebengine] ?= "fails to build with printing-and-pdf PACKAGECONFIG enabled (default): ERROR: Feature 'webengine_printing_and_pdf': Forcing to 'ON' breaks its condition: TARGET Qt::PrintSupport AND QT_FEATURE_printer AND ( CUPS_FOUND OR WIN32 ) Condition values dump: TARGET Qt::PrintSupport not found QT_FEATURE_printer not evaluated CUPS_FOUND = 'TRUE' WIN32 = '' http://gecko.lge.com:8000/Errors/Details/565744"
SKIP_RECIPE[qtwebview] ?= "depends on blacklisted qtwebengine"
SKIP_RECIPE[qtinterfaceframework] ?= "depends on blacklisted qtmultimedia"

# oe-core
SKIP_RECIPE[efivar] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Fails to build with gold, makeguids Your linker is not supported guids.txt guid-symbols.c include/efivar/efivar-guids.h guids.lds http://gecko.lge.com:8000/Errors/Details/565720', '', d)}"
SKIP_RECIPE[efibootmgr] ?= "depends on blacklisted efivar"

# meta-oe
SKIP_RECIPE[exprtk] ?= "fails to fetch: WARNING: Failed to fetch URL git://github.com/ArashPartow/exprtk.git;branch=master;protocol=https, attempting MIRRORS if available DEBUG: Fetcher failure: Unable to find revision 281c2ccc65b8f91c012ea3725ebcef406378a225 in branch master even from upstream: http://gecko.lge.com:8000/Errors/Details/565715"
SKIP_RECIPE[fwts] ?= "fails to build with: fwts/22.01.00-r0/build/src/lib/src/.libs/libfwts.so: error: undefined reference to 'gzgets' http://gecko.lge.com:8000/Errors/Details/565736"
SKIP_RECIPE[graphene] ?= "fails to build with: Could not find GIR file 'GObject-2.0.gir'; check XDG_DATA_DIRS or use --includedir, later moved from meta-oe to oe-core and possibly fixed in mickledore, http://gecko.lge.com:8000/Errors/Details/565726"
SKIP_RECIPE[mozjs-78] ?= "fails to build probably due to our backported rust upgrade with: error: Error loading target specification: Could not find specification for target 'aarch64-webos-linux'. Run `rustc --print target-list` for a list of built-in targets http://gecko.lge.com:8000/Errors/Details/565735"
SKIP_RECIPE[mozjs-91] ?= "fails to build probably due to our backported rust upgrade with: error: Error loading target specification: Could not find specification for target 'aarch64-webos-linux'. Run `rustc --print target-list` for a list of built-in targets http://gecko.lge.com:8000/Errors/Details/565737"
SKIP_RECIPE[netplan] ?= "fails to build with gold: aarch64-webos-linux/11.3.0/ld: error: abicompat.lds:10:8: syntax error, unexpected STRING http://gecko.lge.com:8000/Errors/Details/565730"
SKIP_RECIPE[transmission] ?= "fails to fetch: WARNING: Failed to fetch URL gitsm://github.com/transmission/transmission;branch=master;protocol=https, attempting MIRRORS if available DEBUG: Fetcher failure: Unable to find revision bb6b5a062ee594dfd4b7a12a6b6e860c43849bfd in branch master even from upstream: http://gecko.lge.com:8000/Errors/Details/565717"
SKIP_RECIPE[vboxguestdrivers] ?= "BROKEN: kernel-module-vboxguest conflicts in pkgdata with the vboxguest kernel modules we have enabled in linux-yocto meta-webos/recipes-kernel/linux/linux-yocto/vbox_gfx.cfg:CONFIG_VBOXGUEST=m http://gecko.lge.com:8000/Errors/Details/565857"

# meta-networking
SKIP_RECIPE[networkmanager] ?= "fails to build with: NetworkManager-1.36.2/meson.build:821:4: ERROR: Assert failed: vala api require GObject introspection. Use -Dvapi=false to disable it: http://gecko.lge.com:8000/Errors/Details/565739"
SKIP_RECIPE[networkmanager-openvpn] ?= "depends on blacklisted networkmanager"
SKIP_RECIPE[python3-networkmanager] ?= "rdepends on blacklisted networkmanager"

# meta-python
SKIP_RECIPE[python3-grpcio] ?= "fails to build with: distutils.errors.DistutilsExecError: command 'TOPDIR/BUILD/work/raspberrypi4_64-webos-linux/python3-grpcio/1.45.0-r0/recipe-sysroot-native/usr/bin/aarch64-webos-linux/aarch64-webos-linux-gcc' failed with exit code 1 http://gecko.lge.com:8000/Errors/Details/565724"
SKIP_RECIPE[python3-grpcio-tools] ?= "depends on blacklisted python3-grpcio"
SKIP_RECIPE[python3-google-api-core] ?= "rdepends on blacklisted python3-grpcio"
SKIP_RECIPE[python3-googleapis-common-protos] ?= "rdepends on blacklisted python3-grpcio"
SKIP_RECIPE[python3-google-api-python-client] ?= "rdepends on blacklisted python3-google-api-core"

# meta-multimedia
SKIP_RECIPE[mycroft] ?= "rdepends on blacklisted python3-google-api-python-client"
SKIP_RECIPE[packagegroup-meta-multimedia] ?= "rdepends on blacklisted mycroft"

# meta-virtualization
SKIP_RECIPE[python3-newrelic] ?= "fails to build with: FileExistsError: File already exists: TOPDIR/BUILD/work/raspberrypi4_64-webos-linux/python3-newrelic/6.2.0.156-r0/image/usr/bin/newrelic-admin http://gecko.lge.com:8000/Errors/Details/565723"
SKIP_RECIPE[nerdctl] ?= "fails to fetch: DEBUG: Fetcher failure: Unable to find revision 48f189a53a24c12838433f5bb5dd57f536816a8a in branch master even from upstream NOTE: Removing /mnt/mirror-write/webos/master/downloads/git2/github.com.containerd.nerdctl.git http://gecko.lge.com:8000/Errors/Details/565716"
SKIP_RECIPE[runv] ?= "fails to build with: go: cannot find main module, but found .git/config in TOPDIR http://gecko.lge.com:8000/Errors/Details/565721"
SKIP_RECIPE[upx] ?= "fails to fetch: WARNING: Failed to fetch URL git://github.com/upx/upx-vendor-doctest;name=vendor_doctest;subdir=git/vendor/doctest;branch=upx-vendor;protocol=https, attempting MIRRORS if available, DEBUG: Fetcher failure: Unable to find revision 666e648b68fda2deb141a1fe93e3fd1e2795dd0f in branch upx-vendor even from upstream: http://gecko.lge.com:8000/Errors/Details/565718"
SKIP_RECIPE[xen-tools] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Fails to build with gold, aarch64-webos-linux/11.3.0/ld: error: mapfile-GNU:1:9: invalid use of VERSION in input file, details in https://lists.yoctoproject.org/g/meta-virtualization/message/5202 http://gecko.lge.com:8000/Errors/Details/565743', '', d)}"
SKIP_RECIPE[xen] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Fails to build with gold, aarch64-webos-linux-ld: internal error in output_address, at ../../gold/output.cc:3027, http://gecko.lge.com:8000/Errors/Details/565742"
SKIP_RECIPE[xtf] ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'Rdepends on blacklisted xen-tools-xl from xen-tools"
SKIP_RECIPE[xvisor] ?= "fails to fetch: WARNING: Failed to fetch URL git://github.com/avpatel/xvisor-next.git;branch=master;protocol=https, attempting MIRRORS if available DEBUG: Fetcher failure: Unable to find revision 6b23764a1439f9d08b2ed2f363da522460d8a22b in branch master even from upstream http://gecko.lge.com:8000/Errors/Details/565714"
SKIP_RECIPE[uxen-guest-tools] ?= "fails to build for x86 qemux86 qemux86_64-webos-linux/uxen-guest-tools/4.1.8-r0/uxen-4.1.8-72a4af9/vm-support/linux/uxenplatform/platform.c:110:13: error: conflicting types for 'add_hwgenerator_randomness'; have 'void(const char *, size_t,  size_t)' {aka 'void(const char *, long unsigned int,  long unsigned int)'} http://gecko.lge.com:8000/Errors/Details/576618"

# meta-webos
SKIP_RECIPE[gator-mod] ?= "Needs to be updated for newer kernels now used on rpi tracked in [WRP-577], gator-mod/6.7+gitAUTOINC+3ff46fedd4-r5.0/git/driver/gator_events_mmapped.c:77:21: error: storage size of ts isnt known http://gecko.lge.com:8000/Errors/Details/565745"
SKIP_RECIPE[sqlcipher] ?= "fails to build with: sqlcipher/3.4.2-r0/recipe-sysroot/usr/bin/crossscripts/aarch64-webos-linux-libtool: Command not found, unused since 2019 when prs was removed with 3c91edef9b74e3f42c2d77b95771ec52f11d3c88, http://gecko.lge.com:8000/Errors/Details/565725"
