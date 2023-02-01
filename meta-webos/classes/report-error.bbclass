#
# Collects debug information in order to create error report files.
#
# Copyright (c) 2014-2023 LG Electronics, Inc.
# Copyright (C) 2013 Intel Corporation
# Author: Andreea Brandusa Proca <andreea.b.proca@intel.com>
#
# Licensed under the MIT license, see COPYING.MIT for details

# Based on oe-core/meta/classes/report-error.bbclass
# http://git.openembedded.org/openembedded-core/commit/meta/classes/report-error.bbclass?id=c195d70f35ea522854dcdd53aeae60eec6b7ad7e
# and changes from
# http://lists.openembedded.org/pipermail/openembedded-core/2014-March/090621.html
# to upload it automatically
# and more changes for uploading the build metrics (not just the logs with errors)

ERR_REPORT_DIR ?= "${LOG_DIR}/error-report"
ERR_REPORT_PORT ?= "80"

ERR_REPORT_UPLOAD_FAILURES[type] = "boolean"
ERR_REPORT_UPLOAD_FAILURES ?= "0"
ERR_REPORT_UPLOAD_ALL[type] = "boolean"
ERR_REPORT_UPLOAD_ALL ?= "0"

def errorreport_loadeventdata(e):
    logpath = e.data.getVar('ERR_REPORT_DIR')
    jsonfile = os.path.join(logpath, "error-report.txt")
    return errorreport_loaddata(jsonfile)

def errorreport_loaddata(jsonfile):
    import json
    import codecs
    try:
        with codecs.open(jsonfile, 'r', 'utf-8') as f:
            jsondata = json.load(f)
    except Exception as e:
        with codecs.open(jsonfile, 'r', 'utf-8') as f:
            data = f.read()
        bb.error("report-error.bbclass failed to load JSON data from file '%s': %s\n%s" % (jsonfile, e, data))
        # return almost empty dict so that the handler can continue to add stuff to it
        # and handler exception wont cause whole build to fail
        jsondata = {}
        jsondata['failures'] = []
        jsondata['build_started'] = '0'

    return jsondata

def errorreport_savedata(e, newdata, file):
    import json
    import codecs
    logpath = e.data.getVar('ERR_REPORT_DIR')
    jsonfile = os.path.join(logpath, file)
    with codecs.open(jsonfile, 'w', 'utf-8') as f:
        json.dump(newdata, f, indent=4, sort_keys=True)
    return jsonfile

def errorreport_get_user_info(e):
    """
    Read user info from variables or from git config
    """
    import subprocess
    username = e.data.getVar('ERR_REPORT_USERNAME')
    email = e.data.getVar('ERR_REPORT_EMAIL')
    if not username or email:
        # try to read them from git config
        try:
            username = subprocess.check_output(['git', 'config', '--get', 'user.name']).decode("utf-8").strip()
        except:
            username = "No user.name in .gitconfig"
        try:
            email = subprocess.check_output(['git', 'config', '--get', 'user.email']).decode("utf-8").strip()
        except:
            email = "No user.email in .gitconfig"
    return (username, email)

def errorreport_get_uptime():
    import subprocess
    return subprocess.check_output('uptime').decode("utf-8").strip()

def errorreport_get_hostname():
    import subprocess
    return subprocess.check_output('hostname').decode("utf-8").strip()

def errorreport_get_icecc(e):
    import subprocess
    path = e.data.getVar("WEBOS_EXTRA_PATH");
    # enabled in bitbake metadata
    icecc = e.data.getVar("ICECC_DISABLED");
    # return code from checkicecc.py
    icecc_status = "0"
    # output with scheduler and network name from checkicecc.py
    icecc_check = "OK"
    try:
        # need to extend PATH with WEBOS_EXTRA_PATH
        icecc_check = subprocess.check_output('PATH=$PATH:%s checkicecc.py' % path, shell=True).decode("utf-8").strip()
    except subprocess.CalledProcessError as e:
        icecc_status = str(e.returncode)
    return (icecc, icecc_status, icecc_check)

