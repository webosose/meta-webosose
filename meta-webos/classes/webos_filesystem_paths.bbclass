# Copyright (c) 2012-2018 LG Electronics, Inc.

#
# Variables invented by webOS for standard locations
#

webos_bootdir = "${base_prefix}/boot"
webos_browserpluginsdir = "${libdir}/BrowserPlugins"
webos_defaultconfdir = "${sysconfdir}/default"
webos_execstatedir = "${localstatedir}/lib"
webos_fontsdir = "${datadir}/fonts"
webos_homedir = "${base_prefix}/home"
webos_firmwaredir = "${base_libdir}/firmware"
webos_logdir = "${localstatedir}/log"
webos_mediadir = "${base_prefix}/media"
webos_mntdir = "${base_prefix}/mnt"
# Discourage use of ${libdir}/pkgconfig by not providing a variable for it.
webos_pkgconfigdir = "${datadir}/pkgconfig"
webos_preservedtmpdir = "${localstatedir}/tmp"
# Having a Qt plugins directory is standard, but the value used by webOS OSE isn't.
webos_qtpluginsdir = "${libdir}/qt5/plugins"
webos_runtimeinfodir = "${localstatedir}/run"
webos_srcdir = "${prefix}/src"
webos_udevscriptsdir = "${base_libdir}/udev"
webos_upstartconfdir = "${sysconfdir}/event.d"


#
# Variables for webOS additions to the filesystem hierarchy
#

# Increment this every time values for the variables below change. But
# don't increment if merely adding a new variable for an existing location.
WEBOS_FILESYSTEM_LAYOUT_VERSION = "1"

webos_prefix = "${prefix}/palm"
webos_localstatedir = "${localstatedir}/palm"
webos_sysconfdir = "${sysconfdir}/palm"

# The /palm/ subdirectories have been deliberately left as literals.
webos_accttemplatesdir = "${prefix}/palm/public/accounts"
# This is the location of webOS applications, both JS and native. There is a
# subdirectory tree for each application that is named using its complete name.
webos_applicationsdir = "${prefix}/palm/applications"
webos_frameworksdir = "${prefix}/palm/frameworks"
webos_keysdir = "${prefix}/palm/data"
# This is the location of the pre-installed catalog apps IPKs
webos_picapkgdir = "${webos_mntdir}/pica"
# This is the location of webOS application plugins. There is a subdirectory for
# each application that is named using the final field of its complete name.
webos_pluginsdir = "${prefix}/palm/plugins"
# This is the location of the trees for JS services; the files for native (dynamic)
# services are located under sbindir, libdir, etc. as if they were Linux daemons.
webos_servicesdir = "${prefix}/palm/services"
webos_smartkeydatadir = "${prefix}/palm/smartkey"
webos_soundsdir = "${prefix}/palm/sounds"
webos_sysmgrdir = "${prefix}/palm/sysmgr"

# Note that everything under localstatedir is erased by a NYX_SYSTEM_ERASE_VAR
# erasure.
webos_db8datadir = "${localstatedir}/db"
webos_filecachedir = "${localstatedir}/file-cache"
webos_preferencesdir = "${localstatedir}/preferences"

webos_sysbus_prefix = "${datadir}"
webos_sysbus_datadir = "${webos_sysbus_prefix}/luna-service2"
webos_sysbus_dyndatadir = "${localstatedir}/luna-service2"
webos_sysbus_devdatadir = "${localstatedir}/luna-service2-dev"
webos_sysbus_apipermissionsdir = "${webos_sysbus_datadir}/api-permissions.d"
webos_sysbus_dynapipermissionsdir = "${webos_sysbus_dyndatadir}/api-permissions.d"
webos_sysbus_devapipermissionsdir = "${webos_sysbus_devdatadir}/api-permissions.d"
webos_sysbus_containersdir = "${webos_sysbus_datadir}/containers.d"
webos_sysbus_permissionsdir = "${webos_sysbus_datadir}/client-permissions.d"
webos_sysbus_dynpermissionsdir = "${webos_sysbus_dyndatadir}/client-permissions.d"
webos_sysbus_devpermissionsdir = "${webos_sysbus_devdatadir}/client-permissions.d"
webos_sysbus_rolesdir = "${webos_sysbus_datadir}/roles.d"
webos_sysbus_dynrolesdir = "${webos_sysbus_dyndatadir}/roles.d"
webos_sysbus_devrolesdir = "${webos_sysbus_devdatadir}/roles.d"
webos_sysbus_servicedir = "${webos_sysbus_datadir}/services.d"
webos_sysbus_dynservicedir = "${webos_sysbus_dyndatadir}/services.d"
webos_sysbus_devservicesdir = "${webos_sysbus_devdatadir}/services.d"
webos_sysbus_manifestsdir = "${webos_sysbus_datadir}/manifests.d"
webos_sysbus_dynmanifestsdir = "${webos_sysbus_dyndatadir}/manifests.d"
webos_sysbus_devmanifestsdir = "${webos_sysbus_devdatadir}/manifests.d"
webos_sysbus_groupsdir = "${webos_sysbus_datadir}/groups.d"

