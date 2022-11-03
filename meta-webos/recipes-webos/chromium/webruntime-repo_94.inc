# Copyright (c) 2022 LG Electronics, Inc.

WEBOS_VERSION = "94.0.4606.128-12_6d96a9ecffb069fd881bcc6db6602a4366a6dff1"

# 9.4.146.25-chromium94.3
SRCREV_v8 = "20f6074f6735d664fc398c1ca74aceb5d291d646"

SRC_URI += "file://0001-grit-Remove-usage-of-U-mode-bit-for-opening-files-in.patch"
SRC_URI += "file://0001-Fix-compilation-with-lttng-ust-2.13.patch"
