# Copyright (c) 2015-2023 LG Electronics, Inc.
#
# allarch
#
# Intercept the upstream allarch.bbclass to set SELECTED_OPTIMIZATION, FULL_OPTIMIZATION values
# to "none"
#

require ${COREBASE}/meta/classes/allarch.bbclass

python () {
    if d.getVar("PACKAGE_ARCH") == "all":
        # Reset *_OPTIMIZATION, because there is
        # TARGET_CFLAGS -> SELECTED_OPTIMIZATION -> FULL_OPTIMIZATION -> WEBOS_MINSIZE_CCARGS -> feature-webos-minsize.inc
        # dependency and MACHINEs with and without minsize in TUNE_FEATURE have different sstate signature for allarch recipes
        d.setVar("SELECTED_OPTIMIZATION", "none")
        d.setVar("FULL_OPTIMIZATION", "none")
 
}
