# Copyright (c) 2012-2013 LG Electronics, Inc.

# For now it's the same as DISTRO_FEATURES_LIBC_DEFAULT in
# oe-core/meta/conf/distro/include/default-distrovars.inc
WEBOS_DISTRO_FEATURES_LIBC_DEFAULT ?= "\
  ipv4 ipv6 libc-backtrace libc-big-macros libc-bsd libc-cxx-tests libc-catgets libc-charsets libc-crypt \
  libc-crypt-ufc libc-db-aliases libc-envz libc-fcvt libc-fmtmsg libc-fstab libc-ftraverse \
  libc-getlogin libc-idn libc-inet-anl libc-libm libc-libm-big libc-locales libc-locale-code \
  libc-memusage libc-nis libc-nsswitch libc-rcmd libc-rtld-debug libc-spawn libc-streams libc-sunrpc \
  libc-utmp libc-utmpx libc-wordexp libc-posix-clang-wchar libc-posix-regexp libc-posix-regexp-glibc \
  libc-posix-wchar-io"
WEBOS_DISTRO_FEATURES_LIBC = "${WEBOS_DISTRO_FEATURES_LIBC_DEFAULT}"
WEBOS_DISTRO_FEATURES_LIBC_class-nativesdk = "${WEBOS_DISTRO_FEATURES_LIBC_DEFAULT}"
