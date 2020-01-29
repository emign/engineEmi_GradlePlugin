![](https://img.shields.io/github/v/release/emign/engineEmi_GradlePlugin?labelColor=262B30)
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