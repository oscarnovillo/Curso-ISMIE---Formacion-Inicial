package domain.services;

import domain.modelo.Cliente;
import domain.modelo.Monedero;

import java.util.List;

public interface ServicesMonederos {
    boolean scExisteMonedero(Monedero monedero);

    boolean scAnadirMonedero(Monedero monedero, Cliente cliente);

    List<Monedero> scGetListaMonederosCliente(Cliente cliente);
}
