plugins {
    id("org.openjfx.javafxplugin") version "0.1.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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
    from(layout.projectDirectory.file("LICENSE")) { into("META-INF/launcher-gui") }
}
