# Copyright (c) 2012-2018 LG Electronics, Inc.
#
# webos_enhanced_submissions
#
# Parse a WEBOS_VERSION in the following format:
#
#    <component-version>-<enhanced-submission>
#
# where <enhanced-submission> is of the form:
#
#    <submission>_<40-character-SHA-1>[;branch=<branch>]
#
# setting WEBOS_COMPONENT_VERSION, WEBOS_SUBMISSION, WEBOS_GIT_PARAM_TAG,
# WEBOS_GIT_TAG, WEBOS_GIT_PARAM_BRANCH, WEBOS_SRCREV, SRCREV, and PV.
#
# The default tag name is the webOS convention for submission tags, i.e.,
# they are of the form:
#    submissions/<decimal-integer>                  (default branch: "master")
# or, when the submission is from some permanent branch:
#    submissions/<name>.<decimal-integer>           (default branch: "@<name>")
# where <decimal-integer> does not contain leading zeros.
#
# <name> is the branch name without leading '@' and can have following forms:
#    <decimal-integer> when the branch was created from submissions/<decimal-integer>.
#    <decimal-integer>.<codename> when the branch was also created from
#       submissions/<decimal-integer> tag, but for some specific <codename> release.
#    <decimal-integer>.<codename>.<decimal-integer> is used for multiple levels of
#       branched submissions, e.g. NN.<codename>.MM.PP is PP-th submission along the
#       @NN.<codename>.MM branch.
#
# The default branch name value can be overriden by "branch" parameter in WEBOS_VERSION
# or by setting WEBOS_GIT_PARAM_BRANCH.
#
# The default tag name can be overriden by setting WEBOS_GIT_PARAM_TAG.
#
# WEBOS_SUBMISSION '0' has special meaning to disable check
# that selected SHA-1 is matching with submissions tag
#
# There is limited support for recipes with multiple git repositories in SRC_URI.
# Exactly one of them needs to have empty 'name' parameter or 'name' parameter with
# value 'main' and this one will get SRCREV set and verified by this bbclass.

inherit webos_submissions

def webos_enhsub_get_srcrev(d, webos_v):
    webos_srcrev = webos_version_get_srcrev(webos_v)
    webos_submission = d.getVar('WEBOS_SUBMISSION', True)
    # submission 0 means that we're using:
    # a) AUTOREV
    # b) SHA-1 possibly not included in any tag or branch
    # c) something else (like reference to a Gerrit review)
    #
    # a) and b) should be set in SRCREV
    # c) should be set in WEBOS_GIT_PARAM_TAG with WEBOS_GIT_TAG enabled and let git ls-remote resolve it
    # Another way to handle c) is to override WEBOS_GIT_TAG directly with ";tag=<ref>"
    if webos_submission == '0':
        # return only valid SRCREV or "AUTOINC", otherwise "INVALID"
        if webos_srcrev == "AUTOINC" or not (len(webos_srcrev) != 40 or (False in [c in "abcdef0123456789" for c in webos_srcrev])):
            return webos_srcrev
        else:
            return "INVALID"
    if webos_srcrev == None or len(webos_srcrev) != 40 or (False in [c in "abcdef0123456789" for c in webos_srcrev]):
        file = d.getVar('FILE', True)
        webos_git_repo_tag = d.getVar('WEBOS_GIT_REPO_TAG', True) or "submissions/%s" % webos_submission
        bb.fatal(("%s: WEBOS_VERSION needs to end with _<SHA-1> where " +
                     "<SHA-1> is the 40-character identifier of '%s' tag")
                     % (file, webos_git_repo_tag))
    # only valid SRCREVs at this point
    return webos_srcrev

def webos_enhsub_get_tag(d, webos_v):
    webos_submission = d.getVar('WEBOS_SUBMISSION', True)
    webos_git_repo_tag = d.getVar('WEBOS_GIT_REPO_TAG', True) or "submissions/%s" % webos_submission
    return webos_git_repo_tag

# Set WEBOS_SRCREV to value from WEBOS_VERSION.
WEBOS_SRCREV = "${@webos_enhsub_get_srcrev(d, '${WEBOS_VERSION}')}"

