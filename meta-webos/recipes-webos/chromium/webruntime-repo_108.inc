# Copyright (c) 2023 LG Electronics, Inc.

WEBOS_REPO_NAME = "chromium108"

# The submission doesn't exist yet and the repo wasn't publish, but we need
# some WEBOS_VERSION to set SRCREV to allow parsing the recipes which include
# this .inc file.
WEBOS_VERSION = "108.0.5359.217-1_deadbeefdecafcafebaddeadbeefdecafcafebad"

# 10.8.168.25-chromium108.2
WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium108"
WEBOS_VERSION_V8 = "10.8.168.25-chromium108.3_df187f4df764f33a5674eae21c1cb42c1f48b8a6"
SRCREV_v8 = "fd143650b4f9a6c37695cafbd39baed42ac64038"

# http://gpro.lge.com/c/webosose/chromium94/+/363094 avcodec/x86/mathops: clip constants used with shift instructions within inline assembly
# http://gpro.lge.com/c/webosose/chromium108/+/363100 avcodec/x86/mathops: clip constants used with shift instructions within inline assembly
SRC_URI += "file://0001-avcodec-x86-mathops-clip-constants-used-with-shift-i.patch"
