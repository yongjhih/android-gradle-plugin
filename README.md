android-gradle-plugin
=====================

Import from aosp https://android.googlesource.com/a/platform/tools/base

Usage
=====

The precompiled maven has been push into https://github.com/yongjhih/android-gradle-plugin.m2:

```gradle
repositories {
    maven { url 'https://github.com/yongjhih/android-gradle-plugin.m2/raw/master/' }
}

dependencies {
    classpath 'com.android.tools.build:gradle:0.14.2'
}
```

Compilation
===========

```bash
$ repo init -u https://android.googlesource.com/a/platform/manifest -b  gradle_0.13.1
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