def errorreport_get_submit_info(e):
    """
    Read submit info from ~/.oe-send-error or ask interactively and save it there.
    """
    home = os.path.expanduser("~")
    userfile = os.path.join(home, ".oe-send-error")
    if os.path.isfile(userfile):
        with open(userfile) as g:
            username = g.readline()
            email = g.readline()
    else:
        print("Please enter your name and your email (optionally), they'll be saved in the file you send.")
        username = raw_input("Name: ")
        email = raw_input("E-mail (not required): ")
        server = raw_input("Server: ")
        port = raw_input("Port: ")
        if len(username) > 0 and len(username) < 50:
            with open(userfile, "w") as g:
                g.write(username + "\n")
                g.write(email + "\n")
                g.write(server + "\n")
                g.write(port + "\n")
        else:
            print("Invalid inputs, try again.")
            return errorreport_get_submit_info()
        return (username, email, server, port)

def errorreport_get_gitmirror():
    import subprocess
    gitmirror = "N/A"
    try:
        # number of "redirects"
        gitmirror = subprocess.check_output(r'git config --get-regexp "^url\..*\.insteadof" | wc -l', shell=True).decode("utf-8")
        # one example to know what mirror was used
        try:
            gitmirror += subprocess.check_output(r'git config --get-regexp "^url\..*\.insteadof" "git://github.com/openembedded"', shell=True).decode("utf-8")
        except:
            # git config returns error return code when option doesn't exist
            gitmirror += "\nNo mirror for git://github.com/openembedded\n"
            # some jenkins slaves and developers have .gitconfig which redirects whole github.com (and doesn't have entry for git://github.com/openembedded)
            try:
                gitmirror += subprocess.check_output(r'git config --get-regexp "^url\..*\.insteadof" "git://github.com"', shell=True).decode("utf-8")
            except:
                # git config returns error return code when option doesn't exist
                gitmirror += "\nNo mirror for git://github.com"
            pass
    except Exception as e:
        bb.error("Failed to check gitmirror: %s" % e)
        pass # keep "N/A"
    return gitmirror

def errorreport_checkmirror(name, url):
    import os
    if url.startswith('file://'):
        pathstr = url[7:]
        if not os.path.isdir(pathstr):
            return "\n%s parameter '%s' points to non-existent directory" % (name, url)
        else:
            entries = os.listdir(pathstr)
            if not entries:
                return "\n%s parameter '%s' points to empty directory, did you forgot to mount it?" % (name, url)
            else:
                return "\n%s points to directory with %s entries" % (name, len(entries))
    return ""

def errorreport_get_sstatemirror(e):
    sstatemirror = e.data.getVar("SSTATE_MIRRORS")
    # assume simplest form:
    # file://.* file:///oe_cache/sstate-cache/PATH \n
    # errorreport_checkmirror expects path starting with file:// but without /PATH suffix
    if sstatemirror:
        import re
        for line in sstatemirror.split("\\n"):
            m = re.match(r"^\s*file://.* (file://.*)/PATH.*$", line)
            if m and m.group(1):
                sstatemirror += errorreport_checkmirror("sstatemirror", m.group(1))
    return sstatemirror

def errorreport_get_premirror(e):
    premirror = e.data.getVar("PREMIRRORS")
    if premirror:
        url = e.data.getVar("SOURCE_MIRROR_URL")
        if url:
            premirror += errorreport_checkmirror("premirror", url)
    return premirror

