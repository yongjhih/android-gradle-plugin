android-gradle-plugin
=====================

Import from aosp https://android.googlesource.com/platform/tools/base

Usage
=====

* build.gradle:

```gradle
android {
    defaultConfig {
        multiDexEnabled true
        multiDexKeepProguard file('multiDexKeep.pro') // keep specific classes using proguard syntax
        multiDexKeepFile file('multiDexKeep.txt') // keep specific classes
    }
}
dependencies {
    compile 'com.android.support:multidex:1.0.0'
}
```

* multiDexKeep.pro for example (e.g. build/intermediates/multi-dex/debug/manifest_keep.txt):

```proguard
-keep public class uk.co.senab.photoview.PhotoView {
    <init>();
}
```

* multiDexKeep.txt for example (e.g. build/intermediates/multi-dex/debug/maindexlist.txt):

``` 
android/support/v4/json/JSONStringer$Scope.class
android/support/v4/json/JSONArray.class
android/support/v4/json/JSONStringer.class
android/support/v4/json/JSON.class
android/support/v4/json/JSONObject$1.class
android/support/v4/json/JSONObject.class
```

Installation
============

The precompiled maven has been pushed into https://github.com/yongjhih/android-gradle-plugin.m2:

```gradle 
buildscript {
    repositories {
        maven { url 'https://github.com/yongjhih/android-gradle-plugin.m2/raw/master/' }
        mavenCentral()
    }
    dependencies {
        classpath 'com.infstory.tools.build:gradle:0.14.+'
    }
}
```

Compilation
===========

```bash
$ repo init -u https://android.googlesource.com/a/platform/manifest -b  gradle_0.13.3
$ repo sync
$ repo forall -c 'git checkout gradle_0.14.1'
$ cd base
$ git remote add yongjhih https://github.com/yongjhih/android-gradle-plugin
$ git checkout yongjhih/master
$ cd ..
$ ./gradlew prepareRepo
$ CUSTOM_GRADLE=0.14.2 ./gradlew clean assemble setupGradleInIde publishLocal
```

Finally, the archives are in {repo-root-dir}/out

Ref.
====

* http://tools.android.com/build/gradleplugin

See Also
========

* http://stackoverflow.com/a/26782043/2872224
