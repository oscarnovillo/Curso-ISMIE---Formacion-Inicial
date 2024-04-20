package gui.pantallas.productos_cliente;

import domain.modelo.Cliente;
import domain.modelo.LineaCompra;
import domain.modelo.Producto;
import domain.services.ServicesCompras;
import domain.services.ServicesProductos;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductosClienteViewModel {

    private final ServicesProductos servicesProductos;
    private final ServicesCompras servicesCompras;
    private final ObservableList<Producto> observableProductos;
    private final ObjectProperty<ProductosClienteState> state;

    @Inject
    public ProductosClienteViewModel(ServicesProductos servicesProductos, ServicesCompras servicesCompras) {
        this.servicesProductos = servicesProductos;
        this.servicesCompras = servicesCompras;
        state = new SimpleObjectProperty<>(new ProductosClienteState(false, false, null));
        observableProductos = FXCollections.observableArrayList();
    }

    public ObservableList<Producto> getProductos() {
        return FXCollections.unmodifiableObservableList(observableProductos);
    }

    public ReadOnlyObjectProperty<ProductosClienteState> getState() {
        return state;
    }

    public void agregarProductoACarrito(Cliente c, LineaCompra lc) {
        if (lc.getProducto() != null) {
            if (servicesCompras.scAddProductoCompraCliente(c, lc)) {
                observableProductos.clear();
                observableProductos.addAll(servicesProductos.scGetProductosDisponiblesNoCaducadosAlergenos(c));
                state.set(new ProductosClienteState(true, false, null));
            } else {
                state.set(new ProductosClienteState(false, false, ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_PRODUCTO));
            }
        } else {
            state.set(new ProductosClienteState(false, false, ConstantesPantallas.DEBE_SELECCIONAR_UN_PRODUCTO));
        }
    }

    public void mostrarTodosLosProductos(Cliente c) {
        observableProductos.clear();
        observableProductos.addAll(servicesProductos.scGetProductosDisponiblesNoCaducadosAlergenos(c));
        state.set(new ProductosClienteState(false, false, null));
    }

    public void mostrarSoloALimentos(Cliente c) {
        observableProductos.clear();
        observableProductos.addAll(servicesProductos.scGetProductsWithIngredientsClient(c));
        state.set(new ProductosClienteState(false, false, null));
    }

    public void buscarProductoPorNombre(Cliente c, String nombre) {
        observableProductos.clear();
        observableProductos.addAll(servicesProductos.scBuscarProductoDisponiblesNoCaducados(c, nombre));
        state.set(new ProductosClienteState(false, false, null));
    }

    public void mostrarCarrito() {
        state.set(new ProductosClienteState(false, true, null));
    }

    public void cargarProductos(Cliente c) {
        observableProductos.clear();
        observableProductos.addAll(servicesProductos.scGetProductosDisponiblesNoCaducadosAlergenos(c));
    }
}
