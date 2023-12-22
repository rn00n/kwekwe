import org.springframework.boot.gradle.tasks.bundling.BootJar

group = "com.rn00n.kwekwe.core"
version = "0.0.1"
plugins {
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    idea
}

dependencies {
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
//    implementation("javax.annotation:javax.annotation-api:1.3.2")
//    implementation("javax.persistence:javax.persistence-api:2.2")
    annotationProcessor(group = "com.querydsl", name = "querydsl-apt", classifier = "jpa")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

kotlin.sourceSets.main {
    println("kotlin sourceSets builDir:: $buildDir")
    setBuildDir("$buildDir")
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}