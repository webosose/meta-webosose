# Backport 4.1 version from oe-core master as-is in:
# https://git.openembedded.org/openembedded-core/commit/?id=1c5c8ff97ba0a7f9adc592d702b865b3d166a24b
# and 4.2 version in:
# https://git.openembedded.org/openembedded-core/commit/?id=c6654fab00a1b4e4bb05eec8b77c8c60e1f8a709
# and 4.3 version in:
# https://git.openembedded.org/openembedded-core/commit/?id=39f987fcb20ad7c0e45425b9f508d463c50ce0c1
#
# This include points at uninative tarballs generated and maintained
# by the Yocto Project. These file can be included by distros if they
# want to use/maintain a unified sstate feed for native/cross artefacts
# rather than the feeds having native/cross artefacts which are specific
# to the distro running on the build machine.
#

UNINATIVE_MAXGLIBCVERSION = "2.38"
UNINATIVE_VERSION = "4.3"

UNINATIVE_URL ?= "http://downloads.yoctoproject.org/releases/uninative/${UNINATIVE_VERSION}/"
UNINATIVE_CHECKSUM[aarch64] ?= "8df05f4a41455018b4303b2e0ea4eac5c960b5a13713f6dbb33dfdb3e32753ec"
UNINATIVE_CHECKSUM[i686] ?= "bea76b4a97c9ba0077c0dd1295f519cd599dbf71f0ca1c964471c4cdb043addd"
UNINATIVE_CHECKSUM[x86_64] ?= "1c35f09a75c4096749bbe1e009df4e3968cde151424062cf4aa3ed89db22b030"
