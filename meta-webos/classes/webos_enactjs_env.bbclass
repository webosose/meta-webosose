# Copyright (c) 2017-2021 LG Electronics, Inc.
#
# webos_enactjs_env
#
# Base-level bbclass to setup the development environment for Enact code.
# Provides access to enact-dev, Node, and NPM.
#

# Dependencies:
#   - enact-dev-native to get enact-dev and npm
DEPENDS_append = " enact-dev-native"

# Allow overriding the path to the enact-dev tools, in case an app needs
# to pull in a different version
WEBOS_ENACTJS_TOOL_PATH ??= "${STAGING_DIR_NATIVE}/opt/enact-dev"

ENACT_NODE ??= "${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/${BUILD_ARCH}/node"
ENACT_DEV ??= "${ENACT_NODE} ${WEBOS_ENACTJS_TOOL_PATH}/bin/enact.js"
ENACT_NPM ??= "${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/${BUILD_ARCH}/npm"

inherit webos_npm_env
WEBOS_NPM_BIN = "${ENACT_NPM}"
