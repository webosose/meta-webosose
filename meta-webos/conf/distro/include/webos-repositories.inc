# Copyright (c) 2012-2019 LG Electronics, Inc.

# The default repo name is the "base" component name (no -native, etc.)
WEBOS_REPO_NAME ??= "${BPN}"

WEBOS_GIT_PARAM_BRANCH ?= "master"
WEBOS_GIT_BRANCH ?= ";branch=${WEBOS_GIT_PARAM_BRANCH}"
# Default is empty but webos_enhanced_submissions.bbclass will always set the value
WEBOS_GIT_PARAM_TAG ?= ""
WEBOS_GIT_TAG ?= ";tag=${WEBOS_GIT_PARAM_TAG}"

WEBOSOSE_GIT_REPO ?= "git://github.com/webosose"
WEBOSOSE_GIT_PARAM_PROTOCOL ?= "git"
WEBOSOSE_GIT_PROTOCOL = ";protocol=${WEBOSOSE_GIT_PARAM_PROTOCOL}"
WEBOSOSE_GIT_REPO_COMPLETE ?= "${WEBOSOSE_GIT_REPO}/${WEBOS_REPO_NAME}${WEBOS_GIT_TAG}${WEBOS_GIT_BRANCH}${WEBOSOSE_GIT_PROTOCOL}"
