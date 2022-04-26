# Copyright (c) 2014-2022 LG Electronics, Inc.

require node-gyp-packages-native.inc

SUMMARY = "Dependent packages and registry for node-gyp"
SECTION = "nodejs/module"

PR = "${INC_PR}.0"

inherit native

SRC_URI += "file://registry-${PV}.tar.gz"

do_install () {
    for p in $(find ${WORKDIR}/${PN} -mindepth 1 -maxdepth 1 -type d); do
        package_name=$(basename $p | sed -rn "s|^(.*)_[0-9]+\.[0-9]+.[0-9]+|\1|p")
        ver=$(basename $p | sed -rn "s|^.*_([0-9]+\.[0-9]+.[0-9]+)|\1|p")

        mkdir -p ${D}${datadir}/npmcache/node-gyp-native/${package_name}/${ver}
        cp -R $p/package  ${D}${datadir}/npmcache/node-gyp-native/${package_name}/${ver}

        tar cfz ${D}${datadir}/npmcache/node-gyp-native/${package_name}/${ver}/package.tgz -C ${D}${datadir}/npmcache/node-gyp-native/${package_name}/${ver} package
    done

    for r in $(find ${WORKDIR}/registry-${PV}  -name *.registry); do
        filename="$(basename $r)"
        package_name="${filename%.*}"
        install -m 644 -D $r ${D}${datadir}/npmcache/node-gyp-native/$package_name/.cache.json
    done
}
