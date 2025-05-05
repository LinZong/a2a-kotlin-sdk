plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "a2a-kotlin"
include("a2a-kotlin-core")
include("a2a-local-transport")
