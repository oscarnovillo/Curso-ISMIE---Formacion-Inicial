package gui.pantallas.compras_realizadas;

import domain.modelo.Cliente;
import domain.modelo.LineaCompra;
import domain.services.ServicesCompras;
import domain.services.ServicesProductos;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ComprasRealizadasViewModel {

    private final ServicesCompras servicesCompras;
    private final ObjectProperty<ComprasRealizadasState> state;
    private final ObservableList<LineaCompra> observableCompras;

    @Inject
    public ComprasRealizadasViewModel(ServicesProductos servicesProductos, ServicesCompras servicesCompras) {
        this.servicesCompras = servicesCompras;
        state = new SimpleObjectProperty<>(new ComprasRealizadasState(false, false, null));
        observableCompras = FXCollections.observableArrayList();
    }

    public ReadOnlyObjectProperty<ComprasRealizadasState> getState() {
        return state;
    }

    public ObservableList<LineaCompra> getCompras() {
        return FXCollections.unmodifiableObservableList(observableCompras);
    }

    public void cargarCompras(Cliente cliente,int indice) {
        observableCompras.clear();
        observableCompras.addAll(servicesCompras.scGetCompras(cliente).get(indice));
    }

    public void verProductos() {
        state.set(new ComprasRealizadasState(true, false, null));
    }

    public void verPerfil() {
        state.set(new ComprasRealizadasState(false, true, null));
    }

    public List<Integer> getIndiceCompras(Cliente cliente) {
        List<Integer> indices = new ArrayList<>();
        AtomicInteger indice = new AtomicInteger();
        servicesCompras.scGetCompras(cliente).forEach(compras -> {
            indices.add(indice.get());
            indice.getAndIncrement();
        });
        return indices;
    }
}
