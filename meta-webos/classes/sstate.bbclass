# Copyright (c) 2021 LG Electronics, Inc.
#
# Intercept the upstream sstate.bbclass
#  to fix errors about read-only sstate mirrors.
#

require ${COREBASE}/meta/classes/sstate.bbclass

inherit webos_deploy

def sstate_package(ss, d):
    import oe.path

    tmpdir = d.getVar('TMPDIR')

    sstatebuild = d.expand("${WORKDIR}/sstate-build-%s/" % ss['task'])
    d.setVar("SSTATE_CURRTASK", ss['task'])
    bb.utils.remove(sstatebuild, recurse=True)
    bb.utils.mkdirhier(sstatebuild)
    for state in ss['dirs']:
        if not os.path.exists(state[1]):
            continue
        srcbase = state[0].rstrip("/").rsplit('/', 1)[0]
        # Find and error for absolute symlinks. We could attempt to relocate but its not
        # clear where the symlink is relative to in this context. We could add that markup
        # to sstate tasks but there aren't many of these so better just avoid them entirely.
        for walkroot, dirs, files in os.walk(state[1]):
            for file in files + dirs:
                srcpath = os.path.join(walkroot, file)
                if not os.path.islink(srcpath):
                    continue
                link = os.readlink(srcpath)
                if not os.path.isabs(link):
                    continue
                if not link.startswith(tmpdir):
                    continue
                bb.error("sstate found an absolute path symlink %s pointing at %s. Please replace this with a relative link." % (srcpath, link))
        bb.debug(2, "Preparing tree %s for packaging at %s" % (state[1], sstatebuild + state[0]))
        os.rename(state[1], sstatebuild + state[0])

    workdir = d.getVar('WORKDIR')
    sharedworkdir = os.path.join(d.getVar('TMPDIR'), "work-shared")
    for plain in ss['plaindirs']:
        pdir = plain.replace(workdir, sstatebuild)
        if sharedworkdir in plain:
            pdir = plain.replace(sharedworkdir, sstatebuild)
        bb.utils.mkdirhier(plain)
        bb.utils.mkdirhier(pdir)
        os.rename(plain, pdir)

    d.setVar('SSTATE_BUILDDIR', sstatebuild)
    d.setVar('SSTATE_INSTDIR', sstatebuild)

    if d.getVar('SSTATE_SKIP_CREATION') == '1':
        return

    sstate_create_package = ['sstate_report_unihash', 'sstate_create_package']
    if d.getVar('SSTATE_SIG_KEY'):
        sstate_create_package.append('sstate_sign_package')

    for f in (d.getVar('SSTATECREATEFUNCS') or '').split() + \
             sstate_create_package + \
             (d.getVar('SSTATEPOSTCREATEFUNCS') or '').split():
        # All hooks should run in SSTATE_BUILDDIR.
        bb.build.exec_func(f, d, (sstatebuild,))

    # SSTATE_PKG may have been changed by sstate_report_unihash
    siginfo = d.getVar('SSTATE_PKG') + ".siginfo"
    if not os.path.exists(siginfo):
        bb.siggen.dump_this_task(siginfo, d)
    else:
        try:
            os.utime(siginfo, None)
        except PermissionError:
            pass
        except OSError as e:
            # Handle read-only file systems gracefully
            if e.errno != errno.EROFS:
                raise e

    return

python sstate_eventhandler() {
    d = e.data
    writtensstate = d.getVar('SSTATE_CURRTASK')
    if not writtensstate:
        taskname = d.getVar("BB_RUNTASK")[3:]
        spec = d.getVar('SSTATE_PKGSPEC')
        swspec = d.getVar('SSTATE_SWSPEC')
        if taskname in ["fetch", "unpack", "patch", "populate_lic", "preconfigure"] and swspec:
            d.setVar("SSTATE_PKGSPEC", "${SSTATE_SWSPEC}")
            d.setVar("SSTATE_EXTRAPATH", "")
        d.setVar("SSTATE_CURRTASK", taskname)
        siginfo = d.getVar('SSTATE_PKG') + ".siginfo"
        if not os.path.exists(siginfo):
            bb.siggen.dump_this_task(siginfo, d)
        else:
            try:
                os.utime(siginfo, None)
            except PermissionError:
                pass
            except OSError as e:
                # Handle read-only file systems gracefully
                if e.errno != errno.EROFS:
                    raise e

}
