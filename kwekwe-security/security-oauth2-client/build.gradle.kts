import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.rn00n.kwekwe.security.oauth2.client"
version = "0.0.1"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    api(project(":kwekwe-core"))
    api(project(":kwekwe-security:security-jwt"))
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
