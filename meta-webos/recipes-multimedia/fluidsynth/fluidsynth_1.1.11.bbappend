# Copyright (c) 2020 LG Electronics, Inc.

# Set nobranch, because 1.1.x branch used in the recipe was removed
# from the repo, warrior and newer don't have this issue, because using
# version 2.x and this bbappend can be removed when upgrading to newer
# Yocto release.
SRC_URI = " \
    git://github.com/FluidSynth/fluidsynth.git;nobranch=1 \
    file://0001-Use-ARM-NEON-accelaration-for-float-multithreaded-se.patch \
"
