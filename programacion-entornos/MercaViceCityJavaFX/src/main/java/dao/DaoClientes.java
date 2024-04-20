package dao;

import domain.modelo.Cliente;
import domain.modelo.Ingrediente;

import java.util.Collection;
import java.util.List;

public interface DaoClientes {

    boolean addCliente(Cliente cliente);

    boolean deleteCLiente(Cliente cliente);

    boolean existeCliente(Cliente c);

    boolean setNombreCliente(Cliente cliente, String nombre);

    String getNombreCliente(Cliente c);

    Collection<Cliente> getClientList();

    boolean anadirAlergeno(Ingrediente alergeno, Cliente cliente);

    boolean tieneAlergia(Ingrediente alergeno, Cliente cliente);

    int getMediaDescuento();

    void setDescuento(int porcentajeDescuento);

    List<Ingrediente> getAlergenos(Cliente cliente);

    Cliente getCliente(Cliente cliente);
}
