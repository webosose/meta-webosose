# Copyright (c) 2023-2025 LG Electronics, Inc.

EXTENDPRAUTO:append = "webos1"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895304
# ERROR: QA Issue: File /usr/src/debug/python3-kivy/2.3.0/kivy/_clock.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/_metrics.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/weakproxy.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/properties.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/_event.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/shader.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/stencil_instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/gl_instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/svg.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/vertex_instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/opengl.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/context_instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/vertex.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/texture.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/compiler.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/scissor_instructions.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/buffer.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/vbo.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/transformation.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/context.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/boxshadow.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/fbo.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/opengl_utils.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/tesselator.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl_backend/cgl_debug.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl_backend/cgl_gl.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl_backend/cgl_mock.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl_backend/cgl_sdl2.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/graphics/cgl_backend/cgl_glew.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/lib/gstplayer/_gstplayer.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/window/_window_sdl2.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/window/window_info.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/text/_text_sdl2.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/text/text_layout.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/text/_text_pango.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/audio/audio_sdl2.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/image/_img_sdl2.c in package python3-kivy-src contains reference to TMPDIR
# File /usr/src/debug/python3-kivy/2.3.0/kivy/core/clipboard/_clipboard_sdl2.c in package python3-kivy-src contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
