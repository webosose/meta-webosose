#!/bin/bash

# Copyright (c) 2013-2025 LG Electronics, Inc.
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

if [ $# -lt 1 ] ; then
  cat <<EOF
Usage: $0 build_number [URL]
       build_number - some always increasing number (e.g. \${BUILD_NUMBER} from jenkins)
       URL - optional parameter, if provided scp will be used to
             download and update \${URL}/latest_project_baselines.txt
             and also \${URL}/history will be populated
EOF
exit 1
fi

BN=$1
URL=$2

if [ -n "$URL" ] ; then
  if ! scp "${URL}/latest_project_baselines.txt" . ; then
    # create directories on target side
    TARGET=`echo ${URL} | sed 's/:.*//g'`
    DIRECTORY=`echo ${URL} | sed 's/.*://g'`
    echo "Trying to create ${DIRECTORY} ${DIRECTORY}/history on ${TARGET}"
    ssh ${TARGET} mkdir -p ${DIRECTORY} ${DIRECTORY}/history
  fi
fi

if [ ! -f latest_project_baselines.txt ] ; then
  # create dummy file
  echo ". origin/master" > latest_project_baselines.txt
fi

`dirname $0`/build_changes.sh > build_changes.log
`dirname $0`/set_commits.sh

cp latest_project_baselines.txt latest_project_baselines_${BN}.txt

if [ -n "$URL" ] ; then
  scp latest_project_baselines.txt "${URL}/"
  scp latest_project_baselines_${BN}.txt "${URL}/history/"
fi
