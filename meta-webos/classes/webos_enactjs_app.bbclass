# Copyright (c) 2016-2019 LG Electronics, Inc.
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
#   - ilib-webapp so we can override @enact/i18n/ilib with device submission
#   - virtual/webruntime to use the mksnapshot binary to build v8 app snapshot blobs
#   - enact-framework to use a shared Enact framework libraries
WEBOS_ENACTJS_APP_DEPENDS = "ilib-webapp virtual/webruntime enact-framework"
DEPENDS_append = " ${WEBOS_ENACTJS_APP_DEPENDS}"

# chromium doesn't build for armv[45]*
COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE_aarch64 = "(.*)"
COMPATIBLE_MACHINE_armv6 = "(.*)"
COMPATIBLE_MACHINE_armv7a = "(.*)"
COMPATIBLE_MACHINE_armv7ve = "(.*)"
COMPATIBLE_MACHINE_x86 = "(.*)"
COMPATIBLE_MACHINE_x86-64 = "(.*)"

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

# Allows overriding the build flags used with enact-dev
WEBOS_ENACTJS_PACK_OPTS ??= "--production --isomorphic --snapshot --locales=tv"

# When true, will override the app's version of Enact, React, etc. with the build-target versions
# Defaults true. Any apps not compatible will system-wide Enact version standard will need to change this.
WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE ??= "true"

# Path to javascript override of a build submission ilib
WEBOS_ENACTJS_ILIB_OVERRIDE ??= "${STAGING_DATADIR}/javascript/ilib"

# On-device path to ilib json assets
WEBOS_ENACTJS_ILIB_ASSETS ??= "/usr/share/javascript/ilib"

# Whether to force transpiling to full ES5, rather than ES6 targetted to webOS Chrome version.
WEBOS_ENACTJS_FORCE_ES5 ??= "false"

# May be provided by machine target; ensure the variable exists for allarch filtering
LIB32_PREFIX ??= ""

# use allarch enact framework data, filtering out "lib32-" prefix
WEBOS_ENACTJS_FRAMEWORK ??= "${@ '${STAGING_DATADIR}/javascript/enact'.replace('${LIB32_PREFIX}', '')}"

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

