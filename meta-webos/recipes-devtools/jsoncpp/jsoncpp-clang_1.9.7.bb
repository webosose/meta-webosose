# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO = "webos1"

FILESEXTRAPATHS:prepend := "${META_OE_LAYER}/recipes-devtools/jsoncpp/jsoncpp:"

require recipes-devtools/jsoncpp/jsoncpp_1.9.7.bb
require jsoncpp-clang.inc
