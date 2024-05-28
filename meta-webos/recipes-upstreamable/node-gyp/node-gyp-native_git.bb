# Copyright (c) 2013-2024 LG Electronics, Inc.

DESCRIPTION = "Node.js native addon build tool"
HOMEPAGE = "https://github.com/nodejs/node-gyp"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=694e396551033371686c80d3a1a69e88"
DEPENDS = "nodejs-native node-gyp-packages-native"

PV = "10.0.1+git"
PR = "r0"
SRCREV = "da19158e7a02c574d4f6d3d367ee264cb08d47ec"
SRC_URI = "git://github.com/nodejs/node-gyp.git;branch=main;protocol=https"

S = "${WORKDIR}/git"

inherit native

# nmp's cache has following structure
# package_name (folder)
#  |___package_version (1.2.3 for example)
#  |   |___package (folder named 'package') folder with unpacked package
#  |   |___package.tgz (tarball named package.tgz)
#  |___another_package_version
#  |
#  ...
#  |
#  .cache.json (data from online npm registry)

# On upgrading node-gyp in the situation when some dependencies are not met
# npm will print something like "cannot fetch some-tarball-x.y.z".
# This means that dependecy on newer version (x.y.z) was brought.
# To fix - just go to node-gyp-packages-native.inc and point SRC_URI of mentioned
# tarball to the needed version x.y.z. Than build again. Fix checksums. Fix licenes.
#
# There is no reason to upgrade registry every time when upgrading
# node-gyp. But on some day after upgrading some node-gyp dependent package
# it can bring dependency which is not present in the registry.
# For example node-gyp contains dependency on 'abbrev'. Dependency rule looks like
# '=1.0.5' which means than node-gyp 'wants' abbrev v1.0.5. On some day we decided
# to upgrade node-gyp. And upgraded node-gyp-native contains rule '=2.0.0'. There is
# no any information about abbrev 2.0.0 in our local registry and we will get a
# strange error. And in that messages we need to search for something like:
#
#  No compatible version found: abbrev@'=2.0.0-0'
#  npm ERR! notarget Valid install targets:
#  npm ERR! notarget ["1.0.0","1.0.1","1.0.5"]
#
# This meens that registry doesn't contain needed version. So we need to upgrade it
# To upgrade registry items. Untar registry folder. Open registry of needed packages using url
# https://registry.npmjs.org/<needed-package>. Replace it with new one. Than tar it and put
# into the place.
#
# Good luck :)

do_install () {
    #force npm to install modules to correct place
    export npm_config_prefix=${D}${prefix}
    export TMPDIR=${T}
    #install from fetched files
    ${STAGING_LIBDIR_NATIVE}/node_modules/npm/bin/npm-cli.js install ${S} --no-registry --cache ${STAGING_DATADIR_NATIVE}/npmcache/node-gyp-native/
    rmdir ${D}/${sysconfdir} || true

    rm -rf ${D}/${libdir}/node_modules/node-gyp
    mkdir -p ${D}/${libdir}/node_modules/node-gyp
    cp -R --no-dereference --preserve=mode,links -v ${S}/addon.gypi ${S}/bin ${S}/gyp ${S}/lib ${S}/LICENSE ${S}/node_modules ${S}/package.json ${S}/README.md ${D}/${libdir}/node_modules/node-gyp

    # this symlink isn't created without -g
    install -d ${D}/${bindir}
    ln -snf ../lib/node_modules/node-gyp/bin/node-gyp.js ${D}/${bindir}/node-gyp
}

# Workaround for network access issue during do_install task
# http://gecko.lge.com/Errors/Details/433062
do_install[network] = "1"
