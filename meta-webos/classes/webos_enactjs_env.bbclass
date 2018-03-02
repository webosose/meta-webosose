# Copyright (c) 2017-2018 LG Electronics, Inc.
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

do_compile_prepend() {
    # changing the home directory to the working directory, the .npmrc will be created in this directory
    export HOME=${WORKDIR}

    # configure cache to be in working directory
    ${ENACT_NPM} set cache ${WORKDIR}/npm_cache

    # Fix to prevent NPM from not honoring shrinkwrap; see https://github.com/npm/npm/issues/17960
    ${ENACT_NPM} config set package-lock true

    # Tell NPM to run packages using the Node binary that started it
    ${ENACT_NPM} config set scripts-prepend-node-path true

    # explicity set NPM registry URI
    ${ENACT_NPM} set registry https://registry.npmjs.org/

    # clean any cache prior to each compile
    ${ENACT_NPM} cache clean --force
}
