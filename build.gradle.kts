plugins {
	java
	checkstyle
	id("com.diffplug.spotless") version "8.3.0"
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

dependencies {
	implementation("org.springframework.boot:spring-boot-h2console")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.2")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotless {
	java {
		target("src/*/java/**/*.java")
		eclipse().configFile("config/formatter/eclipse-java-style.xml")
		trimTrailingWhitespace()
		endWithNewline()
	}
}

tasks.register("format") {
	dependsOn("spotlessApply")
}

tasks.named("check") {
	dependsOn("spotlessCheck")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
