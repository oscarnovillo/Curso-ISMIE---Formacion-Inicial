package tui;

import domain.modelo.*;
import domain.services.ServicesClientes;
import domain.services.ServicesCompras;
import domain.services.ServicesMonederos;
import domain.services.ServicesProductos;
import jakarta.inject.Inject;
import tui.common.Constantes;

import java.util.List;
import java.util.Scanner;

public class UICliente {

    private final ServicesClientes scClientes;
    private final ServicesCompras scCompras;
    private final ServicesProductos scProductos;
    private final ServicesMonederos scMonederos;
    private final UIIngredientes uiIngredientes;
    private final Scanner sc;

    @Inject
    public UICliente(ServicesClientes scClientes, ServicesCompras scCompras,
                     ServicesProductos scProductos, ServicesMonederos scMonederos,
                     UIIngredientes uiIngredientes, Scanner sc) {
        this.scClientes = scClientes;
        this.scCompras = scCompras;
        this.scProductos = scProductos;
        this.scMonederos = scMonederos;
        this.uiIngredientes = uiIngredientes;
        this.sc = sc;
    }


    public void uiClientName(Cliente c) {
        System.out.println(scClientes.scGetNombre(c));
    }

    public void uiMostrarProductosDisponibles(Cliente c) {
        System.out.println(Constantes.LISTA_DE_PRODUCTOS);
        scProductos.scGetProductosDisponiblesNoCaducadosAlergenos(c).forEach(System.out::println);
    }

    public void uiBuscarProductoDisponible(Cliente cliente) {
        System.out.println(Constantes.INTRODUCE_UN_NOMBRE_DE_PRODUCTO);
        String nombre = sc.nextLine();
        scProductos.scBuscarProductoDisponiblesNoCaducados(cliente, nombre).forEach(System.out::println);
    }

    public void uiAnadirMonedero(Cliente cliente) {
        System.out.println(Constantes.INTRODUCE_EL_NUMERO_DEL_MONEDERO);
        int codMonedero = sc.nextInt();
        sc.nextLine();
        if (!scMonederos.scExisteMonedero(new Monedero(codMonedero))) {
            System.out.println(Constantes.INTRODUCE_EL_IMPORTE_DEL_MONEDERO);
            double importeMonedero = sc.nextDouble();
            if (scMonederos.scAnadirMonedero(new Monedero(codMonedero, importeMonedero), cliente)) {
                System.out.println(Constantes.MONEDERO_ANADIDO);
            } else {
                System.out.println(Constantes.MONEDRO_NO_ANADIDO);
            }
        } else {
            System.out.println(Constantes.MONEDRO_NO_ANADIDO);
        }
    }

    public void uiMostrarMonederosCliente(Cliente cliente) {
        scMonederos.scGetListaMonederosCliente(cliente).forEach(System.out::println);
    }

    public void uiAnadirProductoCarrito(Cliente cliente) {
        System.out.println(Constantes.INTRODUCE_UN_ID_DE_PRODUCTO);
        int idProducto = sc.nextInt();
        sc.nextLine();
        if (scProductos.scExisteProducto(new ProductoNormal(idProducto))) {
            System.out.println(Constantes.CANTIDAD_DISPONIBLE + scProductos.scGetProductStock(new ProductoNormal(idProducto)));
            System.out.println(Constantes.INTRODUCE_CANTIDAD);
            int cantidad = sc.nextInt();
            sc.nextLine();
            Producto producto = scProductos.scGetProductList().stream()
                    .filter(producto1 -> producto1.getId() == idProducto).findFirst().get();
            LineaCompra lineaCompra = new LineaCompra(producto, cantidad);
            if (scCompras.scAddProductoCompraCliente(cliente, lineaCompra)) {
                System.out.println(Constantes.PRODUCTO_ANADIDO_AL_CARRITO);
            } else {
                System.out.println(Constantes.PRODUCTO_NO_ANADIDO_AL_CARRITO);
            }
        } else {
            System.out.println(Constantes.PRODUCTO_NO_ANADIDO_AL_CARRITO);
        }
    }

    public void uiVerCarrito(Cliente cliente) {
        scCompras.scGetCarrito(cliente).forEach(System.out::println);
    }

    public void uiPagarCompra(Cliente cliente) {
        System.out.println(Constantes.INICIANDO_PROCESO_DE_PAGO);
        if (scCompras.scPagarCompra(cliente)) {
            System.out.println(Constantes.COMPRA_REALIZADA);
        } else {
            System.out.println(Constantes.NO_SE_HA_PODIDO_REALIZAR_LA_COMPRA);
        }
    }

    public void uiCambiarNombre(Cliente cliente) {
        System.out.println(Constantes.PORFAVOR_INDICA_TU_NOMBRE);
        String nombre = sc.nextLine();
        if (scClientes.scSetNombre(cliente, nombre)) {
            System.out.println(Constantes.NOMBRE_CAMBIADO_CORRECTAMENTE);
        } else {
            System.out.println(Constantes.NO_SE_HA_PODIDO_CAMBIAR_EL_NOMBRE);
        }
    }

    public void uiAnadirAlergeno(Cliente cliente) {
        List<Ingrediente> alergenos = uiIngredientes.uiListaIngredientes(sc);
        alergenos.forEach(ingrediente -> {
            if (!scClientes.scEsAlergico(ingrediente, cliente)
                    && scClientes.scAnadirAlergeno(ingrediente, cliente)) {
                System.out.println(Constantes.ALERGENO + ingrediente + Constantes.ANADIDO);
            }
        });
    }

    public void uiVerGastoTotal(Cliente cliente) {
        System.out.println(Constantes.SU_GASTO_TOTAL_ES_DE +
                scClientes.getCosteCompras(cliente) + Constantes.EURO);
    }

    public void uiVerProductosConIngredientes(Cliente cliente) {
        scProductos.scGetProductsWithIngredientsClient(cliente).forEach(System.out::println);
    }
}