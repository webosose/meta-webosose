# https://git.openembedded.org/meta-openembedded/commit/?id=2ba9d36b1e367ee373f83aaba5cb77908c853a75

INSANE_SKIP:${PN} = "already-stripped"
do_install:append() {
	sed -i -e 's#${RECIPE_SYSROOT}##g' ${D}${bindir}/gphoto2
}

# this looks strange, gphoto2 is a binary file and already-stripped is caused by the sed applied above
# it should be fixed properly in meta-oe
