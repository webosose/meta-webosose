#!/bin/sh
# Copyright (c) 2021-2023 LG Electronics, Inc.
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

# First, if the cdc_ether isn't built as a module, there's nothing to do
if ! zgrep -qs CONFIG_USB_NET_CDCETHER=m /proc/config.gz; then
    exit 0
fi

# To provide enough time for inbuilt adapter to be configured

if ! grep -qs qemu /etc/hostname ; then
    sleep 5
    /sbin/modprobe cdc_ether
fi
