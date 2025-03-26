# Copyright (c) 2016-2025 LG Electronics, Inc.
#
# webos_enactjs_app
#
# This class is to be inherited by every recipe whose component is an Enact
# application.
#
# This class uses enact-dev (provided by enyo-dev-native) to build the
# application from source and to stage it.
# Once staged, it relies on standard (inherited) packaging functionality.

# inherit from the generic app .bbclass

WEBOS_SYSTEM_BUS_MANIFEST_TYPE ?= "APPLICATION"

inherit webos_app
inherit webos_filesystem_paths
inherit webos_enactjs_env

# Dependencies:
#   - ilib-webapp so we can override NPM ilib dependency with device submission
#   - mksnapshot-cross to use the mksnapshot binary to build v8 app snapshot blobs
#   - enact-framework, enact-sandstone to use a shared Enact framework libraries
#   - coreutils-native to use timeout utility to prevent frozen NPM processes
WEBOS_ENACTJS_APP_DEPENDS = "ilib-webapp enact-framework enact-sandstone coreutils-native"
DEPENDS:append = " ${WEBOS_ENACTJS_APP_DEPENDS}"
DEPENDS:append = " ${@ 'mksnapshot-cross-${TARGET_ARCH}' if not d.getVar('WEBOS_ENACTJS_PACK_OVERRIDE').strip() and '--snapshot' in d.getVar('WEBOS_ENACTJS_PACK_OPTS') else ''}"

# chromium doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

# The appID of the app
WEBOS_ENACTJS_APP_ID ??= "${BPN}"

# This variable can be used to specify the path to the directory
# that contains the package.json file for the Enact project.
# This path is relative to the app project's root directory (which is the
# default location for the package.json file).
WEBOS_ENACTJS_PROJECT_PATH ??= "."

# Enact build tools will output the resources
WEBOS_LOCALIZATION_INSTALL_RESOURCES = "false"

# Allows a component to override the standard build command with something custom
WEBOS_ENACTJS_PACK_OVERRIDE ??= ""

# Allows overriding the build arguments for Enact CLI
WEBOS_ENACTJS_PACK_OPTS ??= "--production --isomorphic --snapshot --locales=webos"

# When true, will force building apps in full CSS modular mode. Otherwise will build CSS in a global
# context with *.module.css in the modular context.
WEBOS_ENACTJS_FORCE_CSS_MODULES ??= "true"

# When true, will override the app's version of Enact, React, etc. with the build-target versions
# Defaults true. Any apps not compatible will system-wide Enact version standard will need to change this.
WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE ??= "true"

# Path to javascript override of a build submission ilib
WEBOS_ENACTJS_ILIB_OVERRIDE ??= "${STAGING_DATADIR}/javascript/ilib"
WEBOS_ENACTJS_ILIB_OVERRIDE_ALLARCH ??= "${@ '${WEBOS_ENACTJS_ILIB_OVERRIDE}'.replace('${LIB32_PREFIX}', '')}"

# On-device path to ilib json assets
WEBOS_ENACTJS_ILIB_ASSETS ??= "${datadir}/javascript/ilib"

# Whether to force transpiling to full ES5, rather than ES6 targetted to webOS Chrome version.
WEBOS_ENACTJS_FORCE_ES5 ??= "false"

# May be provided by machine target; ensure the variable exists for allarch filtering
LIB32_PREFIX ??= ""

# support potential allarch enact framework data path, filtering out "lib32-" prefix
WEBOS_ENACTJS_FRAMEWORK ??= "${STAGING_DATADIR}/javascript/enact"
WEBOS_ENACTJS_FRAMEWORK_ALLARCH ??= "${@ '${WEBOS_ENACTJS_FRAMEWORK}'.replace('${LIB32_PREFIX}', '')}"

# Don't need to configure or compile anything for an enyojs app, but don't use
# do_<task>[noexec] = "1" so that recipes that inherit can still override

do_configure() {
    :
}

