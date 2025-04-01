# Copyright (c) 2013-2025 LG Electronics, Inc.

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
SKIP_RECIPE[qtapplicationmanager] ?= "fails to build: Qt6::QWaylandEglPlatformIntegrationPlugi references non-existent qtapplicationmanager/6.4.1-r0/recipe-sysroot/usr/lib/plugins/platforms/libqwayland-egl.so http://gecko.lge.com:8000/Errors/Details/565731"
SKIP_RECIPE[qtdeviceutilities] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565732"
SKIP_RECIPE[qtcharts] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565734"
SKIP_RECIPE[qtconnectivity] ?= "fails to build with dbus and widgets disabled in qtbase: Failed to find optional Qt component DBus which is needed for bluetooth The DBus target is mentioned as a dependency for Bluetooth, but not declared.. http://gecko.lge.com:8000/Errors/Details/565738"
SKIP_RECIPE[qtdatavis3d] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/565733"
SKIP_RECIPE[qtgraphs] ?= "fails to build with ninja: error: unknown target 'install' http://gecko.lge.com:8000/Errors/Details/728937"
SKIP_RECIPE[qtmultimedia] ?= "fails to build: qtmultimedia/6.0.0-r1/git/src/qtmultimediaquicktools/qsgvideonode_texture.cpp:56:30: error: 'class QSGRhiSupport' has no member named 'isRhiEnabled'. http://gecko.lge.com:8000/Errors/Details/565741"
SKIP_RECIPE[qtpdf] ?= "fails to build with widgets disabled in qtbase: Failed to find required Qt component Widgets. http://gecko.lge.com:8000/Errors/Details/565740"
SKIP_RECIPE[python3-pyqt6] ?= "fails to build with widgets disabled in qtbase: Project ERROR: Unknown module(s) in QT: widgets http://gecko.lge.com:8000/Errors/Details/800723"
SKIP_RECIPE[python3-shiboken6] ?= "fails to build: python3-shiboken6-native/6.6.3/git/sources/shiboken6/ApiExtractor/include.h:58:16: error: strong_ordering in namespace Qt does not name a type http://gecko.lge.com:8000/Errors/Details/800722"
SKIP_RECIPE[qtwebengine] ?= "fails to build with printing-and-pdf PACKAGECONFIG enabled (default): ERROR: Feature 'webengine_printing_and_pdf': Forcing to 'ON' breaks its condition: TARGET Qt::PrintSupport AND QT_FEATURE_printer AND ( CUPS_FOUND OR WIN32 ) Condition values dump: TARGET Qt::PrintSupport not found QT_FEATURE_printer not evaluated CUPS_FOUND = 'TRUE' WIN32 = '' http://gecko.lge.com:8000/Errors/Details/565744"
SKIP_RECIPE[qtwebview] ?= "depends on blacklisted qtwebengine"
SKIP_RECIPE[qtinterfaceframework] ?= "depends on blacklisted qtmultimedia"
SKIP_RECIPE[qtspeech] ?= "depends on blacklisted qtmultimedia"
SKIP_RECIPE[python3-pyside6] ?= "depends on blacklisted qtmultimedia, qtcharts, qtdatavis3d, qtconnectivity, qtspeech, qtgraphs"
SKIP_RECIPE[qttranslations] ?= "depends on linguist being enabled in target qttools, which got disabled in http://gpro.lge.com/c/webos-pro/meta-lg-webos/+/406780/14/meta-webos/dynamic-layers/qt6-layer/recipes-qt/qt6/qttools_git.bbappend"
SKIP_RECIPE[packagegroup-qt6-essentials] ?= "rdepends on blacklisted qttranslations to rprovide qttranslations-qtbase, qttranslations-qtdeclarative"
SKIP_RECIPE[qtdoc] ?= "depends on blacklisted qtpdf, qtmultimedia, qtgraphs, qtcharts"

