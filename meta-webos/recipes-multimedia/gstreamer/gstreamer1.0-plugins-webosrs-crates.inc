# Copyright (c) 2023 LG Electronics, Inc.
INC_PR .= ".0"

SRC_URI += " \
    crate://crates.io/anyhow/1.0.71 \
    crate://crates.io/atomic_refcell/0.1.10 \
    crate://crates.io/autocfg/1.1.0 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/cfg-expr/0.15.1 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/futures-channel/0.3.28 \
    crate://crates.io/futures-core/0.3.28 \
    crate://crates.io/futures-executor/0.3.28 \
    crate://crates.io/futures-macro/0.3.28 \
    crate://crates.io/futures-task/0.3.28 \
    crate://crates.io/futures-util/0.3.28 \
    crate://crates.io/gio-sys/0.16.3 \
    crate://crates.io/glib/0.16.7 \
    crate://crates.io/glib-macros/0.16.8 \
    crate://crates.io/glib-sys/0.16.3 \
    crate://crates.io/gobject-sys/0.16.3 \
    crate://crates.io/gstreamer/0.19.8 \
    crate://crates.io/gstreamer-base/0.19.3 \
    crate://crates.io/gstreamer-base-sys/0.19.3 \
    crate://crates.io/gstreamer-sys/0.19.4 \
    crate://crates.io/gstreamer-video/0.19.5 \
    crate://crates.io/gstreamer-video-sys/0.19.5 \
    crate://crates.io/hashbrown/0.12.3 \
    crate://crates.io/heck/0.4.1 \
    crate://crates.io/indexmap/1.9.3 \
    crate://crates.io/libc/0.2.144 \
    crate://crates.io/memchr/2.5.0 \
    crate://crates.io/muldiv/1.0.1 \
    crate://crates.io/num-integer/0.1.45 \
    crate://crates.io/num-rational/0.4.1 \
    crate://crates.io/num-traits/0.2.15 \
    crate://crates.io/once_cell/1.17.1 \
    crate://crates.io/option-operations/0.5.0 \
    crate://crates.io/paste/1.0.12 \
    crate://crates.io/pin-project-lite/0.2.9 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/pkg-config/0.3.27 \
    crate://crates.io/pretty-hex/0.3.0 \
    crate://crates.io/proc-macro-crate/1.3.1 \
    crate://crates.io/proc-macro-error/1.0.4 \
    crate://crates.io/proc-macro-error-attr/1.0.4 \
    crate://crates.io/proc-macro2/1.0.58 \
    crate://crates.io/quote/1.0.27 \
    crate://crates.io/serde/1.0.163 \
    crate://crates.io/serde_spanned/0.6.1 \
    crate://crates.io/slab/0.4.8 \
    crate://crates.io/smallvec/1.10.0 \
    crate://crates.io/syn/1.0.109 \
    crate://crates.io/syn/2.0.16 \
    crate://crates.io/system-deps/6.1.0 \
    crate://crates.io/target-lexicon/0.12.7 \
    crate://crates.io/thiserror/1.0.40 \
    crate://crates.io/thiserror-impl/1.0.40 \
    crate://crates.io/toml/0.7.3 \
    crate://crates.io/toml_datetime/0.6.1 \
    crate://crates.io/toml_edit/0.19.8 \
    crate://crates.io/unicode-ident/1.0.8 \
    crate://crates.io/version-compare/0.1.1 \
    crate://crates.io/version_check/0.9.4 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winnow/0.4.1 \
"
SRC_URI[anyhow-1.0.71.sha256sum] = "9c7d0618f0e0b7e8ff11427422b64564d5fb0be1940354bfe2e0529b18a9d9b8"
SRC_URI[atomic_refcell-0.1.10.sha256sum] = "79d6dc922a2792b006573f60b2648076355daeae5ce9cb59507e5908c9625d31"
SRC_URI[autocfg-1.1.0.sha256sum] = "d468802bab17cbc0cc575e9b053f41e72aa36bfa6b7f55e3529ffa43161b97fa"
SRC_URI[bitflags-1.3.2.sha256sum] = "bef38d45163c2f1dde094a7dfd33ccf595c92905c8f8f4fdc18d06fb1037718a"
SRC_URI[byte-slice-cast-1.2.2.sha256sum] = "c3ac9f8b63eca6fd385229b3675f6cc0dc5c8a5c8a54a59d4f52ffd670d87b0c"
SRC_URI[cfg-expr-0.15.1.sha256sum] = "c8790cf1286da485c72cf5fc7aeba308438800036ec67d89425924c4807268c9"
SRC_URI[cfg-if-1.0.0.sha256sum] = "baf1de4339761588bc0619e3cbc0120ee582ebb74b53b4efbf79117bd2da40fd"
SRC_URI[futures-channel-0.3.28.sha256sum] = "955518d47e09b25bbebc7a18df10b81f0c766eaf4c4f1cccef2fca5f2a4fb5f2"
SRC_URI[futures-core-0.3.28.sha256sum] = "4bca583b7e26f571124fe5b7561d49cb2868d79116cfa0eefce955557c6fee8c"
SRC_URI[futures-executor-0.3.28.sha256sum] = "ccecee823288125bd88b4d7f565c9e58e41858e47ab72e8ea2d64e93624386e0"
SRC_URI[futures-macro-0.3.28.sha256sum] = "89ca545a94061b6365f2c7355b4b32bd20df3ff95f02da9329b34ccc3bd6ee72"
SRC_URI[futures-task-0.3.28.sha256sum] = "76d3d132be6c0e6aa1534069c705a74a5997a356c0dc2f86a47765e5617c5b65"
SRC_URI[futures-util-0.3.28.sha256sum] = "26b01e40b772d54cf6c6d721c1d1abd0647a0106a12ecaa1c186273392a69533"
SRC_URI[gio-sys-0.16.3.sha256sum] = "e9b693b8e39d042a95547fc258a7b07349b1f0b48f4b2fa3108ba3c51c0b5229"
SRC_URI[glib-0.16.7.sha256sum] = "ddd4df61a866ed7259d6189b8bcb1464989a77f1d85d25d002279bbe9dd38b2f"
SRC_URI[glib-macros-0.16.8.sha256sum] = "fb1a9325847aa46f1e96ffea37611b9d51fc4827e67f79e7de502a297560a67b"
SRC_URI[glib-sys-0.16.3.sha256sum] = "c61a4f46316d06bfa33a7ac22df6f0524c8be58e3db2d9ca99ccb1f357b62a65"
SRC_URI[gobject-sys-0.16.3.sha256sum] = "3520bb9c07ae2a12c7f2fbb24d4efc11231c8146a86956413fb1a79bb760a0f1"
SRC_URI[gstreamer-0.19.8.sha256sum] = "85fc926d081923c840403ec5ec3b2157a7cd236a2587c3031a4f0206f13ed500"
SRC_URI[gstreamer-audio-0.19.4.sha256sum] = "b6c77f86675dd2ea500bcbe17c11a146354df354a24dc222cbda793707e75833"
SRC_URI[gstreamer-audio-sys-0.19.4.sha256sum] = "b328f1bd487ef22e5ec6c03113515a2b1be10ea9bc12ecab646966d1b48361d5"
SRC_URI[gstreamer-base-0.19.3.sha256sum] = "a61a299f9ea2ca892b43e2e428b86c679875e95ba23f8ae06fd730308df630f0"
SRC_URI[gstreamer-base-sys-0.19.3.sha256sum] = "dbc3c4476e1503ae245c89fbe20060c30ec6ade5f44620bcc402cbc70a3911a1"
SRC_URI[gstreamer-sys-0.19.4.sha256sum] = "545f52ad8a480732cc4290fd65dfe42952c8ae374fe581831ba15981fedf18a4"
SRC_URI[gstreamer-video-0.19.5.sha256sum] = "eb19dcbdd5436483e764318bef157070f192acc5b1199e85878723a9ce33d4e3"
SRC_URI[gstreamer-video-sys-0.19.5.sha256sum] = "7546bc798c898f2083330d81a7efff48f65a31b03873f410538032d26ec0cdc7"
SRC_URI[hashbrown-0.12.3.sha256sum] = "8a9ee70c43aaf417c914396645a0fa852624801b24ebb7ae78fe8272889ac888"
SRC_URI[heck-0.4.1.sha256sum] = "95505c38b4572b2d910cecb0281560f54b440a19336cbbcb27bf6ce6adc6f5a8"
SRC_URI[indexmap-1.9.3.sha256sum] = "bd070e393353796e801d209ad339e89596eb4c8d430d18ede6a1cced8fafbd99"
SRC_URI[libc-0.2.144.sha256sum] = "2b00cc1c228a6782d0f076e7b232802e0c5689d41bb5df366f2a6b6621cfdfe1"
SRC_URI[memchr-2.5.0.sha256sum] = "2dffe52ecf27772e601905b7522cb4ef790d2cc203488bbd0e2fe85fcb74566d"
SRC_URI[muldiv-1.0.1.sha256sum] = "956787520e75e9bd233246045d19f42fb73242759cc57fba9611d940ae96d4b0"
SRC_URI[num-integer-0.1.45.sha256sum] = "225d3389fb3509a24c93f5c29eb6bde2586b98d9f016636dff58d7c6f7569cd9"
SRC_URI[num-rational-0.4.1.sha256sum] = "0638a1c9d0a3c0914158145bc76cff373a75a627e6ecbfb71cbe6f453a5a19b0"
SRC_URI[num-traits-0.2.15.sha256sum] = "578ede34cf02f8924ab9447f50c28075b4d3e5b269972345e7e0372b38c6cdcd"
SRC_URI[once_cell-1.17.1.sha256sum] = "b7e5500299e16ebb147ae15a00a942af264cf3688f47923b8fc2cd5858f23ad3"
SRC_URI[option-operations-0.5.0.sha256sum] = "7c26d27bb1aeab65138e4bf7666045169d1717febcc9ff870166be8348b223d0"
SRC_URI[paste-1.0.12.sha256sum] = "9f746c4065a8fa3fe23974dd82f15431cc8d40779821001404d10d2e79ca7d79"
SRC_URI[pin-project-lite-0.2.9.sha256sum] = "e0a7ae3ac2f1173085d398531c705756c94a4c56843785df85a60c1a0afac116"
SRC_URI[pin-utils-0.1.0.sha256sum] = "8b870d8c151b6f2fb93e84a13146138f05d02ed11c7e7c54f8826aaaf7c9f184"
SRC_URI[pkg-config-0.3.27.sha256sum] = "26072860ba924cbfa98ea39c8c19b4dd6a4a25423dbdf219c1eca91aa0cf6964"
SRC_URI[pretty-hex-0.3.0.sha256sum] = "c6fa0831dd7cc608c38a5e323422a0077678fa5744aa2be4ad91c4ece8eec8d5"
SRC_URI[proc-macro-crate-1.3.1.sha256sum] = "7f4c021e1093a56626774e81216a4ce732a735e5bad4868a03f3ed65ca0c3919"
SRC_URI[proc-macro-error-1.0.4.sha256sum] = "da25490ff9892aab3fcf7c36f08cfb902dd3e71ca0f9f9517bea02a73a5ce38c"
SRC_URI[proc-macro-error-attr-1.0.4.sha256sum] = "a1be40180e52ecc98ad80b184934baf3d0d29f979574e439af5a55274b35f869"
SRC_URI[proc-macro2-1.0.58.sha256sum] = "fa1fb82fc0c281dd9671101b66b771ebbe1eaf967b96ac8740dcba4b70005ca8"
SRC_URI[quote-1.0.27.sha256sum] = "8f4f29d145265ec1c483c7c654450edde0bfe043d3938d6972630663356d9500"
SRC_URI[serde-1.0.163.sha256sum] = "2113ab51b87a539ae008b5c6c02dc020ffa39afd2d83cffcb3f4eb2722cebec2"
SRC_URI[serde_spanned-0.6.1.sha256sum] = "0efd8caf556a6cebd3b285caf480045fcc1ac04f6bd786b09a6f11af30c4fcf4"
SRC_URI[slab-0.4.8.sha256sum] = "6528351c9bc8ab22353f9d776db39a20288e8d6c37ef8cfe3317cf875eecfc2d"
SRC_URI[smallvec-1.10.0.sha256sum] = "a507befe795404456341dfab10cef66ead4c041f62b8b11bbb92bffe5d0953e0"
SRC_URI[syn-1.0.109.sha256sum] = "72b64191b275b66ffe2469e8af2c1cfe3bafa67b529ead792a6d0160888b4237"
SRC_URI[syn-2.0.16.sha256sum] = "a6f671d4b5ffdb8eadec19c0ae67fe2639df8684bd7bc4b83d986b8db549cf01"
SRC_URI[system-deps-6.1.0.sha256sum] = "e5fa6fb9ee296c0dc2df41a656ca7948546d061958115ddb0bcaae43ad0d17d2"
SRC_URI[target-lexicon-0.12.7.sha256sum] = "fd1ba337640d60c3e96bc6f0638a939b9c9a7f2c316a1598c279828b3d1dc8c5"
SRC_URI[thiserror-1.0.40.sha256sum] = "978c9a314bd8dc99be594bc3c175faaa9794be04a5a5e153caba6915336cebac"
SRC_URI[thiserror-impl-1.0.40.sha256sum] = "f9456a42c5b0d803c8cd86e73dd7cc9edd429499f37a3550d286d5e86720569f"
SRC_URI[toml-0.7.3.sha256sum] = "b403acf6f2bb0859c93c7f0d967cb4a75a7ac552100f9322faf64dc047669b21"
SRC_URI[toml_datetime-0.6.1.sha256sum] = "3ab8ed2edee10b50132aed5f331333428b011c99402b5a534154ed15746f9622"
SRC_URI[toml_edit-0.19.8.sha256sum] = "239410c8609e8125456927e6707163a3b1fdb40561e4b803bc041f466ccfdc13"
SRC_URI[unicode-ident-1.0.8.sha256sum] = "e5464a87b239f13a63a501f2701565754bae92d243d4bb7eb12f6d57d2269bf4"
SRC_URI[version-compare-0.1.1.sha256sum] = "579a42fc0b8e0c63b76519a339be31bed574929511fa53c1a3acae26eb258f29"
SRC_URI[version_check-0.9.4.sha256sum] = "49874b5167b65d7193b8aba1567f5c7d93d001cafc34600cee003eda787e483f"
SRC_URI[winapi-0.3.9.sha256sum] = "5c839a674fcd7a98952e593242ea400abe93992746761e38641405d28b00f419"
SRC_URI[winapi-i686-pc-windows-gnu-0.4.0.sha256sum] = "ac3b87c63620426dd9b991e5ce0329eff545bccbbb34f3be09ff6fb6ab51b7b6"
SRC_URI[winapi-x86_64-pc-windows-gnu-0.4.0.sha256sum] = "712e227841d057c1ee1cd2fb22fa7e5a5461ae8e48fa2ca79ec42cfc1931183f"
SRC_URI[winnow-0.4.1.sha256sum] = "ae8970b36c66498d8ff1d66685dc86b91b29db0c7739899012f63a63814b4b28"
