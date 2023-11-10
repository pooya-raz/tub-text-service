import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone
import org.gradle.api.JavaVersion.VERSION_21

plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")
    id("net.ltgt.errorprone") version "3.0.1"
    id("net.ltgt.nullaway") version "1.5.0"
    id("com.diffplug.spotless") version "6.22.0"
}

group = "org.tub"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_21

repositories {
    mavenCentral()
}
val nettyResolverDnsVersion = "4.1.85"
val wireMockVersion = "2.35.0"
val logbackVersion = "7.2"
val enablePreview = "--enable-preview"
val commonsCollectionsVersion = "4.4"
val commonsLang3Version = "3.12.0"

dependencies {
    //Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework:spring-aspects")
    implementation("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Logging
    implementation("net.logstash.logback:logstash-logback-encoder:${logbackVersion}")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("com.github.tomakehurst:wiremock-jre8-standalone:${wireMockVersion}")

    // Mac M1 Dependency
    implementation("io.netty:netty-resolver-dns-native-macos:${nettyResolverDnsVersion}.Final:osx-aarch_64")

    // Commons-collections
    implementation("org.apache.commons:commons-collections4:${commonsCollectionsVersion}")
    implementation("org.apache.commons:commons-lang3:${commonsLang3Version}")

    //NullAway
    annotationProcessor("com.uber.nullaway:nullaway:0.10.9")

    // Optional, some source of nullability annotations.
    // Not required on Android if you use the support
    // library nullability annotations.
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    errorprone("com.google.errorprone:error_prone_core:2.18.0")
    errorproneJavac("com.google.errorprone:javac:9+181-r4173-1")
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(enablePreview)
    finalizedBy("jacocoTestReport")
}

tasks.withType<JavaCompile> {
    if (!name.lowercase().contains("test")) {
        options.errorprone {
            check("NullAway", CheckSeverity.ERROR)
            option("NullAway:AnnotatedPackages", "com.uber")
        }
    }
    options.compilerArgs.add(enablePreview)
}
tasks {
    compileTestJava {
        options.errorprone.isEnabled.set(false)
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

sonar {
    properties {
        property("sonar.projectKey", "tub-text-service")
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat("2.38.0")
    }
}