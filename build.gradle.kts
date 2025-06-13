plugins {
    java
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "xyz.jessyu"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    maven {
        name="GitHubPackages"
        url = uri("https://maven.pkg.github.com/StudentRentalSystem/querygenerator")
        credentials {
            username = "x-access-token"
            password = System.getenv("CLIENT_TOKEN")
        }
    }
    maven {
        name="GitHubPackages"
        url = uri("https://maven.pkg.github.com/StudentRentalSystem/llmdataparser")
        credentials {
            username = "x-access-token"
            password = System.getenv("CLIENT_TOKEN")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.projectlombok:lombok")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.json:json:20231013")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    annotationProcessor("org.projectlombok:lombok")
    implementation("io.github.studentrentalsystem:querygenerator:1.0.2")
    implementation("io.github.studentrentalsystem:llmdataparser:1.0.2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
