# Copyright (c) 2015-2023 LG Electronics, Inc.

inherit deploy

# Always execute do_webos_deploy_fixup so that the
# IMAGE_VERSION_SUFFIX portion of the image filenames, which is formed from
# WEBOS_DISTRO_BUILD_ID, always corresponds to its value for this build.
do_webos_deploy_fixup[nostamp] = "1"
do_webos_deploy_fixup() {
    # this should be last command, recipes should prepend to it
    true
}

# The "before" is required, otherwise do_webos_deploy_fixup isn't run
# because it doesn't appear in task dependencies of the default task (do_build).
# The "after" only tells bitbake when to run it.
addtask webos_deploy_fixup after do_vmdkimg do_rootfs do_deploy do_image_complete before do_build