def errorreport_get_hw_specs():
    import subprocess

    ram = subprocess.check_output('free -g | tail -n 3', shell=True).decode("utf-8")
    cpu = subprocess.check_output('cat /proc/cpuinfo | grep "^model name" | sed "s/  *$//g; s/^model name[^:]*: //g" | sort -u', shell=True).decode("utf-8")
    # total number of threads
    cpu += subprocess.check_output('cat /proc/cpuinfo | grep "^processor" | wc -l', shell=True).decode("utf-8")
    # total number of cores
    cpu += subprocess.check_output('cat /proc/cpuinfo | egrep "core id|physical id" | tr -d "\n" | sed "s/physical/\\nphysical/g" | grep -v ^$ | sort | uniq | wc -l', shell=True).decode("utf-8")
    # total number of CPUs
    cpu += subprocess.check_output('cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l', shell=True).decode("utf-8")
    disk = subprocess.check_output('df -h . | tail -n 1', shell=True).decode("utf-8")
    return (ram, cpu, disk)

def errorreport_get_sstate_reuse_ratio(e):
    import os.path

    bsdir = e.data.expand("${BUILDSTATS_BASE}/${BUILDNAME}")
    if not os.path.exists(bsdir):
        return

    sstate_ps, no_sstate_ps, sstate_pd, no_sstate_pd = set(), set(), set(), set()
    all_tasks = []

    for pf in os.listdir(bsdir):
        taskdir = os.path.join(bsdir, pf)
        if not os.path.isdir(taskdir):
            continue

        tasks = os.listdir(taskdir)
        if 'do_populate_sysroot' in tasks:
            no_sstate_ps.add(pf)
        elif 'do_populate_sysroot_setscene' in tasks:
            sstate_ps.add(pf)
        if 'do_packagedata' in tasks:
            no_sstate_pd.add(pf)
        elif 'do_packagedata_setscene' in tasks:
            sstate_pd.add(pf)
        all_tasks.append(tasks)

    return (all_tasks, sstate_ps, no_sstate_ps, sstate_pd, no_sstate_pd)

def errorreport_get_top_20_tasks(e, jsondata):
    import os.path, subprocess

    bsdir = e.data.expand("${BUILDSTATS_BASE}/${BUILDNAME}")
    # it can be empty (with only build_stat file), so check that there are at least 2 entries before calling grep
    if len(os.listdir(bsdir)) > 1:
        top20 = subprocess.check_output(r"grep -R '^.*Elapsed time' %s/ | sed 's/^\(.*\)\/\([^\/]*\/[^\/]*\):Elapsed time: \([^ ]*\) .*$/\3 \2/g' | sort -n | tail -n 20" % bsdir, shell=True).decode("utf-8")
        for line in top20.splitlines():
            bsdata={}
            (time, task) = line.split()
            bsdata['time'] = time
            if len(task) > 200:
                task = task[:197] + "..."
            bsdata['task'] = task
            jsondata['top20tasks'].append(bsdata)
    return jsondata

def errorreport_get_mcf_status(e):
    import os.path
    mcfstatus = os.path.join(e.data.getVar('TOPDIR'), 'mcf.status')
    if os.path.isfile(mcfstatus):
        with open(mcfstatus) as f:
            content = f.read()
        return content;
    return "N/A"

def errorreport_get_time_log(d):
    import os.path
    timefile = os.path.join(d.getVar('TOPDIR'), 'time.txt')
    trigger_time = "0"
    if os.path.isfile(timefile):
        trigger_line = "TIME: jenkins trigger: "
        time_log = ""
        with open(timefile) as f:
            for l in f.readlines():
                if l.startswith(trigger_line):
                    trigger_time = l[len(trigger_line):]
                time_log += l
            return (trigger_time, time_log)
    return (trigger_time, "N/A")

