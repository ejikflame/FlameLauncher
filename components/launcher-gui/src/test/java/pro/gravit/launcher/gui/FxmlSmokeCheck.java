package pro.gravit.launcher.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import org.junit.jupiter.api.Test;

public class FxmlSmokeCheck {
    @Test
    void loadsLayoutsAndRendersLoginWindow() throws Exception {
        main(new String[]{System.getProperty("gui.runtime.path")});
    }

    public static void main(String[] args) throws Exception {
        Path runtime = Path.of(args[0]).toAbsolutePath();
        CompletableFuture<Integer> result = new CompletableFuture<>();
        Platform.startup(() -> {
            try {
                Platform.setImplicitExit(false);
                List<Path> layouts;
                try (var stream = Files.walk(runtime)) {
                    layouts = stream.filter(p -> p.toString().endsWith(".fxml")).sorted().toList();
                }
                int loaded = 0;
                for (String locale : List.of("ru", "en", "uk")) {
                    ResourceBundle resources;
                    try (var input = Files.newInputStream(runtime.resolve("runtime_" + locale + ".properties"))) {
                        resources = new PropertyResourceBundle(input);
                    }
                    for (Path layout : layouts) {
                        try {
                            FXMLLoader loader = new FXMLLoader(layout.toUri().toURL(), resources);
                            Parent root = loader.load();
                            if (root == null) throw new AssertionError("Empty layout: " + layout);
                            loaded++;
                        } catch (Throwable error) {
                            throw new AssertionError(locale + ": " + runtime.relativize(layout), error);
                        }
                    }
                }
                ResourceBundle resources;
                try (var input = Files.newInputStream(runtime.resolve("runtime_ru.properties"))) {
                    resources = new PropertyResourceBundle(input);
                }
                Parent login = new FXMLLoader(runtime.resolve("scenes/login/login.fxml").toUri().toURL(), resources).load();
                Scene scene = new Scene(login, 930, 560);
                scene.getStylesheets().add(runtime.resolve("styles/variables.css").toUri().toString());
                scene.getStylesheets().add(runtime.resolve("styles/global.css").toUri().toString());
                Stage window = new Stage();
                window.setScene(scene);
                window.show();
                login.applyCss();
                login.layout();
                if (!window.isShowing() || scene.getWidth() <= 0) throw new AssertionError("Window did not open");
                var image = scene.snapshot(null);
                if (image.getWidth() != 930 || image.getHeight() != 560) throw new AssertionError("Invalid render size");
                window.close();
                result.complete(loaded);
            } catch (Throwable error) {
                result.completeExceptionally(error);
            }
        });
        try {
            System.out.println("FXML loaded: " + result.get(60, TimeUnit.SECONDS));
            System.out.println("Login stage opened and rendered: 930 x 560");
        } finally {
            Platform.exit();
        }
    }
}
