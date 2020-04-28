import java.io.PrintWriter
import java.net.URL
import java.util.*

buildscript {
	repositories {
		maven { url = uri("https://plugins.gradle.org/m2/") }
		mavenCentral()

	}
	dependencies {
		classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4")
		classpath("org.jetbrains.dokka:dokka-gradle-plugin:0.10.1")
		classpath("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
		classpath("com.gradle.publish:plugin-publish-plugin:0.10.1")
	}
}

plugins {
	kotlin("jvm") version "1.3.72"
	//id("com.moowork.node") version "1.3.1"
	id("maven-publish")
	id("maven")
	id("org.jetbrains.dokka") version "0.10.1"
	id("java")
	id("com.gradle.plugin-publish") version "0.10.1"
	id("java-gradle-plugin")
}

repositories {
	mavenCentral()
	maven { url = uri("https://dl.bintray.com/korlibs/korlibs/") }
	maven { url = uri("https://plugins.gradle.org/m2/") }
}

gradlePlugin {
	plugins {
		create("engineEmi") {
			id = "me.emig.engineEmi"
			displayName = "EngineEmi"
			description = "Multiplatform Game Engine for Kotlin"
			implementationClass = "me.emig.me.gradle.EngineGradlePlugin"
		}
	}
}


val GROUP_ID: String by project
val ARTIFACT_ID: String by project
val SITE_URL: String by project
val VCS_URL: String by project
val BINTRAY_ORGANIZATION: String by project
val BINTRAY_REPOSITORY: String by project
val engineEmiLibVersion: String by project
val pluginVersion: String by project
val korgeVersion: String by project
val kotlinVersion: String by project

group = GROUP_ID
version = pluginVersion

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
	kotlinOptions {
		//jvmTarget = "1.8"
		jvmTarget = "1.6"
	}
}


dependencies {
	api("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgeVersion")
	implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
	implementation(gradleApi())
	implementation(localGroovy())
}

val sourceSets: SourceSetContainer by project
val publishing: PublishingExtension by project

val sourcesJar by tasks.creating(Jar::class) {
	archiveClassifier.set("sources")
	from(sourceSets["main"].allSource)
}

val javadocJar by tasks.creating(Jar::class) {
	archiveClassifier.set("javadoc")
}

publishing {
	publications {
		maybeCreate<MavenPublication>("maven").apply {

			repositories {
				maven {
					credentials {
						username = "emign"
						password = System.getenv("bintrayApiKey")

					}
					url = uri(
						"https://api.bintray.com/maven/emign/engineEmi/engineEmi/"

					)
				}
			}
			groupId = GROUP_ID
			artifactId = ARTIFACT_ID
			version = pluginVersion

			pom {
				name.set(ARTIFACT_ID).toString()
				description.set(SITE_URL).toString()
				url.set(VCS_URL).toString()

				scm {
					url.set(VCS_URL).toString()
				}
			}
		}
	}
}

fun ByteArray.encodeBase64() = Base64.getEncoder().encodeToString(this)

val release by tasks.creating {
	dependsOn("publish")
	group = "publishing"

	doLast {
		val subject = BINTRAY_ORGANIZATION
		val repo = "engineEmi"
		val _package = ARTIFACT_ID
		val version = pluginVersion

		((URL("https://bintray.com/api/v1/content/$BINTRAY_ORGANIZATION/$BINTRAY_REPOSITORY/$BINTRAY_REPOSITORY/$version/publish")).openConnection() as java.net.HttpURLConnection).apply {

			requestMethod = "POST"
			doOutput = true


			//setRequestProperty("Authorization", "Basic " + "$publishUser:$publishPassword".toByteArray().encodeBase64().toString())
			setRequestProperty(
				"Authorization",
				"Basic " + "emign:${System.getenv("bintrayApiKey")}".toByteArray().encodeBase64().toString()

			)
			PrintWriter(outputStream).use { printWriter ->
				printWriter.write("""{"discard": false, "publish_wait_for_secs": -1}""")
			}
			println(inputStream.readBytes().toString(Charsets.UTF_8))
		}
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
