# Copyright (c) 2012-2014 LG Electronics, Inc.
#
# webos_arch_indep
#
# This class is to be inherited by the recipe for every component that is CPU
# architecture independent.
#

inherit allarch

python () {
    if bb.data.inherits_class('webos_machine_dep', d) or bb.data.inherits_class('webos_machine_impl_dep', d):
        pa = d.getVar('PACKAGE_ARCH', True)
        if pa == "all":
            pn = d.getVar('PN', True)
            bb.error("%s: You should inherit webos_machine_dep or webos_machine_impl_dep _after_ webos_arch_indep to set PACKAGE_ARCH to MACHINE_ARCH" % pn)
}
