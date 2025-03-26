# https://git.openembedded.org/openembedded-core/commit/?h=styhead&id=350ff7d53f7506de2bc01f0efc569b8294b9afea
# https://git.openembedded.org/openembedded-core/commit/?h=styhead&id=9221e4ab8cca4c06dc3d5c1de2fd4ce46477578a

remove_sysroot_paths_from_configargs () {
	replacement=${1}
	# Prevent sysroot path from being used in configargs.h header, as it will
	# be rewritten when used by other sysroots preventing support for gcc
	# plugins. Additionally the path is embeddeded into the output binary, this
	# prevents building a reproducible binary.
	oe_runmake configure-gcc
	sed -i "s@${STAGING_DIR_TARGET}@$replacement@g" ${B}/gcc/configargs.h
	sed -i "s@${STAGING_DIR_HOST}@/$replacement@g" ${B}/gcc/configargs.h
}

remove_sysroot_paths_from_checksum_options () {
	stagingdir=${1}
	replacement=${2}
	# Prevent sysroot/workdir paths from being used in checksum-options.
	# checksum-options is used to generate a checksum which is embedded into
	# the output binary.
	oe_runmake TARGET-gcc=checksum-options all-gcc
	sed -i "s@${DEBUG_PREFIX_MAP}@@g" ${B}/gcc/checksum-options
	sed -i "s@$stagingdir@$replacement@g" ${B}/gcc/checksum-options
}

cleanup_installed_include_fixed () {
	find ${D}${libdir}/gcc/${TARGET_SYS}/${BINV}/include-fixed -type f -not -name "README" -not -name limits.h -not -name syslimits.h | xargs rm -f
}

do_compile:prepend() {
    remove_sysroot_paths_from_configargs '/host'
    remove_sysroot_paths_from_checksum_options '${STAGING_DIR_HOST}' '/host'
}

do_install:append() {
    cleanup_installed_include_fixed
}