do_locate_enactjs_appinfo() {
    if [ -d ${S}/webos-meta ] ; then
        cp -fr ${S}/webos-meta/* ${S}
        rm -fr ${S}/webos-meta
    fi
}
addtask do_locate_enactjs_appinfo after do_configure before do_install

do_npm_shrink_enact_override() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    # ensure an NPM shrinkwrap file exists so app has its dependencies locked in
    if [ ! -f npm-shrinkwrap.json ] ; then
        bbwarn "NPM shrinkwrap file not found. Ensure a shrinkwrap is included with the app source to lock in dependencies."
    else
        cp -f npm-shrinkwrap.json npm-shrinkwrap.json.bak
    fi

    cp -f package.json package.json.bak

    if [ -f ${S}/${WEBOS_ENACTJS_PROJECT_PATH}/package.json ] ; then
        export ENACTJS_FRAMEWORK_VARIANT=$(${WEBOS_NODE_BIN} -e " \
                try { \
                    const deps = require(process.argv[1]).dependencies || []; \
                    if (Object.keys(deps).includes('@enact/sandstone')) { \
                        console.log('-sandstone'); \
                    }
                } catch(e) {} \
            " ${S}/${WEBOS_ENACTJS_PROJECT_PATH}/package.json)
    fi

    # apply shrinkwrap override, rerouting to shared enact framework tarballs as needed
    if [ "${WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE}" = "true" ] ; then
        bbnote "Attempting to use submission enact framework"
        FRAMEWORK_PATH="${WEBOS_ENACTJS_FRAMEWORK}${ENACTJS_FRAMEWORK_VARIANT}"
        if [ -d ${FRAMEWORK_PATH} ] ; then
            bbnote "Using system submission Enact framework from ${FRAMEWORK_PATH}"
            ${ENACT_BOOTSTRAP_OVERRIDE} "${FRAMEWORK_PATH}"
            if [ ! -d node_modules ] ; then
                mkdir node_modules
            fi
            cp -r ${FRAMEWORK_PATH}/node_modules_override/* node_modules
        else
            FRAMEWORK_ALLARCH_PATH="${WEBOS_ENACTJS_FRAMEWORK_ALLARCH}${ENACTJS_FRAMEWORK_VARIANT}"
            if [ -d ${FRAMEWORK_ALLARCH_PATH} ] ; then
                bbnote "Using system submission Enact framework from ${FRAMEWORK_ALLARCH_PATH}"
                ${ENACT_BOOTSTRAP_OVERRIDE} "${FRAMEWORK_ALLARCH_PATH}"
                if [ ! -d node_modules ] ; then
                    mkdir node_modules
                fi
                cp -r ${FRAMEWORK_ALLARCH_PATH}/node_modules_override/* node_modules
            else
                bbwarn "Enact framework submission could not be found"
            fi
        fi
    fi
}
addtask do_npm_shrink_enact_override after do_patch do_prepare_recipe_sysroot before do_configure

do_compile() {
    :
}

do_compile:append() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    # clear local cache prior to each compile
    bbnote "Clearing any existing node_modules"
    rm -fr node_modules
    cd ${working}
}

# FIXME: After fixing all of the enact app recipes, we have to remove this task
# to aollow fetching npm sub-modules only for do_fetch.
do_npm_install() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    NPM_OPTS="${WEBOS_NPM_INSTALL_FLAGS} install"
    if [ -z "${WEBOS_ENACTJS_PACK_OVERRIDE}" ] ; then
        # When a standard Enact app, we can safely skip installing any devDependencies
        NPM_OPTS="${WEBOS_NPM_INSTALL_FLAGS} install --only=production"
    fi

    # compile and install node modules in source directory
    bbnote "Begin NPM install process"
    # Remove any errant package locks since we are solely handling shrinkwrap
    rm -f package-lock.json

    ${WEBOS_NPM_BIN} ${NPM_OPTS}
    cd ${working}
}
addtask do_npm_install after do_compile before do_npm_install_postprocess

do_npm_install_postprocess() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    if [ ! -z "${WEBOS_ENACTJS_ILIB_OVERRIDE}" ] ; then
        ## only override ilib if using Enact submission via shrinkwrap override
        if [ "${WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE}" = "true" ] ; then
            SUB_ILIB=${WEBOS_ENACTJS_ILIB_OVERRIDE}
            if [ ! -d ${WEBOS_ENACTJS_ILIB_OVERRIDE} ] ; then
                SUB_ILIB=${WEBOS_ENACTJS_ILIB_OVERRIDE_ALLARCH}
            fi
            # use ilib submission component rather than one bundled within @enact/i18n
            if [ -d ${SUB_ILIB}/lib ] ; then
                # Support both old and current local ilib locations
                for LOC_ILIB in node_modules/ilib node_modules/@enact/i18n/ilib ; do
                    if [ -d ${LOC_ILIB} ] ; then
                        # override local lib with system-based submission
                        cp -fr ${SUB_ILIB}/lib ${LOC_ILIB}
                        cp -f ${SUB_ILIB}/package.json ${LOC_ILIB}
                        cp -f ${SUB_ILIB}/index.js ${LOC_ILIB}
                    fi
                done
            fi
        fi
    fi

    cp -f package.json.bak package.json
    if [ -f npm-shrinkwrap.json.bak ] ; then
        cp -f npm-shrinkwrap.json.bak npm-shrinkwrap.json
    fi
    cd ${working}
}
addtask do_npm_install_postprocess after do_npm_install before do_install

V8_SNAPSHOT_EXTRA_ARGS = " --turbo_instruction_scheduling"
do_install() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    # Support optional transpiling to full ES5 if needed
    export ES5="${WEBOS_ENACTJS_FORCE_ES5}"

    # Support forcing CSS modules for apps designed for Enact <3.0
    export ENACT_FORCECSSMODULES="${WEBOS_ENACTJS_FORCE_CSS_MODULES}"

    # Target build polyfills, transpiling, and CSS autoprefixing to Chrome 108
    export BROWSERSLIST="Chrome 108"

    # use local on-device ilib locale assets
    if [ ! -z "${WEBOS_ENACTJS_ILIB_ASSETS}" ] ; then
        export ILIB_BASE_PATH="${WEBOS_ENACTJS_ILIB_ASSETS}"
    fi

    export V8_MKSNAPSHOT="mksnapshot-cross-${TARGET_ARCH}"
    export V8_SNAPSHOT_ARGS="--random-seed=314159265 --startup-blob=snapshot_blob.bin --abort_on_uncaught_exception${V8_SNAPSHOT_EXTRA_ARGS}"

    # Stage app
    appdir="${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}"

    if [ ! -z "${WEBOS_ENACTJS_PACK_OVERRIDE}" ] ; then
        bbnote "Custom App Build for ${WEBOS_ENACTJS_APP_ID}"
        ${WEBOS_ENACTJS_PACK_OVERRIDE}
        if [ ! -d "$appdir" ] ; then
            install -d "$appdir"
            mv -f -v ./dist/* "$appdir"
        fi
    else
        # Normal App Build
        bbnote "Bundling Enact app to $appdir"
        ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS} -o "$appdir"
    fi

    if [ ! -f $appdir/index.html ] ; then
        bberror "No bundling results in $appdir; exiting!"
        exit 1
    fi

    if [ -f $appdir/snapshot_blob.bin ] ; then
        chown root:root "$appdir/snapshot_blob.bin"
    fi

    cd ${working}
}

FILES:${PN} += "${webos_applicationsdir}"
