#!/bin/sh
# Copyright (c) 2019-2023 LG Electronics, Inc.
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

if [ "$(id -u)" -ne "0" ]; then
	echo "This script requires root."
	exit 1
fi

PREF_DIR="/var/luna/preferences"
DEVICE="/dev/mmcblk0"
PART="2"

updatePartitionTable() {
	start=$(fdisk -l ${DEVICE}|grep ${DEVICE}p${PART}|awk '{print $2}')

	fdisk ${DEVICE} <<EOF
p
d
2
n
p
2
$start

No
w
EOF
	echo "Partition table updated!"
}

if [ -f ${PREF_DIR}/rootfs_resized ]; then
	echo "Resize rootfs done already!"
else
	updatePartitionTable
	partprobe
	resize2fs ${DEVICE}p${PART}
	touch ${PREF_DIR}/rootfs_resized
	echo "Resize rootfs done!"
fi
