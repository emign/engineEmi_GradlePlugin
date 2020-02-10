![version](https://img.shields.io/bintray/v/emign/engineEmi/engineEmi?color=blue&label=latest%20version&style=flat-square)
```
buildscript {
    repositories {
        maven { url = uri("https://dl.bintray.com/emign/engineEmi") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
    dependencies {
        classpath("me.emig:engineEmiGradlePlugin:ENGINEEMIVERSION")
    }
}

apply plugin: me.emig.engineEmi.gradle.EngineEmiGradlePlugin
```
`gradle wrapper --gradle-version=5.6.4`.
Add to `settings.gradle` : `enableFeaturePreview('GRADLE_METADATA')` 