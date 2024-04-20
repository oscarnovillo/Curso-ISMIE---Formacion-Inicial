package gui.pantallas.productos_admin;

import domain.modelo.Producto;
import domain.services.ServicesProductos;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductosAdminViewModel {
    private final ServicesProductos servicesProductos;
    private final ObservableList<Producto> observableProductos;
    private final ObjectProperty<ProductosAdminState> state;

    @Inject
    public ProductosAdminViewModel(ServicesProductos servicesProductos) {
        this.servicesProductos = servicesProductos;
        state = new SimpleObjectProperty<>(new ProductosAdminState(false, false, false, null));
        observableProductos = FXCollections.observableArrayList(servicesProductos.scGetProductList());
    }

    public ObservableList<Producto> getProductos() {
        return FXCollections.unmodifiableObservableList(observableProductos);
    }

    public ReadOnlyObjectProperty<ProductosAdminState> getState() {
        return state;
    }

    public void agregarProducto(Producto p) {
        if (servicesProductos.scAnadirProducto(p)) {
            observableProductos.clear();
            observableProductos.addAll(servicesProductos.scGetProductList());
            state.set(new ProductosAdminState(false, false, true, null));
        } else {
            state.set(new ProductosAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_PRODUCTO));
        }
    }

    public void eliminarProducto(Producto p) {
        if (servicesProductos.scEliminarProducto(p)) {
            observableProductos.clear();
            observableProductos.addAll(servicesProductos.scGetProductList());
            state.set(new ProductosAdminState(false, true, false, null));
        } else {
            state.set(new ProductosAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_ELIMINAR_EL_PRODUCTO));
        }
    }

    public void actualizarProducto(Producto p) {
        if (servicesProductos.scEliminarProducto(p) && servicesProductos.scAnadirProducto(p)) {
            observableProductos.clear();
            observableProductos.addAll(servicesProductos.scGetProductList());
            state.set(new ProductosAdminState(true, false, false, null));
        } else {
            state.set(new ProductosAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_ACTUALIZAR_EL_PRODUCTO));
        }

    }
}
