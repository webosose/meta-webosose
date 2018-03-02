# Copyright (c) 2013 LG Electronics, Inc.
#
# webos_certificates
#
# This class is to be inherited by all packages that contain public key
# certificate files.
# Variable CERT_SOURCE_DIR must be set. It is the directory under which the
# certificate files are in the image. Example "/usr/share/ca-certificates"
# Variable CERT_TARGET_DIR must be set. It is the directory under which
# links to the certificates will be in the image. Example: "/etc/ssl"
#

DEPENDS_append = " openssl-native"

# Creates symlinks from a directory tree's files that match pattern.
# DESTDIR/file.crt -> SOURCEDIR/path/to/file.pem
# arg1: extension such as .crt for matching pattern for 'find'
# arg2: file extension such as .pem to replace the old extension
# arg3: cert file directory prefix: <prefix>here/are/the/actual/certs/
webos_certificates_linkfiles() {
    local OLDEXTENSION=$1
    local NEWEXTENSION=$2
    local TREEPREFIX=$3

    for i in $(find $SOURCEDIR -type f -name "*$OLDEXTENSION")
    do
        local NEWNAME=`basename $i | sed "s_${OLDEXTENSION}_${NEWEXTENSION}_"`
        local FILEPATH="$TREEPREFIX$i"
        local LINKPATH="$DESTDIR/$NEWNAME"
        ln -s $FILEPATH $LINKPATH
        TEMPORARY_LINKS="$TEMPORARY_LINKS;$LINKPATH"
    done
}

# Removes all files in a semicolon-separated (;) list.
# arg1: Semicolon-separated list of file paths
webos_certificates_removefiles() {
    local IFS=";"
    for path in $1;
    do
        if [ -n "$path" ]; then
            rm -f $path
        fi
    done
}

webos_certificates_linkcertificates() {
    # use relative paths
    SOURCEDIR=$(echo $1 | sed "s:^/::")
    DESTDIR=$(echo $2 | sed "s:^/::")

    # Create links for c_rehash to process.
    # Use current working directory as prefix to create working links.
    # These links will be deleted later.
    # Create similar lines if packages contain other file name extensions also. (crl, pem, ...)
    webos_certificates_linkfiles ".crt" ".pem" "`pwd`/"

    # create hash symlinks
    c_rehash $DESTDIR > /dev/null 2>&1

    # Remove the links we created before c_rehash and then replace them with new ones.
    webos_certificates_removefiles $TEMPORARY_LINKS

    # Create links to replace the previous links that were deleted.
    # These are the final ones that will work properly in the image, but not during build phase.
    # Links from c_rehash will point to these.
    # Create similar lines if packages contain other file name extensions also. (crl, pem, ...)
    webos_certificates_linkfiles ".crt" ".pem" "/"
}

webos_certificates_check_sanity() {
    if [ -z "$1" ];
    then
        bbfatal "$2 must be set!"
    fi
}

do_install_append_class-target() {
    webos_certificates_check_sanity "${CERT_SOURCE_DIR}" "CERT_SOURCE_DIR"
    webos_certificates_check_sanity "${CERT_TARGET_DIR}" "CERT_TARGET_DIR"

    cd ${D}
    webos_certificates_linkcertificates ${CERT_SOURCE_DIR} ${CERT_TARGET_DIR}
    cd -
}

