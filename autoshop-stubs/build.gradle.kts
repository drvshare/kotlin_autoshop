plugins {
    kotlin("jvm")
}

kotlin {

    sourceSets {
        dependencies {
            implementation(project(":autoshop-common"))

            implementation(kotlin("stdlib-jdk8"))
            implementation(kotlin("test-junit"))

        }
    }
}
