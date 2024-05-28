# Backport 4.1 version from oe-core master as-is in:
# https://git.openembedded.org/openembedded-core/commit/?id=1c5c8ff97ba0a7f9adc592d702b865b3d166a24b
# and 4.2 version in:
# https://git.openembedded.org/openembedded-core/commit/?id=c6654fab00a1b4e4bb05eec8b77c8c60e1f8a709
# and 4.3 version in:
# https://git.openembedded.org/openembedded-core/commit/?id=39f987fcb20ad7c0e45425b9f508d463c50ce0c1
# and 4.4 version in:
# https://git.openembedded.org/openembedded-core/commit/?id=56fdd8b79e2f7ec30d2cdcfa0c399a6553efac1e
#
# This include points at uninative tarballs generated and maintained
# by the Yocto Project. These file can be included by distros if they
# want to use/maintain a unified sstate feed for native/cross artefacts
# rather than the feeds having native/cross artefacts which are specific
# to the distro running on the build machine.
#

UNINATIVE_MAXGLIBCVERSION = "2.39"
UNINATIVE_VERSION = "4.4"

UNINATIVE_URL ?= "http://downloads.yoctoproject.org/releases/uninative/${UNINATIVE_VERSION}/"
UNINATIVE_CHECKSUM[aarch64] ?= "b61876130f494f75092f21086b4a64ea5fb064045769bf1d32e9cb6af17ea8ec"
UNINATIVE_CHECKSUM[i686] ?= "9f28627828f0082cc0344eede4d9a861a9a064bfa8f36e072e46212f0fe45fcc"
UNINATIVE_CHECKSUM[x86_64] ?= "d81c54284be2bb886931fc87281d58177a2cd381cf99d1981f8923039a72a302"
