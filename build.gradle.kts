plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "phss.discordlink"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://jitpack.io/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/central/") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
    implementation("net.dv8tion:JDA:5.0.0-alpha.8") { exclude("opus-java") }
    testImplementation("junit", "junit", "4.12")
}

tasks {
    build { dependsOn(shadowJar) }
    shadowJar {
        archiveFileName.set("${rootProject.name}.jar")
    }
}