# Copyright (c) 2023-2025 LG Electronics, Inc.

# Restricted to only these 2 MACHINEs by COMPATIBLE_MACHINE
SOLANA ?= ""
SOLANA:aarch64 = "solana-cli solana-keygen solana-program-library sugar"
SOLANA:x86-64 = "solana-cli solana-keygen solana-program-library sugar"

RDEPENDS:${PN}:append:webos = " \
    ${SOLANA} \
"
