#!/bin/bash

# Copyright (c) 2013 LG Electronics, Inc.
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

cat latest_project_baselines.txt | { while read line
do
        path=$(echo $line | awk '{ print $1 }')
        previous_commit=$(echo $line |awk '{ print $2 }')
        if [ -d "$path" ]; then
                cd $path
                current_commit=$(git log --pretty=format:'%H' -n 1)
                if [ $path = "." ]; then
                        echo "Root level"
                else
                        echo $path
                fi
                echo "-------------------------------------------------------------"
                if [ $current_commit = $previous_commit ]; then
                        echo "No changes"
                else
                        echo "Changes:"
                        git log $previous_commit..$current_commit
                fi
                echo "-------------------------------------------------------------"
                printf "\n"
                if [ $path != "." ]; then
                        cd ..
                fi
        else
                echo "-------------------------------------------------------------"
                echo "Warning: latest_project_baselines.txt contains layer $path"
                echo "that does not exists"
                echo "Layer is removed from change logging in next"
                echo "latest_project_baselines.txt update."

                echo "-------------------------------------------------------------"
                printf "\n"
        fi
done
}