# meta-oe
SKIP_RECIPE[exprtk] ?= "fails to fetch: WARNING: Failed to fetch URL git://github.com/ArashPartow/exprtk.git;branch=master;protocol=https, attempting MIRRORS if available DEBUG: Fetcher failure: Unable to find revision 281c2ccc65b8f91c012ea3725ebcef406378a225 in branch master even from upstream: http://gecko.lge.com:8000/Errors/Details/565715"
SKIP_RECIPE[fwts] ?= "fails to build with: fwts/22.01.00-r0/build/src/lib/src/.libs/libfwts.so: error: undefined reference to 'gzgets' http://gecko.lge.com:8000/Errors/Details/565736"
SKIP_RECIPE[graphene] ?= "fails to build with: Could not find GIR file 'GObject-2.0.gir'; check XDG_DATA_DIRS or use --includedir, later moved from meta-oe to oe-core and possibly fixed in mickledore, http://gecko.lge.com:8000/Errors/Details/565726"
SKIP_RECIPE[lv-drivers] ?= "not compatible with lvgl-9 from meta-webos-backports-5.0/recipes-graphics/lvgl, the recipe was removed from meta-oe in scarthgap http://gecko.lge.com:8000/Errors/Details/867539"
SKIP_RECIPE[lv-lib-png] ?= "not compatible with lvgl-9 from meta-webos-backports-5.0/recipes-graphics/lvgl, the recipe was removed from meta-oe in scarthgap http://gecko.lge.com:8000/Errors/Details/867540"
SKIP_RECIPE[dialog-lvgl] ?= "not compatible with lvgl-9 from meta-webos-backports-5.0/recipes-graphics/lvgl, the recipe was added in langdale with https://git.openembedded.org/meta-openembedded/commit/?id=8d75eff6af0fd6ccf8a45b5c98c4a0194e292a2f and removed from meta-oe in scarthgap https://git.openembedded.org/meta-openembedded/commit/?id=e4865f3ee0df8ad9f789a9612f8cb7147992222b"
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

# Not really broken, just sstate is big (~800MB) and we don't need to test it with world builds
EXCLUDE_FROM_WORLD:pn-webkitgtk = "1"
EXCLUDE_FROM_WORLD:pn-opengl-es-cts = "1"
EXCLUDE_FROM_WORLD:pn-tearsofsteel-1080p = "1"
EXCLUDE_FROM_WORLD:pn-bigbuckbunny-1080p = "1"
EXCLUDE_FROM_WORLD:pn-bigbuckbunny-720p = "1"
EXCLUDE_FROM_WORLD:pn-bigbuckbunny-480p = "1"

# based on looking at 10000 biggest sstate archives from just one DISTRO with 3 MACHINEs (and multilib enabled)
# all accessed in last 10 days, the other huge archives are:
# webkitgtk (1.5GB) - epiphany would normally pull it into world, but we don't have x11 in DISTRO_FEATURES which is required by epiphany
# opengl-es-cts (546MB)
# tearsofsteel-1080p (707MB)
# bigbuckbunny-1080p (882MB)
# bigbuckbunny-720p (317MB)
# bigbuckbunny-480p (211MB)
# nodejs-16 (735MB 119 times), nodejs-18 (829MB 111 times)
# tesseract-lang (454MB 223 times)
# rust-llvm-native (420MB but only 23 times)
# openssl (394MB package.tar.zst 102 times, 254MB package_write_ipk.tar.zst 112 times)
# linux-firmware (380MB 62 times)
# cmake (306MB 72 times)
# qtbase (294MB 475 times)
# mimic (293MB 91 times)
# mozjs-102 (257MB 56 times)
# opencv (256MB 358 times)
# clang-native (255MB 10 times)
# rocksdb (255MB 103 times)
# webruntime (207MB 730 times)
# minifi-cpp (240MB 70 times)
# clang (207MB 99 times)
# qtdeclarative (183MB 259 times)
# ltp (195MB 175 times)
# com.demo.simplevideoplayer (181MB 10 times)
# etcd (172MB 36 times)
# ceres-solver (165MB 106 times)
# qt3d (164MB 60 times)
# edgeai-vision (162MB 368 times)
# than round 150MB-100MB virtual/kernel providers, vsomeip, hdf5, rust, spirv-tools, boost, poco, grpc, go-cross-*,
# armnn, luna-service2, localization-data, libyang, influxdb, ...
