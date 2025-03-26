# Copyright (c) 2019-2025 LG Electronics, Inc.

SMACK_RULES_GEN ?= "${exec_prefix}/share/smack/smack_rules_gen"
APP_DIR ?= "${webos_applicationsdir}"
APP_RULES_DIR ?= "${sysconfdir}/smack/accesses.d"

do_smack_labeling[dirs] = "${IMAGE_ROOTFS}"
fakeroot python do_smack_labeling() {

    import errno
    import glob
    import json

    if not bb.utils.contains('DISTRO_FEATURES', 'smack', True, False, d):
        return

    bb.note('Smack labeling...')

    # Expanded Smack variables
    ROOTFS = d.getVar('IMAGE_ROOTFS')
    LABELS_FILE = d.getVar('SMACK_LABELS_FILE')
    SMACK_RULES_GEN = ROOTFS + d.expand(d.getVar('SMACK_RULES_GEN'))
    APP_DIR = ROOTFS + d.expand(d.getVar('APP_DIR'))
    APP_RULES_DIR = ROOTFS + d.expand(d.getVar('APP_RULES_DIR'))


    def lgetxattr(f, attr):
        try:
            value = os.getxattr(f, attr, follow_symlinks=False)
            return value.decode('utf8')
        except OSError as ex:
            if ex.errno == errno.ENODATA:
                return None


    def lsetxattr(f, attr, value):
        os.setxattr(f, attr.encode('utf8'), value.encode('utf8'), follow_symlinks=False)


    def lremovexattr(f, attr):
        os.removexattr(f, attr, follow_symlinks=False)


    # Fix transmutation with recursive function
    def fix_transmute(path, deflabel, deftransmute):
        isrealdir = os.path.isdir(path) and not os.path.islink(path)
        curlabel = lgetxattr(path, 'security.SMACK64')
        transmute = lgetxattr(path, 'security.SMACK64TRANSMUTE') == 'TRUE'

        if not curlabel:
            lsetxattr(path, 'security.SMACK64', deflabel)
            if not transmute and deftransmute and isrealdir:
                lsetxattr(path, 'security.SMACK64TRANSMUTE', 'TRUE')

        if isrealdir:
            if transmute:
                deflabel = lgetxattr(path, 'security.SMACK64')
                deftransmute = True
                if deflabel is None:
                    raise RuntimeError('%s: transmuting directory without Smack label' % path)
            elif curlabel:
               deflabel = '_'
               deftransmute = False

            for entry in os.listdir(path):
                fix_transmute(os.path.join(path, entry), deflabel, deftransmute)


    # Set labels in accordance with LABELS_FILE
    def labeling(path, PARAM_DICT):
        isrealdir = os.path.isdir(path) and not os.path.islink(path)
        isrealfile = os.path.isfile(path) and not os.path.islink(path)

        # Label files but not symlinks
        if isrealfile:
            if PARAM_DICT['-a']:
                lsetxattr(path, 'security.SMACK64', PARAM_DICT['-a'])
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64', PARAM_DICT['-a']))
            if PARAM_DICT['-e']:
                lsetxattr(path, 'security.SMACK64EXEC', PARAM_DICT['-e'])
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64EXEC', PARAM_DICT['-a']))
            if PARAM_DICT['-m']:
                lsetxattr(path, 'security.SMACK64MMAP', PARAM_DICT['-m'])
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64MMAP', PARAM_DICT['-a']))
            if PARAM_DICT['-A']:
                lremovexattr(path, 'security.SMACK64')
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64', 'REMOVE'))
            if PARAM_DICT['-E']:
                lremovexattr(path, 'security.SMACK64EXEC')
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64EXEC', 'REMOVE'))
            if PARAM_DICT['-M']:
                lremovexattr(path, 'security.SMACK64MMAP')
                bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64MMAP', 'REMOVE'))
        # Label directories but not symlinks
        if isrealdir:
            if PARAM_DICT['-mask']:
                for mask in PARAM_DICT['-mask'].split(','):
                    for entry in glob.glob(path + '/' + mask):
                        # Check if file to avoid recursive
                        if os.path.isfile(entry):
                            labeling(entry, PARAM_DICT)
                # Search subdirectories
                if PARAM_DICT['-r']:
                    for entry in next(os.walk(path))[1]:
                        labeling(os.path.join(path, entry), PARAM_DICT)
            else:
                if PARAM_DICT['-a']:
                    lsetxattr(path, 'security.SMACK64', PARAM_DICT['-a'])
                    bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64', PARAM_DICT['-a']))
                if PARAM_DICT['-t']:
                    lsetxattr(path, 'security.SMACK64TRANSMUTE', 'TRUE')
                    bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64TRANSMUTE', 'TRUE'))
                if PARAM_DICT['-A']:
                    lremovexattr(path, 'security.SMACK64')
                    bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64', 'REMOVE'))
                if PARAM_DICT['-T']:
                    lremovexattr(path, 'security.SMACK64TRANSMUTE')
                    bb.note('(path, label, value) = (%s, %s, %s)' % (path, 'security.SMACK64TRANSMUTE', 'REMOVE'))
                if PARAM_DICT['-r']:
                    for entry in os.listdir(path):
                        labeling(os.path.join(path, entry), PARAM_DICT)


    # Read the LABELS_FILE and set labels
    with open(LABELS_FILE, 'r') as f:
        for line in f.readlines():
            # skip blank lines
            if line.strip():
                # skip comments
                if line[0] == '#':
                    continue
                else:
                    first_item, *items = line.split()
                    path = ROOTFS + d.expand(first_item)
                    arg = None
                    PARAM_DICT = {'-a': None, '-e': None, '-m': None,
                        '-r': False, '-t': False, '-mask': None,
                        '-A': False, '-E': False, '-M': False, '-T': False}
                    for item in items:
                        # Attributes with value
                        if arg is None:
                            arg = item
                        elif arg == '-a':
                            PARAM_DICT['-a'] = item
                            arg = None
                        elif arg == '-e':
                            PARAM_DICT['-e'] = item
                            arg = None
                        elif arg == '-m':
                            PARAM_DICT['-m'] = item
                            arg = None
                        elif arg == '-mask':
                            PARAM_DICT['-mask'] = item
                            arg = None
                        else:
                            raise RuntimeError('%s: wrong argument' % arg)

                        # Attributes without value
                        if arg == '-r':
                            PARAM_DICT['-r'] = True
                            arg = None
                        if arg == '-t':
                            PARAM_DICT['-t'] = True
                            arg = None
                        if arg == '-A':
                            PARAM_DICT['-A'] = True
                            arg = None
                        if arg == '-E':
                            PARAM_DICT['-E'] = True
                            arg = None
                        if arg == '-M':
                            PARAM_DICT['-M'] = True
                            arg = None
                        if arg == '-T':
                            PARAM_DICT['-T'] = True
                            arg = None

                    labeling(path, PARAM_DICT)


    if os.path.exists(APP_DIR):
        for entry in os.listdir(APP_DIR):
            app_json_path = os.path.join(APP_DIR, entry, "appinfo.json")
            if os.path.exists(app_json_path):
                with open(app_json_path, "r") as app_json:
                    app_type = json.load(app_json)

                if app_type["type"] == "web":
                    PARAM_DICT = {'-a': "webOS::WAM", '-e': None, '-m': None, '-r': True,'-t': True, '-mask': None,
                                  '-A': False, '-E': False, '-M': False, '-T': False}
                    labeling(os.path.join(APP_DIR, entry), PARAM_DICT)
                elif app_type["type"] != "stub":
                    PARAM_DICT = {'-a': "webOS::App::" + entry, '-e': "webOS::App::" + entry, '-m': None, '-r': True,
                                  '-t': True, '-mask': None, '-A': False, '-E': False, '-M': False, '-T': False}
                    labeling(os.path.join(APP_DIR, entry), PARAM_DICT)

                    app_rule_file = os.path.join(APP_RULES_DIR, entry)
                    if not os.path.exists(app_rule_file):
                        os.system('%s -b %s -o %s' % (SMACK_RULES_GEN, entry, app_rule_file))

    fix_transmute(ROOTFS, '_', False)

    bb.note('Smack labeling done.')
}
