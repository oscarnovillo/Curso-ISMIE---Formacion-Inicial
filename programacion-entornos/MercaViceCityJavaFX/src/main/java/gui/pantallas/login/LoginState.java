package gui.pantallas.login;

import lombok.Data;

@Data
public class LoginState {

    private final boolean loginOK;
    private final boolean register;
    private final String error;

}
