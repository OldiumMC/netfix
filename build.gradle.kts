plugins {
    id("fabric-loom") version "1.5-SNAPSHOT"
    id("legacy-looming") version "1.5-SNAPSHOT"
}

dependencies {
    "minecraft"("com.mojang:minecraft:1.8.9")
    "mappings"("net.legacyfabric:yarn:1.8.9+build.535:v2")
    "modImplementation"("net.fabricmc:fabric-loader:0.15.9")
}