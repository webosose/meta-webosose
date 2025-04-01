# https://git.openembedded.org/meta-openembedded/commit/?h=scarthgap&id=15c8096177edbf2e05915d6b8df2139434eef83d
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://0001-build-properly-spell-provide-in-.wrap-files.patch"
