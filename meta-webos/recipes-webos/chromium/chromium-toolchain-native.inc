# Copyright (c) 2023 LG Electronics, Inc.

require webruntime-common.inc

LICENSE = "Apache-2.0-with-LLVM-exception"

LIC_FILES_CHKSUM = "\
    file://src/LICENSE;md5=0fca02217a5d49a14dfe2d11837bb34d \
    file://oss-pkg-info.yaml;md5=dac1866e82f07906c382718584e415e5 \
"

# These are prebuilt binaries, doesn't make sense to package them as target package
inherit native

OFFSET = "src/third_party/llvm-build/Release+Asserts"

S = "${WORKDIR}/git"

do_configure() {
}

do_compile() {
}

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${S}/${OFFSET}/bin/* ${D}${bindir}

    install -d ${D}${libdir}/clang/${CLANG_VERSION}/
    cp -R --no-dereference --preserve=mode,links -v ${S}/${OFFSET}/lib/clang/${CLANG_VERSION}/include ${D}${libdir}/clang/${CLANG_VERSION}
}
