package gui.pantallas.clientes_admin;

import domain.modelo.Cliente;
import domain.services.ServicesClientes;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientesAdminViewModel {
    private final ServicesClientes servicesClientes;
    private final ObservableList<Cliente> observableClientes;
    private final ObjectProperty<ClientesAdminState> state;

    @Inject
    public ClientesAdminViewModel(ServicesClientes servicesClientes) {
        this.servicesClientes = servicesClientes;
        state = new SimpleObjectProperty<>(new ClientesAdminState(false, false, false, null));
        observableClientes = FXCollections.observableArrayList(servicesClientes.scGetClientListSortDni());
    }

    public ObservableList<Cliente> getClientes() {
        return FXCollections.unmodifiableObservableList(observableClientes);
    }

    public ReadOnlyObjectProperty<ClientesAdminState> getState() {
        return state;
    }

    public void agregarCliente(Cliente c) {
        if (servicesClientes.scRegistrarCliente(c)) {
            observableClientes.clear();
            observableClientes.addAll(servicesClientes.scGetClientListSortDni());
            state.set(new ClientesAdminState(false, false, true, null));
        } else {
            state.set(new ClientesAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_CLIENTE));
        }
    }

    public void eliminarCliente(Cliente c) {
        if (servicesClientes.scEliminarCliente(c)) {
            observableClientes.clear();
            observableClientes.addAll(servicesClientes.scGetClientListSortDni());
            state.set(new ClientesAdminState(false, true, false, null));
        } else {
            state.set(new ClientesAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_ELIMINAR_EL_CLIENTE));
        }
    }

    public void actualizarCliente(Cliente c) {
        if (servicesClientes.scEliminarCliente(c) && servicesClientes.scRegistrarCliente(c)) {
            observableClientes.clear();
            observableClientes.addAll(servicesClientes.scGetClientListSortDni());
            state.set(new ClientesAdminState(true, false, false, null));
        } else {
            state.set(new ClientesAdminState(false, false, false, ConstantesPantallas.NO_SE_HA_PODIDO_ACTUALIZAR_EL_CLIENTE));
        }

    }
}
