#!/bin/sh
# Copyright (c) 2019-2024 LG Electronics, Inc.
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

# check available disk space
BLOCKS_TOTAL=$(stat -f -c "%b" /var/db)
BLOCKS_FREE=$(stat -f -c "%a" /var/db)

# If /var/db contain less than the minimum required free space, do factory reset
WEBOS_THRESHOLD_SIZE_MAINDB=4194304
DB_MINIMUM_SIZE_IN_BYTES=$WEBOS_THRESHOLD_SIZE_MAINDB
BLOCK_SIZE=$(stat -f -c "%s" /var/db)
BLOCKS_THRESHOLD=$(($DB_MINIMUM_SIZE_IN_BYTES/$BLOCK_SIZE))

# if not enough disk space, make factory reset and reboot.
if [ "$BLOCKS_FREE" -le "$BLOCKS_THRESHOLD" ]; then
    /usr/lib/db8/bin/errorNoSpace.bash
    exit 1
fi

# move logs
if [ -d /var/dblog ]; then
    mv /var/dblog/* /var/db
    rmdir /var/dblog
fi

/usr/sbin/mojodb_migrate /etc/palm/db8/maindb.conf
