# Copyright (c) 2023 LG Electronics, Inc.

addtask do_hack_recipe_sysroot after do_prepare_recipe_sysroot before do_configure
EXPORT_FUNCTIONS do_hack_recipe_sysroot
do_hack_recipe_sysroot() {
    ln -sf ${STAGING_LIBDIR}/${TARGET_SYS}/*/crt* ${STAGING_LIBDIR}
    ln -sf ${STAGING_LIBDIR}/${TARGET_SYS}/*/libgcc* ${STAGING_LIBDIR}
}
