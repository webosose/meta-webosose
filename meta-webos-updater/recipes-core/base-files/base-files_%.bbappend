# Copyright (c) 2019 LG Electronics, Inc.

EXTENDPRAUTO_append = "webosupdater1"

generate_fstab_entries_append_sota() {
    echo "# mount /media that was not handled by ostree"
    echo "/sysroot/media /media none defaults,bind 0 0"
}
