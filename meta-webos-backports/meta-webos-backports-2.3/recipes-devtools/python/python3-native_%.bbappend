# backport from oe-core 462ccb35a5de32b52ddb733d1868df6ac5426f20
# needed for python3-pyparsing-native

RPROVIDES += "\
    python3-datetime-native \
    python3-enum-native \
    python3-terminal-native \
"

# and from oe-core 800753069f667cd1664d70b3779150c467e3b3fe
RPROVIDES += "\
    python3-debugger-native \
    python3-stringold-native \
"
