# Backport https://github.com/kraj/meta-clang/commit/464230f0007e94beb2ca03018553ebcb18b0a569
PASSTHROUGH:append = "LLVM_BUILD_TOOLS;LLVM_USE_HOST_TOOLS;LLVM_CONFIG_PATH;\"
EXTRA_OECMAKE:append:class-nativesdk = "-DLLVM_CONFIG_PATH=${STAGING_BINDIR_NATIVE}/llvm-config"
EXTRA_OECMAKE:append:class-target = "-DLLVM_CONFIG_PATH=${STAGING_BINDIR_NATIVE}/llvm-config"
