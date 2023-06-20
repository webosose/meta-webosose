#!/bin/sh
# Copyright (c) 2017-2018 LG Electronics, Inc.
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

# first pass we register file cache and dbkinds
logger -s "Configuring dbkinds & filecache"
/usr/bin/luna-send -n 1 palm://com.palm.configurator/run '{"types":["dbkinds","filecache"]}'

# This has to happen *after* the kinds are created.
logger -s "Configuring dbpermissions"
/usr/bin/luna-send -n 1 palm://com.palm.configurator/run '{"types":["dbpermissions"]}'
