import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.rn00n.kwekwe.web"
version = "0.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    api(project(":kwekwe-security:security-jwt"))
    implementation(project(":kwekwe-security:security-oauth2-client"))
    api(project(":kwekwe-core"))
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}