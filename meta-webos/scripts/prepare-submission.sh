#!/bin/bash

# Copyright (c) 2013-2025 LG Electronics, Inc.

# Used to prepare a submission to the build of a component

# Global vars
debug=
submission=
version=
recipe=
remote=origin
branch=master
checkout=.
update=Y
minimal_submission="1"
minimal_version="0.1.0"
script_version="1.0.3"

usage () {
    cat << EOF
NAME:
    prepare-submission.sh - Prepare a submission to the build of a component

SYNOPSIS:
    prepare-submission.sh [-h] [-d] [-V] [-U] [-v VERSION]
              [-r RECIPE] [-R REMOTE-NAME] [-b BRACH] [-c CHECKOUT]
              SUBMISSION [COMMIT]

    NOTE: As this is a shell script, commands must come AFTER the options

DESCRIPTION:
    Creating submission or version tags manually is error prone, most common
    issues are:
    * tags are moved after they have been used in build
    * tags aren't annotated, so we cannot find who created or moved tag
    * tags don't match expected format or minimal submission (1),
      version (0.1.0)
    * developers are tagging latest commit in their local tree (after they
      check that it was merged on gerrit), but even when it's the same
      commit it has different revision in master branch, because it was
      cherry-picked by gerrit.

    This script checks for issues listed above, creates correct tags,
    pushes tags and allows automatic update of WEBOS_VERSION in recipe
    when -r parameter is used. It will also show how commit subject and
    git log --oneline --no-merges in ":Detailed Notes:" should look.

OPTIONS:
  -h
        Display this help and exit.

  -d
        Show some debug information.

  -v <version>
        Version, create versions/<version> tag (minimal value $minimal_version)

  -r <recipe>
        Path to recipe, when supplied script will update WEBOS_VERSION in recipe
        and suggest commit subject and git log for ":Detailed Notes:" in commit message

  -R <remote-name>
        Git remote name when you're using different value from the default 'origin'.

  -b <branch>
        Git branch which needs to exist in remote (default value 'master').
        Script will check if commit is from this branch.

  -U
        Supress default git remote update, git fetch --tags when checking for tag existence
        in remote repository. Use only when you're really sure that your checkout is up-to-date.

  -c <checkout>
        Path to your checkout (default value current work directory)

  -V
        Output version

EXAMPLES:
    * cd projects/meta-webos/recipes-webos
      prepare-submission.sh -v 2.0.0 -c ~/projects/component -r component/component.bb -R g2g 6 abcdef123
      1) Checks for possible issues
      2) Creates tags submissions/6 and versions/2.0.0 pointing to commit abcdef123
      3) Pushes tags submissions/6 and versions/2.0.0 to remote called g2g
      4) Updates WEBOS_VERSION in recipe component/component.bb
      5) Shows how commit subject and :Detailed Notes: should look like

EOF
}

# Print error information and exit.
echo_error () {
    echo "ERROR: $1" >&2
    exit 1
}

echo_warn () {
    echo "WARN: $1" >&2
}

echo_info () {
    echo "INFO: $1"
}

echo_debug () {
    [ -n "$debug" ] && echo "DEBUG: $1" >&2
}

echo_confirmation () {
  echo -n "Press <Enter> to confirm; ^C to abort: $1"
  read A
  [ -n "$A" ] && exit 1
}

remote_check() {
    remote_name=$1
    echo_debug "Checking if remote '$remote_name' exists"
    check=`(cd $checkout && git remote | grep "^$remote_name$")`
    [ -z "$check" ] && echo_error "Remote '$remote_name' isn't defined in checkout '$checkout'"
}

branch_check() {
    branch_name=$1
    echo_debug "Checking if branch '$branch_name' exists"
    check=`(cd $checkout && git branch -a | grep "^[\* ]*\(remotes/\)*${remote_prefix}$branch_name$")`
    [ -z "$check" ] && echo_error "Branch '$branch_name' isn't available in checkout '$checkout'"
}

tag_check_does_exist() {
    tag_name=$1
    echo_debug "Checking if tag '$tag_name' does exists"
    check=`(cd $checkout && git tag -l | grep "^$tag_name$")`
    [ -z "$check" ] && echo_warn "Tag $tag_name doesn't exists"
}

