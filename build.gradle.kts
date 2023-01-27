import org.gradle.api.JavaVersion.VERSION_19

plugins {
    java
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
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

}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(enablePreview)
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add(enablePreview)
}
