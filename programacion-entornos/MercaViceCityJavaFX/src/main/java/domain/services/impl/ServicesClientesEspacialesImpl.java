package domain.services.impl;

import dao.DaoClientes;
import domain.services.ServicesClientesEspaciales;
import jakarta.inject.Inject;

public class ServicesClientesEspacialesImpl implements ServicesClientesEspaciales {

    private final DaoClientes daoClientes;

    @Inject
    public ServicesClientesEspacialesImpl(DaoClientes daoClientes) {
        this.daoClientes = daoClientes;
    }

    @Override
    public boolean scSetDescuentoClientesEspaciales(int porcentajeDescuento) {
        if (porcentajeDescuento < 90 && porcentajeDescuento > 5) {
            daoClientes.setDescuento(porcentajeDescuento);
            return true;
        } else {
            return false;
        }
    }
}
