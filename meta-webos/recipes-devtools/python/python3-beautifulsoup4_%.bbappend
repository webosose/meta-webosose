# Work around
# ERROR: Task
# virtual:native:/OE/lge/build/webosose/kirkstone/meta-oe/meta-python/recipes-devtools/python/python3-beautifulsoup4_4.10.0.bb:do_populate_sysroot
# has circular dependency on
# virtual:native:/OE/lge/build/webosose/kirkstone/meta-oe/meta-python/recipes-devtools/python/python3-soupsieve_2.3.1.bb:do_populate_sysroot

RDEPENDS:${PN}:remove:class-native = "python3-soupsieve"