tag_check_doesnt_exist() {
    tag_name=$1
    echo_debug "Checking if tag '$tag_name' doesn't exists"
    check=`(cd $checkout && git tag -l | grep "^$tag_name$")`
    [ -n "$check" ] && (cd $checkout && git show -1 $tag_name | head) && echo_confirmation "$tag_name tag already exists, do you really want to move it?"
}

commit_in_branch_check() {
    branch_name=$1
    commit_name=$2
    echo_debug "Checking if commit '$commit_name' is from branch '$branch_name'"
    check=`(cd $checkout && git rev-list ${remote_prefix}$branch_name | grep "^$commit_name$")`
    [ -z $check ] && echo_warn "Revision '$commit_name' doesn't exist in ${remote_prefix}$branch_name, you cannot use commit which exists only in your local tree"
}

commit_already_tagged_check() {
    commit_name=$1
    echo_debug "Checking if commit '$commit_name' was already tagged by some other tag name"
    (cd $checkout && \
        for tag in `git tag -l`; do \
            echo "`git rev-list $tag | head -n 1` $tag"; \
        done | grep -q "^$commit_name " && \
        echo "WARN: Revision '$commit' was already tagged as:" && \
        for tag in `git tag -l`; do \
            echo "`git rev-list $tag | head -n 1` $tag";\
        done | grep "^$commit_name ")
}

create_tag() {
    tag_name=$1
    (cd $checkout && \
        export cmd="git tag -f -a -m '$tag_name' $tag_name $NEW_COMMIT" && \
        echo $cmd && \
        $cmd) && \
    echo "Tag '$tag_name' created successfully"
}

push_tag() {
    tag_name=$1
    (cd $checkout && \
        export cmd="git push $remote $tag_name" && \
        echo $cmd && \
        $cmd) && \
    echo "Tag '$tag_name' was pushed successfully"
}

# Parse and verify arguments
while getopts "hdUVv:R:r:b:c:" OPT; do
    case $OPT in
        h)
            usage
            exit 0
            ;;
        d)
            debug="Y"
            ;;
        U)
            update=""
            ;;
        v)
            version=$OPTARG
            ;;
        V)
            echo "prepare-submission $script_version"
            exit
            ;;
        r)
            recipe=$OPTARG
            ;;
        R)
            remote=$OPTARG
            ;;
        b)
            branch=$OPTARG
            ;;
        c)
            checkout=$OPTARG
            ;;
    esac
done

shift `expr $OPTIND - 1`
submission=$1
commit=$2

if [ -z "$submission" ]; then
    usage
    echo_error "SUBMISSION value is mandatory."
fi

if [ ! -d "$checkout/.git" ]; then
    if [ ! \( -d "$checkout/refs" -a -d "$checkout/objects" -a -d "$checkout/info" -a -d "$checkout/branches" \) ]; then
        echo_error "Directory '$checkout' doesn't look like git checkout, it doesn't have .git or (refs, objects, info, branches - bare) subdirectories."
    fi
    mirror=`(cd $checkout && git config --get remote.$remote.mirror)`
    [ "$mirror" = "true" ] && echo_error "Directory '$checkout' is mirror clone which cannot be used to push tags to $remote"
    remote_prefix=
else
    remote_prefix=$remote/
fi

