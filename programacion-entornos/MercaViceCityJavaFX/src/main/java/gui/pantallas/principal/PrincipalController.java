package gui.pantallas.principal;


import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import gui.main.common.Constantes;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import gui.pantallas.common.Pantallas;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class PrincipalController {

    // objeto especial para DI
    final Instance<Object> instance;

    private Stage primaryStage;
    private final Alert alert;
    private Cliente actualUser;
    private double xOffset;
    private double yOffset;

    @FXML
    private BorderPane root;
    @FXML
    private HBox windowHeader;
    @FXML
    private MFXFontIcon closeIcon;
    @FXML
    private MFXFontIcon minimizeIcon;
    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private MenuBar menuPrincipal;
    @FXML
    private Menu menuAdministracion;
    @FXML
    private Menu menuCliente;

    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
        alert = new Alert(Alert.AlertType.NONE);
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public void initialize() {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> showAlertConfirmClose());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) root.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !primaryStage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass
                    .getPseudoClass(ConstantesPantallas.ALWAYS_ON_TOP), newVal);
            primaryStage.setAlwaysOnTop(newVal);
        });

        windowHeader.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
        menuPrincipal.setVisible(false);
        cargarPantalla(Pantallas.LOGIN);
    }

    public Cliente getActualUser() {
        return actualUser;
    }

    public double getWidth() {
        return root.getScene().getWindow().getWidth();
    }

    private void showAlertConfirmClose() {
        Alert alertCerrar = new Alert(Alert.AlertType.WARNING);
        alertCerrar.getButtonTypes().remove(ButtonType.OK);
        alertCerrar.getButtonTypes().add(ButtonType.CANCEL);
        alertCerrar.getButtonTypes().add(ButtonType.YES);
        alertCerrar.setTitle(ConstantesPantallas.SALIR);
        alertCerrar.setContentText(ConstantesPantallas.SEGURO_QUE_QUIERE_CERRAR_LA_APLICACION);
        alertCerrar.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alertCerrar.showAndWait();
        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                Platform.exit();
            }
        });
    }

    public void showAlert(Alert.AlertType alertType, String titulo, String mensaje) {
        alert.setAlertType(alertType);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    private void cargarPantalla(Pantallas pantalla) {
        Pane panePantalla;
        ResourceBundle r = ResourceBundle.getBundle(ConstantesPantallas.I_18_N_TEXTOS_UI);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            fxmlLoader.setResources(r);
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getRuta()));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setPrincipalController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @FXML
    private void menuOnClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case ConstantesPantallas.MENU_ITEM_PANTALLA_INICIO:
                cargarPantalla(Pantallas.BIENVENIDA);
                break;
            case ConstantesPantallas.MENU_ITEM_PRODUCTOS_ADMIN:
                cargarPantalla(Pantallas.PRODUCTOS_ADMIN);
                break;
            case ConstantesPantallas.MENU_ITEM_PRODUCTOS_CLIENTE:
                cargarPantalla(Pantallas.PRODUCTOS_CLIENTE);
                break;
            case ConstantesPantallas.MENU_ITEM_CLIENTES_ADMIN:
                cargarPantalla(Pantallas.CLIENTES_ADMIN);
                break;
            case ConstantesPantallas.MENU_ITEM_CARRITO:
                cargarPantalla(Pantallas.CARRITO);
                break;
            case ConstantesPantallas.MENU_ITEM_PERFIL_CLIENTE:
                cargarPantalla(Pantallas.PERFIL);
                break;
            case ConstantesPantallas.MENU_ITEM_COMPRAS_CLIENTE:
                cargarPantalla(Pantallas.COMPRAS_ANTIGUAS);
                break;
        }
    }

    @FXML
    private void cambiarcss() {
        if (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isEmpty()
                || (primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().isPresent()
        && primaryStage.getScene().getRoot().getStylesheets().stream().findFirst().get().contains(ConstantesPantallas.STYLE))) {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ConstantesPantallas.CSS_DARKMODE_CSS).toExternalForm());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else {
            try {
                primaryStage.getScene().getRoot().getStylesheets().clear();
                primaryStage.getScene().getRoot().getStylesheets().add(getClass().getResource(ConstantesPantallas.CSS_STYLE_CSS).toExternalForm());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @FXML
    private void acercaDe() {
        showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.ACERCA_DE, ConstantesPantallas.DATOS_AUTOR);
    }

    @FXML
    private void logout() {
        actualUser = null;
        menuPrincipal.setVisible(false);
        cargarPantalla(Pantallas.LOGIN);
    }

    @FXML
    private void exit() {
        showAlertConfirmClose();
    }

    //evento de otra pantalla
    public void onLoginHecho(Cliente usuario) {
        actualUser = usuario;
        menuPrincipal.setVisible(true);
        menuAdministracion.setVisible(false);
        menuCliente.setVisible(true);
        if (actualUser.getDni().equals(ConstantesPantallas.ADMIN)) {
            menuAdministracion.setVisible(true);
            menuCliente.setVisible(false);
        }
        cargarPantalla(Pantallas.BIENVENIDA);
    }

    public void onRegitroIniciado(ClienteNormal clienteNormal) {
        actualUser = clienteNormal;
        cargarPantalla(Pantallas.REGISTRO);
    }

    public void onRegistroHecho() {
        menuPrincipal.setVisible(true);
        menuAdministracion.setVisible(false);
        cargarPantalla(Pantallas.BIENVENIDA);
    }

    public void onVerCarrito() {
        cargarPantalla(Pantallas.CARRITO);
    }

    public void onVerProductos() {
        cargarPantalla(Pantallas.PRODUCTOS_CLIENTE);
    }

    public void onVerPerfil() {
        cargarPantalla(Pantallas.PERFIL);
    }
}
