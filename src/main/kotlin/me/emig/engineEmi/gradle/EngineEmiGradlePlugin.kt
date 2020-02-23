package me.emig.engineEmi.gradle

import com.soywiz.korge.gradle.KorgeGradlePlugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import java.net.URI


val engineEmiVersion = "0.42"
//val engineEmiVersion findProperty("engineEmiAndPluginVersion")
val korgeBintrayUrl = "https://dl.bintray.com/korlibs/korlibs/"
val engineEmiBintrayUrl = "https://dl.bintray.com/emign/engineEmi/"


open class EngineEmiGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {


        project.repositories.apply {
            maven {
                it.url = URI(korgeBintrayUrl)
                it.content {
                    it.excludeGroup("Kotlin/Native")
                }
            }
            maven {
                it.url = URI(engineEmiBintrayUrl)
                it.content {
                    it.excludeGroup("Kotlin/Native")
                }
            }
            jcenter().content{
                it.excludeGroup("Kotlin/Native")
            }
            mavenCentral().content {
                it.excludeGroup("Kotlin/Native")
            }
        }


        project.pluginManager.apply(KorgeGradlePlugin::class.java)


        project.dependencies.add("commonMainApi","me.emig:engineEmi:$engineEmiVersion")

        project.tasks.register("openLocal") {
            it.group = "engineEmi"
            it.dependsOn("runJvmFirstThread")
        }

        project.tasks.register("openInBrowser") {
            it.group = "engineEmi"
            it.dependsOn("jsWebRun")
        }

        project.tasks.register("runTests", Test::class.java){

        }









    }
}

