package gui.pantallas.login;

import domain.modelo.Cliente;
import domain.services.ServicesClientes;
import gui.pantallas.common.ConstantesPantallas;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class LoginViewModel {


    private final ServicesClientes servicesClientes;
    private final ObjectProperty<LoginState> state;

    @Inject
    public LoginViewModel(ServicesClientes servicesClientes) {
        this.servicesClientes = servicesClientes;
        state = new SimpleObjectProperty<>(new LoginState(false, false, null));
    }

    public ReadOnlyObjectProperty<LoginState> getState() {
        return state;
    }

    public void doLogin(Cliente usuario) {
        if (servicesClientes.scExisteCliente(usuario)) {
            state.setValue(new LoginState(true, false, null));
        } else {
            state.setValue(new LoginState(false, false, ConstantesPantallas.DNI_NO_REGISTRADO));
        }
    }

    public void doRegister(Cliente usuario) {
        if (servicesClientes.scExisteCliente(usuario)) {
            state.setValue(new LoginState(false, false, ConstantesPantallas.DNI_YA_REGISTRADO));
        } else {
            servicesClientes.scRegistrarCliente(usuario);
            state.setValue(new LoginState(false, true, null));
        }
    }
}
