# Copyright (c) 2016-2018 LG Electronics, Inc.
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
inherit webos_app
inherit webos_filesystem_paths
inherit webos_enactjs_env

# Dependencies:
#   - nodejs-native for node binary (to run enyo-pack)
#   - ilib-webapp so we can override @enact/i18n/ilib with device submission
#   - chromium53 to use the mksnapshot binary to build v8 app snapshot blobs
#   - enact-framework to use a shared Enact framework libraries
WEBOS_ENACTJS_APP_DEPENDS = "ilib-webapp chromium53 enact-framework"
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
WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE = "true"

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
    cd ${S}/${WEBOS_ENACTJS_PROJECT_PATH}

    # clear local cache prior to each compile
    rm -fr node_modules

    # ensure an NPM shrinkwrap file exists so app has its dependencies locked in
    if [ ! -f npm-shrinkwrap.json ] ; then
        bberror "NPM shrinkwrap file not found. Ensure a shrinkwrap is included with the app source to lock in dependencies."
        exit 1
    fi

    # backup the shrinkwrap in the event "NPM install" alters it
    cp -f npm-shrinkwrap.json npm-shrinkwrap.json.bak

    # compile and install node modules in source directory
    ${ENACT_NPM} --arch=${TARGET_ARCH} install

    # restore backup shrinkwrap file
    mv -f npm-shrinkwrap.json.bak npm-shrinkwrap.json

    if [ "${WEBOS_ENACTJS_SHRINKWRAP_OVERRIDE}" = "true" ] ; then
        if [ -d "${STAGING_DATADIR}/javascript/enact" ] ; then
            for LIB in $(find ${STAGING_DATADIR}/javascript/enact -maxdepth 1 -mindepth 1 -type d) ; do
                BASE=$(basename ${LIB})
                if [ "$(echo ${BASE} | cut -c1-1)" = "@" ] ; then
                    # scoped module, so copy internal packages
                    for SCOPED_LIB in $(find ${STAGING_DATADIR}/javascript/enact/${BASE} -maxdepth 1 -mindepth 1 -type d) ; do
                        SCOPED_BASE=$(basename ${SCOPED_LIB})
                        mkdir -p node_modules/${BASE}
                        rm -fr node_modules/${BASE}/${SCOPED_BASE}
                        cp -fr ${SCOPED_LIB} node_modules/${BASE}/${SCOPED_BASE}
                    done
                else
                    rm -fr node_modules/${BASE}
                    cp -fr ${LIB} node_modules/${BASE}
                fi
            done
        fi
    fi

    cd ${working}
}

do_install() {
    working=$(pwd)
    cd ${WEBOS_ENACTJS_PROJECT_PATH}

    # use local on-device ilib locale assets
    export ILIB_BASE_PATH="/usr/share/javascript/ilib"

    gzip -cd ${STAGING_DIR_HOST}${bindir_cross}/${HOST_SYS}-mksnapshot.gz > ${B}/${HOST_SYS}-mksnapshot
    chmod +x ${B}/${HOST_SYS}-mksnapshot
    export V8_MKSNAPSHOT="${B}/${HOST_SYS}-mksnapshot"

    # TODO Need to remove this line when PLAT-41192 is resolved
    export V8_SNAPSHOT_ARGS="--random-seed=314159265 --startup-blob=snapshot_blob.bin"

    if [ ! -z "${WEBOS_ENACTJS_PACK_OVERRIDE}" ] ; then
        echo "Custom App Build for ${WEBOS_ENACTJS_APP_ID}"
        ${WEBOS_ENACTJS_PACK_OVERRIDE}
    else
        # Normal App Build
        ${ENACT_DEV} pack ${WEBOS_ENACTJS_PACK_OPTS}

    fi

    # Stage app
    appdir="${D}${webos_applicationsdir}/${WEBOS_ENACTJS_APP_ID}"
    mkdir -p "${appdir}"
    cp -R --no-dereference --preserve=mode,links -v ./dist/* "${appdir}"

    cd ${working}
}

FILES_${PN} += "${webos_applicationsdir}"
