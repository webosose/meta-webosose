# Copyright (c) 2017 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"

# ERROR: gnutls-3.5.3-r0 do_package_qa: QA Issue: ELF binary 'gnutls/3.5.3-r0/packages-split/gnutls/usr/lib/libgnutls.so.30.9.0' has relocations in .text [textrel]
INSANE_SKIP_${PN} += "textrel"
