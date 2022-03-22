//import io.qameta.allure.gradle.AllureExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL

plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.qameta.allure") version "2.9.6"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
}
val allureVersion = "2.14.0"

allure {
    version.set(allureVersion)
}

group = "edu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.3.3")
    //implementation("io.qameta.allure:allure-gradle:2.8.0")

  //  testImplementation("io.qameta.allure:allure-java-commons:$allureVersion")

    runtimeOnly("com.h2database:h2")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:1.4.200")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.testcontainers:testcontainers:1.16.0")
    testImplementation("org.testcontainers:postgresql:1.16.0")

    testRuntimeOnly("io.qameta.allure:allure-junit5:$allureVersion")
//    testCompile("io.qameta.allure:allure-java-commons:$allureVersion")
//    testCompile("io.qameta.allure:allure-attachments:$allureVersion")
//    testCompile("io.qameta.allure:allure-generator:$allureVersion")
//    testCompile("io.qameta.allure:allure-httpclient:$allureVersion")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = FULL
        events("passed", "skipped", "failed")
    }
}

tasks.withType<BootJar> {
    archiveFileName.set("server.jar")
}
