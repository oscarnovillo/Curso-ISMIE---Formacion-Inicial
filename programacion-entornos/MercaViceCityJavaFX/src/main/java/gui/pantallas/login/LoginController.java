package gui.pantallas.login;

import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class LoginController extends BasePantallaController {

    private final LoginViewModel loginViewModel;

    @FXML
    private ImageView logo;

    @FXML
    private MFXTextField txtDNI;

    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    public void initialize() {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.MEDIA_LOGO_PNG)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logo.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        loginViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isLoginOK()) {
                //cambiar de pantalla
                this.getPrincipalController().onLoginHecho(new ClienteNormal(txtDNI.getText()));
            }
            if (newState.isRegister()) {
                this.getPrincipalController().onRegitroIniciado(new ClienteNormal(txtDNI.getText()));
            }
        });
    }

    @FXML
    private void doLogin() {
        Cliente usuario = new ClienteNormal(txtDNI.getText());
        loginViewModel.doLogin(usuario);
    }

    @FXML
    private void doRegister() {
        Cliente usuario = new ClienteNormal(txtDNI.getText());
        loginViewModel.doRegister(usuario);
    }
}
