# based on https://github.com/shr-project/meta-updater/blob/31a770d3c0cb22c9f4ece068208d2e6b2c28f1ee/conf/distro/sota.conf.inc#L15
# but without reproducible_build_simple in INHERIT as it was removed from oe-core in:
# https://git.openembedded.org/openembedded-core/commit/?id=f38a8de19550ae216575b5b39163666f74b07e2d
# also remove reproducible_build removal as it's not included by default as well since:
# https://git.openembedded.org/openembedded-core/commit/?id=abb0671d2cebfd7e8df94796404bbe9c7f961058
# and image-prelink removed in:
# https://git.openembedded.org/openembedded-core/commit/?id=f9719cc1c3fe9d380336e7af418daf27473b2e8b
# and sha256sum is already in HOSTTOOLS as well:
# https://git.openembedded.org/openembedded-core/commit/?id=d0d3abdf9e2dec57f3849813faa5e7e3d34b83a4
# and git was included since the initial HOSTTOOLS implementation in:
# https://git.openembedded.org/openembedded-core/commit/?id=fa764a403da34bb0ca9fa3767a9e9dba8d685965
# Apply https://github.com/uptane/meta-updater/pull/35

# Common settings to enable SOTA
# This can be pulled into a custom distribution, or included in
# local.conf directly with:
#
# require conf/distro/sota.conf.inc

DISTRO_FEATURES:append = " sota usrmerge"
DISTRO_FEATURES_NATIVE:append = " sota"
INHERIT += " sota"

HOSTTOOLS += "sync"
HOSTTOOLS_NONFATAL += "java repo"