# Legacy sysbus locations
webos_sysbus_pubservicesdir = "${webos_sysbus_prefix}/dbus-1/services"
webos_sysbus_prvservicesdir = "${webos_sysbus_prefix}/dbus-1/system-services"
webos_sysbus_pubrolesdir = "${webos_sysbus_prefix}/ls2/roles/pub"
webos_sysbus_prvrolesdir = "${webos_sysbus_prefix}/ls2/roles/prv"
webos_sysbus_dynpubservicesdir = "${localstatedir}/palm/ls2/services/pub"
webos_sysbus_dynprvservicesdir = "${localstatedir}/palm/ls2/services/prv"
webos_sysbus_dynpubrolesdir = "${localstatedir}/palm/ls2/roles/pub"
webos_sysbus_dynprvrolesdir = "${localstatedir}/palm/ls2/roles/prv"
webos_sysbus_devpubservicesdir = "${localstatedir}/palm/ls2-dev/services/pub"
webos_sysbus_devprvservicesdir = "${localstatedir}/palm/ls2-dev/services/prv"
webos_sysbus_devpubrolesdir = "${localstatedir}/palm/ls2-dev/roles/pub"
webos_sysbus_devprvrolesdir = "${localstatedir}/palm/ls2-dev/roles/prv"

webos_sysmgr_datadir = "${libdir}/luna"
webos_sysmgr_localstatedir = "${localstatedir}/luna"

webos_cryptofsdir = "${webos_mediadir}/cryptofs"

# Everything under this tree is erased by a NYX_SYSTEM_ERASE_VAR erasure.
webos_browserstoragedir = "${webos_cryptofsdir}/.browser"

# This is the tree for components downloaded from the app catalog; everything
# under this tree is erased by a NYX_SYSTEM_ERASE_VAR erasure.
webos_downloadeddir = "${webos_cryptofsdir}/apps"
# The old name for webos_downloadeddir
webos_appstoragedir = "${webos_downloadeddir}"
webos_downloaded_applicationsdir = "${webos_downloadeddir}/usr/palm/applications"
# The old name for webos_downloaded_applicationsdir
webos_installedappsdir = "${webos_downloaded_applicationsdir}"
webos_downloaded_frameworksdir = "${webos_downloadeddir}/usr/palm/frameworks"
webos_downloaded_pluginsdir = "${webos_downloadeddir}/usr/palm/plugins"
webos_downloaded_servicesdir = "${webos_downloadeddir}/usr/palm/services"

# The specs for webos_execstatedir apply to this location with the additional
# constraint that everything under this tree persists a NYX_SYSTEM_ERASE_VAR
# erasure. It is only deleted by a NYX_SYSTEM_ERASE_ALL erasure.
webos_persistentstoragedir = "${webos_cryptofsdir}/data"

# db8 database use this directory to store mediadb database.
# mediadb database store results of fileindexer and contain information
# about media files. Potentially this db can be big
webos_db8mediadir = "${webos_persistentstoragedir}/db8/mediadb"

# On devices that support it, this tree is externally mountable as (USB) mass
# storage. Applications that want their data to be visible in this manner should
# store them here instead of under webos_persistentstoragedir. This tree is
# erased by NYX_SYSTEM_ERASE_MEDIA.
webos_mountablestoragedir = "${webos_mediadir}/internal"
# The old name for webos_mountablestoragedir
webos_localstoragedir = "${webos_mountablestoragedir}"

# This is the root of the tree that is accessible to developers who log into a
# device with ssh when it is in developer mode. It is where their side-loaded
# (webOS and native) apps will be installed for debugging. Everything
# under this tree is erased by a NYX_SYSTEM_ERASE_DEVELOPER erasure.
webos_developerdir = "${webos_mediadir}/developer"

# Unit test executables and other test scripts or executables are installed,
# if at all, under ${webos_testsdir}/${PN}
webos_testsdir = "/opt/webos/tests"

# This tree contains subdirectories of various types of customization data
webos_customizationdir = "${prefix}/palm/customization"

# This directory is shared by the emulator for network mounting by its host OS
webos_emulatorshareddir = "${webos_mediadir}/shared"

# The presence of this file indicates that First Use has been completed.
webos_firstusesentinelfile = "${webos_sysmgr_localstatedir}/preferences/ran-firstuse"

# Note that everything under localstatedir is erased by a NYX_SYSTEM_ERASE_VAR erasure.
webos_crashddir = "${webos_logdir}/crashd"

# Path to file which indicate failure to init settingsservice
webos_settingsservice_errorsentinelfile = "${webos_localstatedir}/settingsservice_critical_error"
