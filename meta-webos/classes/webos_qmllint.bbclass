# Copyright (c) 2017 LG Electronics, Inc.
#
# QML syntax verifier
#

DEPENDS_append = " qtdeclarative-native libxml2-native"

inherit qmake5_paths

do_compile_prepend () {
    bbnote "Verify QML syntax(step 1): .qml or .js files stored as qresource"
    find ${S} -type f -name "*.qrc" | while read qrc; do
        local _dirname_=$(dirname $qrc)
        ${STAGING_BINDIR_NATIVE}/xmllint --xpath '//RCC/qresource/file' $qrc | sed 's/<file>//g' | sed 's/<\/file>/\n/g' | grep -E "*.qml$|*.js$" | while read file; do
            if [ -f "$_dirname_/$file" ]; then
                bbnote "Inspecting $_dirname_/$file"
                ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "$_dirname_/$file"
            fi
        done
    done
    bbnote "Done verifying QML syntax(step 1), no errors detected"
}

do_install_append () {
    bbnote "Verify QML syntax(step 2): .qml or .js files to be installed"
    find ${D} -type f -name "*.qml" -o -name "*.js" | while read file; do
        bbnote "Inspecting $file"
        ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_QT_BINS}/qmllint "$file"
    done
    bbnote "Done verifying QML syntax(step 2), no errors detected"
}
