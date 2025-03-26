# Copyright (c) 2017-2025 LG Electronics, Inc.
#
# webos_enactjs_env
#
# Base-level bbclass to setup the development environment for Enact code.
# Provides access to Cli, Node, and NPM.
#

# Dependencies:
#   - enact-dev-native to get cli & jsdoc-to-ts and nodejs-native to get node & npm
DEPENDS:append = " enact-dev-native jsdoc-to-ts-native nodejs-native"

# Allow overriding the path to the enact-dev tools, in case an app needs
# to pull in a different version
WEBOS_ENACTJS_TOOL_PATH ??= "${STAGING_BINDIR_NATIVE}"
WEBOS_ENACTJS_JSDOC_TO_TS_PATH ??= "${STAGING_BINDIR_NATIVE}"

ENACT_DEV ??= "${WEBOS_NODE_BIN} ${WEBOS_ENACTJS_TOOL_PATH}/enact"
ENACT_JSDOC_TO_TS ??= "${WEBOS_NODE_BIN} ${WEBOS_ENACTJS_JSDOC_TO_TS_PATH}/jsdoc-to-ts"
ENACT_BOOTSTRAP_OVERRIDE ??= "${ENACT_DEV} bootstrap --base=false --sampler=false --link=false --override"

inherit webos_npm_env
