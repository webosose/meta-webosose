#!/usr/bin/env python3

# Copyright (c) 2014-2023 LG Electronics, Inc.
"""checkicecc

Script to check the icecc installation and running status.
Requires no input. Returns scheduler name, network name and
the following error code:
    code   meaning
    ---------------------------------------------------------
      0    OK.
      1    Warning: icecc daemon is not running.
      2    Error: icecc daemon is not configured correctly.
      3    Warning: icecc is not installed.
      4    Error: incorrect argument.

optional arguments:
      -h --help     Display help
      -v --version  Display script version

"""
__version__ = "2.0.0"

import subprocess,getopt,sys

def usage():
    print(__doc__)


def _icecc_installed():
    try:
        outstring = subprocess.check_output('dpkg -l | grep icecc' ,
                                        shell=True,
                                        universal_newlines=True).split()
        for idx in range(len(outstring)):
            if outstring[idx] == "ii":
                if outstring[idx+1] == 'icecc':
                    return True
        return False
    except:
        return False

def checkiceccstatus(argv):

    try:
        options, args = getopt.getopt(argv, "hv", ["help","version"])
    except getopt.GetoptError:
        usage()
        sys.exit(4)

    for var,val in options:
        if var in ('-h', '--help'):
            usage()
            sys.exit(0)
        if var in ('-v', '--version'):
            print("Version %s" % __version__ )
            sys.exit(0)

    daemon = ''
    scheduler = ''
    netname = ''
    err = 0


    if _icecc_installed():
        outstring = subprocess.check_output('ps aux | grep iceccd ',
                                            shell=True,
                                            universal_newlines=True).split()
        for idx in range(len(outstring)-1):
            if outstring[idx].find('iceccd') != -1 :
                if outstring[idx+1] == "-d":
                    daemon = outstring[idx]
                    for jdx in range(idx,len(outstring)):
                        if outstring[jdx] == '-s':
                            scheduler = outstring[jdx+1]
                        if outstring[jdx] == '-n':
                            netname = outstring[jdx+1]
                    break

        if daemon == '':
            print ( "Warning: icecc daemon is not running.")
            err = 1
            return scheduler, netname, err
        elif scheduler == '' or netname == '':
            print ( "Error: icecc daemon is not configured correctly.")
            err = 2
            return scheduler, netname, err
    else:
        print( "Warning: icecc is not installed.")
        err = 3
        return scheduler, netname, err

    return scheduler, netname, err

if __name__ == "__main__":
    scheduler, netname, err = checkiceccstatus(sys.argv[1:])
    print( "scheduler = %s, netname = %s, err = %s" % (scheduler, netname, err))
    if err != 0:
        sys.exit(err)
