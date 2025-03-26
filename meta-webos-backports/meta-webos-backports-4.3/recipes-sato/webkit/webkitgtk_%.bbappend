# Backport changes from:
# https://lists.openembedded.org/g/openembedded-core/message/187251
# https://git.openembedded.org/openembedded-core/commit/?id=9179fdfb4c0802b3744a9730a1f906110b3e5538

# And for armv7* don't enable it for softfp, because after:
# https://github.com/WebKit/WebKit/commit/a2ec4ef1997d6fafa6ffc607bffb54e76168a918
# https://bugs.webkit.org/show_bug.cgi?id=242172
# softfp armv7* fails because WEBASSEMBLY is left enabled by default and JIT gets
# explicitly disabled causing:
# http://errors.yoctoproject.org/Errors/Details/734587/
# PR was sent upstream, but the end result is the same both JIT and WEBASSEMBLY disabled
# https://github.com/WebKit/WebKit/pull/17447
EXTRA_OECMAKE:append:armv7a = " -DENABLE_JIT=${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'ON', 'OFF', d)}"
EXTRA_OECMAKE:append:armv7r = " -DENABLE_JIT=${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'ON', 'OFF', d)}"
EXTRA_OECMAKE:append:armv7ve = " -DENABLE_JIT=${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'ON', 'OFF', d)}"
