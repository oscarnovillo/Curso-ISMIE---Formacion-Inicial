module MercaViceCityJSON {

    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;

    requires lombok;
    requires org.apache.logging.log4j;

    requires jakarta.inject;
    requires jakarta.cdi;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;

    exports gui.main;
    exports gui.main.common;
    exports gui.pantallas.principal;
    exports gui.pantallas.common;
    exports gui.pantallas.login;
    exports gui.pantallas.inicio;
    exports gui.pantallas.registro;
    exports gui.pantallas.productos_admin;
    exports gui.pantallas.clientes_admin;
    exports gui.pantallas.productos_cliente;
    exports gui.pantallas.carrito;
    exports gui.pantallas.perfil;
    exports gui.pantallas.compras_realizadas;

    exports configuracion;
    exports configuracion.common;
    exports dao.impl;
    exports domain.services;
    exports domain.services.impl;
    exports domain.modelo;
    exports tui;
    exports di;

    opens domain.modelo.common;
    opens domain.modelo;
    opens configuracion.common;
    opens configuracion;
    opens tui.common;

    opens gui.main;
    opens gui.pantallas.common;
    opens gui.pantallas.login;
    opens gui.pantallas.principal;
    opens gui.pantallas.inicio;
    opens gui.pantallas.registro;
    opens gui.pantallas.productos_admin;
    opens gui.pantallas.clientes_admin;
    opens gui.pantallas.productos_cliente;
    opens gui.pantallas.carrito;
    opens gui.pantallas.perfil;
    opens gui.pantallas.compras_realizadas;

    opens css;
    opens fxml;
    opens media;
}
