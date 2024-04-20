package gui.pantallas.productos_admin;

import domain.modelo.Ingrediente;
import domain.modelo.Producto;
import domain.modelo.ProductoNormal;
import domain.modelo.ProductoPerecedero;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.*;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.List;


public class ProductosAdminController extends BasePantallaController {

    private final ProductosAdminViewModel productosAdminViewModel;

    @FXML
    private MFXButton addProducto;
    @FXML
    private MFXButton updateProducto;
    @FXML
    private MFXButton deleteProducto;
    @FXML
    private MFXButton unselect;

    @FXML
    private Label labelCaducidad;

    @FXML
    private MFXTextField txtNombre;
    @FXML
    private MFXTextField txtPrecio;
    @FXML
    private MFXTextField txtStock;
    @FXML
    private MFXDatePicker pickerCaducidad;
    @FXML
    private MFXComboBox<String> selectorType;
    @FXML
    private MFXListView<Ingrediente> listIngredientes;
    @FXML
    private MFXTextField txtIngrediente;

    @FXML
    private TableView<Producto> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> columnID;
    @FXML
    private TableColumn<Producto, String> columnTipo;
    @FXML
    private TableColumn<Producto, String> columnNombre;
    @FXML
    private TableColumn<Producto, Double> columnPrecio;
    @FXML
    private TableColumn<Producto, Integer> columnStock;
    @FXML
    private TableColumn<ProductoPerecedero, LocalDate> columnCaducidad;

    @Inject
    public ProductosAdminController(ProductosAdminViewModel productosAdminViewModel) {
        this.productosAdminViewModel = productosAdminViewModel;
    }

    public void initialize() {
        unselect.setVisible(false);

        updateProducto.setDisable(true);
        deleteProducto.setDisable(true);

        labelCaducidad.setVisible(false);
        pickerCaducidad.setVisible(false);

        txtPrecio.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        txtStock.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));

        listIngredientes.getSelectionModel().setAllowsMultipleSelection(false);
        selectorType.getItems().addAll(ConstantesPantallas.PRODUCTO_PERECEDERO, ConstantesPantallas.PRODUCTO_NORMAL);

        columnID.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRODUCT_TYPE));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRECIO));
        columnStock.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.STOCK));
        columnCaducidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CADUCIDAD));
        tablaProductos.setItems(productosAdminViewModel.getProductos());

        productosAdminViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isProductoAgregado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.PRODUCTO_AGREGADO);
            }
            if (newState.isProductoActualizado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.PRODUCTO_ACTUALIZADO);
            }
            if (newState.isProductoEliminado()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.OPERACION_REALIZADA, ConstantesPantallas.PRODUCTO_ELIMINADO);
            }
        });
    }

    @FXML
    private void updateFields() {
        Producto p = getProductFromTable();
        if (p != null) {
            updateProducto.setDisable(false);
            deleteProducto.setDisable(false);
            txtNombre.setText(p.getNombre());
            txtPrecio.setText(((Double) p.getPrecio()).toString());
            txtStock.setText(((Integer) p.getStock()).toString());
            selectorType.setValue(p.getProductType());
            selectorType.setDisable(true);
            addProducto.setVisible(false);
            unselect.setVisible(true);
            listIngredientes.getItems().clear();
            listIngredientes.getItems().setAll(p.getIngredientes());
            if (p.getProductType().equals(ConstantesPantallas.PRODUCTO_PERECEDERO)) {
                labelCaducidad.setVisible(true);
                pickerCaducidad.setVisible(true);
                pickerCaducidad.setValue(((ProductoPerecedero) p).getCaducidad());
            } else {
                labelCaducidad.setVisible(false);
                pickerCaducidad.setVisible(false);
            }
        }
    }

    @FXML
    private void onBtnAgregarProductoClicked() {
        Producto p = getProductFromFields();
        productosAdminViewModel.agregarProducto(p);
        onBtnAnularSeleccionClicked();
    }

    @FXML
    private void onBtnEliminarProductoClicked() {
        Producto p = getProductFromTable();
        productosAdminViewModel.eliminarProducto(p);
        onBtnAnularSeleccionClicked();
    }

    @FXML
    private void onBtnActualizarProductoClicked() {
        Producto p = getProductFromFields();
        p.setId(tablaProductos.getSelectionModel().getSelectedItem().getId());
        productosAdminViewModel.actualizarProducto(p);
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
        updateProducto.setDisable(true);
        deleteProducto.setDisable(true);
        unselect.setVisible(false);
        addProducto.setVisible(true);
        selectorType.setDisable(false);
        txtNombre.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        listIngredientes.getItems().clear();
        pickerCaducidad.setVisible(true);
        pickerCaducidad.setValue(null);
        labelCaducidad.setVisible(true);
    }

    @FXML
    private void onTipoProductoElegido() {
        if (selectorType.getValue().equals(ConstantesPantallas.PRODUCTO_PERECEDERO)) {
            labelCaducidad.setVisible(true);
            pickerCaducidad.setVisible(true);
        } else {
            labelCaducidad.setVisible(false);
            pickerCaducidad.setVisible(false);
        }
    }

    private Producto getProductFromFields() {
        List<Ingrediente> ingredientes = listIngredientes.getItems();
        Producto p;
        if (selectorType.getValue().equals(ConstantesPantallas.PRODUCTO_PERECEDERO)) {
            p = new ProductoPerecedero(txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()), ingredientes, pickerCaducidad.getValue());
        } else {
            p = new ProductoNormal(txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()), ingredientes);
        }
        return p;
    }

    private Producto getProductFromTable() {
        return tablaProductos.getSelectionModel().getSelectedItem();
    }
}