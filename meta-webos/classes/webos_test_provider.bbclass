# Copyright (c) 2014-2018 LG Electronics, Inc.
#
# webos_test_provider
#
# This class is to be inherited by every recipe whose component is able to build
# and install unit- or other test scripts based on the build variables
# WEBOS_CONFIG_BUILD_TESTS and WEBOS_CONFIG_INSTALL_TESTS.
#
# For components which also inherit from the webos_cmake bbclass, it adds the
# following defines to their CMake command lines.
#
#   -DWEBOS_CONFIG_BUILD_TESTS:BOOL=<TRUE or FALSE>
#   -DWEBOS_CONFIG_INSTALL_TESTS:BOOL=<TRUE or FALSE>
#
# By definition, WEBOS_CONFIG_INSTALL_TESTS implies WEBOS_CONFIG_BUILD_TESTS
# so either one will cause tests to be built.
#

# Bring in the ptest functionality and packages etc.
inherit ptest
inherit webos_filesystem_paths

# (Weakly) set default values for both control variables to ensure they are defined
#
# As the tests are placed in their own package, which may or may not be included in
# a particular image, set the default so that they are always built (i.e. available
# for inclusion).

WEBOS_CONFIG_BUILD_TESTS[type] = "boolean"
WEBOS_CONFIG_BUILD_TESTS ??= "${PTEST_ENABLED}"
WEBOS_CONFIG_INSTALL_TESTS[type] = "boolean"
WEBOS_CONFIG_INSTALL_TESTS ??= "${PTEST_ENABLED}"

# Pass the control variableis into CMake (will have no effect if component does not use CMake)
EXTRA_OECMAKE += "-DWEBOS_CONFIG_BUILD_TESTS:BOOL=${@ 'TRUE' if oe.data.typed_value('WEBOS_CONFIG_BUILD_TESTS',d) or oe.data.typed_value('WEBOS_CONFIG_INSTALL_TESTS',d) else 'FALSE' }"
EXTRA_OECMAKE += "-DWEBOS_CONFIG_INSTALL_TESTS:BOOL=${@ 'TRUE' if oe.data.typed_value('WEBOS_CONFIG_INSTALL_TESTS',d) else 'FALSE' }"

# Ensure tests are installed if they are in the correct place
FILES_${PN}-ptest += "${webos_testsdir}/${BPN}"

# Bring in the g-lib test runner, as something is bound to use it
RDEPENDS_${PN}-ptest += "glib-2.0-utils"

# Also, add an RDEPENDS on ptest-runner - saves adding it to a packagegroup
RDEPENDS_${PN}-ptest += "ptest-runner"
