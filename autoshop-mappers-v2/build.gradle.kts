plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    jvm {}
//    linuxX64 {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":autoshop-transport-main-openapi-v2"))
                implementation(project(":autoshop-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

    }
}
