# Copyright (c) 2017-2022 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos6"

# Add gallium-llvm which will enable svga as well
PACKAGECONFIG:append:qemuall = " gallium-llvm"

# svga fails to build for qemuarm:
# mesa-20.0.2/src/gallium/winsys/svga/drm/vmw_msg.c:90:4: error: impossible constraint in 'asm'
# http://caprica.lgsvl.com:8080/Errors/Details/1472308
GALLIUMDRIVERS_LLVM:qemuarm = "r300,nouveau"
