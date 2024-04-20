package main;

import com.github.javafaker.Faker;
import main.streams.StreamsClientes;
import main.streams.StreamsPedidos;
import main.streams.StreamsProductos;
import main.streams.StreamsVideoClub;
import pedidos.dao.modelo.*;
import pedidos.servicios.ServiciosPedido;

import java.util.Random;

public class MainTodasLasSoluciones {

    public static void main(String[] args) {
//      Setup de la información necesaria para ejecutar las soluciones.
//      Recomiendo ir comentando los diferentes runSolutions(), salen muchas líneas

        new VideoClubSetup().setUpVideoClub();
        generalPedidosSetup();

        runSolutionsClientes();
        runSolutionsPedidos();
        runSolutionsProductos();
        runSolutionsVideoclub();
    }

    private static void generalPedidosSetup(){
        setupClienteClientes();
        setupProductos();
        setupPedidos();
    }

    private static void runSolutionsPedidos() {
        StreamsPedidos streamsPedidos = new StreamsPedidos();
        streamsPedidos.productosAgrupadosPorCantidadDeVecesPedidos();
        streamsPedidos.clienteQueMasDineroSehaGastado();
        streamsPedidos.lacantidadMediaPedidaDeCadaProductoEnCadaPedidoCompuesto();
        streamsPedidos.pedidoSimpleConMasLineasdePedido();
        streamsPedidos.todoelDineroFacturadoEnTotalentodosLosPedidos();
    }

    private static void runSolutionsClientes() {
        StreamsClientes streamsClientes = new StreamsClientes();
        streamsClientes.clienteConMasCuentas();
        streamsClientes.clienteYNumeroCuentas();
        streamsClientes.clientesAgrupadosPorNumeroCuentas();
        streamsClientes.clientesConMasCuentasQuelaMedia();
        streamsClientes.mediaDineroTodasCuentas();
        streamsClientes.clientesOrdenadosPorSaldoTotal();
        streamsClientes.clientesYSumaSaldoTodasCuentas();
        streamsClientes.cuartoClienteConMasDinero();
        streamsClientes.numeroClientesPorDominioCorreo();
    }

    private static void runSolutionsProductos(){
        StreamsProductos streamsProductos = new StreamsProductos();
        streamsProductos.productoMasCaro();
        streamsProductos.productoMasBarato();
        streamsProductos.mediaPrecioTodosLosProductos();
        streamsProductos.productosAgrupadosPorRangoPrecio10en10();
        streamsProductos.productosConPrecio11a20YStockMayor5();
    }

    private static void runSolutionsVideoclub(){
        StreamsVideoClub streamsVideoClub = new StreamsVideoClub();
        streamsVideoClub.numeroSociosSancionados();
        streamsVideoClub.mediaEdadDeSociosSancionados();
        streamsVideoClub.listaDiezProductosMasAlquilados();
        streamsVideoClub.numeroProductosAlquiladosPorTipo();
        streamsVideoClub.todosLosActoresDistintosDeTodasLasPeliculas();
        streamsVideoClub.peliculaConMasActores();
        streamsVideoClub.productoConSuValoracionMediaOrdenadosDeMayoraMenor();
        streamsVideoClub.las10PeliculasMejorValoradas();
        streamsVideoClub.los10VideoJuegosMejorValorados();
        streamsVideoClub.numeroDocumentalesyPeliculasSegunSuFormato();
        streamsVideoClub.todosLosFabricantesDistintosDeVideoJuegosEnUnSoloString();
    }

    private static void setupClienteClientes() {
        Faker f = new Faker();
        ServiciosPedido sp = new ServiciosPedido();

        for (int i = 0; i < 100; i++) {
            String nombre = f.gameOfThrones().character();
            String direccion = f.gameOfThrones().city();
            String tel = f.phoneNumber().toString();
            String email = f.internet().emailAddress();
            Cliente cliente = new Cliente(nombre, direccion, tel, email);
            sp.addCliente(cliente);
            Random r = new Random();
            int numeroCuentas = r.nextInt(100) + 1;
            for (int j = 0; j < numeroCuentas; j++) {
                sp.addCuentaACliente(email, f.idNumber().valid()).setSaldo(r.nextInt(100) + 100);
            }
        }
    }

    private static void setupProductos() {
        Faker f = new Faker();
        ServiciosPedido sp = new ServiciosPedido();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            String nombre = f.animal().name();
            int stock = f.number().numberBetween(200, 300);
            int precio = r.nextInt(100);
            Producto producto = new Producto(nombre, stock, precio);
            sp.addProducto(producto);
        }
    }

    private static void setupPedidos() {
        ServiciosPedido sp = new ServiciosPedido();
        Random r = new Random();

        for (int i = 0; i < 100; i++) {
            Cliente cliente = sp.getTodosClientes().get(r.nextInt(sp.getTodosClientes().size()));
            int randomEstado = r.nextInt(Estado.values().length);
            PedidoCompuesto pedidoCompuesto = new PedidoCompuesto(cliente, Estado.values()[randomEstado]);
            int pedidos = r.nextInt(10) + 10;
            for (int j = 0; j <= pedidos; j++) {
                Cuenta cuenta = cliente.getCuentas().get(r.nextInt(cliente.getCuentas().size()));
                PedidoSimple pedidoSimple = new PedidoSimple(cuenta);
                int lineasP = r.nextInt(5) + 1;
                for (int k = 0; k <= lineasP; k++) {
                    Producto producto = sp.todosProductos().get(r.nextInt(sp.todosProductos().size()));
                    sp.addLineaPedidoAPedidoSimple(producto, r.nextInt(4) + 1, pedidoSimple);
                }
                sp.addPedidoSimpleAPedido(pedidoSimple, pedidoCompuesto);
            }
            sp.addPedidoAPedidos(pedidoCompuesto);
        }
    }


}
