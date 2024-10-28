# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/893033
# ERROR: QA Issue: File /usr/lib/.debug/libgfortran.so.5.0.0 in package libgfortran-dbg contains reference to TMPDIR [buildpaths]
# Discussed in:
# https://lists.openembedded.org/g/openembedded-core/message/201904
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
