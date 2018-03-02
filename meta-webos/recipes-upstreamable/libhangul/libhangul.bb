# Copyright (c) 2016-2017 LG Electronics, Inc.

SUMMARY = "Hangul input method library"
AUTHOR = "Pugalendhi Ganesan <pugalendhi.ganesan@lge.com>"
HOMEPAGE = "https://github.com/choehwanjin/libhangul"
SECTION = "libs"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=a6f89e2100d9b6cdffcea4f398e37343"

PR = "r2"

inherit gettext
inherit autotools
inherit pkgconfig

SRCREV = "4a71565b289ac0503673002527584dd356f70719"
PV = "0.1.0+git${SRCPV}"

EXTRA_OECONF += "--libdir=${libdir}/maliit/plugins"

SRC_URI = "git://github.com/choehwanjin/libhangul.git \
    file://0001-Change-the-project-to-address-code.google.com-p-libh.patch \
    file://0002-Add-rule-to-auto-update-when-you-make-dist-ChangeLog.patch \
    file://0003-Change-wrong-name-hangul-jongseong-dicompose-decomp.patch \
    file://0004-Move-internal-implementation-to-hangulinternals.h.patch \
    file://0005-Do-not-use-the-fullpath-to-the-file-name-from-Doxyge.patch \
    file://0006-Doxygen-documentation-updates.patch \
    file://0007-Add-your-ignore-list.patch \
    file://0008-Generate-improved-doxygen-rules.patch \
    file://0009-delete-the-incorrect-Chinese-character.patch \
    file://0010-Update-documents.patch \
    file://0011-Additional-instructions-on-how-to-compile-using-pkg-.patch \
    file://0012-Fix-some-build-warnings.patch \
    file://0013-Add-missing-place-in-the-table-hangul_jamo_to_cjamo.patch \
    file://0014-Add-unit-test-case-for-hangul_jamo_to_cjamo.patch \
    file://0015-fix-typo-in-doxygen.patch \
    file://0016-ISSUE.9-incorrect-Chinese-character-modification.patch \
    file://0017-Additional-symbol-conversion-table-provided-by-the-M.patch \
    file://0018-memory-leak-fixes-that-may-occur-in-hanja_list_new.patch \
    file://0019-Fix-incorrect-sizeof-expression.patch \
    file://0020-Fix-buid-error-required-file-ABOUT-NLS-not-found.patch \
    file://0021-Add-MS-IME-compatibility-function-input-function.patch \
    file://0022-Allow-backspace-processing-in-hangul_ic_process-func.patch \
    file://0023-Add-backspace-test-case-two-sets.patch \
    file://0024-Input-context-input-option-setting-function-added-au.patch \
    file://0025-Add-option-HANGUL_IC_OPTION_COMBI_ON_DOUBLE_STROKE.patch \
    file://0026-Add-option-HANGUL_IC_OPTION_NON_CHOSEONG_COMBI.patch \
"
S = "${WORKDIR}/git"

FILES_${PN} += "${libdir}/maliit/plugins/*"
FILES_${PN}-staticdev += "${libdir}/maliit/plugins/*.a"

# imemanager loads libhangul.so file dynamically
# from maliit/plugins path, since it is using unversioned symlinks
# any change in libhangul will not affect imemanager
INSANE_SKIP_${PN} = "dev-so"
