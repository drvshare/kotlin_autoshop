plugins {
    kotlin("jvm")
}

kotlin {
    sourceSets {
        val coroutinesVersion: String by project
        val atomicfuVersion: String by project

        @Suppress("UNUSED_VARIABLE")
        val main by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }

        @Suppress("UNUSED_VARIABLE")
        val test by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                implementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
                implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
            }
        }
    }
}
