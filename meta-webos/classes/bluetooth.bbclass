# Empty .bbclass just for compatibility with meta-qt5[warrior]
# e.g. qtsystems inherits bluetooth.bbclass and causes:
# ERROR: ParseError at meta-qt5/recipes-qt/qt5/qtsystems_git.bb:15: Could not inherit file classes/bluetooth.bbclass
# bluetooth.bbclass was removed from oe-core in:
#   commit dcf889e93401f7c4de0055d53271eacc3882eccc
#   Author: Adrian Bunk <bunk@stusta.de>
#   Date:   Fri Jul 12 10:48:13 2019 +0300
#   Subject: meta: Remove remnants of bluez4 support
# and the inherit from meta-qt5[master] in:
# https://github.com/meta-qt5/meta-qt5/pull/217
# but we're using oe-core[master] with meta-qt5[warrior]
