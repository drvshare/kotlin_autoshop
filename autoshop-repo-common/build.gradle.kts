plugins {
    kotlin("jvm")
}



kotlin {

    sourceSets {
        val coroutinesVersion: String by project

        val main by getting {
            dependencies {
                implementation(project(":autoshop-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api(kotlin("test-junit"))
            }
        }
    }
}
