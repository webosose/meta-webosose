# https://lists.openembedded.org/g/openembedded-devel/message/107332
# https://git.openembedded.org/meta-openembedded/commit/?id=cdaafdea99c6b49cc56216c6082722e01512a0cd
PACKAGECONFIG[gtk+3] = ",-Dgtk=disabled,gtk+3"
# https://lists.openembedded.org/g/openembedded-devel/message/107333
# https://git.openembedded.org/meta-openembedded/commit/?id=23b62b3391f2e605f59e02abb3c99db2a7b82eb5
REQUIRED_DISTRO_FEATURES += "x11"
