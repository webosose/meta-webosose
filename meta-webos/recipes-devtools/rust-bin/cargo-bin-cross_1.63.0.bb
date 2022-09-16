# Copyright (c) 2022 LG Electronics, Inc.
#
# Authors = "the meta-rust-bin authors"
# Github = "https://github.com/rust-embedded/meta-rust-bin"
# License = "Apache-2.0 | MIT"
#

# Recipe for cargo 20220811
# This corresponds to rust release 1.63.0

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)

def cargo_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "ba1acf26796d2b9be51c5d47f123aef5",
        "arm-unknown-linux-gnueabi": "1e13f968108b187fec71ab62e0938059",
        "arm-unknown-linux-gnueabihf": "83c03465c9c02502f9230bacc40ed16a",
        "armv7-unknown-linux-gnueabihf": "aa496f31b4cbff223662c8d1f46cbf44",
        "i686-unknown-linux-gnu": "d0ffab8f7dd97733ff149d10b4099fbf",
        "x86_64-unknown-linux-gnu": "f3f3e78598f7b8c51a9e47e8c19fb32f",
    }
    return get_by_triple(HASHES, triple)

def cargo_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "4e359ffb5ef7cb69014d1c7c9a9ccdd2efa1220551f09f52aac16777eb75f01b",
        "arm-unknown-linux-gnueabi": "09405d9c79c133f7be749f15f248e7a9eb96508123d9c6549f2c287d60208391",
        "arm-unknown-linux-gnueabihf": "e07eb5f2ec8c913c4c06157de16cf15b2b392b3c0cdf79a5c00b3903f3d40b50",
        "armv7-unknown-linux-gnueabihf": "aa3be47cb33e3ccd1a9ebf9f7dbc47780121fd5e88ae5dbdd4adc35ddb3a1f14",
        "i686-unknown-linux-gnu": "8a157b8899797b3224742ad51e91f21f903f3523513b1a70ba78f60fc7f595ce",
        "x86_64-unknown-linux-gnu": "43445b2131cefa466943ed4867876f5835fcb17243a18e1db1c8231417a1b27e",
    }
    return get_by_triple(HASHES, triple)

def cargo_url(triple):
    URLS = {
        "aarch64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-aarch64-unknown-linux-gnu.tar.gz",
        "arm-unknown-linux-gnueabi": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-arm-unknown-linux-gnueabi.tar.gz",
        "arm-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-arm-unknown-linux-gnueabihf.tar.gz",
        "armv7-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-armv7-unknown-linux-gnueabihf.tar.gz",
        "i686-unknown-linux-gnu": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-i686-unknown-linux-gnu.tar.gz",
        "x86_64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2022-08-11/cargo-1.63.0-x86_64-unknown-linux-gnu.tar.gz",
    }
    return get_by_triple(URLS, triple)

DEPENDS += "rust-bin-cross-${TARGET_ARCH} (= 1.63.0)"
LIC_FILES_CHKSUM = "\
    file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10 \
    file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
"

require recipes-devtools/rust-bin/cargo-bin-cross.inc
