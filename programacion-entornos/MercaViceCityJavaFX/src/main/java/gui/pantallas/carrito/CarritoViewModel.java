package gui.pantallas.carrito;

import domain.modelo.Cliente;
import domain.modelo.LineaCompra;
import domain.services.ServicesCompras;
import domain.services.ServicesProductos;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CarritoViewModel {
    private final ServicesCompras servicesCompras;
    private final ObservableList<LineaCompra> observableLineasCompra;
    private final ObjectProperty<CarritoState> state;

    @Inject
    public CarritoViewModel(ServicesProductos servicesProductos, ServicesCompras servicesCompras) {
        this.servicesCompras = servicesCompras;
        state = new SimpleObjectProperty<>(new CarritoState(false, false, null));
        observableLineasCompra = FXCollections.observableArrayList();
    }

    public ObservableList<LineaCompra> getProductos() {
        return FXCollections.unmodifiableObservableList(observableLineasCompra);
    }

    public ReadOnlyObjectProperty<CarritoState> getState() {
        return state;
    }

    public void pagarCompra(Cliente cliente) {
        if (servicesCompras.scPagarCompra(cliente)) {
            state.set(new CarritoState(true, false, null));
            observableLineasCompra.clear();
            observableLineasCompra.addAll(servicesCompras.scGetCarrito(cliente));
        } else {
            state.set(new CarritoState(false, false, ConstantesPantallas.NO_SE_PUDO_PAGAR_LA_COMPRA));
        }
    }

    public void cargarCarrito(Cliente cliente) {
        observableLineasCompra.clear();
        observableLineasCompra.addAll(servicesCompras.scGetCarrito(cliente));
    }

    public void verProductos() {
        state.set(new CarritoState(false, true, null));
    }
}
