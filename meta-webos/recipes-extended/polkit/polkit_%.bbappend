# Copyright (c) 2024-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos3"

# Make sure to have 'duktape' in PACKAGECONFIG even for a newer recipe that introduces it in PACKAGECONFIG
PACKAGECONFIG:append = " ${@'duktape' if 'duktape' in d.getVarFlags('PACKAGECONFIG') else ''}"
PACKAGECONFIG:remove = "${@'mozjs' if 'duktape' in d.getVarFlags('PACKAGECONFIG') else ''}"

# Use refreshed
# 0004-Make-netgroup-support-optional.patch to avoid patch-fuzz
# it's fixed in kirkstone:
# https://git.openembedded.org/meta-openembedded/commit/?h=kirkstone&id=0b0086ca9acc1a5dc682c93217b9f8996214ae4c
# but not in langdale
FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
