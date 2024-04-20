package gui.pantallas.registro;


import domain.modelo.Cliente;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;


public class RegistroController extends BasePantallaController {

    private final RegistroViewModel registroViewModel;

    @FXML
    private MFXTextField nombre;

    @Inject
    public RegistroController(RegistroViewModel registroViewModel) {
        this.registroViewModel = registroViewModel;
    }

    public void initialize() {
        registroViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isRegister()) {
                this.getPrincipalController().onRegistroHecho();
            }
        });
    }

    @FXML
    private void registrarNombre() {
        Cliente cliente = getPrincipalController().getActualUser();
        registroViewModel.registrarNombre(cliente, nombre.getText());
    }
}
