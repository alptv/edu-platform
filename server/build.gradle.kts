import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import kotlin.collections.mutableMapOf

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
    gradlePluginPortal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.postgresql:postgresql:42.3.3")

    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:1.4.200")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.testcontainers:testcontainers:1.16.0")
    testImplementation("org.testcontainers:postgresql:1.16.0")
    testImplementation("com.codeborne:selenide:5.25.1")

    testRuntimeOnly("io.qameta.allure:allure-junit5:$allureVersion")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperties = mutableMapOf<String, Any>().apply {
        val host =  System.getProperty("selenide.host", "localhost")
        val port =  System.getProperty("selenide.port", "7001")
        val remote = System.getProperty("selenide.remote")

        put("selenide.headless", System.getProperty("selenide.headless", "true"))
        put("selenide.browser", System.getProperty("selenide.browser", "chrome"))
        put("selenide.baseUrl", System.getProperty("selenide.baseUrl", "http://$host:$port"))
        put("allure.results.directory", "$projectDir/build/allure-results")

        if (remote != null) {
            put("selenide.remote", remote)
            put("driverManagerEnabled", "false")
        }
    }

    testLogging {
        exceptionFormat = FULL
        events("passed", "skipped", "failed")
    }
}

tasks.withType<BootJar> {
    archiveFileName.set("server.jar")
}


tasks.register<Test>("unitTest") {
    filter {
        includeTestsMatching("edu.platform.unit.*")
    }
}

tasks.register<Test>("integrationTest") {
    filter {
        includeTestsMatching("edu.platform.integration.*")
    }
}

tasks.register<Test>("e2eTest") {
    filter {
        includeTestsMatching("edu.platform.e2e.*")
    }
}