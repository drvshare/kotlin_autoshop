import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    id("com.bmuschko.docker-java-application")
//    id("com.bmuschko.docker-remote-api")
    kotlin("plugin.serialization")
    kotlin("jvm")
}
dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.1.2")
    implementation("io.ktor:ktor-server-core-jvm:2.1.2")
    implementation("io.ktor:ktor-serialization-jackson-jvm:2.1.2")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

kotlin {

    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                implementation(ktor("core"))
                implementation(ktor("netty"))
                // jackson
                implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("call-logging"))
                implementation(ktor("cors"))
                implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                implementation(project(":autoshop-common"))
                implementation(project(":autoshop-transport-main-openapi-v1"))
//                implementation(project(":autoshop-transport-main-openapi-v2"))
                implementation(project(":autoshop-mappers-v1"))

                // Stubs
                implementation(project(":autoshop-stubs"))
                implementation(project(":autoshop-biz"))
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val test by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
            }
        }
    }
}

docker {
    javaApplication {
        mainClassName.set(application.mainClass.get())
        baseImage.set("adoptopenjdk/openjdk17:alpine-jre")
        maintainer.set("drvshare")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(
            listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
            )
        )
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}

