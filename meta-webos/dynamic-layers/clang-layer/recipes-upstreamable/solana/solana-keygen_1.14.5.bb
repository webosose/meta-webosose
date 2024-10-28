# Copyright (c) 2022-2024 LG Electronics, Inc.

require solana.inc

CARGO_SRC_DIR = "keygen"
SUMMARY = "Solana Keygen Tool suites"
HOMEPAGE = "https://github.com/solana-labs/solana"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894438
# ERROR: QA Issue: File /usr/bin/.debug/solana-keygen in package solana-keygen-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
