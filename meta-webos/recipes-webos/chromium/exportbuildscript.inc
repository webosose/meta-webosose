do_exportbuildscript () {
    filename="${B}/build.sh"
    cat <<'EOF' > $filename
#!/bin/bash
# This script was generated from webOS build.
# DISTRO = "${DISTRO}"
# MACHINE = "${MACHINE}"
# WEBOS_DISTRO_TOPDIR_DESCRIBE = "${WEBOS_DISTRO_TOPDIR_DESCRIBE}"
# WEBOS_DISTRO_TOPDIR_REVISION = "${WEBOS_DISTRO_TOPDIR_REVISION}"
#
# PN = ${PN}
# DEPENDS = "${DEPENDS}"
# RDEPENDS = "${RDEPENDS}"

GN_ARGS="${GN_ARGS}"

declare -A target_arch_to_cpu=(\
    ["aarch64"]="arm64" \
    ["arm"]="arm" \
    ["i586"]="x86" \
    ["i686"]="x86" \
    ["x86_64"]="x64" )
TARGET_CPU="${target_arch_to_cpu["$OECORE_TARGET_ARCH"]}"

# Use the variables on the environment script from the SDK
GN_ARGS_OVERRIDES="\
    cros_host_is_clang=false \
    cros_host_ar=\"ar\" \
    cros_host_cc=\"gcc\" \
    cros_host_cxx=\"g++\" \
    cros_host_ld=\"g++\" \
    cros_host_nm=\"nm\" \
    cros_host_extra_ldflags=\"$BUILD_LDFLAGS\" \
    cros_host_extra_cflags=\"$BUILD_CFLAGS\" \
    cros_host_extra_cppflags=\"$BUILD_CPPFLAGS\" \
    cros_host_extra_cxxflags=\"$BUILD_CXXFLAGS\" \
    cros_target_ar=\"$AR\" \
    cros_target_cc=\"$CC\" \
    cros_target_cxx=\"$CXX\" \
    target_sysroot=\"$SDKTARGETSYSROOT\" \
    v8_snapshot_toolchain=\"//build/toolchain/cros:v8_snapshot_clang_$TARGET_CPU\" \
    host_pkg_config=\"$(pwd)/host-pkg-config\" \
    target_cpu=\"$TARGET_CPU\" \
"

cat << 'HOSTPKGCFG' > host-pkg-config
#!/bin/bash
unset PKG_CONFIG_PATH
unset PKG_CONFIG_LIBDIR
unset PKG_CONFIG_SYSROOT_DIR

/usr/bin/pkg-config "$@"
HOSTPKGCFG

chmod +x host-pkg-config

# Customising target cpu related variables based on TUNE_FEATURES of the SDK
declare -A target_tune_features
for tune_feature in $TUNE_FEATURES;do
    target_tune_features["$tune_feature"]=1
done

if [[ -n "${target_tune_features["callconvention-hard"]}" ]];then
    ARM_FLOAT_ABI="hard"
else
    ARM_FLOAT_ABI="softfp"
fi

if [[ -n "${target_tune_features["armv6"]}" ]]; then
    GN_ARGS_OVERRIDES="$GN_ARGS_OVERRIDES arm_arch=\"armv6\" arm_version=6 arm_float_abi=\"$ARM_FLOAT_ABI\""

    # tcmalloc's atomicops-internals-arm-v6plus.h uses the "dmb" instruction that
    # is not available on (some?) ARMv6 models, which causes the build to fail.
    GN_ARGS_OVERRIDES="$GN_ARGS_OVERRIDES use_allocator=\"none\""

    # The WebRTC code fails to build on ARMv6 when NEON is enabled.
    # https://bugs.chromium.org/p/webrtc/issues/detail?id=6574
    GN_ARGS_OVERRIDES="$GN_ARGS_OVERRIDES arm_use_neon=false"
fi

if [[ -n "${target_tune_features["armv7a"]}" ]] || [[ -n "${target_tune_features["cortexa9"]}" ]]; then
    GN_ARGS_OVERRIDES="$GN_ARGS_OVERRIDES arm_arch=\"armv7-a\" arm_version=7 arm_float_abi=\"$ARM_FLOAT_ABI\""
fi

if [[ -n "${target_tune_features["armv7ve"]}" ]]; then
    GN_ARGS_OVERRIDES="$GN_ARGS_OVERRIDES arm_arch=\"armv7ve\" arm_version=7 arm_float_abi=\"$ARM_FLOAT_ABI\""
fi

# Need to keep the last space to process the last arg
GN_ARGS="$GN_ARGS $GN_ARGS_OVERRIDES "
start_pos=0
inside_quote=0
nchars=${#GN_ARGS}
has_arg=0
declare -A arr_webruntime_gn_args
declare -a arr_webruntime_gn_args_idx # To keep the ordering of args
is_assignment=false
maybe_lvalue=true
# Only interesting the statement which assigns key=value to modify the value.
# Store assignment statement as key=value into assoicated array
# and other statements are stored as position=statement.
for (( i = 0; i < $nchars; ++i)); do
  curr_char="${GN_ARGS:$i:1}"
  if [ $maybe_lvalue ]; then
    [[ $curr_char == "=" ]] && is_assignment=true
    [[ $curr_char =~ '[a-zA-Z0-9.]' ]] || maybe_lvalue=false
  fi
  if [ "${curr_char}" == "\"" ];then
    inside_quote=$(( ($inside_quote + 1) % 2 ))
    # Ending quote also requires to close one arguemnt
    # since gn seems to do that.
    # e.g var1="value1"var2="value2" makes below in args.gn file
    #  var1 = "value1"
    #  var2 = "value2"
    # Hence if the current statement is assignment
    # it need to check statement is completed.
    # Otherwise continue to next char
    [ "$is_assignment" = false ] && continue
  elif [ "$curr_char" != " " ];then
    if [ $has_arg -eq 0 ];then
      has_arg=1
      start_pos=$i
    fi
    continue
  fi

  if [ $inside_quote -eq 0 ] && [ $has_arg -eq 1 ];then
      argument="${GN_ARGS:$start_pos:$(( $i - $start_pos + 1 ))}"
      key="${argument%%=*}"
      [ "$is_assignment" = false ] && key="$start_pos"
      arr_webruntime_gn_args["$key"]="${argument}"
      tmp_array=()
      # Replace previous key=value with new key=value
      for value in "${arr_webruntime_gn_args_idx[@]}";do
        [[ $value != $key ]] && tmp_array+=($value)
      done
      arr_webruntime_gn_args_idx=("${tmp_array[@]}" "$key")
      unset tmp_array
      has_arg=0
      is_assignment=false
      maybe_lvalue=true
  fi
done

# Example of removing an exising argument
to_remove=( \
)

for remove in ${to_remove[@]}; do
  unset arr_webruntime_gn_args["$remove"]
  arr_webruntime_gn_args_idx=( "${arr_webruntime_gn_args_idx[@]/$remove}" )
done

# SDK CC and CXX has -Werror=return-type flag with the command
# and the compiler complains it when compiling chromium
arr_webruntime_gn_args["cros_target_cxx"]="${arr_webruntime_gn_args["cros_target_cxx"]/-Werror=return-type}"
arr_webruntime_gn_args["cros_target_cc"]="${arr_webruntime_gn_args["cros_target_cc"]/-Werror=return-type}"

GN_ARGS=""
for key in ${arr_webruntime_gn_args_idx[@]} ;do
  GN_ARGS="$GN_ARGS ${arr_webruntime_gn_args["$key"]}"
done

export PATH="$(realpath ../depot_tools):$PATH"
OUT_DIR="out/Release_sdk"

if [ ! -f $OUT_DIR/build.ninja ] || [[ $(realpath $0) -nt $OUT_DIR/build.ninja ]]; then
  echo GN_ARGS is $GN_ARGS
  gn gen $OUT_DIR --args="$GN_ARGS"
fi

# Limit jobs up to # of cpus - 2 not to use 100% cpu
JOBS_CPU=$(($(nproc --all) - 2))
# Limit jobs based on available memory (2G per one job) to avoid OOM
JOBS_RAM=$(($(free -g | grep Mem | awk '{ print $7}') / 2 ))
JOBS_MAX=$(($JOBS_CPU > $JOBS_RAM ? $JOBS_RAM : $JOBS_CPU))
echo "Using -j $JOBS_MAX"

ninja -j $JOBS_MAX -C $OUT_DIR ${TARGET}
EOF
    chmod +x $filename
}

addtask do_exportbuildscript after do_configure
