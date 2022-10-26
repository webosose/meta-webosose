# Copyright (c) 2022 LG Electronics, Inc.
#
# Authors = "the meta-rust-bin authors"
# Github = "https://github.com/rust-embedded/meta-rust-bin"
# License = "Apache-2.0 | MIT"
#
# webos_cargo
#
inherit webos_rust-common

# Many crates rely on pkg-config to find native versions of their libraries for
# linking - do the simple thing and make it generally available.
DEPENDS:append = "\
    cargo-bin-cross-${TARGET_ARCH} \
    pkgconfig-native \
"

# Move CARGO_HOME from default of ~/.cargo
export CARGO_HOME = "${WORKDIR}/cargo_home"

# If something fails while building, this might give useful information
export RUST_BACKTRACE = "1"

# Do build out-of-tree
B = "${WORKDIR}/target"
export CARGO_TARGET_DIR = "${B}"

RUST_TARGET = "${@rust_target(d, 'TARGET')}"
RUST_BUILD = "${@rust_target(d, 'BUILD')}"

# Additional flags passed directly to the "cargo build" invocation
EXTRA_CARGO_FLAGS ??= ""
EXTRA_RUSTFLAGS ??= ""
RUSTFLAGS += "${EXTRA_RUSTFLAGS}"

# Space-separated list of features to enable
CARGO_FEATURES ??= ""

# Control the Cargo build type (debug or release)
CARGO_BUILD_TYPE ?= "--release"

CARGO_INSTALL_DIR ?= "${D}${bindir}"

CARGO_DEBUG_DIR = "${B}/${RUST_TARGET}/debug"
CARGO_RELEASE_DIR = "${B}/${RUST_TARGET}/release"
WRAPPER_DIR = "${WORKDIR}/wrappers"

# Set the Cargo manifest path to the typical location
CARGO_MANIFEST_PATH ?= "${S}/Cargo.toml"

FILES:${PN}-dev += "${libdir}/*.rlib"

CARGO_BUILD_FLAGS = "\
    --verbose \
    --manifest-path ${CARGO_MANIFEST_PATH} \
    --target=${RUST_TARGET} \
    ${CARGO_BUILD_TYPE} \
    ${@oe.utils.conditional('CARGO_FEATURES', '', '', '--features "${CARGO_FEATURES}"', d)} \
    ${EXTRA_CARGO_FLAGS} \
"

create_cargo_config() {
    if [ "${RUST_BUILD}" != "${RUST_TARGET}" ]; then
        echo > ${CARGO_HOME}/config
        echo "[target.${RUST_BUILD}]" >> ${CARGO_HOME}/config
        echo "linker = '${WRAPPER_DIR}/linker-native-wrapper.sh'" >> ${CARGO_HOME}/config

        echo >> ${CARGO_HOME}/config
        echo "[target.${RUST_TARGET}]" >> ${CARGO_HOME}/config
        echo "linker = '${WRAPPER_DIR}/linker-wrapper.sh'" >> ${CARGO_HOME}/config
    else
        echo > ${CARGO_HOME}/config
        echo "[target.${RUST_TARGET}]" >> ${CARGO_HOME}/config
        echo "linker = '${WRAPPER_DIR}/linker-wrapper.sh'" >> ${CARGO_HOME}/config
    fi

    echo >> ${CARGO_HOME}/config
    echo "[build]" >> ${CARGO_HOME}/config
    echo "rustflags = ['-C', 'rpath']" >> ${CARGO_HOME}/config

    echo >> ${CARGO_HOME}/config
    echo "[profile.release]" >> ${CARGO_HOME}/config
    echo "debug = true" >> ${CARGO_HOME}/config
}