def errorreport_senddata(e, jsonfile):
    """
    From scripts/send-error-report to automate report submissions.
    """

    import os, json

    if os.path.isfile(jsonfile):
        server = e.data.getVar('ERR_REPORT_SERVER')
        port = e.data.getVar('ERR_REPORT_PORT')
        bb.note("Uploading the report to %s:%s" % (server, port))

        try:
            jsondata = errorreport_loaddata(jsonfile)
            if not jsondata['username'] or not server:
                (username, email, server, port) = errorreport_get_submit_info(e)
                jsondata['username'] = username.strip()
                jsondata['email'] = email.strip()
            data = json.dumps(jsondata, indent=4, sort_keys=True)
        except Exception as e:
            bb.error("Invalid JSON data in file %s: %s" % (jsonfile, e))
            return

        try:
            url = "http://%s:%s/ClientPost/" % (server, port)
            import urllib.request, urllib.error, urllib.parse
            version = "0.3"
            headers={'Content-type': 'application/json', 'User-Agent': "send-error-report/"+version}

            uparams = urllib.parse.urlencode({'data': data}).encode('ascii')
            req = urllib.request.Request(url, headers=headers)
            try:
                with urllib.request.urlopen(req, uparams) as response:
                    if response.getcode() == 200:
                        bb.note("Report submitted: %s %s" % (response.getcode(), response.read().decode('utf-8')))
                    else:
                        bb.warn("There was a problem submiting your data: %s" % response.read())
            except urllib.error.HTTPError as e:
                bb.warn("There was a problem submiting your data: %s" % e.reason)
        except Exception as e:
            bb.warn("Server connection failed %s" % e)

    else:
        bb.error("No data file found.")

