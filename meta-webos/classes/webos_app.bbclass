# Copyright (c) 2014-2023 LG Electronics, Inc.
#
# This class is intended to be inherited by every recipe whose component is an
# application for the webOS platform. The definition of an application is that
# it will render pixels on screen or the component installs an appinfo.json.

inherit webos_preferred_gfx_image_format
inherit webos_app_generate_security_files

# Most webos app need localization resources to be located in its directory.
webos_localization_resources_dir ?= "${webos_applicationsdir}/${BPN}/resources"
