import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("kapt")
}

repositories {
    mavenLocal()
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

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {

    val springBootVersion = project.properties["spring-boot.version"] as String
    val springCloudVersion = project.properties["spring-cloud.version"] as String

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
        }
    }

    tasks.register("printProperties") {
        doLast {
            println(springBootVersion)
            println(springCloudVersion)
        }
    }
}

