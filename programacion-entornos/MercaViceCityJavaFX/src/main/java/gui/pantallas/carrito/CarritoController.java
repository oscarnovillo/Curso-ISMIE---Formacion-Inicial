package gui.pantallas.carrito;

import domain.modelo.*;
import gui.pantallas.common.BasePantallaController;
import gui.pantallas.common.ConstantesPantallas;
import io.github.palexdev.materialfx.controls.MFXListView;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;


public class CarritoController extends BasePantallaController {

    private final CarritoViewModel carritoViewModel;

    @FXML
    private TableView<LineaCompra> tablaProductos;
    @FXML
    private TableColumn<Producto, Integer> columnID;
    @FXML
    private TableColumn<Producto, String> columnTipo;
    @FXML
    private TableColumn<Producto, String> columnNombre;
    @FXML
    private TableColumn<Producto, Double> columnPrecio;
    @FXML
    private TableColumn<LineaCompra, Integer> columnUnidades;
    @FXML
    private TableColumn<ProductoPerecedero, LocalDate> columnCaducidad;
    @FXML
    private MFXListView<Ingrediente> listIngredientes;

    @Inject
    public CarritoController(CarritoViewModel carritoViewModel) {
        this.carritoViewModel = carritoViewModel;
    }


    public void initialize() {

        columnID.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.ID));
        columnTipo.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRODUCT_TYPE));
        columnNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE));
        columnPrecio.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.PRECIO));
        columnUnidades.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CANTIDAD));
        columnCaducidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CADUCIDAD));
        tablaProductos.setItems(carritoViewModel.getProductos());

        carritoViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().showAlert(Alert.AlertType.ERROR, ConstantesPantallas.ERROR, newState.getError());
            }
            if (newState.isCompraRealizada()) {
                this.getPrincipalController().showAlert(Alert.AlertType.INFORMATION, ConstantesPantallas.COMPRA_REALIZADA, ConstantesPantallas.COMPRA_REALIZADA_CON_ÉXITO);
            }
            if (newState.isVerProductos()) {
                this.getPrincipalController().onVerProductos();
            }
        });
    }

    @Override
    public void principalCargado() {
        carritoViewModel.cargarCarrito(getPrincipalController().getActualUser());
    }

    @FXML
    private void updateFields() {
        LineaCompra lineaCompra = tablaProductos.getSelectionModel().getSelectedItem();
        if (lineaCompra.getProducto() != null) {
            listIngredientes.getItems().clear();
            listIngredientes.getItems().setAll(lineaCompra.getProducto().getIngredientes());
        }
    }

    @FXML
    private void onBtnPagarClicked() {
        Cliente cliente = getPrincipalController().getActualUser();
        carritoViewModel.pagarCompra(cliente);
    }

    @FXML
    private void verProductos() {
        carritoViewModel.verProductos();
    }
}
