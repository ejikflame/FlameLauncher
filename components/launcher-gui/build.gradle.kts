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

val headlessGui by configurations.creating
dependencies {
    headlessGui("org.testfx:openjfx-monocle:21.0.2")
}

tasks.test {
    classpath += headlessGui
    systemProperty("gui.runtime.path", layout.projectDirectory.dir("runtime").asFile.absolutePath)
    systemProperty("glass.platform", "Monocle")
    systemProperty("monocle.platform", "Headless")
    systemProperty("prism.order", "sw")
    systemProperty("java.awt.headless", "true")
}

tasks.register<JavaExec>("smokeGui") {
    group = "verification"
    description = "Load all GUI layouts in all locales and render the login window without a display."
    dependsOn(tasks.testClasses)
    mainClass.set("pro.gravit.launcher.gui.FxmlSmokeCheck")
    classpath = sourceSets.test.get().runtimeClasspath + headlessGui
    args(layout.projectDirectory.dir("runtime").asFile.absolutePath)
    systemProperty("glass.platform", "Monocle")
    systemProperty("monocle.platform", "Headless")
    systemProperty("prism.order", "sw")
    systemProperty("java.awt.headless", "true")
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
