# Backport changes from
# https://github.com/kraj/meta-clang/pull/851
# Merged in:
# https://github.com/kraj/meta-clang/commit/0128318a86833f9e30f3ca81886a7ca4bfb0baa6

# Not using ${BPN}, because that in this special case contains the version as well
FILESEXTRAPATHS:prepend := "${THISDIR}/llvm-project-source:"
SRC_URI += "file://0040-ToolChains-Gnu.cpp-ARMLibDirs-search-also-in-lib32.patch"
