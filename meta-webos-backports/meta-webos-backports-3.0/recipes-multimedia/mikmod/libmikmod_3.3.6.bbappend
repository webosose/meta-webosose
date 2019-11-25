# backport fix from meta-oe commit:
# 9cbc93d310 libmikmod: fix SRC_URI

SRC_URI = "${SOURCEFORGE_MIRROR}/project/mikmod/outdated_versions/${BPN}/${PV}/${BPN}-${PV}.tar.gz"
