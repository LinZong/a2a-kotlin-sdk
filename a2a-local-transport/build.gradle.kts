plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":a2a-kotlin-core"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}