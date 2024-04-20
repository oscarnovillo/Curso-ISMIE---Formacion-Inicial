package gui.pantallas.common;

public enum Pantallas {

    BIENVENIDA(ConstantesPantallas.FXML_PANTALLA_INICIO_FXML),
    LOGIN(ConstantesPantallas.FXML_PANTALLA_LOGIN_FXML),
    REGISTRO(ConstantesPantallas.FXML_PANTALLA_REGISTRO_FXML),
    PRODUCTOS_ADMIN(ConstantesPantallas.FXML_PANTALLA_PRODUCTOS_ADMIN_FXML),
    CLIENTES_ADMIN(ConstantesPantallas.FXML_PANTALLA_CLIENTES_ADMIN_FXML),
    PRODUCTOS_CLIENTE(ConstantesPantallas.FXML_PANTALLA_PRODUCTOS_CLIENTE_FXML),
    CARRITO(ConstantesPantallas.FXML_PANTALLA_CARRITO_FXML),
    PERFIL(ConstantesPantallas.FXML_PANTALLA_PERFIL_FXML),
    COMPRAS_ANTIGUAS(ConstantesPantallas.FXML_PANTALLA_COMPRAS_FXML);

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }


}
