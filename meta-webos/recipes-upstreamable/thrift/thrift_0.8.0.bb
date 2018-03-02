# Copyright (c) 2016-2018 LG Electronics, Inc.

SUMMARY = "The Apache Thrift software framework, for scalable cross-language services development"
SECTION = "libs"
HOMEPAGE = "http://thrift.apache.org/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=84a7db8aa9da4256d0430e3696f3ff20"

SRC_URI = "http://archive.apache.org/dist/thrift/${PV}/thrift-${PV}.tar.gz"
SRC_URI += "file://0001-Werror-removed.patch \
    file://gcc6.patch"

SRC_URI[md5sum] = "d29dfcd38d476cbc420b6f4d80ab966c"
SRC_URI[sha256sum] = "5e280097d88400f5e2db75595a04e1981538e48869cd6915bb9c4831605f0793"

inherit autotools pkgconfig autotools-brokensep

PR = "r3"

PACKAGECONFIG ??= "cpp c-glib"
PACKAGECONFIG[cpp] =  "--with-cpp --with-boost=${STAGING_DIR_HOST}${prefix},--without-cpp,boost openssl"
PACKAGECONFIG[zlib] =  "--with-zlib,--without-zlib,zlib"
PACKAGECONFIG[ruby] =  "--with-ruby,--without-ruby,ruby"
PACKAGECONFIG[c-glib] =  "--with-c_glib,--without-c_glib,glib-2.0"
PACKAGECONFIG[libevent] =  "--with-libevent,--without-libevent,libevent"

EXTRA_OECONF = " \
        --without-tests         \
        --disable-tutorial      \
        --without-csharp        \
        --without-d             \
        --without-erlang        \
        --without-go            \
        --without-haskell       \
        --without-java          \
        --without-lua           \
        --without-nodejs        \
        --without-perl          \
        --without-php           \
        --without-php_extension \
        --without-python        \
        --without-qt4           \
"

FILES_SOLIBSDEV = "${libdir}/lib${BPN}.so ${libdir}/lib${BPN}_c_glib.so"
FILES_${PN} += "${libdir}/lib${BPN}-${PV}.so"

do_configure() {
        export ac_cv_func_malloc_0_nonnull=yes
        export ac_cv_func_realloc_0_nonnull=yes
        oe_runconf
        sed -i 's|^hardcode_libdir_flag_spec=.*|hardcode_libdir_flag_spec=""|g' *libtool
        sed -i 's|^runpath_var=LD_RUN_PATH|runpath_var=_NO_RPATH_|g' *libtool
}
