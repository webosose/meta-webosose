# Copyright (c) 2014-2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# Replace bison with bison-native, configure only checks for ac_prog
# configure:for ac_prog in 'bison -y' byacc
# add libxml2-native for deterministic build (it detects xmlcatalog and xmllint)
DEPENDS = "bison-native flex flex-native cracklib libxml2-native"
