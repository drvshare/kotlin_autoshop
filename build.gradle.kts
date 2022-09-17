plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "ru.drvshare.autoshop"
version = "0.0.1-SNAPSHOT"

allprojects {
repositories {
        google()
    mavenCentral()
        maven { url = uri("https://jitpack.io") }
}
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

/*
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}*/
