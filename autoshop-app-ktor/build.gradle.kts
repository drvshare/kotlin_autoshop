import org.jetbrains.kotlin.util.suffixIfNot
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

val ktorVersion: String by project
val logbackVersion: String by project
val serializationVersion: String by project

// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    id("application")
    id("com.bmuschko.docker-java-application")
    id("com.bmuschko.docker-remote-api")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
    mainClass.set("ru.drvshare.autoshop.ApplicationKt")
}

kotlin {
    jvm {}

    /*val nativeTarget = when (System.getProperty("os.name")) {
        "Mac OS X" -> macosX64("native")
        "Linux" -> linuxX64("native")
        // Windows is currently not supported
        // Other supported targets are listed here: https://ktor.io/docs/native-server.html#targets
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "ru.drvshare.autoshop.main"
            }
        }
    }
*/
    sourceSets {
        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"

                implementation(project(":autoshop-common"))
                implementation(project(":autoshop-transport-main-openapi-v2"))
//                implementation(project(":autoshop-biz"))
                implementation(project(":autoshop-mappers-v2"))
                implementation(project(":autoshop-stubs"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
/*        val nativeMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")

                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val nativeTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }*/
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(ktor("core")) // "io.ktor:ktor-server-core:$ktorVersion"
                implementation(ktor("netty")) // "io.ktor:ktor-ktor-server-netty:$ktorVersion"

                // jackson
                implementation(ktor("jackson", "serialization")) // io.ktor:ktor-serialization-jackson
                implementation(ktor("content-negotiation")) // io.ktor:ktor-server-content-negotiation
                implementation(ktor("kotlinx-json", "serialization")) // io.ktor:ktor-serialization-kotlinx-json

                implementation(ktor("locations"))
                implementation(ktor("caching-headers"))
                implementation(ktor("call-logging"))
                implementation(ktor("auto-head-response"))
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("default-headers")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-cors:$ktorVersion"
                implementation(ktor("auto-head-response"))
                implementation(ktor("websockets"))

                implementation(ktor("websockets")) // "io.ktor:ktor-websockets:$ktorVersion"
                implementation(ktor("auth")) // "io.ktor:ktor-auth:$ktorVersion"
                implementation(ktor("auth-jwt")) // "io.ktor:ktor-auth-jwt:$ktorVersion"

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                // transport models
                implementation(project(":autoshop-common"))
                implementation(project(":autoshop-transport-main-openapi-v1"))
                implementation(project(":autoshop-transport-main-openapi-v2"))
                implementation(project(":autoshop-mappers-v1"))

                // v2 api
                implementation(project(":autoshop-mappers-v2"))

                // Stubs
                implementation(project(":autoshop-stubs"))
//                implementation(project(":autoshop-biz"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(ktor("test-host")) // "io.ktor:ktor-server-test-host:$ktorVersion"
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
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

/*
tasks {
    val linkReleaseExecutableNative by getting(KotlinNativeLink::class)

    val dockerDockerfile by creating(Dockerfile::class) {
        group = "docker"
        from("ubuntu:22.02")
        doFirst {
            copy {
                from(linkReleaseExecutableNative.binary.outputFile)
                into("${this@creating.temporaryDir}/app")
            }
        }
        copyFile("app", "/app")
        entryPoint("/app")
    }
    create("dockerBuildNativeImage", DockerBuildImage::class) {
        group = "docker"
        dependsOn(dockerDockerfile)
        images.add("${project.name}:${project.version}")
    }
}
*/
