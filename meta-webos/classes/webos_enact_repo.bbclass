# Copyright (c) 2018-2025 LG Electronics, Inc.

# The default repo name is the "base" component name (no -native, etc.)
WEBOS_REPO_NAME ??= "${BPN}"

WEBOS_GIT_PARAM_BRANCH ?= "master"
WEBOS_GIT_BRANCH ?= ";branch=${WEBOS_GIT_PARAM_BRANCH}"
# Default is empty but webos_enhanced_submissions.bbclass will always set the value
WEBOS_GIT_PARAM_TAG ?= ""
WEBOS_GIT_TAG ?= ";tag=${WEBOS_GIT_PARAM_TAG}"
WEBOS_GIT_PARAM_PROTOCOL ?= "https"
WEBOS_GIT_PROTOCOL = ";protocol=${WEBOS_GIT_PARAM_PROTOCOL}"

ENACTJS_GIT_REPO ?= "git://github.com/enactjs"
ENACTJS_GIT_REPO_COMPLETE ?= "${ENACTJS_GIT_REPO}/${WEBOS_REPO_NAME}${WEBOS_GIT_PROTOCOL}${WEBOS_GIT_TAG}${WEBOS_GIT_BRANCH}"
