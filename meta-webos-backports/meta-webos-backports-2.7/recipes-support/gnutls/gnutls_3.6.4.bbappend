# Copyright (c) 2019 LG Electronics, Inc.

# Backported from:
# 712c78984c gnutls: Use ca-certificates as default trust store file

EXTRA_OECONF += "--with-default-trust-store-file=/etc/ssl/certs/ca-certificates.crt"
