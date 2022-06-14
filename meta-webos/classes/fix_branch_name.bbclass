# Copyright (c) 2022 LG Electronics, Inc.
#
# fix_branch_name
#
# It is used to change the branch name specified in 'SRC_URI'.
# This can be used for workround when branch name is changed from master to another on github etc.
#

FIX_BRANCH_FROM ?= "branch=master"
FIX_BRANCH_TO   ?= "branch=main"

python() {
    src_uri = d.getVar('SRC_URI').replace(d.getVar('FIX_BRANCH_FROM'),d.getVar('FIX_BRANCH_TO'))
    d.delVar('SRC_URI')
    d.setVar('SRC_URI',src_uri)
}
