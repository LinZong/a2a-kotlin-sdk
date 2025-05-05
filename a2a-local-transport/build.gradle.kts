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

java {
    withSourcesJar()
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set("A2A Transport Implementation - Local")
                description.set("A2A transport implementation that support transferring JSON-RPC message through direct method call.")
                url.set("https://github.com/LinZong/a2a-kotlin-sdk/")
            }
        }
    }
}