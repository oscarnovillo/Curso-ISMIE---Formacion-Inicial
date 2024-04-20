package org.example.authenticationserver_davidroldan.common;

public class Constantes {
    public static final String ID = "id";
    public static final String CREDENTIALS = "credentials";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";
    public static final String USER_OR_PASSWORD_IS_EMPTY = "User or password empty";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String TOKEN_EXPIRED = "Token expired";
    public static final String TOKEN_IS_EMPTY = "Token is empty";
    public static final String USER = "USER";
    public static final String PATHLOGIN = "/login";
    public static final String PATHREGISTER = "/register";
    public static final String PATHREFRESH = "/refresh";
    public static final String TOKEN = "token";
    public static final String NO_SE_ENCONTRO_LA_ENTRADA_DE_CLAVE_PRIVADA_EN_EL_KEYSTORE = "No se encontró la entrada de clave privada en el keystore.";
    public static final String ERROR_AL_CARGAR_LA_CLAVE_PRIVADA_DEL_KEYSTORE = "Error al cargar la clave privada del keystore.";
    public static final String TOKEN_EXPIRADO = "Token expirado";
    public static final String PKCS_12 = "PKCS12";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String[] SWAGGER_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };
    public static final String SPRING = "spring";
    public static final String APPLICATION_SECURITY_JWT_REFRESH_TOKEN_EXPIRATION = "${application.security.jwt.refresh-token.expiration}";
    public static final String APPLICATION_SECURITY_JWT_ACCESS_TOKEN_EXPIRATION = "${application.security.jwt.access-token.expiration}";
    public static final String APPLICATION_SECURITY_KEYSTORE_PATH = "${application.security.keystore.path}";
    public static final String APPLICATION_SECURITY_KEYSTORE_PASSWORD = "${application.security.keystore.password}";
    public static final String APPLICATION_SECURITY_KEYSTORE_USERKEYSTORE = "${application.security.keystore.userkeystore}";

    private Constantes() {
    }
}
