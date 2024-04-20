package gui.main;

import gui.main.common.Constantes;
import gui.pantallas.principal.PrincipalController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            ResourceBundle r = ResourceBundle.getBundle(Constantes.I_18_N_TEXTOS_UI);

            fxmlLoader.setResources(r);
            Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(Constantes.FXML_PANTALLA_PRINCIPAL_FXML));
            PrincipalController controller = fxmlLoader.getController();
            controller.setStage(stage);

            Scene scene = new Scene(fxmlParent);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.getScene().getStylesheets().add(getClass().getResource(Constantes.CSS_STYLE_CSS).toExternalForm());
            stage.setTitle(r.getString(Constantes.APP_TITLE));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
