package gui.pantallas.inicio;

import domain.services.ServicesClientes;
import gui.pantallas.common.BasePantallaController;
import jakarta.inject.Inject;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.util.Duration;

public class PantallaInicioController extends BasePantallaController {

    @FXML
    private Label lbBienvenido;

    private final ServicesClientes scClientes;

    @Inject
    public PantallaInicioController(ServicesClientes scClientes) {
        this.scClientes = scClientes;
    }


    private void animarPantalla() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(lbBienvenido);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(1);
        translate.setInterpolator(Interpolator.LINEAR);
        translate.setFromX(getPrincipalController().getWidth());
        translate.setToX(lbBienvenido.getLayoutX());
        translate.play();
    }

    @Override
    public void principalCargado() {
        String bienvenida = "Bienvenido \n";
        lbBienvenido.setText(bienvenida + scClientes.scGetNombre(getPrincipalController().getActualUser()));
        animarPantalla();
    }

}
