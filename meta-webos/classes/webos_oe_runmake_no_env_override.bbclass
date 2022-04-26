# Copyright (c) 2012-2022 LG Electronics, Inc.
#
# webos_oe_runmake_no_env_override
#
# By default, oe_runmake arranges for environment variables to override make
# variables. Inherit this class (before making any assignments to EXTRA_OEMAKE)
# to disable this. Typically, this is done when using a configurator that
# generates Makefiles with the expected settings from the environment already
# assigned to make variables (e.g. qmake). Another scenario when it's needed is
# when a Makefile expects to append to the CFLAGS, etc. that are passed in from
# the environment.
#

EXTRA_OEMAKE = ""
