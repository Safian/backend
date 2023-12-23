import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management")
    id("org.springframework.boot") version "3.2.0" apply false
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20" apply false
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "19"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {

    val springBootVersion = project.properties["spring-boot.version"] as String
    val springCloudVersion = project.properties["spring-cloud.version"] as String

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    repositories {
        mavenCentral()
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "19"
        }
    }

    tasks.register("printProperties") {
        doLast {
            println(springBootVersion)
            println(springCloudVersion)
        }
    }
}

