# Copyright (c) 2023 LG Electronics, Inc.

export CCACHE_STATSLOG = "${WORKDIR}/ccache_stats.log"

CCACHE_STATS = "${TOPDIR}/ccache_stats.json"

def getHit(stdout: str):
    """
    stdout is passed under the form.
    Returns only the hit rate for lines starting with Hits:
    Summary:
        Hits:           3819 / 4455 (85.72 %)
        ....
    """
    import re
    for line in stdout.split('\n'):
        l = line.strip()
        if l.startswith('Hits:'):
            return l.split('Hits:')[-1].strip()
    return "-"

python do_ccache_stats() {
    if not bb.data.inherits_class('ccache',d):
        bb.note("%s doesn't inherit ccache" % d.getVar('PN'))
        bb.note("Skip ccache hit_check")
        return

    import json
    import re
    stat_file = d.getVar('CCACHE_STATSLOG')
    if not os.path.exists(stat_file):
        bb.note("%s is not exist." % stat_file)
        bb.note("Skip ccache hit_check")
        return
    try:
        stdout, _ = bb.process.run('ccache --show-log-stats')
    except Exception as err:
        bb.warn("ccache show stat error: %s" % err)
        return
    else:
        ccache_stats_json = d.getVar('CCACHE_STATS')
        pn = d.getVar('PN')
        hit_rate = getHit(stdout)

        lock = bb.utils.lockfile(ccache_stats_json + '.lock')
        data_json = dict()
        if os.path.exists(ccache_stats_json):
            with open(ccache_stats_json, 'r') as f:
                data_json = json.load(f)
        with open(ccache_stats_json, 'w') as f:
            data_json[pn] = hit_rate
            json.dump(data_json,f,indent='\t',sort_keys=True)
        bb.utils.unlockfile(lock)

    # clean statfile
    bb.utils.remove(stat_file)
}
addtask do_ccache_stats after do_compile before do_build