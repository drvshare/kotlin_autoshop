val logbackVersion: String by project
val kotlinLoggingJvmVersion: String by project

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

kotlin {

    sourceSets {
        val datetimeVersion: String by project

        dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation(kotlin("reflect"))
//            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            implementation("ch.qos.logback:logback-classic:$logbackVersion")
            implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")

            api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
        }
    }
}
