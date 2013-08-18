APKreator
=========

####Create gorgeous Android root apps in seconds with a few lines of XML!
APKreator abstracts Android application development through an XML-driven Domain-Specific Language (DSL).
This abstraction layer is based on the plugin system I developed for [Pimp My Rom](http://pimpmyrom.org).
It is divided in two distinct parts:

* **The Container:** a "blank" application that you will configure through your XML files. (More containers coming soon).
* **The Plugin System:** your XML configuration files, which you will learn everything about below **--> YOUR Part of the work**


## Example implementation
Before diving into the documentation, let me show you how quick and easy it is to create a beautiful root app with APKreator.
For this example, we will mimic some functionnalities of the "Network & Internet" section of [Pimp My Rom](https://play.google.com/store/apps/details?id=com.androguide.pimpmyrom).

Here is what the "blank" container looks like before we do any work:

![blank-container](http://imageshack.us/a/img835/8608/271v.png)


##### The Plugin Folder
First off, you need to know that all APKreator plugin files live under the **/sdcard/.APKreator** (hidden) folder.   
Until the web-app which will allow you to compile the container with your package name, icon and package your plugin files into the container's assets, this is the location the app grabs its plugins from.
  
  
##### App Configuration (config.xml)
So let's create our *config.xml*, this is the file where we define our app's title, color-scheme, amount of tabs, tabs headers etc...   
Let's say that we want our app to:  
* Be named Pimp My Rom
* Have a blue color-scheme *(the Google Play Books blue: #3F9FE0)*
* Have 3 tabs (we will only fill the first one in this example), respectively named "Network & Internet", "Multitasking", "Kernel"
* Have the Pimp My Rom icon.   
  For this, we simply drop the icon in */sdcard/.APKreator* and name it *icon.png*, done!   
   

So here is the corresponding XML code for our config.xml file:
```xml
<plugin>
    <config app-name="Pimp My Rom"
            app-color="#3F9FE0"
            tabs-amount="3"
            tab0="Network & Internet"
            tab1="Multitasking"
            tab2="Kernel"/>
</plugin>
```
Easy, right?!  
Here's the result of these 8 small lines of code:  
  
![empty-container-configured](http://img194.imageshack.us/img194/3169/rlwb.png)
  
  
  
  
  
#####Adding UI elements to a tab and controlling build.prop tweaks & shell commands
Now let's create a /tabs folder in /sdcard/.APKreator, and in this folder we are going to create 3 empty files:  
tab0.xml, tab1.xml and tab2.xml (one for each corresponding entry in config.xml)

In our first tab (Network & Internet), we want to mimic some Pimp My Rom features, namely:  
* **Wifi Scan Interval**: this is a build.prop tweak which takes a numerical value, so we will control it with a SeekBar to give the user full control. _(wifi.supplicant_scan_interval)_
* **HSUPA** : We want to allow the user to enable or disable HSUPA network mode to their liking. So we are going to use a switch and declare the values we want for the ON and OFF states respectively. _(ro.ril.hsxpa)_
* **Block Redirects** : A shell command which blocks network redirections using sysctl. For this we only need a button which will execute our command on click. _(busybox sysctl -e -w net.ipv4.conf.default.accept_redirects=0;)_
* **Wifi Connect Speed** : a build.prop tweak for which we want to provide several choices for the value. We are thus going to use a spinner, where each entry is a value for the build property declared in the ```<prop>``` tag

Here is our tab0.xml:  
```xml
<plugin>
    <card type="build.prop">
        <name>Wifi Scan Interval</name>
        <description>This determines the interval of time at which the system automatically scans for available Wi-Fi networks. A high value will help with battery life.
        </description>
        <prop>wifi.supplicant_scan_interval</prop>
        <control type="seekbar"/>
        <min-value>0</min-value>
        <max-value>120</max-value>
        <default-value>15</default-value>
        <unit>sec.</unit>
    </card>

    <card type="build.prop">
        <control type="switch"
                 on="3"
                 off="2"/>
        <name>Enable HSUPA</name>
        <description>Enable or disable HSUPA network mode</description>
        <prop>ro.ril.hsxpa</prop>
    </card>
    
    <card type="shell">
        <control type="button"
                 text="Apply"/>
        <command>busybox sysctl -e -w net.ipv4.conf.default.accept_redirects=0;</command>
        <name>Block Redirects</name>
        <description>Blocks all internet redirections. Warning: some websites might become unavailable.</description>
    </card>
    
    <card type="build.prop">
        <name>Wifi Connect Speed</name>
        <description>Reduces or increases the built-in delay for connecting to Wi-Fi</description>
        <prop>ro.mot.eri.losalert.delay</prop>
        <control type="spinner"
                 values-amount="6"
                 value0="200"
                 value1="400"
                 value2="600"
                 value3="800"
                 value4="1000"
                 value5="1200"/>
    </card>
</plugin>
```

And we are all done! Our 4 tweaks are now fully functionnal, and our app looks stunning!  
  
![screenshot1](http://imageshack.us/a/img708/4104/fa10.png) - ![screenshot2](http://img829.imageshack.us/img829/3426/l286.png) - ![screenshot3](http://img855.imageshack.us/img855/2917/wlgm.png) - ![screenshot5](http://img12.imageshack.us/img12/1165/2wby.png) - ![screenshot6](http://img542.imageshack.us/img542/7821/7fwi.png) - ![screenshot7](http://img203.imageshack.us/img203/3590/uqed.png) - ![screenshot8](http://img33.imageshack.us/img33/6660/1wmy.png)
