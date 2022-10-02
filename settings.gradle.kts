rootProject.name = "kotlin_autoshop"


pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val bmuschkoVersion: String by settings

        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        id("io.kotest.multiplatform") version kotestVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("com.bmuschko.docker-remote-api") version bmuschkoVersion apply false

    }
}
include("autoshop-common")
include("autoshop-transport-main-openapi-v1")
//include("autoshop-transport-main-openapi-v2")
include("autoshop-mappers-v1")
//include("autoshop-mappers-v2")
//include("autoshop-app-ktor")
