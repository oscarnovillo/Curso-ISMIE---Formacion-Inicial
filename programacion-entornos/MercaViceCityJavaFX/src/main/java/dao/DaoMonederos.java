package dao;

import domain.modelo.Cliente;
import domain.modelo.Monedero;

import java.util.List;

public interface DaoMonederos {
    boolean addMonederoCliente(Monedero monedero, Cliente cliente);

    boolean existeMonedero(Monedero monedero);

    double getSaldoTotal(Cliente cliente);

    List<Monedero> getMonederosClienteList(Cliente cliente);

    boolean restarDineroMonederos(Cliente cliente, double importe);
}
