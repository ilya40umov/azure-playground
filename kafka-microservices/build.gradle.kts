plugins {
	kotlin("jvm") version "1.9.25" apply false
	kotlin("plugin.spring") version "1.9.25" apply false
	id("org.springframework.boot") version "3.4.4" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "me.ilya40umov"
    version = "0.0.1-SNAPSHOT"

    apply<JavaPlugin>()
    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    extra["springCloudAzureVersion"] = "5.22.0"

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs += "-Xjsr305=strict"
                jvmTarget = "21"
            }
        }
        withType<Test> {
            useJUnitPlatform {
                testLogging.showStandardStreams = true
            }
        }
    }
}
