# Copyright (c) 2022 LG Electronics, Inc.
#
# Authors = "the meta-rust-bin authors"
# Github = "https://github.com/rust-embedded/meta-rust-bin"
# License = "Apache-2.0 | MIT"
#

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "1cb6f3e6054298c946993029a26f610f",
        "aarch64-unknown-linux-musl": "ad0293c26d615e85ceff8468b32ceb17",
        "arm-unknown-linux-gnueabi": "423b683727b8ebecb9d3a893134a0c78",
        "arm-unknown-linux-gnueabihf": "6caca9742e4377171201039a0b754145",
        "armv5te-unknown-linux-gnueabi": "07d5e7d892cf028e221d3e420e4ec474",
        "armv5te-unknown-linux-musleabi": "c1d055e5e05edf73363a132f1cf0c58e",
        "armv7-unknown-linux-gnueabihf": "dc85de93030ce16c2ccfe602ee778164",
        "armv7-unknown-linux-musleabihf": "773bd7a1c5a294fee0aeab5b2232c7bf",
        "i686-unknown-linux-gnu": "748087a92606e191266500e87060baf4",
        "mips-unknown-linux-gnu": "f98a0ba89adddf0471bc6e8153c8888d",
        "mipsel-unknown-linux-gnu": "5c1083e09f134cddb29937dabb2d5665",
        "powerpc-unknown-linux-gnu": "f6b8de0e7584aafc05592558c14dc920",
        "x86_64-unknown-linux-gnu": "260c066bc19d6963296033d66a8b42cd",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "4afce4152945642fb4c1a7fa44ffe396f98ce094108ffe29b1833413e8eed49d",
        "aarch64-unknown-linux-musl": "0f3d44e3432ed152d5ad9759741bcc8844386af3286beb6dc856323d7f56565c",
        "arm-unknown-linux-gnueabi": "55e59d256f7b00681ac039c110cebba016f82acacd1f3bf8942cd76887d0f8a8",
        "arm-unknown-linux-gnueabihf": "6cb1493e1b0bc981c59b2457d76e16f330b0543737cda969246b377e4520c45c",
        "armv5te-unknown-linux-gnueabi": "cfb04fd59ba012538500da9e9ff5ef1cbf6940c1414cd71dd5a6225f798f1a90",
        "armv5te-unknown-linux-musleabi": "b28460e19e127903f11baa562f91d665b8687c77d3ab5e9bdd5158f1060b4738",
        "armv7-unknown-linux-gnueabihf": "998c9a1af2fce446f37b77d6400a32fb3097def293d2f73faedd0d0cafd42a33",
        "armv7-unknown-linux-musleabihf": "d37ae049dca59b174db5b54a1c157b54c8cc49396699e135f5fad9d306568ac9",
        "i686-unknown-linux-gnu": "5df51e9119d49addbf78ca6fbaf78a869f7aea46853a8fdfe339d543d0d88c3b",
        "mips-unknown-linux-gnu": "0e235151b2f33a03a4717b2e9f3644a3d3ef502844545fea3645214403ac143a",
        "mipsel-unknown-linux-gnu": "696c84c83cb22c3019ff18cdc21fc5206bc7e56d4f51c86e26dc4ebfaf952aa5",
        "powerpc-unknown-linux-gnu": "460dbad0c90b35c3adda748d62efb568c8bb7703c8ce489a4da05c75c594a841",
        "x86_64-unknown-linux-gnu": "4211c28e3359e915c116524aeb13a728dfd1e8889d1b01d32ed64b2995534eae",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "a61240fe855eee27ac96c6c04c7b7169",
        "arm-unknown-linux-gnueabi": "eda67b58b320b67506d1104b1614f157",
        "arm-unknown-linux-gnueabihf": "5cc9e950f58b8d24e72efd9842495e86",
        "armv7-unknown-linux-gnueabihf": "d14aa97373754ba04b4276cf33c6de41",
        "i686-unknown-linux-gnu": "06dac391e5114edd841be9c91f05956d",
        "x86_64-unknown-linux-gnu": "35f1c900b44c2b3f20d1938f5610a84c",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "a4189dda06098813fb1e08861deae8993c809051c3c716b6aefe3793f5f0fb5e",
        "arm-unknown-linux-gnueabi": "4c8f7000a1662e0bc2d1e5153d56784c2fafadfc0306d26dfd3e902c568b50b1",
        "arm-unknown-linux-gnueabihf": "63eab3494123b67b7b0cccca5689e430ab0c62200c48b96892afc6cd0ebfa4e6",
        "armv7-unknown-linux-gnueabihf": "0e0347051ba9530ccdba8fdafd394a52c4623fe4f9d1ee53f6f5d66f4fd2c25d",
        "i686-unknown-linux-gnu": "4b7ec3ebbc32fd269775367b3dd800f53bec91ffcc33e7e1f6cd98f81bc4095f",
        "x86_64-unknown-linux-gnu": "c5bb7656557bf6451b2c53b18b6d092814fcba922ff2ffa4f704a41d79595f2d",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=93a95682d51b4cb0a633a97046940ef0"

require recipes-devtools/rust-bin/rust-bin-cross.inc
