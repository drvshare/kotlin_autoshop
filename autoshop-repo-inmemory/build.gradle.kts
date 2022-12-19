plugins {
    kotlin("jvm")
}

kotlin {

    sourceSets {
        val cache4kVersion: String by project
        val coroutinesVersion: String by project
        val kmpUUIDVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val main by getting {
            dependencies {
                implementation(project(":autoshop-common"))

                implementation(kotlin("stdlib-jdk8"))
                implementation("io.github.reactivecircus.cache4k:cache4k:$cache4kVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.benasher44:uuid:$kmpUUIDVersion")
                implementation(project(":autoshop-repo-common"))

            }
        }

        @Suppress("UNUSED_VARIABLE")
        val test by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}
