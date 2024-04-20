package domain.services.impl;

import dao.DaoProductos;
import domain.modelo.ProductoPerecedero;
import domain.services.ServicesProductosPerecederos;
import jakarta.inject.Inject;

import java.time.LocalDate;

public class ServicesProductosPerecederosImpl implements ServicesProductosPerecederos {

    private final DaoProductos daoProductos;

    @Inject
    public ServicesProductosPerecederosImpl(DaoProductos daoProductos) {
        this.daoProductos = daoProductos;
    }

    @Override
    public boolean productoCaducado(ProductoPerecedero perecedero) {
        return daoProductos.getCaducidad(perecedero).isBefore(LocalDate.now());
    }

}
