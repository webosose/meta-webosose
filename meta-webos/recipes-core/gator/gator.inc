# Based on https://github.com/kratsg/meta-l1calo/blob/master/recipes-kernel/gator/gator_git.bb
# and modified heavily, including split into separate recipe for daemon
# and kernel module

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

INC_PR = "r5"

SRC_URI = "git://github.com/ARM-software/gator.git;protocol=https;branch=main" 
SRCREV = "3ff46fedd4d097813156069edab9704cc65e0a42"
PV = "6.7+git${SRCPV}"
