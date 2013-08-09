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
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Declare the user-defined variables at the very beginning
# so it doesn't get lost in the stdout
echo -n -e "${CYAN}Package Name?${RESET}
"
read $package

echo -n -e "${CYAN}App Name?${RESET}
"
read $name

# Install git, the sdk and gradle if the user hasn't already installed these
# echo -n -e "${CYAN}Install dependencies? (git, JDK, Android SDK, Gradle)  (${GREEN}Y${RESET}/${RED}N${RESET}${CYAN})${RESET}
# "
# read $dependencies
# if [ $dependencies=='Y' ] || [ $dependencies=='y' ] || [ $dependencies=='Yes' ] || [ $dependencies=='yes' ]; then

# 	# apt-get git & the Open JDK
# 	sudo apt-get install git git-core openjdk-6-jdk
# 	mkdir tools
# 	cd tools
	
# 	# wget gradle 1.7
# 	wget http://services.gradle.org/distributions/gradle-1.7-bin.zip 
# 	mv tools/gradle-1.7-bin.zip gradle1.7.zip
# 	unzip tools/gradle1.7.zip tools/

# 	# wget the Android SDK (rev22)
# 	wget http://dl.google.com/android/android-sdk_r22.0.5-linux.tgz tools/
# 	mv tools/android-sdk_r22.0.5-linux.tgz tools/android-sdk.tgz
# 	tar -zxvf tools/android-sdk.tgz

# 	# export PATH variables
# 	export PATH=$PATH:$DIR/tools/android-sdk/tools
# 	export PATH=$PATH:$DIR/tools/android-sdk/build-tools
# 	export PATH=$PATH:$DIR/tools/android-sdk/platform-tools
# 	export PATH=$PATH:$DIR/tools/gradle1.7/bin
# fi

# echo -e "${CYAN}Cloning APKreator mainstream repo...${RESET}"
# mkdir APKreatorProject
# cd APKreator
# git init && git remote add origin https://github.com/Androguide/APKreator.git
# git pull origin master

# Prompt the user to create his icon using AssetStudio
echo -n -e "${CYAN}Create launcher icon pack? (${GREEN}Y${RESET}/${RED}N${RESET}${CYAN})${RESET}
"
read input
if [[ $input == 'Y' || $input == 'y'  ]]; then
	echo -e "${CYAN}Redirecting you to Android Assets Studio, create your launcher icon and download the package.
Then, simply extract the zip package in this folder${RESET}"
	sleep 1
	echo "..."
	sleep 1
	echo "..."
	sleep 1
	echo "..."
	sleep 1
	URL='http://android-ui-utils.googlecode.com/hg/asset-studio/dist/icons-launcher.html#foreground.space.trim=1&foreground.space.pad=0&foreColor=33b5e5%2C0&crop=0&backgroundShape=bevel&backColor=ffffff%2C100'
	[[ -x $BROWSER ]] && exec "$BROWSER" "$URL"
	path=$(which xdg-open || which gnome-open) && exec "$path" "$URL"
	echo "Can't find browser"
else
	echo -e "${CYAN}Skipping icon pack creation...${RESET}"
fi

echo -e "${GREEN}Building with package name '$package' and app name '$name'...${RESET}"

# Replace all occurences (including the app_name string)
# of the default package name (com.androguide.apkreator) & app name (APKreator)
sed -i 's/APKreator/$name/g' APKreator/res/values/strings.xml
sed -i 's/com.androguide.apkreator/$package' APKreator/build.gradle
gradle build release


