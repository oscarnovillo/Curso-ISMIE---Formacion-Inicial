package gui.pantallas.perfil;

import domain.modelo.Cliente;
import domain.modelo.Ingrediente;
import domain.modelo.Monedero;
import domain.services.ServicesClientes;
import domain.services.ServicesMonederos;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PerfilViewModel {

    private final ServicesClientes servicesClientes;
    private final ServicesMonederos servicesMonederos;
    private final ObservableList<Monedero> observableMonederos;
    private final ObservableList<Ingrediente> observableIngredientes;
    private final ObjectProperty<PerfilState> state;

    @Inject
    public PerfilViewModel(ServicesClientes servicesClientes, ServicesMonederos servicesMonederos) {
        this.servicesClientes = servicesClientes;
        this.servicesMonederos = servicesMonederos;
        state = new SimpleObjectProperty<>(new PerfilState(false, false, false, null));
        observableMonederos = FXCollections.observableArrayList();
        observableIngredientes = FXCollections.observableArrayList();
    }

    public ObservableList<Monedero> getMonederos() {
        return FXCollections.unmodifiableObservableList(observableMonederos);
    }

    public ObservableList<Ingrediente> getAlergenos() {
        return FXCollections.unmodifiableObservableList(observableIngredientes);
    }

    public ReadOnlyObjectProperty<PerfilState> getState() {
        return state;
    }

    public void cargarMonederos(Cliente cliente) {
        observableMonederos.clear();
        observableMonederos.addAll(servicesMonederos.scGetListaMonederosCliente(cliente));
    }

    public void cargarAlergenos(Cliente cliente) {
        observableIngredientes.clear();
        observableIngredientes.addAll(servicesClientes.scGetAlergenos(cliente));
    }

    public void cambiarNombre(Cliente cliente, String nombre) {
        if (servicesClientes.scSetNombre(cliente, nombre)) {
            state.set(new PerfilState(true, false, false, null));
        } else {
            state.set(new PerfilState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_CAMBIAR_EL_NOMBRE));
        }
    }

    public void anadirMonedero(Cliente cliente, Monedero monedero) {
        if (servicesMonederos.scAnadirMonedero(monedero, cliente)) {
            observableMonederos.clear();
            observableMonederos.addAll(servicesMonederos.scGetListaMonederosCliente(cliente));
            state.set(new PerfilState(false, true, false, null));
        } else {
            state.set(new PerfilState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_MONEDERO));
        }
    }

    public void anadirAlergeno(Cliente cliente, Ingrediente alergeno) {
        if (servicesClientes.scAnadirAlergeno(alergeno, cliente)) {
            observableIngredientes.clear();
            observableIngredientes.addAll(servicesClientes.scGetAlergenos(cliente));
            state.set(new PerfilState(false, false, true, null));
        } else {
            state.set(new PerfilState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_ALERGENO));
        }
    }
}
