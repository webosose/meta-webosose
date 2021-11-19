# note that we allow for us to use data later than our code version
#
SUMMARY = "tzcode, timezone zoneinfo utils -- zic, zdump, tzselect"
LICENSE = "PD & BSD & BSD-3-Clause"

LIC_FILES_CHKSUM = "file://LICENSE;md5=c679c9d6b02bc2757b3eaf8f53c43fba"

SRC_URI =" http://www.iana.org/time-zones/repository/releases/tzcode${PV}.tar.gz;name=tzcode \
           http://www.iana.org/time-zones/repository/releases/tzdata${PV}.tar.gz;name=tzdata \
           "
UPSTREAM_CHECK_URI = "http://www.iana.org/time-zones"

SRC_URI[tzcode.md5sum] = "7320f6fb86f7d579feddfe52edc4984c"
SRC_URI[tzcode.sha256sum] = "3e10308976b09305d15cb4a32ff75483421f2063bfa24a9be366a027e7cd2902"
SRC_URI[tzdata.md5sum] = "69931af503dd781745dcd7ef1e7814e5"
SRC_URI[tzdata.sha256sum] = "0be1ba329eae29ae1b54057c3547b3e672f73b3ae7643aa87dac85122bec037e"

S = "${WORKDIR}"

inherit native

EXTRA_OEMAKE += "cc='${CC}'"

do_install () {
        install -d ${D}${bindir}/
        install -m 755 zic ${D}${bindir}/
        install -m 755 zdump ${D}${bindir}/
        install -m 755 tzselect ${D}${bindir}/
}
