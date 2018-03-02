# Copyright (c) 2012-2018 LG Electronics, Inc.

# The default repo name is the "base" component name (no -native, etc.)
WEBOS_REPO_NAME ??= "${BPN}"

WEBOS_GIT_PARAM_BRANCH ?= "master"
WEBOS_GIT_BRANCH ?= ";branch=${WEBOS_GIT_PARAM_BRANCH}"
# Default is empty but webos_enhanced_submissions.bbclass will always set the value
WEBOS_GIT_PARAM_TAG ?= ""
WEBOS_GIT_TAG ?= ";tag=${WEBOS_GIT_PARAM_TAG}"

WEBOSOSE_GIT_REPO ?= "git://github.com/webosose"
WEBOSOSE_GIT_REPO_COMPLETE ?= "${WEBOSOSE_GIT_REPO}/${WEBOS_REPO_NAME}${WEBOS_GIT_TAG}${WEBOS_GIT_BRANCH}"

# Temporary replace it with gpro/webosose, because some components were updated in meta-webos
# but weren't updated on github.com, e.g. cmake-modules-webos-native submissions/33 on gpro, but only submissions/19 on github.com
WEBOSOSE_GIT_REPO = "git://gpro.lgsvl.com/webosose"
WEBOSOSE_GIT_REPO_COMPLETE_append = ";protocol=ssh"

ISIS_PROJECT_GIT_REPO ?= "git://github.com/isis-project"
# isis-project components don't have submissions => treat like any other upstream
# component whose source is stored in Git and use SRCREV to specify from where to fetch.
ISIS_PROJECT_GIT_REPO_COMPLETE ?= "${ISIS_PROJECT_GIT_REPO}/${WEBOS_REPO_NAME}"
ISIS_PROJECT_DOWNLOAD ?= "http://github.com/downloads/isis-project"
