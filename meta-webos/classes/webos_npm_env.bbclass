# Copyright (c) 2021-2023 LG Electronics, Inc.
#
# webos_npm_env
#
# Base-level bbclass to setup the development environment for NPM.
#

def get_nodejs_arch(d):
    target_arch = d.getVar('TRANSLATED_TARGET_ARCH')

    if target_arch == "x86-64":
        target_arch = "x64"
    elif target_arch == "aarch64":
        target_arch = "arm64"
    elif target_arch == "powerpc":
        target_arch = "ppc"
    elif target_arch == "powerpc64":
        target_arch = "ppc64"
    elif (target_arch == "i486" or target_arch == "i586" or target_arch == "i686"):
        target_arch = "ia32"

    return target_arch

WEBOS_NPM_BIN ?= "${STAGING_BINDIR_NATIVE}/npm"
WEBOS_NPM_CACHE_DIR ?= "${WORKDIR}/npm_cache"
WEBOS_NPM_REGISTRY ?= "https://registry.npmjs.org/"
WEBOS_NPM_ARCH ?= "${@get_nodejs_arch(d)}"
WEBOS_NPM_INSTALL_FLAGS ?= "--arch=${WEBOS_NPM_ARCH} --target_arch=${WEBOS_NPM_ARCH} --production --without-ssl --insecure --no-optional --verbose"

WEBOS_NODE_BIN ??= "${STAGING_BINDIR_NATIVE}/node"

# for node-gyp
WEBOS_NODE_VERSION = "16.19.0"
WEBOS_NODE_SRC_URI = "https://nodejs.org/dist/v${WEBOS_NODE_VERSION}/node-v${WEBOS_NODE_VERSION}.tar.xz;name=node"
WEBOS_NODE_GYP = "node-gyp --arch '${TARGET_ARCH}' --nodedir '${WORKDIR}/node-v${WEBOS_NODE_VERSION}'"
SRC_URI[node.sha256sum] = "4f1fec1aea2392f6eb6d1d040b01e7ee3e51e762a9791dfea590920bc1156706"

do_compile:prepend() {
    # this is needed to use user's gitconfig even after changing the HOME directory bellow
    # need to check ${HOME}/.gitconfig existence not only because it might be missing in real HOME of given user
    # but also HOME might be already changed to WORKDIR or some other directory somewhere else
    [ "${HOME}" != "${WORKDIR}" -a -e ${HOME}/.gitconfig ] && cp ${HOME}/.gitconfig ${WORKDIR}

    # changing the home directory to the working directory, the .npmrc will be created in this directory
    bbnote "webos_npm_env: set HOME to WORKDIR"
    export HOME=${WORKDIR}

    # configure cache to be in the WORKDIR directory
    bbnote "webos_npm_env: set npm cache"
    ${WEBOS_NPM_BIN} set cache ${WEBOS_NPM_CACHE_DIR}

    # clear local cache prior to each compile
    bbnote "webos_npm_env: clear npm cache and ${S}/node_modules"
    ${WEBOS_NPM_BIN} cache clear --force
    rm -rf ${S}/node_modules

    # Prefer using offline cached packages
    bbnote "webos_npm_env: config npm offline"
    ${WEBOS_NPM_BIN} config set prefer-offline true

    # Fix to prevent NPM from not honoring shrinkwrap; see https://github.com/npm/npm/issues/17960
    bbnote "webos_npm_env: config npm package-lock"
    ${WEBOS_NPM_BIN} config set package-lock true

    # Tell NPM to run packages using the Node binary that started it
    bbnote "webos_npm_env: config npm node-path"
    ${WEBOS_NPM_BIN} config set scripts-prepend-node-path true

    # does not build dev packages
    bbnote "webos_npm_env: config npm dev false"
    ${WEBOS_NPM_BIN} config set dev false

    # access npm registry using http
    bbnote "webos_npm_env: config npm strict-ssl false"
    ${WEBOS_NPM_BIN} set strict-ssl false

    # configure http proxy if neccessary
    if [ -n "${http_proxy}" ]; then
        bbnote "webos_npm_env: config proxy ${http_proxy}"
        ${WEBOS_NPM_BIN} config set proxy ${http_proxy}
    fi
    if [ -n "${HTTP_PROXY}" ]; then
        bbnote "webos_npm_env: config proxy ${HTTP_PROXY}"
        ${WEBOS_NPM_BIN} config set proxy ${HTTP_PROXY}
    fi

    # explicity set NPM registry URI
    bbnote "webos_npm_env: set npm registry to ${WEBOS_NPM_REGISTRY}"
    ${WEBOS_NPM_BIN} set registry ${WEBOS_NPM_REGISTRY}
}
