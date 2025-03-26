# Copyright (c) 2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# Fix SRC_URI based on https://lists.openembedded.org/g/openembedded-core/message/209315
SRC_URI:remove = "https://boostorg.jfrog.io/artifactory/main/release/${PV}/source/${BOOST_P}.tar.bz2"
SRC_URI:prepend = "https://archives.boost.io/release/${PV}/source/${BOOST_P}.tar.bz2 "
