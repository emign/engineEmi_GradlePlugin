package me.emig.engineEmi.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import java.net.URI
import com.soywiz.korge.gradle.KorgeGradlePlugin



val engineEmiVersion = "0.32"
val korgePluginVersion = "1.5.5.0"
val korgeBintrayUrl = "https://dl.bintray.com/korlibs/korlibs/"
val engineEmiBintrayUrl = "https://dl.bintray.com/emign/engineEmi/"

open class EngineEmiGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {


        project.buildscript.apply {
            repositories.apply {
                mavenCentral()
                maven {
                    it.url = URI(korgeBintrayUrl)
                    it.content { innerIt ->
                        innerIt.excludeGroup("Kotlin/Native")
                    }
                }
                maven {
                    it.url = URI(engineEmiBintrayUrl)
                    it.content { innerIt ->
                        innerIt.excludeGroup("Kotlin/Native")
                    }
                }
            }


        }

        project.buildscript.dependencies.apply {
           // add("classpath", "me.emig.engineEmig:$engineEmiVersion")
           // add("classpath", "com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
        }

        //println(project.repositories)

        project.plugins.apply(KorgeGradlePlugin::class.java)




        project.tasks.register("AAaaaaaaaaaaaaaaaa"){
            println("KDKJGDKJGKDJGK")
        }
    }
}