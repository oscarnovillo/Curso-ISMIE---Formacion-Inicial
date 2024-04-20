package gui.pantallas.registro;

import domain.modelo.Cliente;
import domain.services.ServicesClientes;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RegistroViewModel {

    private final ServicesClientes servicesClientes;
    private final ObjectProperty<RegistroState> state;

    @Inject
    public RegistroViewModel(ServicesClientes servicesClientes) {
        this.servicesClientes = servicesClientes;
        state = new SimpleObjectProperty<>(new RegistroState(false, null));
    }

    public ReadOnlyObjectProperty<RegistroState> getState() {
        return state;
    }

    public void registrarNombre(Cliente cliente, String nombre) {
        if (servicesClientes.scSetNombre(cliente, nombre)) {
            state.setValue(new RegistroState(true, null));
        } else {
            state.setValue(new RegistroState(false, ConstantesPantallas.NOMBRE_NO_VÁLIDO));
        }
    }

}
