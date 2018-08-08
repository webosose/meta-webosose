# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

EXTRA_OECMAKE_append_arm = " -DCMAKE_CXX_FLAGS_RELEASE=-Wa,-mimplicit-it=thumb"

PACKAGECONFIG[text] = "-DBUILD_opencv_text=ON,-DBUILD_opencv_text=OFF,tesseract,"
PACKAGECONFIG[eigen] = "-DWITH_EIGEN=ON,-DWITH_EIGEN=OFF,libeigen gflags glog,"

# Adds dependency on GPLv2 tbb
PACKAGECONFIG_remove = "tbb"

# Depends on blacklisted glog
PACKAGECONFIG_remove_armv4 = "eigen"
PACKAGECONFIG_remove_armv5 = "eigen"
