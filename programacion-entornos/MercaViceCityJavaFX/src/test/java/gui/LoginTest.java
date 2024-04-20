package gui;

import domain.services.ServicesClientes;
import domain.services.impl.ServicesClientesImpl;
import gui.main.common.Constantes;
import gui.pantallas.common.ConstantesPantallas;
import gui.pantallas.login.LoginController;
import gui.pantallas.login.LoginViewModel;
import gui.pantallas.principal.PrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class LoginTest extends ApplicationTest {


    LoginController controller;
    private PrincipalController principalController; // = mock(PrincipalController.class);;
    private ServicesClientes servicesClientes; // = mock(LoginUseCase.class);
    private LoginViewModel loginViewModel;


    @Start
    public void start(Stage stage) throws IOException {

        principalController = mock(PrincipalController.class);
        servicesClientes = mock(ServicesClientesImpl.class);
        loginViewModel = new LoginViewModel(servicesClientes);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(param -> new LoginController(loginViewModel));
        ResourceBundle r = ResourceBundle.getBundle(Constantes.I_18_N_TEXTOS_UI);
        fxmlLoader.setResources(r);
        Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesPantallas.FXML_PANTALLA_LOGIN_FXML));
        controller = fxmlLoader.getController();
        controller.setPrincipalController(principalController);

        stage.setScene(new Scene(fxmlParent));
        stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.show();

    }


    @Test
    void adminLogin(FxRobot robot) {
        //given
        robot.clickOn("#txtDNI");
        robot.write("admin");
        when(servicesClientes.scExisteCliente(argThat(usuario -> usuario.getDni().equals("admin")))).thenReturn(true);
        //when(loginUseCase.doLogin(any(Usuario.class))).thenReturn(true);

        //when
        robot.clickOn("#btnLogin");

        //then
        verify(principalController).onLoginHecho(argThat(usuario -> usuario.getDni().equals("admin")));
    }

    @Test
    void should_alert_error(FxRobot robot) {
        //given
        robot.clickOn("#txtDNI");
        robot.write("otro");
        when(servicesClientes.scExisteCliente(argThat(usuario -> !usuario.getDni().equals("admin")))).thenReturn(false);

        //when
        robot.clickOn("#btnLogin");

        //then
        verify(principalController).showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, ConstantesPantallas.DNI_NO_REGISTRADO);
    }
}

