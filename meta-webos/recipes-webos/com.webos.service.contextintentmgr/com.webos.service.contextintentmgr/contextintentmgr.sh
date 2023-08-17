#!/bin/sh
# Copyright (c) 2019-2023 LG Electronics, Inc.

# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at

# http://www.apache.org/licenses/LICENSE-2.0

# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# SPDX-License-Identifier: Apache-2.0

# set the directory path for CIM nodes
export CIM_NODES_DIR="/usr/palm/services/com.webos.service.contextintentmgr/.nodered"

# set the user directory path for CIM
export CIM_USER_DIR="/var/cim/.nodered"

# ensure that CIM user directories exist
mkdir -p $CIM_USER_DIR
mkdir -p ${CIM_USER_DIR}/node_modules

# set user directories permission level to nobody
chown -R nobody:nogroup $CIM_USER_DIR

# create symlink for default CIM nodes, needed for CIM runtime
for NODE_PATH in ${CIM_NODES_DIR}/node_modules/*; do
    destlink=${CIM_USER_DIR}/node_modules/$(basename "$NODE_PATH")
    if [ ! -e $destlink ]; then
        ln -s $NODE_PATH $destlink
    fi
done

# make a copy of package.json
package=${CIM_USER_DIR}/package.json
if [ ! -f $package ]; then
    cp ${CIM_NODES_DIR}/package.json $CIM_USER_DIR
fi