do_compile() {
    working=$(pwd)

    bbnote "Using Enact project at ${WEBOS_ENACTJS_PROJECT_PATH}"
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    # clear local cache prior to each compile
    bbnote "Clearing any existing node_modules"
    rm -fr node_modules

    # ensure an NPM shrinkwrap file exists so app has its dependencies locked in
    if [ ! -f npm-shrinkwrap.json ] ; then
        bberror "NPM shrinkwrap file not found. Ensure a shrinkwrap is included with the app source to lock in dependencies."
        exit 1
    fi

    # apply shrinkwrap override, rerouting to shared enact framework tarballs as needed
    if [ "${WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE}" = "true" ] ; then
        if [ -d ${WEBOS_ENACTJS_FRAMEWORK} ] ; then
            bbnote "Using system submission Enact framework from ${WEBOS_ENACTJS_FRAMEWORK}"
            ${ENACT_NODE} "${WEBOS_ENACTJS_TOOL_PATH}/node_binaries/enact-override.js" "${WEBOS_ENACTJS_FRAMEWORK}"
        fi
    fi

    NPM_OPTS="--arch=${TARGET_ARCH} --loglevel=verbose install"
    if [ -z "${WEBOS_ENACTJS_PACK_OVERRIDE}" ] ; then
        # When a standard Enact app, we can safely skip installing any devDependencies
        NPM_OPTS="${NPM_OPTS} --only=production"
    fi

    # compile and install node modules in source directory
    bbnote "Begin NPM install process"
    ATTEMPTS=0
    STATUS=-1
    while [ ${STATUS} -ne 0 ] ; do
        ATTEMPTS=$(expr ${ATTEMPTS} + 1)
        if [ ${ATTEMPTS} -gt 5 ] ; then
            bberror "All attempts to NPM install have failed; exiting!"
            exit ${STATUS}
        fi

        # Remove any errant package locks since we are solely handling shrinkwrap
        rm -f package-lock.json

        # backup the shrinkwrap in the event "npm install" alters it
        if [ -f npm-shrinkwrap.json ] ; then
            cp -f npm-shrinkwrap.json npm-shrinkwrap.json.orig
        fi

        bbnote "NPM install attempt #${ATTEMPTS} (of 5)..." && echo
        STATUS=0
        ${ENACT_NPM} ${NPM_OPTS} || eval "STATUS=\$?"
        if [ ${STATUS} -ne 0 ] ; then
            bbwarn "...NPM process failed with status ${STATUS}"
        else
            bbnote "...NPM process succeeded" && echo
        fi
        # restore backup shrinkwrap file
        if [ -f npm-shrinkwrap.json.orig ] ; then
            mv -f npm-shrinkwrap.json.orig npm-shrinkwrap.json
        fi
    done

    if [ ! -z "${WEBOS_ENACTJS_ILIB_OVERRIDE}" ] ; then
        ## only override ilib if using Enact submission via shrinkwrap override
        if [ "${WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE}" = "true" ] ; then
            if [ -d node_modules/@enact/i18n ] ; then
                # use ilib submission component rather than one bundled within @enact/i18n
                if [ -d ${WEBOS_ENACTJS_ILIB_OVERRIDE}/lib ] ; then
                    # override local lib with system-based submission
                    cp -fr ${WEBOS_ENACTJS_ILIB_OVERRIDE}/lib node_modules/@enact/i18n/ilib

                    if [ -f ${WEBOS_ENACTJS_ILIB_OVERRIDE}/package.json ] ; then
                        cp -f ${WEBOS_ENACTJS_ILIB_OVERRIDE}/package.json node_modules/@enact/i18n/ilib
                    fi

                    # removed unneeded files
                    rm -f node_modules/@enact/i18n/ilib/lib/ilib-*.js
                    rm -f node_modules/@enact/i18n/ilib/lib/RhinoLoader.js
                    rm -f node_modules/@enact/i18n/ilib/lib/ZoneInfo.js
                fi
            fi
        fi
    fi

    cd ${working}
}

do_install() {
    working=$(pwd)
    cd ${WEBOS_ENACTJS_PROJECT_PATH}

    # Support optional transpiling to full ES5 if needed
    export ES5="${WEBOS_ENACTJS_FORCE_ES5}"

    # Target build polyfills, transpiling, and CSS autoprefixing to Chrome 68
    export BROWSERSLIST="Chrome 68"

    # use local on-device ilib locale assets
    if [ ! -z "${WEBOS_ENACTJS_ILIB_ASSETS}" ] ; then
        export ILIB_BASE_PATH="${WEBOS_ENACTJS_ILIB_ASSETS}"
    fi

    if [ -f ${STAGING_DIR_HOST}${bindir_cross}/${HOST_SYS}-mksnapshot.gz ]; then
        gzip -cd ${STAGING_DIR_HOST}${bindir_cross}/${HOST_SYS}-mksnapshot.gz > ${B}/${HOST_SYS}-mksnapshot
        chmod +x ${B}/${HOST_SYS}-mksnapshot
        export V8_MKSNAPSHOT="${B}/${HOST_SYS}-mksnapshot"
        # TODO Need to remove this line when PLAT-41192 is resolved
        export V8_SNAPSHOT_ARGS="--random-seed=314159265 --startup-blob=snapshot_blob.bin --abort_on_uncaught_exception"
    fi

    # Stage app
    appdir="${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}"
    mkdir -p "${appdir}"

    if [ ! -z "${WEBOS_ENACTJS_PACK_OVERRIDE}" ] ; then
        bbnote "Custom App Build for ${WEBOS_ENACTJS_APP_ID}"
        ${WEBOS_ENACTJS_PACK_OVERRIDE}
        mv -f -v ./dist/* "${appdir}"
    else
        # Normal App Build
        bbnote "Bundling Enact app to ${appdir}"
        ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS} -o "${appdir}" --verbose
    fi

    if [ -f ${appdir}/snapshot_blob.bin ] ; then
        chown root:root "${appdir}/snapshot_blob.bin"
    fi

    cd ${working}
}

FILES_${PN} += "${webos_applicationsdir}"
