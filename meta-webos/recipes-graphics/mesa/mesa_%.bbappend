# Copyright (c) 2017-2021 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos6"

# Add gallium-llvm which will enable svga as well
PACKAGECONFIG_append_qemuall = " gallium-llvm"

# svga fails to build for qemuarm:
# mesa-20.0.2/src/gallium/winsys/svga/drm/vmw_msg.c:90:4: error: impossible constraint in 'asm'
# http://caprica.lgsvl.com:8080/Errors/Details/1472308
GALLIUMDRIVERS_LLVM_qemuarm = "r300,nouveau"
