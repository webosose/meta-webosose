# Backported from oe-core:
# c99255d688 u-boot: install dumpimage and fit_check_sign in u-boot-tools

require u-boot-common.inc
require u-boot.inc

DEPENDS += "bc-native dtc-native"
