# https://git.openembedded.org/openembedded-core/commit/?h=scarthgap&id=cd530108e1b31ff3dff9e677e8e5af920e6609aa
# Fixes glib-networking.do_configure with newer meson
# http://gecko.lge.com:8000/Errors/Details/1000943
do_install:append:class-native () {
    # Link gio-querymodules into ${bindir} as otherwise tools like meson won't find it
    ln -rs ${D}${libexecdir}/gio-querymodules ${D}${bindir}
}
