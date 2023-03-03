import org.gradle.api.JavaVersion.VERSION_19

plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.sonarqube") version "4.0.0.2929"
    id("jacoco")
}

group = "org.tub"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = VERSION_19

repositories {
    mavenCentral()
}
val nettyResolverDnsVersion = "4.1.85"
val wireMockVersion = "2.35.0"
val logbackVersion = "7.2"
val enablePreview = "--enable-preview"
val commonsCollectionsVersion = "4.4"
val commonsLang3Version = "3.12.0"
val poiVersion = "5.2.3"

dependencies {
    //Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework:spring-aspects")
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

    // Office documents
    implementation("org.apache.poi:poi-ooxml:${poiVersion}")

}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(enablePreview)
    finalizedBy("jacocoTestReport")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add(enablePreview)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}
