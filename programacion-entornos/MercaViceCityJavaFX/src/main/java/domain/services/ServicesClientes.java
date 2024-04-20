package domain.services;

import domain.modelo.Cliente;
import domain.modelo.Ingrediente;

import java.util.List;

public interface ServicesClientes {
    boolean scRegistrarCliente(Cliente cliente);

    boolean scExisteCliente(Cliente cliente);

    boolean scIsAdmin(Cliente cliente);

    boolean scSetNombre(Cliente cliente, String nombre);

    String scGetNombre(Cliente cliente);

    boolean scEliminarCliente(Cliente cliente);

    List<Cliente> scGetClientListSortDni();

    boolean scAnadirAlergeno(Ingrediente alergeno, Cliente cliente);

    Double getCosteCompras(Cliente cliente);

    List<Cliente> scGetClientListSortGasto();

    boolean scEsAlergico(Ingrediente ingrediente, Cliente cliente);

    List<Ingrediente> scGetAlergenos(Cliente cliente);
}
