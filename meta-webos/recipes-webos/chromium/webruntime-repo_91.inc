# Copyright (c) 2021-2022 LG Electronics, Inc.

LIC_FILES_CHKSUM += "file://oss-pkg-info.yaml;md5=c31f1ae166739be9ca186deacf4b32e4"

WEBOS_REPO_NAME = "chromium91"
WEBOS_VERSION = "91.0.4472.114-18_427f71612496e3072c1a0c2ee6126be3fa11a941"

WEBOS_REPO_NAME_V8 = "chromium-v8"
WEBOS_GIT_PARAM_BRANCH_V8 = "@chromium91"
WEBOS_VERSION_V8 = "8.7.220.29-chromium91.3_7e5684397e1522f3b5b7ea8155c2f1e22fa1cdd2"
SRCREV_v8 = "b958ec542dcb086f422a1216a959be38a4cc0339"

SRC_URI += " \
    file://0003-jinja2-fix-compatibility-with-python3-3.10.patch \
    file://0004-mojo-use-python3-to-generate-src-files.patch \
    file://0005-mojom_parser.py-Handle-exceptions-when-sharding.patch \
    file://0006-Migrate-policy-tools-to-python3.patch \
    file://0007-components-policy-use-python3-for-some-actions.patch \
    file://0008-components-policy-use-python3.patch \
    file://0009-json_schema_compiler-use-python3-for-feature_compile.patch \
    file://0010-json_schema_compiler-use-python3-in-json_schema_api..patch \
    file://0011-build-Convert-third_party-closure_compiler-compile_j.patch \
    file://0012-Reland-components-resources-use-python3-for-tools-li.patch \
    file://0013-build-Convert-third_party-node-node.gni-to-py3.patch \
    file://0014-components-policy-fix-some-places-for-python3.patch \
    file://0015-build-Convert-tools-json_to_struct-to-py3.patch \
    file://0016-build-Convert-tools-grit-preprocess_if_expr.gni-to-p.patch \
    file://0017-Sort-the-feature_set-to-avoid-non-determistic-build-.patch \
    file://0018-webos-fix-python-syntax.patch \
    file://0019-build-Convert-tools-metrics-BUILD.gn-to-py3.patch \
    file://0020-build-Convert-services-metrics-public-cpp-to-py3.patch \
    file://0021-Reland-Convert-generate_about_tracing-target-to-py3.patch \
    file://0022-Run-generate_bindings.py-under-Python-3.patch \
    file://0023-Render-bindings-with-Python-3.patch \
    file://0024-blink-use-python3-for-instrumentation_probes.patch \
    file://0025-Remove-run_under_python2-option-from-grit_rule.gni.patch \
    file://0026-catapult-Python-3-migration-of-common.patch \
    file://0027-devtools-frontend-Reland-Reland-use-python3-in-devto.patch \
    file://0028-protobuf-use-python3-for-protoc_wrapper.py.patch \
    file://0029-components-run-gen_file_type_proto.py-in-python3.patch \
    file://0030-Update-ssl_error_assistant-code-to-py3.patch \
    file://0031-Py2-Py3-for-third_party-dom_distiller_js.patch \
    file://0032-Py2-Py3-fixup-for-dom_distiller_js.patch \
    file://0033-catapult-use-python3-for-rcssmin.patch \
    file://0034-build-Convert-third_party-opus-BUILD.gn-to-py3.patch \
"
