# Copyright (c) 2021-2025 LG Electronics, Inc.
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
WEBOS_NPM_CACHE_DIR ?= "${WORKDIR}/npm-cache"
WEBOS_NPM_REGISTRY ?= "https://registry.npmjs.org/"
WEBOS_NPM_ARCH ?= "${@get_nodejs_arch(d)}"
WEBOS_NPM_INSTALL_FLAGS ?= "--arch=${WEBOS_NPM_ARCH} --target_arch=${WEBOS_NPM_ARCH} --production --without-ssl --insecure --no-optional"

WEBOS_NODE_BIN ??= "${STAGING_BINDIR_NATIVE}/node"

# for node-gyp
WEBOS_NODE_VERSION = "20.12.2"
WEBOS_NODE_SRC_URI = "https://nodejs.org/dist/v${WEBOS_NODE_VERSION}/node-v${WEBOS_NODE_VERSION}.tar.xz;name=node"
WEBOS_NODE_GYP = "node-gyp --arch '${TARGET_ARCH}' --nodedir '${UNPACKDIR}/node-v${WEBOS_NODE_VERSION}'"
SRC_URI[node.sha256sum] = "d7cbcc5fbfb31e9001f3f0150bbeda59abe5dd7137aaa6273958cd59ce35ced7"

do_npm_install:prepend() {
    # this is needed to use user's gitconfig (and other .gitconfig* gitconfig* files user might
    # have included from main .gitconfig file)  even after changing the HOME directory bellow

    # need to check ${HOME}/.gitconfig existence not only because it might be missing in real
    # HOME of given user, but also HOME might be already changed to WORKDIR or some other
    # directory somewhere else like in com.webos.app.enactbrowser which was using own
    # do_compile:prepend with another "export HOME=${WORKDIR}" executed before this prepend
    # which gets executed before this prepend and then the ~ is already ${WORKDIR} and ${WORKDIR}/.gitconfig
    # doesn't exist as shown here:
    # http://gecko.lge.com/Errors/Details/40702

    if [ "${HOME}" != "${WORKDIR}" -a -e ${HOME}/.gitconfig ] ; then
        bbnote "webos_npm_env: copy gitconfig files from user's HOME ${HOME} to WORKDIR ${WORKDIR}"
        cp -rav ${HOME}/.gitconfig* ${WORKDIR}/
        cp -rav ${HOME}/gitconfig ${WORKDIR}/ || true
    fi

    # changing the home directory to the working directory, the .npmrc will be created in this directory
    bbnote "webos_npm_env: set HOME to WORKDIR"
    export HOME=${WORKDIR}

    export NPM_ENV=production

    # configure cache to be in the WORKDIR directory
    bbnote "webos_npm_env: set npm cache"
    ${WEBOS_NPM_BIN} set cache ${WEBOS_NPM_CACHE_DIR}

    # clear local cache prior to each compile
    bbnote "webos_npm_env: clear npm cache and ${S}/node_modules"
    rm -rf ${S}/node_modules
    ${WEBOS_NPM_BIN} cache clear --force

    # Prefer using offline cached packages
    bbnote "webos_npm_env: config npm offline"
    ${WEBOS_NPM_BIN} config set prefer-offline true

    # Fix to prevent NPM from not honoring shrinkwrap; see https://github.com/npm/npm/issues/17960
    bbnote "webos_npm_env: config npm package-lock"
    ${WEBOS_NPM_BIN} config set package-lock true

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
