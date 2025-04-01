# Copyright (c) 2014-2025 LG Electronics, Inc.

WEBOS_PREFERRED_GFX_IMAGE_FORMAT ??= ""
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST ??= "webos-image-converter.list"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED ??= "1"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_UTILITY ??= ""

VIRTUAL-RUNTIME_webos-image-conversion-utility ?= ""

WEBOS_PREFERRED_GFX_IMAGE_FORMAT_RDEPENDS = "${@ \
    '${VIRTUAL-RUNTIME_webos-image-conversion-utility}' \
    if '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED}' == '1' else \
    '' \
}"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_RDEPENDS[vardepvalue] = "${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_RDEPENDS}"
RDEPENDS:${PN}:append = " ${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_RDEPENDS}"

WEBOS_PREFERRED_GFX_IMAGE_FORMAT_DEPENDS = "${@ \
    '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_UTILITY}' \
    if '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED}' == '1' else \
    '' \
}"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_DEPENDS[vardepvalue] = "${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_DEPENDS}"
DEPENDS:append = " ${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_DEPENDS}"

WEBOS_PREFERRED_GFX_IMAGE_FORMAT_INTERNAL = "${@ \
    '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT}' \
    if '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED}' == '1' else \
    '' \
}"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_INTERNAL[vardepvalue] = "${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_INTERNAL}"

WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST_INTERNAL = "${@ \
    '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST}' \
    if '${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED}' == '1' else \
    '' \
}"
WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST_INTERNAL[vardepvalue] = "${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST_INTERNAL}"

inherit webos_filesystem_paths

def webos_preferred_gfx_image_format_find_app_info_dir(d, target_dir):
    import fnmatch

    matches = []
    for root, dirnames, filenames in os.walk(target_dir):
        for filename in fnmatch.filter(filenames, 'appinfo.json'):
            matches.append(os.path.join(root, filename))
    return matches


def webos_preferred_gfx_image_format_parse_json(d, f):
    import json
    if os.path.isfile(f):
        with open(f, 'r') as the_file:
            return json.loads(the_file.read())
    return []


def webos_preferred_gfx_image_format_detect_native_usage(d, app_info):
    keys = ['splashBackground', 'bgImage']
    data = webos_preferred_gfx_image_format_parse_json(d, app_info)

    extension = d.getVar('WEBOS_PREFERRED_GFX_IMAGE_FORMAT_INTERNAL')
    if extension == '':
        return

    pn = d.getVar('PN')

    offending_keys = {}
    for key in keys:
        if key in data and not data[key].endswith(extension):
            offending_keys[key] = data[key]

    if len(offending_keys) > 0:
        bb.note('%s is not using native image format in %s' % (pn, offending_keys))

    return offending_keys


def webos_preferred_gfx_image_format_generate_conversion_file(d, base_dir, images):
    extension = d.getVar('WEBOS_PREFERRED_GFX_IMAGE_FORMAT_INTERNAL')
    if extension == '' or len(images) == 0:
        return
    out_file = base_dir + '/' + d.getVar('WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST_INTERNAL')
    with open(out_file, 'w+') as the_file:
        for i in images:
            # Remove the $D from the file as that will be added correctly during the
            # post_inst step
            source = base_dir.replace(d.getVar('D'), '') + '/' + images[i]
            target = source[0 : len(source) - len(extension)] + extension
            line = '%s %s\n' % (source, target)
            the_file.write(line)


fakeroot python do_convert_webos_preferred_gfx_image_format () {
    enabled = d.getVar('WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED')
    pn = d.getVar('PN')
    if enabled != '1':
        bb.note('%s has explicitly disabled image conversion' % pn)
        return

    app_info_dir = webos_preferred_gfx_image_format_find_app_info_dir(d, d.getVar('D'))
    if not app_info_dir:
        bb.fatal("Could not find %s:appinfo.json, no conversion will be performed" % pn)
        return

    for app_info in app_info_dir:
        base = app_info.replace('/appinfo.json','')
        images = webos_preferred_gfx_image_format_detect_native_usage(d, app_info)
        webos_preferred_gfx_image_format_generate_conversion_file(d, base, images)
}

addtask do_convert_webos_preferred_gfx_image_format after do_install before do_populate_sysroot do_package

# Both the original image file and appinfo.json remain untouched after the conversion
# due to the following:
#   1) The files might be used elsewhere for example second screen apps
#   2) The platform remains flexible and does not enforce it
# Hence the UI layer needs to probe the existance of the native version
pkg_postinst:${PN}:append() {
    enabled=${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_ENABLED}
    if [ "$enabled" = "1" ] ; then
        pncorrected=${PN}
        if [ -n "${STARFISH_TV_BASENAME}" ]; then
            if [ "${PN}" != "${STARFISH_TV_BASENAME}" ] ; then
                pncorrected=${STARFISH_TV_BASENAME}
            fi
        fi
        f=$D${webos_applicationsdir}/$pncorrected/${WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST}

        if [ -e $f ] ; then
            files=`cat $f`
            set -- $files
            while [ $# -gt 0 ]; do
                sh $D${sbindir}/webos-image-converter.sh $D$1 $D$2
                shift 2
            done
            # Cannot remove the 'WEBOS_PREFERRED_GFX_IMAGE_FORMAT_CONVERSION_LIST' file due to the fact that some applications get moved around
        else
            echo "No image conversions needed for ${PN}"
        fi
    fi
}