# we don't include SRCPV in PV, so we have to manually include SRCREVs in do_fetch vardeps
do_fetch[vardeps] += "SRCREV_main SRCREV"
SRCREV = "${WEBOS_SRCREV}"
SRCREV_main = "${WEBOS_SRCREV}"

# append WEBOS_PV_SUFFIX to PV when you're using 0 as WEBOS_SUBMISSION to make it clear which SHA-1 was built
WEBOS_PV_SUFFIX = "+gitr${SRCPV}"

# srcrev is mandatory and enough, don't put tag= in SRC_URI
# to reenable you need to set WEBOS_GIT_TAG to ";tag=${WEBOS_GIT_PARAM_TAG}"
WEBOS_GIT_PARAM_TAG = "${@webos_enhsub_get_tag(d, '${WEBOS_VERSION}')}"
WEBOS_GIT_TAG = ""

WEBOS_GIT_PARAM_BRANCH = "${@webos_version_get_branch('${WEBOS_VERSION}')}"

# When SRCREV isn't SHA-1 show error
do_fetch[prefuncs] += "webos_enhsub_srcrev_sanity_check"

# '0' in 'webos_submission' is used with AUTOREV or SHA-1 without matching tag
# show non-fatal ERROR to make sure that it's not accidentally merged in master
python webos_enhsub_srcrev_sanity_check() {
    srcrev = d.getVar('SRCREV', True)
    webos_submission = d.getVar('WEBOS_SUBMISSION', True)
    if webos_submission == '0':
        webos_version = d.getVar('WEBOS_VERSION', True)
        pn = d.getVar('PN', True)
        file = d.getVar('FILE', True)
        msg = "WEBOS_VERSION '%s' for recipe '%s' (file '%s') contains submission 0, which indicates using AUTOREV or SHA-1 without matching tag and cannot be used in official builds." % (webos_version, pn, file)
        package_qa_handle_error("webos-enh-sub-autorev-error", msg, d)
    elif (len(srcrev) != 40 or (False in [c in "abcdef0123456789" for c in srcrev])):
        file = d.getVar('FILE', True)
        bb.error("%s: SRCREV needs to contain 40-character SHA1" % file)
}

