# Backport changes from:
# https://git.openembedded.org/meta-openembedded/commit/?id=0262013e03485048bc5d057c414f4989ed1be05a
# https://lists.openembedded.org/g/openembedded-devel/message/104699
# https://lists.openembedded.org/g/openembedded-devel/message/104723

# This is added by annonymous python in meta-oe/recipes-graphics/vk-gl-cts/khronos-cts.inc
# so even :remove operator is too weak for that
# DEPENDS:remove:virtclass-multilib-lib32 = "wayland wayland-protocols"
# DEPENDS:append = " ${MLPREFIX}wayland ${MLPREFIX}wayland-protocols"
python __anonymous() {
    deps = d.getVar("DEPENDS")
    mlprefix = d.getVar("MLPREFIX")
    deps = deps.replace(' wayland-protocols', ' %swayland-protocols' % mlprefix)
    deps = deps.replace(' wayland ', ' %swayland ' % mlprefix)
    d.setVar("DEPENDS", deps)
}
