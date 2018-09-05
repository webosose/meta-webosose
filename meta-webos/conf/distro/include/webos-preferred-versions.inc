# Copyright (c) 2012-2018 LG Electronics, Inc.

PREFERRED_VERSION_openssl ?= "1.0.%"
PREFERRED_VERSION_openssl-native ?= "1.0.%"
PREFERRED_VERSION_nativesdk-openssl ?= "1.0.%"
PNBLACKLIST[openssl10] ?= "we have 1.0.% version in openssl recipe which provides openssl10 as well"
PNBLACKLIST[openssl10-native] ?= "we have 1.0.% version in openssl recipe which provides openssl10 as well"
PNBLACKLIST[nativesdk-openssl10] ?= "we have 1.0.% version in openssl recipe which provides openssl10 as well"
