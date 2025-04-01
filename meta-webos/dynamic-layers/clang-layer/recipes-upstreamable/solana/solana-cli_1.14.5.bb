# Copyright (c) 2022-2025 LG Electronics, Inc.

require solana.inc

CARGO_SRC_DIR = "cli"
SUMMARY = "Solana CLI Tool suites"
HOMEPAGE = "https://github.com/solana-labs/solana"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/894440
# ERROR: QA Issue: File /usr/bin/.debug/solana in package solana-cli-dbg contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
