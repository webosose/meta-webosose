#!/bin/sh
# Copyright (c) 2018-2023 LG Electronics, Inc.
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

if [ -e /var/lib/bluetooth/debug-mode ] ; then
    PmLogCtl def webos-bluetooth-service debug
    # TODO : Need to change the "bluez5" to "@WEBOS_BLUETOOTH_SIL@"
    # The "@WEBOS_BLUETOOTH_SIL@" comes from webos-bluetooth-service.bb file
    # So this script uses 'bluez5' instead of "@WEBOS_BLUETOOTH_SIL@" temporarily.
    PmLogCtl def bluetooth-sil-bluez5 debug
fi
