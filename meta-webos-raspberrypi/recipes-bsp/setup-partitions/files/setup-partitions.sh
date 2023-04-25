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

# Different sizes in in 512b sectors
SECTOR_SIZE_50M="102400"
SECTOR_SIZE_0G5="1048576"
SECTOR_SIZE_1G="2097152"
# SECTOR_SIZE_2G="4194304"

updatePartitionTable() {
	# get disk size
	DISK_SECTORS=`fdisk ${DEVICE} -l | head -n 1 | awk '{print $7}'`
	part2_start=$(fdisk -l ${DEVICE}|grep ${DEVICE}p${PART}|awk '{print $2}')
	org_part2_sectors=$(fdisk -l ${DEVICE}|grep ${DEVICE}p${PART}|awk '{print $4}')

	# rootfs 'A'
	part2_sectors="$org_part2_sectors"
	part3_start=$(($part2_start + $part2_sectors + 1024))

	# rootfs 'B',
	if [ $DISK_SECTORS -gt $(($part2_sectors * 2 + $SECTOR_SIZE_1G + $SECTOR_SIZE_0G5)) ]; then
		# create 'B' partition as normal size
		part3_sectors="$part2_sectors"
	else
		# othersize, just create as 'padding partition'
		part3_sectors="100"
	fi
	part4_start=$(( $part3_start + $part3_sectors + 1024 ))

	# do fdisk
	cat > /tmp/setup-partitions.fdisk <<-EOF
	d
	2
	n
	p
	2
	$part2_start
	+$part2_sectors
	
	n
	p
	3
	$part3_start
	+$part3_sectors
	
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
	echo "Running following fdisk commands:"
	cat /tmp/setup-partitions.fdisk
	fdisk ${DEVICE} < /tmp/setup-partitions.fdisk
	rm -f /tmp/setup-partitions.fdisk
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
