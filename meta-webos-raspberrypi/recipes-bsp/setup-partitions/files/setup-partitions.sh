#!/bin/sh
# Copyright (c) 2023 LG Electronics, Inc.
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
PART=$(mount | grep "\/ " | cut -c 14-14)

updatePartitionTable() {
	# get disk size
	DISK_BYTES=`fdisk /dev/mmcblk0 -l | head -n 1 | awk '{print $5}'`

	# part
	part2_start=$(fdisk -l ${DEVICE}|grep ${DEVICE}p${PART}|awk '{print $2}')
	orgend=$(fdisk -l ${DEVICE}|grep ${DEVICE}p${PART}|awk '{print $3}')
	end=$(fdisk -l ${DEVICE}|head -n 1|awk '{print $7}')

	# rootfs 'A' == 2.5 GB
	part2_sectors="+5242880"
	part3_start=$(( $part2_start + $part2_sectors + 1024 ))

	# rootfs 'B',
	if [ $DISK_BYTES -gt 7864320000 ]; then
		# > 7.5GB (maybe > 8GB SD),
		# create 'B' partition as normal size
		part3_sectors="+5242880"
	else
		# othersize, just create as 'padding partition'
		part3_sectors="+100"
	fi
	part4_start=$(( $part3_start + $part3_sectors + 1024 ))

	# do fdisk
	fdisk ${DEVICE} <<EOF
d
2
n
p
2
$part2_start
$part2_sectors
No
n
p
3
$part3_start
$part3_sectors

n
e
$part4_start


n

+1G
n

+100M
n



w
EOF
	partprobe
	echo "Partition table updated!"
}

createPartitions() {
	# create partitions
	mkfs.ext4 /dev/mmcblk0p5 # /var
	mkfs.ext4 /dev/mmcblk0p6 # /var/db
	mkfs.ext4 /dev/mmcblk0p7 # /media

	# assign labels
	e2label /dev/mmcblk0p2 root_0
	e2label /dev/mmcblk0p3 root_1
	e2label /dev/mmcblk0p5 var
	e2label /dev/mmcblk0p6 db
	e2label /dev/mmcblk0p7 media

	# initialize var content from org rootfs
	mount /dev/mmcblk0p5 /mnt
	cp -ra /var/* /mnt
	mkdir -p /mnt/var
	umount /mnt

	# initialize var/db part
	mount /dev/mmcblk0p6 /mnt
	mkdir -p /mnt/temp
	umount /mnt

	# initialize media part
	mount /dev/mmcblk0p7 /mnt
	mkdir -p /mnt/home
	cp -ra /media/* /mnt
	cp -ra /home/* /mnt
	umount /mnt
}

OVERLAY_PARTITIONS="etc bin sbin usr lib mnt"

mountPartitions() {
	# mount R/W partitions
	mount /dev/mmcblk0p5 /var
	mount /dev/mmcblk0p6 /var/db
	mount /dev/mmcblk0p7 /media

	# bind home from media (genrally largest)
	mount -o bind /media/home /home

	# overlay folders if enabled
	if [ -f /var/luna/preferences/mount_overlay_enabled ]; then
		mkdir -p /media/overlay
		for i in $OVERLAY_PARTITIONS;
		do
			mkdir -p /media/overlay/.work.$i
			mkdir -p /media/overlay/.upper.$i
			mount -t overlay overlay -o lowerdir=/$i,upperdir=/media/overlay/.upper.$i,workdir=/media/overlay/.work.$i /$i
		done
	fi
}

# check partition updated
if [ ! -b "${DEVICE}p3" ]; then
	updatePartitionTable
	createPartitions
	echo "Partition updated!"
fi

# mount partitions
mountPartitions
echo "R/W Partition mounted"
