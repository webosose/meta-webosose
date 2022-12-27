# Copyright (c) 2022-2023 LG Electronics, Inc.
#
# fix_branch_name
#
# This is mainly used to temporarily change the branch name specified in 'SRC_URI'
# This can be used as a workaround when the branch name is changed from master
# to something else unexpectedly on github, etc.
# If this bbclass is inherited but not applied, a warning message is displayed.
#

FIX_BRANCH_FROM ?= "branch=master"
FIX_BRANCH_TO   ?= "branch=main"

python() {
    src_uri_org = d.getVar('SRC_URI')
    src_uri_new = src_uri_org.replace(d.getVar('FIX_BRANCH_FROM'),d.getVar('FIX_BRANCH_TO'))
    if src_uri_org == src_uri_new:
        bb.warn("inherit fix_branch_name line has no effect and can be removed.")
    else:
        d.setVar('SRC_URI',src_uri_new)
}
