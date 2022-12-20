plugins {
    kotlin("jvm")
}



kotlin {

    sourceSets {
        val coroutinesVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val main by getting {
            dependencies {
                implementation(project(":autoshop-common"))
                implementation(project(":autoshop-stubs"))

                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

            }
        }

        @Suppress("UNUSED_VARIABLE")
        val test by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-annotations-common"))

                implementation(project(":autoshop-repo-tests"))
            }
        }

     }
}