python errorreport_handler () {
        import json, time
        import codecs

        logpath = e.data.getVar('ERR_REPORT_DIR')
        datafile = os.path.join(logpath, "error-report.txt")
        if isinstance(e, bb.event.BuildStarted):
            bb.utils.mkdirhier(logpath)
            jsondata = {}
            machine = e.data.getVar("MACHINE")
            jsondata['version'] = '1'
            jsondata['machine'] = machine
            jsondata['build_sys'] = e.data.getVar("BUILD_SYS")
            jsondata['nativelsb'] = e.data.getVar("NATIVELSBSTRING")
            jsondata['distro'] = e.data.getVar("DISTRO")
            jsondata['target_sys'] = e.data.getVar("TARGET_SYS")
            jsondata['sstatemirror'] = errorreport_get_sstatemirror(e)
            jsondata['premirror'] = errorreport_get_premirror(e)
            gitmirror = errorreport_get_gitmirror()
            if len(gitmirror) > 2000:
                gitmirror = gitmirror[:1997] + "..."
            jsondata['gitmirror'] = gitmirror
            (ram, cpu, disk) = errorreport_get_hw_specs()
            jsondata['ram'] = ram
            jsondata['cpu'] = cpu
            jsondata['disk'] = disk
            jsondata['mcf'] = errorreport_get_mcf_status(e)
            jsondata['bb_number_threads'] = e.data.getVar("BB_NUMBER_THREADS")
            jsondata['parallel_make'] = e.data.getVar("PARALLEL_MAKE")
            jsondata['icecc_parallel_make'] = e.data.getVar("ICECC_PARALLEL_MAKE")
            (icecc, icecc_status, icecc_check) = errorreport_get_icecc(e)
            jsondata['icecc'] = icecc
            jsondata['icecc_status'] = icecc_status
            jsondata['icecc_check'] = icecc_check
            jsondata['load_started'] = errorreport_get_uptime()
            jsondata['failures'] = []
            jsondata['top20tasks'] = []
            targets = ' '.join(e.getPkgs())
            # targets list for bitbake world is very long (over 13000 chars), upload only first 2000
            if len(targets) > 2000:
                targets = targets[:1997] + "..."
            jsondata['target'] = targets
            jsondata['branch_commit'] = base_detect_branch(e.data) + ": " + base_detect_revision(e.data)
            jsondata['build_started'] = round(time.time())
            (username, email) = errorreport_get_user_info(e)
            jsondata['username'] = username.strip()
            jsondata['email'] = email.strip()
            jsondata['hostname'] = errorreport_get_hostname()
            jsondata['distro_version'] = e.data.getVar("DISTRO_VERSION")
            jsondata['manufacturing_version'] = e.data.getVar("WEBOS_DISTRO_MANUFACTURING_VERSION")
            jsondata['release_codename'] = e.data.getVar("WEBOS_DISTRO_RELEASE_CODENAME")
            jsondata['build_id'] = e.data.getVar("WEBOS_DISTRO_BUILD_ID")
            lock = bb.utils.lockfile(datafile + '.lock')
            errorreport_savedata(e, jsondata, "error-report.txt")
            bb.utils.unlockfile(lock)

        elif isinstance(e, bb.build.TaskFailed):
            task = e.task
            taskdata={}
            log = e.data.getVar('BB_LOGFILE')
            taskdata['package'] = e.data.expand("${PN}")
            version = e.data.expand("${EXTENDPE}${PV}-${PR}")
            if len(version) > 200:
                version = version[:197] + "..."
            taskdata['version'] = version
            taskdata['task'] = task
            if log:
                try:
                    logFile = codecs.open(log, 'r', 'utf-8')
                    logdata = logFile.read()

                    # Replace host-specific paths so the logs are cleaner
                    for d in ("TOPDIR", "TMPDIR"):
                        s = e.data.getVar(d)
                        if s:
                            logdata = logdata.replace(s, d)

                    logFile.close()
                except:
                    logdata = "Unable to read log file"

            else:
                logdata = "No Log"
            # server will refuse failures longer than param specified in project.settings.py
            # MAX_UPLOAD_SIZE = "5242880"
            # use lower value, because 650 chars can be spent in task, package, version
            max_logdata_size = 5242000
            # upload last max_logdata_size characters
            if len(logdata) > max_logdata_size:
                logdata = "..." + logdata[-max_logdata_size:]
            taskdata['log'] = logdata
            lock = bb.utils.lockfile(datafile + '.lock')
            jsondata = errorreport_loadeventdata(e)
            jsondata['failures'].append(taskdata)
            errorreport_savedata(e, jsondata, "error-report.txt")
            bb.utils.unlockfile(lock)

        elif isinstance(e, bb.event.BuildCompleted):
            (all_tasks, sstate_ps, no_sstate_ps, sstate_pd, no_sstate_pd) = errorreport_get_sstate_reuse_ratio(e)
            lock = bb.utils.lockfile(datafile + '.lock')
            jsondata = errorreport_loadeventdata(e)
            bb.utils.unlockfile(lock)
            jsondata['all_tasks'] = len(all_tasks)
            jsondata['sstate'] = "%s" % len(sstate_ps)
            jsondata['scratch'] = "%s" % len(no_sstate_ps)
            jsondata['sstate_pd'] = "%s" % len(sstate_pd)
            jsondata['scratch_pd'] = "%s" % len(no_sstate_pd)
            jsondata['load_completed'] = errorreport_get_uptime()
            (trigger_time, time_log) = errorreport_get_time_log(e.data)
            jsondata['trigger_time'] = trigger_time
            # send only last 2000 chars if it's longer
            if len(time_log) > 2000:
                time_log = "..." + time_log[-1997:]
            jsondata['time_log'] = time_log
            jsondata['build_completed'] = round(time.time())
            jsondata['build_time'] = int(jsondata['build_completed']) - int(jsondata['build_started'])
            jsondata = errorreport_get_top_20_tasks(e, jsondata)
            upload_failures = oe.data.typed_value('ERR_REPORT_UPLOAD_FAILURES', e.data)
            upload_all = oe.data.typed_value('ERR_REPORT_UPLOAD_ALL', e.data)
            failures = jsondata['failures']
            if failures or (upload_all and (jsondata['all_tasks'] > 100 or jsondata['build_time'] > 300)):
                filename = "error_report_" + e.data.getVar("BUILDNAME") + ".txt"
                jsonfile = errorreport_savedata(e, jsondata, filename)
                bb.note("The report of this build is stored in: %s" % (jsonfile))
                if upload_all or (failures and upload_failures):
                    errorreport_senddata(e, jsonfile)
}

addhandler errorreport_handler
errorreport_handler[eventmask] = "bb.event.BuildStarted bb.event.BuildCompleted bb.build.TaskFailed"
