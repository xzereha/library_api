plugins {
	java
	checkstyle
    jacoco
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Library API"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

checkstyle {
	toolVersion = "13.3.0"
	configFile = file("config/checkstyle/google_checks.xml")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-h2console")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-restclient:4.0.3")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.named<org.gradle.testing.jacoco.tasks.JacocoReport>("jacocoTestReport") {
	dependsOn(tasks.test)
	reports {
		html.required.set(true)
		xml.required.set(true)
        html.outputLocation = layout.buildDirectory.dir("coverage/html")
	}
	val coverageExcludes = listOf("com/example/libraryapi/LibraryApi*")

	// Configure classDirectories from the standard output directory to avoid requiring SourceSetContainer
	val classesDir = fileTree("${buildDir.path}/classes/java/main") {
		exclude(coverageExcludes)
	}
	classDirectories.setFrom(classesDir)
}