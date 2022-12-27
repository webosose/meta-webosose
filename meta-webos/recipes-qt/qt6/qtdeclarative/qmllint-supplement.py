#!/usr/bin/env python3
#
# Copyright (c) 2020-2023 LG Electronics, Inc.
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

import os
import sys
import regex

if len(sys.argv) < 2:
    raise Exception("Missing input file")

file_name = sys.argv[1]
f = open(file_name)
code_text = f.read()
f.close()
pattern_count = 0
warning_count = 0

regex_jsfunction = r"[^\S\r\n]+function\s+(\w+)\s*\(.*\)\s*(\{[^{}]*+(?:(?2)[^{}]*)*+\})"
regex_jsstring = r"\"[^\"]*\"|'[^']*'"
regex_connections = r"[^\S\r\n]+Connections\s*(\{[^{}]*+(?:(?1)[^{}]*)*+\})"
regex_signal = r"[^\S\r\n]+(on\w+)\s*:"
regex_relative_url = r"property\s+url\s+\w+\s*\:\s*(([\"\'])[\w\.]+\/.*\2)"
regex_inline_shader = r"(?:vertex|fragment)Shader\s*\:\s*([\'\"])[^\'\"]*void\s+main\s*\(.*\)[^\'\"]*\1"

# Drop comments
code_text = regex.sub(r"\/\/.*\n", "", str(code_text))
code_text = regex.sub(r"(?s)\/\*.*?\*\/", "", str(code_text))

print("Inspecting {}".format(file_name))

# Case: Referring an object of the same name within a function
matches = regex.finditer(regex_jsfunction, code_text)
for m, match in enumerate(matches, start=1):
    function_name = match.group(1)
    function_body = match.group(2)
    function_body = regex.sub(regex_jsstring, "", function_body)
    match_name = regex.search(r"\s+\b({})\.\b".format(function_name), function_body)
    if match_name:
        if regex.search(r"\b{}\b".format(function_name), function_body[:match_name.start()]) == None:
            print("Referring an object of the same name in function '{}':".format(function_name))
            print("{}\n".format(match.group()))
            pattern_count += 1

# Case: Obsolete signal connection syntax in Connections (warning)
matches = regex.finditer(regex_connections, code_text)
for m, match in enumerate(matches, start=1):
    connections_body = match.group(1)
    mmatches = regex.finditer(regex_signal, connections_body)
    for mm, mmatch in enumerate(mmatches, start=1):
        signal_name = mmatch.group(1)
        print("(Warning) Obsolete signal connection syntax for '{}' in Connections:".format(signal_name))
        print("{}\n".format(match.group()))
        warning_count += 1

# Case: Relative URLs should be resolved explicitly (PLAT-126594, QTBUG-76879)
matches = regex.finditer(regex_relative_url, code_text)
for m, match in enumerate(matches, start=1):
    print("Direct use of relative URL not allowed, use: Qt.resolvedUrl(" + match.group(1) + ") instead\n")
    print("{}\n".format(match.group()))
    pattern_count += 1

# Case: Inline shader code is no longer allowed for fragmentShader and vertexShader in ShaderEffect
matches = regex.finditer(regex_inline_shader, code_text)
for m, match in enumerate(matches, start=1):
    print("Properties 'fragmentShader' or 'vertexShader' should be a url, not an actual shader:\n" + code_text[match.start():match.end()] + "\n")
    print("{}\n".format(match.group()))
    pattern_count += 1

# Finish with summary
if os.environ.get("WEBOS_QMLLINT_ERROR_ON_WARNING") == "1":
    pattern_count += warning_count;
if pattern_count > 0:
    print("Found {} problematic pattern(s) in {}.\n".format(pattern_count, file_name))
    sys.exit(1)

sys.exit(0)
