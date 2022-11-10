plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":autoshop-transport-main-openapi-v1"))
    implementation(project(":autoshop-common"))

    testImplementation(kotlin("test-junit"))
}
