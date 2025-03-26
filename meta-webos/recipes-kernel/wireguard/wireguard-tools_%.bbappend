# Copyright (c) 2017-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos2"

VIRTUAL-RUNTIME_bash ?= "bash"
PACKAGECONFIG[bash-completion] = "WITH_BASHCOMPLETION=yes,WITH_BASHCOMPLETION=no,,${VIRTUAL-RUNTIME_bash},,"
PACKAGECONFIG[wg-quick]        = "WITH_WGQUICK=yes,WITH_WGQUICK=no,,${VIRTUAL-RUNTIME_bash},,"
