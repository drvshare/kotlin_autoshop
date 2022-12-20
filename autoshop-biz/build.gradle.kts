plugins {
    kotlin("jvm")
}

kotlin {

    sourceSets {
        val coroutinesVersion: String by project

//        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        sourceSets {
            @Suppress("UNUSED_VARIABLE")
            val main by getting {
                dependencies {
                    implementation(kotlin("stdlib-common"))
                    implementation(kotlin("stdlib-jdk8"))

                    implementation(project(":autoshop-common"))
                    implementation(project(":autoshop-stubs"))
                    implementation(project(":autoshop-cor"))
                }

                @Suppress("UNUSED_VARIABLE")
                val test by getting {
                    dependencies {
                        implementation(project(":autoshop-repo-stubs"))
                        implementation(project(":autoshop-repo-tests"))

//            implementation(kotlin("test-common"))
//            implementation(kotlin("test-annotations-common"))
                        implementation(kotlin("test-junit"))

                        api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
                    }
                }
            }
        }
    }
}
