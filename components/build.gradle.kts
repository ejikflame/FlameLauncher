plugins {
    id("maven-publish")
}

subprojects {
    apply(plugin = "maven-publish")

    publishing {
        repositories {
            mavenLocal()
            findProperty("maven.upload.repository")?.let {
                maven {
                    url = uri(it)
                }
            }
        }
        publications {
            var name = project.name
            create<MavenPublication>("maven") {
                groupId = "io.github.ejikflame.flamelauncher"
                artifactId = name
                version = project.version as String?

                from(components["java"])
            }
        }
    }
}