if [ -n "$recipe" ] ; then
    [ ! -f "$recipe" ] && echo_error "Recipe file '$recipe' doesn't exists"
    grep -q "^inherit.*webos_enhanced_submissions" "$recipe" || echo_error "Recipe file '$recipe' doesn't inherit webos_enhanced_submissions"
    FILENAME=`basename "$recipe"`
    echo $FILENAME | grep -q "\(\(\.\)\|\(_.*\)\)bb\(append\)*$" || echo_error "Recipe file '$recipe' doesn't end with .bb, _<ver>.bb or .bbappend"
    PN=`echo $FILENAME | sed 's/^\(.*\)\(\(\.\)\|\(_.*\)\)bb\(append\)*$/\1/g'`

    OLD_WEBOS_VERSION_LINE=`grep "^WEBOS_VERSION.*=" "$recipe"`
    [ -z "$OLD_WEBOS_VERSION_LINE" ] && echo_warn "Recipe file '$recipe' doesn't have WEBOS_VERSION assignment"

    OLD_WEBOS_VERSION_ASSIGNMENT=`echo $OLD_WEBOS_VERSION_LINE | sed 's/^WEBOS_VERSION *\(?*=\) *"\([^"]*\)".*/\1/g'`
    OLD_WEBOS_VERSION=`echo $OLD_WEBOS_VERSION_LINE | sed 's/^WEBOS_VERSION *\(?*=\) *"\([^"]*\)".*/\2/g'`
    OLD_VERSION=`echo $OLD_WEBOS_VERSION | sed 's/\([^-]*\)-\([^_]*\)\(_.*\)*/\1/g'`
    OLD_SUBMISSION=`echo $OLD_WEBOS_VERSION | sed 's/\([^-]*\)-\([^_]*\)\(_.*\)*/\2/g'`
    OLD_COMMIT=`echo $OLD_WEBOS_VERSION | sed 's/\([^-]*\)-\([^_]*\)\(_.*\)*/\3/g; s/^_//g'`

    echo_debug "Recipe '$recipe' parsed with PN '$PN', VERSION '$OLD_VERSION', SUBMISSION '$OLD_SUBMISSION', SRCREV '$OLD_COMMIT'"
fi

if [ -n "$version" ] ; then
    echo $version | egrep -q "^[0123456789\.]+~*" || echo_error "Version '$version' should contain only digits, dots and '~<qualifier>'"
    echo $version | egrep -q "^[[:digit:]]+\.[[:digit:]]+\.[[:digit:]]+~*" || echo_warn "Version '$version' should match '^[:digit:]\.[:digit:]\.[:digit:]'"
    min=`echo -e "$version\n$minimal_version" | sort -V | head -n 1`
    [ "$min" = "$version" ] && echo_warn "Version '$version' doesn't look valid, minimal version should be '$minimal_version'"
fi

if [ -n "$submission" ] ; then
    echo $submission | egrep -q "^[[:digit:]]+$" && [ "$submission" -lt "$minimal_submission" ] && echo_warn "Submission '$submission' doesn't look valid, minimal submission should be '$minimal_submission'"
fi

remote_check $remote

[ -n "$update" ] && (cd $checkout && git remote update && git fetch --tags $remote)

branch_check $branch

if [ -z "$commit" ]; then
    echo_info "Revision wasn't set, using latest in $remote/$branch"
    commit=`(cd $checkout && git rev-list ${remote_prefix}$branch | head -n 1)`
fi

echo $commit | grep -q "^[abcdef0123456789]*$" || echo_error "Revision '$commit' isn't valid git commit"

# Run rev-list once more to convert user-supplied commit to 40 characters when shorter was used and confirm its existence
#     bitbake fetcher requires full 40 character SRCREVs, see bitbake/lib/bb/fetch2/git.py:
#         Ensure anything that doesn't look like a sha256 checksum/commit is translated into one
#         if not ud.revisions[name] or len(ud.revisions[name]) != 40  or (False in [c in "abcdef0123456789" for c in ud.revisions[name]]):
commit_check=`(cd $checkout && git rev-list $commit 2>/dev/null | head -n 1)`
[ -z $commit_check ] && echo_error "Revision '$commit' doesn't exist at all in '$checkout'"
NEW_COMMIT=$commit_check

commit_in_branch_check $branch $NEW_COMMIT

commit_already_tagged_check $NEW_COMMIT

if [ -n "$submission" ]; then
    tag_check_doesnt_exist submissions/$submission
    if echo $submission | egrep -q "^[[:digit:]]+$" && [ "$submission" -ne "$minimal_submission" ]; then
        prev_submission=`expr $submission - 1`
        tag_check_does_exist submissions/$prev_submission
    fi
    NEW_SUBMISSION=$submission
fi

if [ -n "$version" ]; then
    tag_check_doesnt_exist versions/$version
    NEW_VERSION=$version
fi

