plugins {
    kotlin("jvm") version "2.0.20"
}

repositories {
    mavenCentral()
}

base {
    archivesName.set("a2a-kotlin-core")
}

java {
    withSourcesJar()
}

dependencies {
    implementation(kotlin("reflect"))
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


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("A2A Kotlin Core Library")
                description.set("A2A Protocol implementation in Kotlin")
                url.set("https://github.com/LinZong/a2a-kotlin-sdk/")
            }
        }
    }
}