# When both SRCREV and WEBOS_SUBMISSION are defined check that they correspond
# This only compares tag and SHA-1 in local checkout (without using git ls-remote)
# This check is executed only when do_fetch is executed, that means that if someone
# moves the tag in remote repository, we won't notice it until do_fetch is re-executed.
do_unpack[postfuncs] += "submission_sanity_check"
python submission_sanity_check() {
    def webos_enhsub_remote_update(d, u, pn, checkout):
        """ Runs git remote update to fetch newly added tags or updated branches in case one of the checks fails
            It runs git remote update twice, first in DL_DIR (e.g. downloads/git2/github.com.openwebos.librolegen/)
            then in actuall checkout in WORKDIR, because we're already in do_unpack task and sanity checks are
            executed in WORKDIR.
            This isn't as efficient as the implementation in newer bitbake, because PREMIRROR tarballs aren't
            recreated after this git remote update, so local builds will fetch the tarball and also run own
            git remote update until PREMIRROR tarball is updated by fetching even newer SRCREV.
        """
        bb.debug(2, "Running git remote update for pn '%s', checkout '%s'" % (pn, checkout))
        fetcher = bb.fetch2.Fetch([u], d)
        localpath = fetcher.localpath(u)
        bb.warn("Fetcher accessing the network, because sanity check failed %s, %s" % (u, localpath))
        cmd = "cd %s && git remote update" % (localpath)
        try:
            output = bb.fetch.runfetchcmd(cmd, d, quiet=True)
        except bb.fetch2.FetchError:
            msg = "Unable to update '%s' checkout for recipe '%s'" % (localpath, pn)
            package_qa_handle_error("webos-enh-sub-error", msg, d)
        # and the same in WORKDIR
        cmd = "cd %s && git remote update" % (checkout)
        try:
            output = bb.fetch.runfetchcmd(cmd, d, quiet=True)
        except bb.fetch2.FetchError:
            msg = "Unable to update '%s' checkout for recipe '%s'" % (checkout, pn)
            package_qa_handle_error("webos-enh-sub-error", msg, d)


    def webos_enhsub_tag_sanity_check(d, fetcher, u, pn, tag_param, rev, webos_git_repo_tag, checkout, file, first=True):
        """ Checks that tag:
            1) exists
            2) is annotated (not lightweight)
            3) uniq
            4) matches with selected SRCREV
        """
        bb.debug(2, "sanity check for tag in pn '%s', tag_param '%s', rev '%s', webos_git_repo_tag '%s', checkout '%s'" % (pn, tag_param, rev, webos_git_repo_tag, checkout))
        cmd = "cd %s && git tag -l 2>/dev/null | grep '^%s$' | wc -l" % (checkout, webos_git_repo_tag)
        tag_exists = bb.fetch.runfetchcmd(cmd, d).strip()
        if tag_exists != "1":
            if first:
                webos_enhsub_remote_update(d, u, pn, checkout)
                webos_enhsub_tag_sanity_check(d, fetcher, u, pn, tag_param, rev, webos_git_repo_tag, checkout, file, False)
                return
            else:
                localpath = fetcher.localpath(u)
                msg = "The tag '%s' for recipe '%s' (file '%s') doesn't exist in local checkout of SHA-1 '%s'. It's possible that the tag already exists in a remote repository, but your local checkout (or checkout downloaded as a tarball from PREMIRROR) contains the requested SHA-1 without a tag assigned to it (this cannot happen with annotated tags, because they have their own SHA-1 which either exists or not). Please update your checkout in %s by executing git fetch --tags and run again." % (webos_git_repo_tag, pn, file, rev, localpath)
                package_qa_handle_error("webos-enh-sub-error", msg, d)
                return
        # for annotated tags there are 2 SHA-1s and we don't care which one is used (same source)
        # $ git show-ref -d --tags 0.5
        #   70fb05fd340ab342c5132dc8bfa174dbe6c9d330 refs/tags/0.5
        #   215f9c884d0139c93feea940d255dc3575678218 refs/tags/0.5^{}
        # prefix with 'refs/tags/' so that partial tags aren't matched, e.g. librolegen:
        # $ git show-ref -d --tags 18
        #   cbedc69733f65cd2f498787a621c014e219d38ab refs/tags/submissions/18
        #   9040954a24115b05219e7dd459dcf91ad05cc739 refs/tags/submissions/18^{}
        # $ git show-ref -d --tags refs/tags/18
        #   <nothing>
        cmd = "cd %s && git show-ref -d --tags refs/tags/%s" % (checkout, webos_git_repo_tag)
        tag_srcrevs = bb.fetch.runfetchcmd(cmd, d).strip().split('\n')
        found_srcrev = False
        if len(tag_srcrevs) > 2:
            msg = "The reference refs/tags/%s is matching more than 2 entries for recipe '%s' (file '%s'):\n%s" % (webos_git_repo_tag, pn, file, '\n'.join(tag_srcrevs))
            package_qa_handle_error("webos-enh-sub-error", msg, d)
        if len(tag_srcrevs) == 1:
            if first:
                webos_enhsub_remote_update(d, u, pn, checkout)
                webos_enhsub_tag_sanity_check(d, fetcher, u, pn, tag_param, rev, webos_git_repo_tag, checkout, file, False)
                return
            else:
                msg = "The tag '%s' for recipe '%s' (file '%s') is lightweight tag, please use annotated tag in next submission" % (webos_git_repo_tag, pn, file)
                package_qa_handle_error("webos-enh-sub-error", msg, d)
        for tag_srcrev in tag_srcrevs:
            (sha, name) = tag_srcrev.split()
            if sha == rev:
                found_srcrev = True
                if tag_srcrev != tag_srcrevs[0] or tag_srcrev.find("^{}") == len(tag_srcrev) - 3:
                    msg = "The tag '%s' for recipe '%s' (file '%s') is annotated, but WEBOS_VERSION '%s' is using SHA-1 of last commit included, not of the tag itself '%s'" % (webos_git_repo_tag, pn, file, webos_version, tag_srcrevs[0].split()[0])
                    package_qa_handle_error("webos-enh-sub-error", msg, d)

        if not found_srcrev:
            if first:
                webos_enhsub_remote_update(d, u, pn, checkout)
                webos_enhsub_tag_sanity_check(d, fetcher, u, pn, tag_param, rev, webos_git_repo_tag, checkout, file, False)
                return
            else:
                if len(tag_srcrevs) < 1:
                    msg = "The SHA-1 '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') doesn't match with tag '%s', tag couldn't be found in refs/tags/" % (rev, pn, file, webos_git_repo_tag)
                    package_qa_handle_error("webos-enh-sub-error", msg, d)
                elif len(tag_srcrevs) == 1:
                    msg = "The SHA-1 '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') doesn't match with tag '%s', which is seen as SHA-1 '%s'" % (rev, pn, file, webos_git_repo_tag, tag_srcrevs[0].split()[0])
                    package_qa_handle_error("webos-enh-sub-error", msg, d)
                else:
                    msg = "The SHA-1 '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') doesn't match with tag '%s', which is seen as SHA-1s:\n%s" % (rev, pn, file, webos_git_repo_tag, '\n'.join(tag_srcrevs))
                    package_qa_handle_error("webos-enh-sub-error", msg, d)

    def webos_enhsub_branch_sanity_check(d, u, fetcher, branch_in_webos_version, branch_in_src_uri, pn, file, checkout, rev, first=True):
        """ Checks that selected SRCREV is included in selected branch
            duplicates bitbake's git fetcher functionality added in
            http://git.openembedded.org/bitbake/commit/?id=89abfbc1953e3711d6c90aff793ee622c22609b1
            http://git.openembedded.org/bitbake/commit/?id=31467c0afe0346502fcd18bd376f23ea76a27d61
            http://git.openembedded.org/bitbake/commit/?id=f594cb9f5a18dd0ab2342f96ffc6dba697b35f65
        """
        bb.debug(2, "sanity check for branch in pn '%s', branch_in_webos_version '%s', branch_in_src_uri '%s', rev '%s', checkout '%s'" % (pn, branch_in_webos_version, branch_in_src_uri, rev, checkout))
        if branch_in_src_uri != branch_in_webos_version:
            msg = "Branch is set in WEBOS_VERSION '%s' for recipe '%s' (file '%s') as well as in SRC_URI '%s' and they don't match" % (branch_in_webos_version, pn, file, branch_in_src_uri)
            package_qa_handle_error("webos-enh-sub-error", msg, d)
        cmd = "cd %s && git branch -a --contains %s --list origin/%s 2> /dev/null | wc -l" % (checkout, rev, branch_in_webos_version)
        try:
            output = bb.fetch.runfetchcmd(cmd, d, quiet=True)
        except bb.fetch2.FetchError:
            msg = "Unable to check if SHA-1 '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') is included in branch '%s'" % (rev, pn, file, branch)
            package_qa_handle_error("webos-enh-sub-error", msg, d)
        if len(output.split()) > 1:
            msg = "Unable to check if SHA-1 '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') is included in branch '%s', unexpected output from '%s': '%s'" % (rev, pn, file, branch_in_webos_version, cmd, output)
            package_qa_handle_error("webos-enh-sub-error", msg, d)
        if output.split()[0] == "0":
            if first:
                webos_enhsub_remote_update(d, u, pn, checkout)
                webos_enhsub_branch_sanity_check(d, u, fetcher, branch_in_webos_version, branch_in_src_uri, pn, file, checkout, rev, False)
                return
            else:
                msg = "Revision '%s' defined in WEBOS_VERSION for recipe '%s' (file '%s') isn't included in branch '%s'" % (rev, pn, file, branch_in_webos_version)
                package_qa_handle_error("webos-enh-sub-error", msg, d)

    src_uri = (d.getVar('SRC_URI', True) or "").split()
    if len(src_uri) == 0:
        return

    externalsrc = d.getVar('EXTERNALSRC', True) or ""
    if len(externalsrc) != 0:
        return

    found_first = False
    workdir = d.getVar('WORKDIR', True)
    pn = d.getVar('PN', True)
    file = d.getVar('FILE', True)
    fetcher = bb.fetch.Fetch(src_uri, d)
    urldata = fetcher.ud
    autoinc_templ = 'AUTOINC+'
    for u in urldata:
        tag_param = urldata[u].parm['tag'] if 'tag' in urldata[u].parm else None
        name_param = urldata[u].parm['name'] if 'name' in urldata[u].parm else 'main'
        if urldata[u].type == 'git' and name_param == 'main':
            if found_first:
                msg = "webos_enhanced_submission bbclass has limited support for recipes with multiple git repos in SRC_URI. They have to have different 'name' parameter and the one which points to repository with submissions tag should have 'name=main'. Recipe '%s' (file '%s') has multiple git repos with 'main' name or without names" % (pn, file)
                package_qa_handle_error("webos-enh-sub-warning", msg, d)
                break
            found_first = True
            destsuffix_param = urldata[u].parm['destsuffix'] if 'destsuffix' in urldata[u].parm else 'git'
            webos_version = d.getVar('WEBOS_VERSION', True)
            srcrev = d.getVar('SRCREV', True)
            name = urldata[u].parm['name'] if 'name' in urldata[u].parm else 'default'
            try:
                rev = urldata[u].method.sortable_revision(urldata[u], d, name)
            except TypeError:
                # support old bitbake versions
                rev = urldata[u].method.sortable_revision(u, urldata[u], d, name)
            # Clean this up when we next bump bitbake version
            if type(rev) != str:
                autoinc, rev = rev
            elif rev.startswith(autoinc_templ):
                rev = rev[len(autoinc_templ):]

            webos_git_repo_tag = d.getVar('WEBOS_GIT_REPO_TAG', True)
            webos_submission = d.getVar('WEBOS_SUBMISSION', True)
            default_webos_git_repo_tag = "submissions/%s" % webos_submission
            if not srcrev:
                # Recipe needs to have SRCREV set one way or another
                # it could be in WEBOS_VERSION, from AUTOREV or by explicit SRCREV assignment
                msg = "Recipe '%s' (file '%s') doesn't contain SRCREV" % (pn, file)
                package_qa_handle_error("webos-enh-sub-error", msg, d)
            if not webos_git_repo_tag:
                webos_git_repo_tag = default_webos_git_repo_tag
            elif webos_git_repo_tag == default_webos_git_repo_tag:
                msg = "Don't set WEBOS_GIT_REPO_TAG when the component is using default scheme 'submissions/${WEBOS_SUBMISSION}' in recipe '%s' (file '%s')" % (pn, file)
                package_qa_handle_error("webos-enh-sub-error", msg, d)
            checkout = "%s/%s" % (workdir, destsuffix_param)

            # '0' in 'webos_submission' is used with AUTOREV -> so don't check AUTOREV against submissions/0 tag
            if webos_submission != '0' and webos_git_repo_tag and rev:
                webos_enhsub_tag_sanity_check(d, fetcher, u, pn, tag_param, rev, webos_git_repo_tag, checkout, file)

            if not 'nobranch' in urldata[u].parm or urldata[u].parm['nobranch'] != "1":
                branch_in_src_uri = urldata[u].parm['branch'] if 'branch' in urldata[u].parm else 'master'
                branch_in_webos_version = d.getVar('WEBOS_GIT_PARAM_BRANCH', True)
                webos_enhsub_branch_sanity_check(d, u, fetcher, branch_in_webos_version, branch_in_src_uri, pn, file, checkout, rev)
    if not found_first:
        msg = "Recipe '%s' (file '%s') doesn't have git repository without 'name' parameter or with 'name=main' in SRC_URI, webos_enhanced_submission bbclass shouldn't be inherited here (it has nothing to do)" % (pn, file)
        package_qa_handle_error("webos-enh-sub-warning", msg, d)
}
