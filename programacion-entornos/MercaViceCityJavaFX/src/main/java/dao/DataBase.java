package dao;

import domain.modelo.Cliente;
import domain.modelo.Producto;

import java.util.List;
import java.util.Map;

public interface DataBase {
    Map<String, Cliente> readJSONClientes();

    boolean writeJSONClientes(Map<String, Cliente> clientes);

    List<Producto> readJSONProductos();

    boolean writeJSONProductos(List<Producto> inventario);
}
