plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

kotlin {

    sourceSets {
        val datetimeVersion: String by project

        dependencies {
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
        }
    }
}
