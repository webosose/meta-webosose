#!/usr/bin/env python3
#
# Copyright (c) 2020 LG Electronics, Inc.
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

import sys
import regex

if len(sys.argv) < 2:
    raise Exception("Missing input file")

file_name = sys.argv[1]
f = open(file_name)
code_text = f.read()
f.close()
pattern_count = 0

regex_jsfunction = r"[^\S\r\n]+function\s+(\w+)\s*\(.*\)\s*(\{[^{}]*+(?:(?2)[^{}]*)*+\})"
regex_jscomment = r"\/\/.*$|\/\*.*\*\/"
regex_jsstring = r"\"[^\"]*\"|'[^']*'"

print("Inspecting {}".format(file_name))

# Case: Referring an object of the same name within a function
matches = regex.finditer(regex_jsfunction, code_text)
for m, match in enumerate(matches, start=1):
    function_name = match.group(1)
    function_body = match.group(2)
    function_body = regex.sub(regex_jscomment, "", function_body)
    function_body = regex.sub(regex_jsstring, "", function_body)
    match_name = regex.search(r"\s+\b({})\.\b".format(function_name), function_body)
    if match_name:
        if regex.search(r"\b{}\b".format(function_name), function_body[:match_name.start()]) == None:
            print("Referring an object of the same name in function '{}' at line: {line}".format(function_name, line = match.start()))
            print("{}\n".format(match.group()))
            pattern_count += 1

# Finish with summary
if pattern_count > 0:
    print("Found {} problematic pattern(s).\n".format(pattern_count))
    sys.exit(1)

sys.exit(0)
