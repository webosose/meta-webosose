# Copyright (c) 2018-2025 LG Electronics, Inc.

SUMMARY = "A module for nodejs usocket"
HOMEPAGE = "https://github.com/jhs67/usocket#readme"
SECTION = "webos/nodejs/module"
LICENSE = "BSD-2-Clause & ISC & MIT & MIT-0"
LICENSE:${PN} = "ISC"
LICENSE:${PN}-isaacs-cliui = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-ansi-regex = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-ansi-styles = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-emoji-regex = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-string-width = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-strip-ansi = "ISC & MIT"
LICENSE:${PN}-isaacs-cliui-wrap-ansi = "ISC & MIT"
LICENSE:${PN}-npmcli-agent = "MIT & ISC"
LICENSE:${PN}-npmcli-fs = "MIT & ISC"
LICENSE:${PN}-pkgjs-parseargs = "Apache-2.0"
LICENSE:${PN}-abbrev = "ISC | MIT"
LICENSE:${PN}-agent-base = "MIT"
LICENSE:${PN}-aggregate-error = "MIT"
LICENSE:${PN}-ansi-regex = "MIT"
LICENSE:${PN}-ansi-styles = "MIT"
LICENSE:${PN}-balanced-match = "MIT"
LICENSE:${PN}-bindings = "MIT"
LICENSE:${PN}-cacache = "ISC"
LICENSE:${PN}-cacache-brace-expansion = "MIT"
LICENSE:${PN}-cacache-glob = "ISC"
LICENSE:${PN}-cacache-minimatch = "ISC"
LICENSE:${PN}-chownr = "ISC"
LICENSE:${PN}-clean-stack = "MIT"
LICENSE:${PN}-color-convert = "MIT"
LICENSE:${PN}-color-name = "MIT"
LICENSE:${PN}-cross-spawn = "MIT"
LICENSE:${PN}-debug = "MIT"
LICENSE:${PN}-debug-ms = "MIT"
LICENSE:${PN}-eastasianwidth = "MIT"
LICENSE:${PN}-emoji-regex = "MIT"
LICENSE:${PN}-encoding = "MIT-0"
LICENSE:${PN}-env-paths = "MIT"
LICENSE:${PN}-err-code = "MIT"
LICENSE:${PN}-exponential-backoff = "Apache-2.0"
LICENSE:${PN}-file-uri-to-path = "MIT"
LICENSE:${PN}-foreground-child = "ISC"
LICENSE:${PN}-fs-minipass = "ISC"
LICENSE:${PN}-graceful-fs = "ISC"
LICENSE:${PN}-http-cache-semantics = "BSD-2-Clause"
LICENSE:${PN}-http-proxy-agent = "MIT"
LICENSE:${PN}-https-proxy-agent = "MIT"
LICENSE:${PN}-iconv-lite = "MIT"
LICENSE:${PN}-imurmurhash = "MIT"
LICENSE:${PN}-indent-string = "MIT"
LICENSE:${PN}-ip = "MIT"
LICENSE:${PN}-is-fullwidth-code-point = "MIT"
LICENSE:${PN}-is-lambda = "MIT"
LICENSE:${PN}-isexe = "ISC"
LICENSE:${PN}-jackspeak = "BlueOak-1.0.0"
LICENSE:${PN}-lru-cache = "ISC"
LICENSE:${PN}-make-fetch-happen = "ISC"
LICENSE:${PN}-minipass = "ISC"
LICENSE:${PN}-minipass-collect = "ISC"
LICENSE:${PN}-minipass-fetch = "MIT"
LICENSE:${PN}-minipass-flush = "ISC"
LICENSE:${PN}-minipass-flush-minipass = "ISC"
LICENSE:${PN}-minipass-pipeline = "ISC"
LICENSE:${PN}-minipass-pipeline-minipass = "ISC"
LICENSE:${PN}-minipass-sized = "ISC"
LICENSE:${PN}-minipass-sized-minipass = "ISC"
LICENSE:${PN}-minizlib = "MIT"
LICENSE:${PN}-minizlib-minipass = "MIT"
LICENSE:${PN}-mkdirp = "MIT"
LICENSE:${PN}-nan = "MIT"
LICENSE:${PN}-negotiator = "MIT"
LICENSE:${PN}-node-gyp = "Apache-2.0 & BSD-2-Clause & ISC & MIT"
LICENSE:${PN}-node-gyp-brace-expansion = "MIT"
LICENSE:${PN}-node-gyp-glob = "MIT"
LICENSE:${PN}-node-gyp-isexe = "MIT"
LICENSE:${PN}-node-gyp-minimatch = "MIT"
LICENSE:${PN}-node-gyp-which = "MIT"
LICENSE:${PN}-nopt = "ISC"
LICENSE:${PN}-p-map = "MIT"
LICENSE:${PN}-path-key = "MIT"
LICENSE:${PN}-path-scurry = "BlueOak-1.0.0"
LICENSE:${PN}-proc-log = "ISC"
LICENSE:${PN}-promise-retry = "MIT"
LICENSE:${PN}-retry = "MIT"
LICENSE:${PN}-safer-buffer = "MIT"
LICENSE:${PN}-semver = "ISC"
LICENSE:${PN}-semver-lru-cache = "ISC"
LICENSE:${PN}-shebang-command = "MIT"
LICENSE:${PN}-shebang-regex = "MIT"
LICENSE:${PN}-signal-exit = "ISC"
LICENSE:${PN}-smart-buffer = "MIT"
LICENSE:${PN}-socks = "MIT"
LICENSE:${PN}-socks-proxy-agent = "MIT"
LICENSE:${PN}-ssri = "ISC"
LICENSE:${PN}-string-width = "MIT"
LICENSE:${PN}-string-width-cjs = "MIT"
LICENSE:${PN}-strip-ansi = "MIT"
LICENSE:${PN}-strip-ansi-cjs = "MIT"
LICENSE:${PN}-tar = "ISC"
LICENSE:${PN}-tar-fs-minipass = "ISC"
LICENSE:${PN}-tar-fs-minipass-minipass = "ISC"
LICENSE:${PN}-tar-minipass = "ISC"
LICENSE:${PN}-unique-filename = "ISC"
LICENSE:${PN}-unique-slug = "ISC"
LICENSE:${PN}-which = "ISC"
LICENSE:${PN}-wrap-ansi-cjs = "MIT"
LICENSE:${PN}-yallist = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5717bc308dbc03edd5e758d11c5bab65 \
    file://node_modules/@isaacs/cliui/LICENSE.txt;md5=83623193d3051ca8068a89a455c699ca \
    file://node_modules/@isaacs/cliui/node_modules/ansi-regex/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/@isaacs/cliui/node_modules/ansi-styles/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/@isaacs/cliui/node_modules/emoji-regex/LICENSE-MIT.txt;md5=ee9bd8b835cfcd512dd644540dd96987 \
    file://node_modules/@isaacs/cliui/node_modules/string-width/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/@isaacs/cliui/node_modules/strip-ansi/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/@isaacs/cliui/node_modules/wrap-ansi/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/@npmcli/fs/LICENSE.md;md5=c637d431ac5faadb34aff5fbd6985239 \
    file://node_modules/@npmcli/fs/lib/cp/LICENSE;md5=ea817882455c03503f7d014a8f54f095 \
    file://node_modules/@pkgjs/parseargs/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327 \
    file://node_modules/abbrev/LICENSE;md5=e9c0b639498fbe60d17b10099aba77c0 \
    file://node_modules/aggregate-error/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/ansi-regex/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/ansi-styles/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/balanced-match/LICENSE.md;md5=7fa99ddc3424107350ca6e9a24552085 \
    file://node_modules/bindings/LICENSE.md;md5=471723f32516f18ef36e7ef63580e4a8 \
    file://node_modules/cacache/LICENSE.md;md5=5324d196a847002a5d476185a59cf238 \
    file://node_modules/cacache/node_modules/brace-expansion/LICENSE;md5=a5df515ef062cc3affd8c0ae59c059ec \
    file://node_modules/cacache/node_modules/glob/LICENSE;md5=72480347f4e847c91bbe6207b7567338 \
    file://node_modules/cacache/node_modules/minimatch/LICENSE;md5=8b78835ea26f80c9067a0e80a294d926 \
    file://node_modules/chownr/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/clean-stack/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/color-convert/LICENSE;md5=9bdadfc9fbb3ab8d5a6d591bdbd52811 \
    file://node_modules/color-name/LICENSE;md5=d301869b39e08b33665b7c4f16b8e41d \
    file://node_modules/cross-spawn/LICENSE;md5=6046ffd2c9edcd9052bb4dd794d12f95 \
    file://node_modules/debug/LICENSE;md5=d85a365580888e9ee0a01fb53e8e9bf0 \
    file://node_modules/debug/node_modules/ms/license.md;md5=fd56fd5f1860961dfa92d313167c37a6 \
    file://node_modules/emoji-regex/LICENSE-MIT.txt;md5=ee9bd8b835cfcd512dd644540dd96987 \
    file://node_modules/encoding/LICENSE;md5=a9992d70215d97e8b82d289cec0c1ffa \
    file://node_modules/env-paths/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/exponential-backoff/LICENSE;md5=175792518e4ac015ab6696d16c4f607e \
    file://node_modules/file-uri-to-path/LICENSE;md5=9513dc0b97137379cfabc81b60889174 \
    file://node_modules/foreground-child/LICENSE;md5=0069d577f409666fc7a8e879eb49c164 \
    file://node_modules/fs-minipass/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/graceful-fs/LICENSE;md5=fd63805fd8e3797063b247781e5ee6e4 \
    file://node_modules/http-cache-semantics/LICENSE;md5=7b7cd412797b9e24e3c58eff96661bf9 \
    file://node_modules/http-proxy-agent/LICENSE;md5=dc944e6612da9341e648e5fd43dab7a2 \
    file://node_modules/iconv-lite/LICENSE;md5=f942263d98f0d75e0e0101884e86261d \
    file://node_modules/indent-string/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/is-fullwidth-code-point/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/is-lambda/LICENSE;md5=66d1a8cf6ce2a2458584a6df341b7da0 \
    file://node_modules/isexe/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/jackspeak/LICENSE.md;md5=95e9f67f2840df3a3a09a77ef3aea34b \
    file://node_modules/lru-cache/LICENSE;md5=28b53f8938bb3cf7c37ed8ac5e7d233e \
    file://node_modules/make-fetch-happen/LICENSE;md5=333cd0e0a8599f78b656ee1df3a44f97 \
    file://node_modules/minipass-collect/LICENSE;md5=9a413e57130b38e03dbfb34457fa2498 \
    file://node_modules/minipass-fetch/LICENSE;md5=f27cfd601484054495697ba3d54de66a \
    file://node_modules/minipass-flush/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/minipass-flush/node_modules/minipass/LICENSE;md5=78e0c554693f15c5d2e74a90dfef3816 \
    file://node_modules/minipass-pipeline/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/minipass-pipeline/node_modules/minipass/LICENSE;md5=78e0c554693f15c5d2e74a90dfef3816 \
    file://node_modules/minipass-sized/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/minipass-sized/node_modules/minipass/LICENSE;md5=78e0c554693f15c5d2e74a90dfef3816 \
    file://node_modules/minipass/LICENSE;md5=5f114ac709a085d123e16c1e6363793f \
    file://node_modules/minizlib/LICENSE;md5=d8a0ca0c46bfa01db064fa836f550966 \
    file://node_modules/minizlib/node_modules/minipass/LICENSE;md5=78e0c554693f15c5d2e74a90dfef3816 \
    file://node_modules/mkdirp/LICENSE;md5=f653359cc2be3ff55aa601d58d84c808 \
    file://node_modules/nan/LICENSE.md;md5=3952ff1c51e4ebe5b12c1bc501de4683 \
    file://node_modules/negotiator/LICENSE;md5=6417a862a5e35c17c904d9dda2cbd499 \
    file://node_modules/node-gyp/LICENSE;md5=694e396551033371686c80d3a1a69e88 \
    file://node_modules/node-gyp/gyp/LICENSE;md5=6cf4f5b9101e7eed6a9d59caf7aa121f \
    file://node_modules/node-gyp/gyp/pylib/packaging/LICENSE;md5=faadaedca9251a90b205c9167578ce91 \
    file://node_modules/node-gyp/gyp/pylib/packaging/LICENSE.APACHE;md5=2ee41112a44fe7014dce33e26468ba93 \
    file://node_modules/node-gyp/gyp/pylib/packaging/LICENSE.BSD;md5=7bef9bf4a8e4263634d0597e7ba100b8 \
    file://node_modules/node-gyp/node_modules/brace-expansion/LICENSE;md5=a5df515ef062cc3affd8c0ae59c059ec \
    file://node_modules/node-gyp/node_modules/glob/LICENSE;md5=72480347f4e847c91bbe6207b7567338 \
    file://node_modules/node-gyp/node_modules/isexe/LICENSE;md5=1a59ac0c921c435f03fc6905e5b34c49 \
    file://node_modules/node-gyp/node_modules/minimatch/LICENSE;md5=8b78835ea26f80c9067a0e80a294d926 \
    file://node_modules/node-gyp/node_modules/which/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/nopt/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/p-map/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/path-key/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/path-scurry/LICENSE.md;md5=95e9f67f2840df3a3a09a77ef3aea34b \
    file://node_modules/proc-log/LICENSE;md5=ff3ebb073dc14dd12f0c142c96b3b04b \
    file://node_modules/promise-retry/LICENSE;md5=d81e220dee93fdbcbf7696cc76cec0a0 \
    file://node_modules/retry/License;md5=c40fe50d231414ad1bc68e8965e7e95e \
    file://node_modules/safer-buffer/LICENSE;md5=3baebc2a17b8f5bff04882cd0dc0f76e \
    file://node_modules/semver/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/semver/node_modules/lru-cache/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/shebang-command/license;md5=05240cd20679544d6e90fcff746425bc \
    file://node_modules/shebang-regex/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/signal-exit/LICENSE.txt;md5=f04dc8bb8ec57c41ddd8ef51491862cb \
    file://node_modules/smart-buffer/LICENSE;md5=5b37b090a43e81bd880398260c467866 \
    file://node_modules/socks/LICENSE;md5=742dc14598fb295b01df682683c57709 \
    file://node_modules/ssri/LICENSE.md;md5=e1e465d0f0648bff1fe285726c8d5adf \
    file://node_modules/string-width-cjs/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/string-width/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/strip-ansi-cjs/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/strip-ansi/license;md5=915042b5df33c31a6db2b37eadaa00e3 \
    file://node_modules/tar/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/tar/node_modules/fs-minipass/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/tar/node_modules/fs-minipass/node_modules/minipass/LICENSE;md5=78e0c554693f15c5d2e74a90dfef3816 \
    file://node_modules/tar/node_modules/minipass/LICENSE;md5=5f114ac709a085d123e16c1e6363793f \
    file://node_modules/unique-filename/LICENSE;md5=454d711efc1fb9a50689cb3f4bf133d4 \
    file://node_modules/unique-slug/LICENSE;md5=2764032ecd89f3bafb0a1a35f966f9e8 \
    file://node_modules/which/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/wrap-ansi-cjs/license;md5=d5f2a6dd0192dcc7c833e50bb9017337 \
    file://node_modules/yallist/LICENSE;md5=82703a69f6d7411dde679954c2fd9dca \
    file://node_modules/@npmcli/agent/README.md;md5=68ab08e1226f022091f9a43dd564c93a \
    file://node_modules/agent-base/README.md;md5=906284d36b823b042d3b665e7e23a242 \
    file://node_modules/eastasianwidth/README.md;md5=23d7429a66f9cf215a19ab6f62ca93d6 \
    file://node_modules/err-code/README.md;md5=08b7e177c532ab406c745d2614cef90d \
    file://node_modules/https-proxy-agent/README.md;md5=1db7c84da2ec71b6bd31924ba1106925 \
    file://node_modules/imurmurhash/README.md;md5=c1fd47197ecab8a0852a47c5876c059e \
    file://node_modules/ip/README.md;md5=6a801a8bfd8ee71dcbc36dd3e98dd69b \
    file://node_modules/socks-proxy-agent/README.md;md5=ace324c0e92e058f687cbb5f71e27be6 \
