package gui.pantallas.clientes_admin;

import domain.modelo.Cliente;
import domain.modelo.ClienteEspacial;
import domain.modelo.ClienteNormal;
import domain.modelo.Ingrediente;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ClientesAdminController extends BasePantallaController {

    private final ClientesAdminViewModel clientesAdminViewModel;

    @FXML
    private Label labelDescuento;

    @FXML
    private MFXButton addCliente;
    @FXML
    private MFXButton updateCliente;
    @FXML
    private MFXButton deleteCliente;
    @FXML
    private MFXButton unselect;

    @FXML
    private MFXTextField txtDNI;
    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtDescuento;
    @FXML
    private MFXComboBox<String> selectorType;
    @FXML
    private MFXListView<Ingrediente> listIngredientes;
    @FXML
    private MFXTextField txtIngrediente;

    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> columnDNI;
    @FXML
    private TableColumn<Cliente, String> columnTipo;
    @FXML
    private TableColumn<Cliente, String> columnNombre;
    @FXML
    private TableColumn<Cliente, Integer> columnDescuento;

    @Inject
    public ClientesAdminController(ClientesAdminViewModel clientesAdminViewModel) {
        this.clientesAdminViewModel = clientesAdminViewModel;
    }

    public void initialize() {
        unselect.setVisible(false);

        updateCliente.setDisable(true);
        deleteCliente.setDisable(true);

        labelDescuento.setVisible(false);
        txtDescuento.setVisible(false);

        txtDescuento.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        listIngredientes.getSelectionModel().setAllowsMultipleSelection(false);
        selectorType.getItems().addAll(ConstantesPantallas.CLIENTE_ESPACIAL, ConstantesPantallas.CLIENTE_NORMAL);

        columnDNI.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.DNI));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CLIENT_TYPE));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE));
        columnDescuento.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PORCENTAJE_DESCUENTO));
        tablaClientes.setItems(clientesAdminViewModel.getClientes());

        clientesAdminViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isClienteRegistrado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.CLIENTE_REGISTRADO);
            }
            if (newState.isClienteActualizado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.CLIENTE_ACTUALIZADO);
            }
            if (newState.isClienteEliminado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.CLIENTE_ELIMINADO);
            }
        });
    }

    @FXML
    private void updateFields() {
        Cliente c = tablaClientes.getSelectionModel().getSelectedItem();
        if (c != null) {
            updateCliente.setDisable(false);
            deleteCliente.setDisable(false);
            txtDNI.setText(c.getDni());
            txtDNI.setDisable(true);
            txtNombre.setText(c.getNombre());
            selectorType.setValue(c.getClientType());
            selectorType.setDisable(true);
            addCliente.setVisible(false);
            unselect.setVisible(true);
            txtNombre.setDisable(false);
            listIngredientes.getItems().clear();
            listIngredientes.getItems().setAll(c.getAlergenos());
            if (c.getDni().equals("admin")) {
                updateCliente.setDisable(true);
                deleteCliente.setDisable(true);
                txtDNI.setDisable(true);
                txtNombre.setDisable(true);
            }
            if (c.getClientType().equals(ConstantesPantallas.CLIENTE_ESPACIAL)) {
                labelDescuento.setVisible(true);
                txtDescuento.setVisible(true);
                txtDescuento.setText(String.valueOf(((ClienteEspacial) c).getPorcentajeDescuento()));
            } else {
                labelDescuento.setVisible(false);
                txtDescuento.setVisible(false);
            }
        }
    }

    @FXML
    private void onBtnAgregarClienteClicked() {
        Cliente c = getSelectedClientFromFields();
        clientesAdminViewModel.agregarCliente(c);
        onBtnAnularSeleccionClicked();
    }

    @FXML
    private void onBtnEliminarClienteClicked() {
        Cliente c = tablaClientes.getSelectionModel().getSelectedItem();
        clientesAdminViewModel.eliminarCliente(c);
        onBtnAnularSeleccionClicked();
    }

    @FXML
    private void onBtnActualizarClienteClicked() {
        Cliente c = getSelectedClientFromFields();
        clientesAdminViewModel.actualizarCliente(c);
        onBtnAnularSeleccionClicked();
    }

    @FXML
    private void onBtnAnadirIngredinteClicked() {
        listIngredientes.getItems().add(new Ingrediente(txtIngrediente.getText()));
    }

    @FXML
    private void onBtnEliminarIngredienteClicked() {
        listIngredientes.getSelectionModel().getSelection().values().forEach(listIngredientes.getItems()::remove);
    }

    @FXML
    private void onBtnAnularSeleccionClicked() {
        txtDNI.setText(ConstantesPantallas.EMPTY_STRING);
        txtDNI.setDisable(false);
        txtNombre.setDisable(false);
        unselect.setVisible(false);
        addCliente.setVisible(true);
        selectorType.setDisable(false);
        txtNombre.setText(ConstantesPantallas.EMPTY_STRING);
        listIngredientes.getItems().clear();
        labelDescuento.setVisible(true);
        txtDescuento.setVisible(true);
        txtDescuento.setText(ConstantesPantallas.EMPTY_STRING);
        updateCliente.setDisable(true);
        deleteCliente.setDisable(true);
    }

    @FXML
    private void onTipoClienteElegido() {
        if (selectorType.getValue().equals(ConstantesPantallas.CLIENTE_ESPACIAL)) {
            labelDescuento.setVisible(true);
            txtDescuento.setVisible(true);
        } else {
            labelDescuento.setVisible(false);
            txtDescuento.setVisible(false);
        }
    }

    private Cliente getSelectedClientFromFields() {
        List<Ingrediente> ingredientes = listIngredientes.getItems();
        String dni = txtDNI.getText();
        Cliente c;
        if (selectorType.getValue().equals(ConstantesPantallas.CLIENTE_ESPACIAL)) {
            c = new ClienteEspacial(dni, txtNombre.getText(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), ingredientes, Integer.parseInt(txtDescuento.getText()));
        } else {
            c = new ClienteNormal(dni, txtNombre.getText(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), ingredientes);
        }
        return c;
    }
}