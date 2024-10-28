#!/bin/sh
# Copyright (c) 2017-2024 LG Electronics, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0

STATIC=`luna-send -n 1 -f luna://com.webos.service.config/getConfigs '{ "configNames":["com.webos.service.ime.*"]}' | grep static | grep -oE "(true|false)"; exit 0`

# If Maliit is dynamic, we don't need to run it on startup
if [ "$STATIC" == "false" ] ; then
    stop ; exit 0
fi

if [ -f $MALIIT_CONF_FILELOCK ]
then
    rm -f $MALIIT_CONF_FILETEMP
fi

if ! [ -f $MALIIT_CONF_FILE ]
then
    mkdir -p $MALIIT_CONF_PATH
    touch $MALIIT_CONF_FILE

    echo "[maliit]" >> $MALIIT_CONF_FILE
    echo "plugins\\accessory=libstt-plugin.so" >> $MALIIT_CONF_FILE
    echo "onscreen\\enabled= \\" >> $MALIIT_CONF_FILE
    echo "libplugin-global.so:, \\" >> $MALIIT_CONF_FILE
    echo "libplugin-chinese.so:chinese, \\" >> $MALIIT_CONF_FILE
    echo "libplugin-japanese.so:japanese" >> $MALIIT_CONF_FILE
fi

# Get the display count (use 1 if unavailable or invalid)
# Start maliit-server.service instance by the display count
c="$(cat $XDG_RUNTIME_DIR/surface-manager.windowcount 2>/dev/null)"
[ "$c" -eq "$c" 2>/dev/null ] || c=1
for i in $(seq $c) ; do
    if [[ $i == 1 ]]; then
        systemctl start maliit-server@$(($i - 1))
    else
        systemctl start maliit-server@"$(($i - 1)) -no-ls2-service"
    fi
done

if [ -z "$WAYLAND_DISPLAY" ]; then
    WAYLAND_DISPLAY="wayland-0"
fi

if [ "$STATIC" == "false" ]; then
    stop
elif ( ! netstat -lx | grep -q "$XDG_RUNTIME_DIR/$WAYLAND_DISPLAY" ); then
    # Compositor seems down, don't rush.
    exec sleep 10
fi
