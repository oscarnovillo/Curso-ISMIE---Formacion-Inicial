package main.streams;

import pedidos.dao.modelo.Cliente;
import pedidos.dao.modelo.Cuenta;
import pedidos.servicios.ServiciosPedido;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class StreamsClientes {


    ServiciosPedido sp = new ServiciosPedido();

    List<Cliente> clientes = sp.getTodosClientes();

    // Cliente con mas cuentas
    public void clienteConMasCuentas() {
        System.out.println("\nCliente con mas cuentas --------------------------------------------------------------------------------------------------------------");
        Cliente cliente = clientes.stream()
                .reduce((cliente1, cliente2) -> cliente1.getCuentas().size() >= cliente2.getCuentas().size() ? cliente1 : cliente2)
                .orElse(null);
        System.out.println(cliente);
    }

    // Cliente + Numero de cuentas de cada cliente.
    public void clienteYNumeroCuentas() {
        System.out.println("\nCliente y numero de cuentas --------------------------------------------------------------------------------------------------------------");
        clientes.forEach(c -> System.out.println(c.getNombre() + " , numero de cuentas: " + c.getCuentas().size()));
    }

    // Clientes agrupados por el numero de cuentas
    public void clientesAgrupadosPorNumeroCuentas() {
        System.out.println("\nClientes agrupados por numero de cuentas --------------------------------------------------------------------------------------------------------------");
        clientes.stream()
                .collect(Collectors.groupingBy(cliente -> cliente.getCuentas().size()))
                .forEach((key, value) -> System.out.println(key + " : " + value));
    }

    // Clientes que tienen mas cuentas o iguales a la media.
    public void clientesConMasCuentasQuelaMedia() {
        System.out.println("\nClientes con mas cuentas que la media -------------------------------------------------------");
        double media = clientes.stream().mapToInt(c -> c.getCuentas().size()).average().orElse(-1);
        System.out.println("Media: " + media);
        clientes.stream()
                .filter(c -> c.getCuentas().size() >= media)
                .forEach(p -> System.out.println(p.getNombre() + " numero de cuentas " + p.getCuentas().size()));
    }

    // media de dinero de todas las cuentas
    public void mediaDineroTodasCuentas() {
        System.out.println("\nMedia de dinero de todas las cuentas --------------------------------------------------------------------------------------------------------------");
        double media = clientes.stream()
                .map(Cliente::getCuentas)
                .flatMap(Collection::stream)
                .mapToInt(Cuenta::getSaldo)
                .average().orElse(0);
        System.out.println("Media de dinero: " + media);
    }


    // Clientes ordenados por el saldo total.
    public void clientesOrdenadosPorSaldoTotal() {
        System.out.println("\nClientes ordenados por saldo total --------------------------------------------------------------------------------------------------------------");
        clientes.stream()
                .sorted(Comparator.comparingInt(o -> o.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum()))
                .forEach(c -> System.out.println("Cuenta: " + c));
    }

    // Cliente con la suma del saldo de todas sus cuentas.
    public void clientesYSumaSaldoTodasCuentas() {
        System.out.println("\nClientes y suma del saldo de todas sus cuentas --------------------------------------------------------------------------------------------------------------");
        clientes.stream()
                .sorted(Comparator.comparingInt(o -> o.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum()))
                .forEach(c -> System.out.println("Dinero total: " + c.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum() + ", cuenta: " + c));
    }


    // el cuarto cliente con más dinero
    public void cuartoClienteConMasDinero() {
        System.out.println("\nCuarto cliente con mas dinero --------------------------------------------------------------------------------------------------------------");
        Cliente cliente = clientes.stream()
                .sorted((c1, c2) -> c2.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum() - c1.getCuentas().stream().mapToInt(Cuenta::getSaldo).sum())
                .limit(4)
                .reduce((c1, c2) -> c2).orElse(null);
        System.out.println("Cuarto cliente con más dinero: " + cliente);
    }


    // numero de clientes agrupados por dominio del correo ya@gmail.com
    public void numeroClientesPorDominioCorreo() {
        System.out.println("\nNumero de clientes por dominio de correo --------------------------------------------------------------------------------------------------------------");
        clientes.stream()
                .map(Cliente::getEmail)
                .map(email -> email.split("@")[1])
                .distinct()
                .forEach(domain -> System.out.println(domain + ": " + clientes.stream()
                        .filter(cliente -> cliente.getEmail().split("@")[1].equals(domain))
                        .count()));
    }
}
