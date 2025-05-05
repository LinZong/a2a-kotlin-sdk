

repositories {
    mavenCentral()
}

allprojects {
    group = "moe.nemesiss.a2a"
    version = "1.0-SNAPSHOT"
}



subprojects {
    apply(plugin = "maven-publish")
}