webos_cargo_do_configure() {
    mkdir -p "${B}"
    mkdir -p "${CARGO_HOME}"
    mkdir -p "${WRAPPER_DIR}"

    # Yocto provides the C compiler in ${CC} but that includes options beyond
    # the compiler binary. cargo/rustc expect a single binary, so we put ${CC}
    # in a wrapper script.
    echo "#!/bin/sh" >"${WRAPPER_DIR}/cc-wrapper.sh"
    echo "${CC} \"\$@\"" >>"${WRAPPER_DIR}/cc-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/cc-wrapper.sh"

    echo "#!/bin/sh" >"${WRAPPER_DIR}/cxx-wrapper.sh"
    echo "${CXX} \"\$@\"" >>"${WRAPPER_DIR}/cxx-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/cxx-wrapper.sh"

    echo "#!/bin/sh" >"${WRAPPER_DIR}/cc-native-wrapper.sh"
    echo "${BUILD_CC} \"\$@\"" >>"${WRAPPER_DIR}/cc-native-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/cc-native-wrapper.sh"

    echo "#!/bin/sh" >"${WRAPPER_DIR}/cxx-native-wrapper.sh"
    echo "${BUILD_CXX} \"\$@\"" >>"${WRAPPER_DIR}/cxx-native-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/cxx-native-wrapper.sh"

    echo "#!/bin/sh" >"${WRAPPER_DIR}/linker-wrapper.sh"
    echo "${CC} ${LDFLAGS} \"\$@\"" >>"${WRAPPER_DIR}/linker-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/linker-wrapper.sh"

    echo "#!/bin/sh" >"${WRAPPER_DIR}/linker-native-wrapper.sh"
    echo "${BUILD_CC} ${BUILD_LDFLAGS} \"\$@\"" >>"${WRAPPER_DIR}/linker-native-wrapper.sh"
    chmod +x "${WRAPPER_DIR}/linker-native-wrapper.sh"

    # Create our global config in CARGO_HOME
    create_cargo_config
}

webos_cargo_do_compile() {
    export TARGET_CC="${WRAPPER_DIR}/cc-wrapper.sh"
    export TARGET_CXX="${WRAPPER_DIR}/cxx-wrapper.sh"
    export CC="${WRAPPER_DIR}/cc-native-wrapper.sh"
    export CXX="${WRAPPER_DIR}/cxx-native-wrapper.sh"
    export TARGET_LD="${WRAPPER_DIR}/ld-wrapper.sh"
    export LD="${WRAPPER_DIR}/ld-native-wrapper.sh"
    export PKG_CONFIG_ALLOW_CROSS="1"
    export LDFLAGS=""
    export RUSTFLAGS="${RUSTFLAGS}"
    bbnote "which rustc:" `which rustc`
    bbnote "rustc --version" `rustc --version`
    bbnote "which cargo:" `which cargo`
    bbnote "cargo --version" `cargo --version`
    bbnote cargo build ${CARGO_BUILD_FLAGS}
    cargo build ${CARGO_BUILD_FLAGS}
}

do_compile[network] = "1"

webos_cargo_do_install() {
    if [ "${CARGO_BUILD_TYPE}" = "--release" ]; then
        local cargo_bindir="${CARGO_RELEASE_DIR}"
    else
        local cargo_bindir="${CARGO_DEBUG_DIR}"
    fi

    local files_installed=""

    for tgt in "${cargo_bindir}"/*; do
        case $tgt in
            *.so|*.rlib)
                install -d "${D}${libdir}"
                install -m755 "$tgt" "${D}${libdir}"
                files_installed="$files_installed $tgt"
                ;;
            *examples)
                if [ -d "$tgt" ]; then
                    for example in "$tgt/"*; do
                        if [ -f "$example" ] && [ -x "$example" ]; then
                            install -d "${CARGO_INSTALL_DIR}"
                            install -m755 "$example" "${CARGO_INSTALL_DIR}"
                            files_installed="$files_installed $example"
                        fi
                    done
                fi
                ;;
            *)
                if [ -f "$tgt" ] && [ -x "$tgt" ]; then
                    install -d "${CARGO_INSTALL_DIR}"
                    install -m755 "$tgt" "${CARGO_INSTALL_DIR}"
                    files_installed="$files_installed $tgt"
                fi
                ;;
        esac
    done

    if [ -z "$files_installed" ]; then
        bbfatal "Cargo found no files to install"
    else
        bbnote "Installed the following files:"
        for f in $files_installed; do
            bbnote "  " `basename $f`
        done
    fi
}

EXPORT_FUNCTIONS do_configure do_compile do_install
