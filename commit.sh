#!/bin/bash

#     Created by Louis Teboul (a.k.a Androguide.fr) 
#     admin@androguide.fr
#     http://androguide.fr // http://pimpmyrom.org
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

CYAN="\\033[1;36m"
RED="\\033[1;31m"
GREEN="\\033[1;32m"
RESET="\\e[0m"

git add .
git add -u
git reset HEAD .gradle/
git reset HEAD .idea/
git status
echo -e -n "${CYAN}Create commit message & push changes now?  (${GREEN}Y${RESET}/${RED}N${RESET}${CYAN})${RESET} 
"
read input
if [[ $input == 'Y' || $input == 'y'  ]]; then
	echo -e -n "${CYAN}Please input your commit message:${RESET} 
"
read commit
git commit -m $commit
git push -u origin master
fi
