plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}


dependencies {
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:2.1.20")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.19.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}