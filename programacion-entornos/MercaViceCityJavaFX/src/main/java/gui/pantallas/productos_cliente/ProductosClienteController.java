package gui.pantallas.productos_cliente;

import domain.modelo.*;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductosClienteController extends BasePantallaController {

    public static final String PRODUCTO_AGREGADO = "Producto agregado";
    private final ProductosClienteViewModel productosClienteViewModel;

    @FXML
    private MFXTextField txtNombre;

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
    private TableColumn<ProductoPerecedero, LocalDate> columnCaducidad;
    @FXML
    private MFXListView<Ingrediente> listIngredientes;

    @FXML
    private MFXComboBox<Integer> comboUnidades;

    @Inject
    public ProductosClienteController(ProductosClienteViewModel productosClienteViewModel) {
        this.productosClienteViewModel = productosClienteViewModel;
    }

    public void initialize() {
        List<Integer> valores = new ArrayList<>();
        for (int i = 1; i < 22; i++) {
            valores.add(i);
        }
        comboUnidades.getItems().addAll(valores);
        comboUnidades.getSelectionModel().selectFirst();
        columnID.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRODUCT_TYPE));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRECIO));
        columnCaducidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CADUCIDAD));
        tablaProductos.setItems(productosClienteViewModel.getProductos());

        productosClienteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isProductoAgregadoALCarrito()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, PRODUCTO_AGREGADO, ConstantesPantallas.EL_PRODUCTO_SE_HA_AGREGADO_AL_CARRITO);
            }
            if (newState.isVerCarrito()) {
                this.getPrincipalController().onVerCarrito();
            }
        });
    }

    @Override
    public void principalCargado() {
        Cliente cliente = getPrincipalController().getActualUser();
        productosClienteViewModel.cargarProductos(cliente);
    }

    @FXML
    private void updateFields(MouseEvent mouseEvent) {
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        if (producto != null) {
            listIngredientes.getItems().clear();
            listIngredientes.getItems().setAll(producto.getIngredientes());
        }
    }

    @FXML
    private void onBtnAgregarACarritoClicked(ActionEvent actionEvent) {
        Cliente cliente = getPrincipalController().getActualUser();
        Producto producto = tablaProductos.getSelectionModel().getSelectedItem();
        LineaCompra lineaCompra = new LineaCompra(producto, comboUnidades.getSelectionModel().getSelectedItem());
        productosClienteViewModel.agregarProductoACarrito(cliente, lineaCompra);
    }

    @FXML
    private void onBtnBuscarProductoClicked(ActionEvent actionEvent) {
        Cliente cliente = getPrincipalController().getActualUser();
        String nombre = txtNombre.getText();
        productosClienteViewModel.buscarProductoPorNombre(cliente, nombre);
    }

    @FXML
    private void onBtnMostrarTodosClicked(ActionEvent actionEvent) {
        Cliente cliente = getPrincipalController().getActualUser();
        productosClienteViewModel.mostrarTodosLosProductos(cliente);
    }

    @FXML
    private void onBtnMostrarSoloAlimentosClicked(ActionEvent actionEvent) {
        Cliente cliente = getPrincipalController().getActualUser();
        productosClienteViewModel.mostrarSoloALimentos(cliente);
    }

    @FXML
    private void verCarrito(ActionEvent actionEvent) {
        productosClienteViewModel.mostrarCarrito();
    }
}