"

inherit npm

PR = "r12"

SRC_URI = "\
    npm://registry.npmjs.org/;package=usocket;version=${PV} \
    npmsw://${THISDIR}/${BPN}/npm-shrinkwrap.json \
    file://0001-package.json-use-node-gyp-10.patch \
"

S = "${WORKDIR}/npm"

# This is needed here only for x86 MACHINEs, because other MACHINEs get it by default from node-gyp:
# https://github.com/nodejs/node-v0.x-archive/commit/d4ccdeaf00d7994b4a959a3ddfe8c52a2bd3c30d
# but not for ia32 target arch on linux, causing:
# http://gecko.lge.com:8000/Errors/Details/546709
# npm ERR! TOPDIR/BUILD/work/qemux86-webos-linux/nodejs-module-usocket/1.0.1-r11/recipe-sysroot-native/usr/bin/i686-webos-linux/../../libexec/i686-webos-linux/gcc/i686-webos-linux/11.3.0/ld: error: Release/obj.target/uwrap/src/uwrap.o: relocation R_386_GOTOFF against preemptible symbol _ZTVN3Nan10ObjectWrapE cannot be used when making a shared object
CXXFLAGS += "-fPIC"

# FIXME-buildpaths!!!
# [WRP-10883] buildpath QA issues
# http://gecko.lge.com:8000/Errors/Details/895293
# ERROR: QA Issue: File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/packaging/__pycache__/version.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/packaging/__pycache__/__init__.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/packaging/__pycache__/_structures.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/__pycache__/input.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/__pycache__/common.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/__pycache__/simple_copy.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/__pycache__/__init__.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/__pycache__/xcode_emulation.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/generator/__pycache__/__init__.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR
# File /usr/lib/node_modules/usocket/node_modules/node-gyp/gyp/pylib/gyp/generator/__pycache__/make.cpython-312.pyc in package nodejs-module-usocket contains reference to TMPDIR [buildpaths]
ERROR_QA:remove = "buildpaths"
WARN_QA:append = " buildpaths"
