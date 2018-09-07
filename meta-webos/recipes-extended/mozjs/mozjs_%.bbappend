# Copyright (c) 2018 LG Electronics, Inc.

EXTENDPRAUTO_append = "webos1"
EXTRA_OECONF += "--with-nspr-cflags='-I${STAGING_INCDIR}/nspr -I${STAGING_INCDIR}/nss3'"
