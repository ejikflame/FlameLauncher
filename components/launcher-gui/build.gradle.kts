plugins {
    id("org.openjfx.javafxplugin") version "0.1.0"
}

val guiSources = rootProject.layout.projectDirectory.dir("launcher-gui")
check(guiSources.file("src/main/java/pro/gravit/launcher/gui/JavaRuntimeModule.java").asFile.exists()) {
    "GUI sources missing. Run: git submodule update --init --recursive"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

sourceSets {
    main {
        java.setSrcDirs(listOf(guiSources.dir("src/main/java")))
    }
}

javafx {
    version = "22"
    modules("javafx.fxml", "javafx.controls", "javafx.web")
}

dependencies {
    compileOnly(project(":components:launcher-runtime"))
    compileOnly(libs.slf4j)
}

tasks.jar {
    archiveFileName.set("JavaRuntime.jar")
    manifest {
        attributes(
            "Module-Main-Class" to "pro.gravit.launcher.gui.JavaRuntimeModule",
            "Module-Config-Class" to "pro.gravit.launcher.gui.core.config.GuiModuleConfig",
            "Module-Config-Name" to "JavaRuntime"
        )
    }
    from(guiSources.file("LICENSE")) { into("META-INF/launcher-gui") }
}
