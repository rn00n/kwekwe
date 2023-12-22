import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.rn00n.kwekwe.api"
version = "0.0.1"

dependencies {
    api(project(":kwekwe-security:security-jwt"))
    api(project(":kwekwe-core"))
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}