if [ -n "$recipe" ]; then
    SUBMISSION=$NEW_SUBMISSION
    VERSION=$NEW_VERSION
    [ -z ${SUBMISSION} ] && SUBMISSION=$OLD_SUBMISSION
    [ -z ${VERSION} ] && VERSION=$OLD_VERSION
    # some sanity checks
    [ -n "$OLD_SUBMISSION" ] && echo_debug "Checking old submission tag from recipe" && tag_check_does_exist submissions/$OLD_SUBMISSION
    [ -n "$OLD_VERSION" ] && echo_debug "Checking old versions tag from recipe" && tag_check_does_exist versions/$OLD_VERSION
    [ -n "$OLD_COMMIT" ] && echo_debug "Checking old commit from recipe" && commit_in_branch_check $branch $OLD_COMMIT

    echo_debug "Checking commit change"
    if [ "$NEW_COMMIT" != "$OLD_COMMIT" -a "$SUBMISSION" = "$OLD_SUBMISSION" ]; then
        echo_warn "Changing commit from '$OLD_COMMIT' to '$NEW_COMMIT' without change in submission number isn't correct"
    fi
fi

MSG="Create the tag(s)"
[ -n "$version" ] && MSG="$MSG versions/$version"
[ -n "$submission" ] && MSG="$MSG submissions/$submission"
MSG="$MSG in $checkout for commit $NEW_COMMIT"

echo_confirmation "$MSG"
[ -n "$version" ] && create_tag versions/$version
[ -n "$submission" ] && create_tag submissions/$submission

echo_confirmation "Push the tag(s) to $remote"
[ -n "$version" ] && push_tag versions/$version
[ -n "$submission" ] && push_tag submissions/$submission

ref_check=`(cd $checkout && git show-ref -d submissions/$submission)`
[ -z "$ref_check" ] && echo_error "Reference 'submissions/$submission' doesn't exist at all in '$checkout'"
[ `echo "$ref_check" | wc -l` -ne 2 ] && echo_error "git show-ref -d 'submissions/$submission' should return 2 lines not `echo "$ref_check" | wc -l` '$ref_check'"
NEW_COMMIT_FROM_REF=`echo "$ref_check" | tail -n 1 | cut -f 1 -d ' '`
NEW_REF=`echo "$ref_check" | head -n 1 | cut -f 1 -d ' '`
        

echo_debug "Checking commit change"
if [ "$NEW_COMMIT" != "$NEW_COMMIT_FROM_REF" ]; then
    echo_warn "Dereferenced 'submissions/$submission' should point to the same commit as we requested '$NEW_COMMIT' but points to '$NEW_COMMIT_FROM_REF'"
fi

if [ -n "$recipe" ] ; then
    NEW_WEBOS_VERSION_LINE="WEBOS_VERSION ${OLD_WEBOS_VERSION_ASSIGNMENT} \"${VERSION}-${SUBMISSION}_${NEW_REF}\""
    REPLACEMENT="s/^${OLD_WEBOS_VERSION_LINE}/${NEW_WEBOS_VERSION_LINE}/g"

    SUBJECT="<image-name>: [Add] ${PN}=${SUBMISSION}"		
    if [ -n "$NEW_VERSION" -a "$NEW_VERSION" != "$OLD_VERSION" ]; then		
        SUBJECT="${SUBJECT},v${NEW_VERSION}"		
    fi

    if [ -n "${OLD_SUBMISSION}" ] ; then
        LOG=`(cd $checkout && git log --no-merges --oneline submissions/${OLD_SUBMISSION}..submissions/${SUBMISSION})`
        ISSUES=`(cd $checkout && git log --no-merges submissions/${OLD_SUBMISSION}..submissions/${SUBMISSION} | grep "^ *\[.\+-[0-9]\+\].*$" | tr -s " " | sed "s/^ *//g")`
    fi

    if [ "$OLD_COMMIT" = "$NEW_COMMIT_FROM_REF" ]; then
        echo_warn "We're changing SHA-1 in WEBOS_VERSION, but last commit included is the same. We're probably changing from lightweight to annotated tag."
    fi

    cat << EOF


Script will update WEBOS_VERSION in $recipe
-${OLD_WEBOS_VERSION_LINE}
+${NEW_WEBOS_VERSION_LINE}


Commit subject should match:
$SUBJECT


:Detailed Notes: should contain:
! Be aware that if you update submission for the same recipe multiple times
  in the same change, this script shows only log from last update. !
submissions/${OLD_SUBMISSION}..submissions/${SUBMISSION}
$LOG

:Issues Addressed:
$ISSUES

EOF
    echo_confirmation "Update recipe"
    sed -i "${REPLACEMENT}" $recipe
fi
