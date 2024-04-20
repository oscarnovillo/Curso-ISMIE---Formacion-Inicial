package gui.pantallas.perfil;

import domain.modelo.Cliente;
import domain.modelo.Ingrediente;
import domain.modelo.Monedero;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PerfilController extends BasePantallaController {

    private final PerfilViewModel perfilViewModel;
    @FXML
    private MFXListView<Ingrediente> listAlergenos;
    @FXML
    private TableView<Monedero> tablaMonederos;
    @FXML
    private TableColumn<Monedero, Integer> columnID;
    @FXML
    private TableColumn<Monedero, Double> columnImporte;
    @FXML
    private MFXTextField txtNombreUsuario;
    @FXML
    private MFXTextField txtAlergeno;
    @FXML
    private MFXTextField txtIdMonedero;
    @FXML
    private MFXTextField txtImporteMonedero;

    @Inject
    public PerfilController(PerfilViewModel perfilViewModel) {
        this.perfilViewModel = perfilViewModel;
    }

    public void initialize() {
        columnID.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NUMERO_MONEDERO));
        columnImporte.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.IMPORTE));
        tablaMonederos.setItems(perfilViewModel.getMonederos());
        listAlergenos.setItems(perfilViewModel.getAlergenos());

        perfilViewModel.getState().addListener((observable, oldValue, newValue) -> {
            if (newValue.getError() != null) {
                getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newValue.getError());
            }
            if (newValue.isAlergenoAnadido()) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.ALERGENO_AÑADIDO, ConstantesPantallas.EL_ALERGENO_HA_SIDO_AÑADIDO_CORRECTAMENTE);
            }
            if (newValue.isMonederoAnadido()) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.MONEDERO_AÑADIDO, ConstantesPantallas.EL_MONEDERO_HA_SIDO_AÑADIDO_CORRECTAMENTE);
            }
            if (newValue.isNombreCambiado()) {
                getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.NOMBRE_CAMBIADO, ConstantesPantallas.EL_NOMBRE_HA_SIDO_CAMBIADO_CORRECTAMENTE);
            }
        });
    }

    @Override
    public void principalCargado() {
        Cliente cliente = getPrincipalController().getActualUser();
        perfilViewModel.cargarAlergenos(cliente);
        perfilViewModel.cargarMonederos(cliente);
    }

    public void onBtnCambiarNombreClicked() {
        Cliente cliente = getPrincipalController().getActualUser();
        String nombre = txtNombreUsuario.getText();
        perfilViewModel.cambiarNombre(cliente, nombre);
    }

    public void onBtnAnadirMonederoClicked() {
        Cliente cliente = getPrincipalController().getActualUser();
        int numeroMonedero = Integer.parseInt(txtIdMonedero.getText());
        double importe = Double.parseDouble(txtImporteMonedero.getText());
        Monedero monedero = new Monedero(numeroMonedero, importe);
        perfilViewModel.anadirMonedero(cliente, monedero);
    }

    public void onBtnAnadirAlergenoClicked() {
        Cliente cliente = getPrincipalController().getActualUser();
        Ingrediente alergeno = new Ingrediente(txtAlergeno.getText());
        perfilViewModel.anadirAlergeno(cliente, alergeno);
    }
}
