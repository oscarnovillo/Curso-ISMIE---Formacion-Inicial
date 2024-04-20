package tui;

import domain.modelo.*;
import domain.services.ServicesClientes;
import domain.services.ServicesClientesEspaciales;
import domain.services.ServicesProductos;
import jakarta.inject.Inject;
import tui.common.Constantes;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UIAdmin {

    private final ServicesClientes scClientes;
    private final ServicesClientesEspaciales scClientesEspaciales;
    private final ServicesProductos scProductos;
    private final UIIngredientes uiIngredientes;
    private final Scanner sc;

    @Inject
    public UIAdmin(ServicesClientes scClientes, ServicesClientesEspaciales scClientesEspaciales,
                   ServicesProductos scProductos, UIIngredientes uiIngredientes, Scanner scanner) {
        this.scClientes = scClientes;
        this.scClientesEspaciales = scClientesEspaciales;
        this.scProductos = scProductos;
        this.uiIngredientes = uiIngredientes;
        this.sc = scanner;
    }

    public void menuAdmin() {
        int opAdmin;
        do {
            System.out.println();
            System.out.println(Constantes.SALIR);
            System.out.println(Constantes.ANADIR_PRODUCTO);
            System.out.println(Constantes.ELIMINAR_PRODUCTO);
            System.out.println(Constantes.VER_PRODUCTOS);
            System.out.println(Constantes.BUSCAR_UN_PRODUCTO);
            System.out.println(Constantes.AUMENTAR_STOCK_PRODUCTO_POR_ID);
            System.out.println(Constantes.DISMINUIR_STOCK_PRODUCTO_POR_ID);
            System.out.println(Constantes.CAMBIAR_PRECIO_PRODUCTO_POR_ID);
            System.out.println(Constantes.ANADIR_CLIENTE);
            System.out.println(Constantes.VER_LISTA_CLIENTES);
            System.out.println(Constantes.ELIMINAR_CLIENTE_POR_DNI);
            System.out.println(Constantes.CAMBIAR_NOMBRE_CLIENTE);
            System.out.println(Constantes.VER_CLIENTES_ORDENADOS_POR_GASTO);
            System.out.println(Constantes.VER_PRODUCTOS_CON_INGREDIENTES_ADMIN);
            System.out.println(Constantes.CAMBIAR_PORCENTAJE_DE_DESCUENTO_CLIENTES_ESPACIALES);
            System.out.println(Constantes.BUSCAR_PRODUCTO_POR_INGREDIENTE);
            System.out.println(Constantes.VER_PRODUCTOS_MAS_COMPRADOS);
            System.out.print(Constantes.ELIGE_UNA_OPCION);
            opAdmin = sc.nextInt();
            sc.nextLine();
            switch (opAdmin) {
                case 0:
                    break;
                case 1:
                    this.uiAnadirProducto(sc);
                    break;
                case 2:
                    this.uiEliminarProducto(sc);
                    break;
                case 3:
                    this.uiMostrarProductos();
                    break;
                case 4:
                    this.uiBuscarProducto(sc);
                    break;
                case 5:
                    this.uiAumentarStock(sc);
                    break;
                case 6:
                    this.uiDisminuirStock(sc);
                    break;
                case 7:
                    this.uiSetPrecioProducto(sc);
                    break;
                case 8:
                    this.uiRegistrarCliente(sc);
                    break;
                case 9:
                    this.uiClientList();
                    break;
                case 10:
                    this.uiEliminarCliente(sc);
                    break;
                case 11:
                    this.uiCambiarNombreCliente(sc);
                    break;
                case 12:
                    this.uiClientListSortGatso();
                    break;
                case 13:
                    this.uiGetProductsWithIngredients();
                    break;
                case 14:
                    this.uiCambiarPorcentajeDescuento(sc);
                    break;
                case 15:
                    this.uiGetProductByIngredient(sc);
                    break;
                case 16:
                    this.uiGetMostBuyedProducts();
                    break;
                default:
                    System.out.println(Constantes.ERROR_ENTRADA_DE_MENU_NO_VALIDA);
            }
        } while (opAdmin != 0);
    }

    private void uiAnadirProducto(Scanner sc) {
        boolean productoAnadido;
        System.out.print(Constantes.INTRODUCE_UN_NOMBRE_DE_PRODUCTO);
        String nombre = sc.nextLine();
        System.out.print(Constantes.INTRODUCE_EL_PRECIO);
        double precio = sc.nextDouble();
        sc.nextLine();
        System.out.print(Constantes.INTRODUCE_EL_STOCK);
        int stock = sc.nextInt();
        sc.nextLine();
        boolean perecedero = true;
        System.out.print(Constantes.INDICA_FECHA_DE_CADUCIDAD);
        String caducidad = sc.nextLine();
        if (caducidad.equals(Constantes.NADA)) {
            perecedero = false;
        }
        Producto producto;
        List<Ingrediente> ingredientes = uiIngredientes.uiListaIngredientes(sc);
        if (perecedero) {
            producto = new ProductoPerecedero(nombre, precio, stock, ingredientes, LocalDate.parse(caducidad));
        } else {
            producto = new ProductoNormal(nombre, precio, stock, ingredientes);
        }
        productoAnadido = scProductos.scAnadirProducto(producto);
        if (productoAnadido) {
            System.out.println(Constantes.PRODUCTO_ANADIDO);
        } else System.out.println(Constantes.PRODUCTO_NO_ANADIDO);
    }


    private void uiEliminarProducto(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_UN_ID_DE_PRODUCTO);
        int idProducto = sc.nextInt();
        sc.nextLine();
        if (scProductos.scEliminarProducto(new ProductoNormal(idProducto))) {
            System.out.println(Constantes.PRODUCTO_ELIMINADO);
        } else {
            System.out.println(Constantes.PRODUCTO_NO_ENCONTRADO);
        }
    }

    private void uiMostrarProductos() {
        System.out.println(Constantes.LISTA_DE_PRODUCTOS);
        scProductos.scGetProductListSortName().forEach(System.out::println);
    }

    private void uiBuscarProducto(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_UN_NOMBRE_DE_PRODUCTO);
        String nombre = sc.nextLine();
        scProductos.scBuscarProducto(nombre).forEach(System.out::println);
    }

    private void uiAumentarStock(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_UN_ID_DE_PRODUCTO);
        int idProducto = sc.nextInt();
        sc.nextLine();
        System.out.print(Constantes.INDICAR_EL_NUMERO_DE_NUEVAS_UNIDADES);
        int nuevasUnidades = sc.nextInt();
        sc.nextLine();
        if (scProductos.scAumentarStock(new ProductoNormal(idProducto), nuevasUnidades)) {
            System.out.println(Constantes.UNIDADES_ANADIDAS);
        } else {
            System.out.println(Constantes.CANTIDAD_DE_UNIDADES_O_ID_NO_VALIDO);
        }
    }

    private void uiDisminuirStock(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_UN_ID_DE_PRODUCTO);
        int idProducto = sc.nextInt();
        sc.nextLine();
        System.out.print(Constantes.INTRODUCE_EL_NUMERO_DE_UNIDADES_A_RETIRAR);
        int unidadesAEliminar = sc.nextInt();
        sc.nextLine();
        if (scProductos.scDisminuirStock(new ProductoNormal(idProducto), unidadesAEliminar)) {
            System.out.println(Constantes.UNIDADES_RETIRADAS);
        } else {
            System.out.println(Constantes.CANTIDAD_DE_UNIDADES_O_ID_NO_VALIDO);
        }
    }

    private void uiSetPrecioProducto(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_UN_ID_DE_PRODUCTO);
        int idProducto = sc.nextInt();
        sc.nextLine();
        System.out.print(Constantes.INTRODUCE_EL_NUEVO_PRECIO);
        double precio = sc.nextDouble();
        sc.nextLine();
        if (scProductos.scSetPrecioProducto(new ProductoNormal(idProducto), precio)) {
            System.out.println(Constantes.PRECIO_MODIFICADO);
        } else {
            System.out.println(Constantes.PRECIO_O_ID_INVALIDO);
        }
    }

    private void uiClientList() {
        scClientes.scGetClientListSortDni().forEach(System.out::println);
    }

    private void uiClientListSortGatso() {
        scClientes.scGetClientListSortGasto().forEach(System.out::println);
    }


    private void uiRegistrarCliente(Scanner sc) {
        String dni;
        System.out.println(Constantes.REGISTRAR_NUEVO_CLIENTE);
        System.out.print(Constantes.INDICA_DNI_DEL_CLIENTE);
        dni = sc.nextLine();
        System.out.print(Constantes.CLIENTE_ESPACIAL_INTRODUCE_1_SI_LO_ES);
        Cliente cliente;
        if (sc.nextLine().equals("1")) {
            cliente = new ClienteEspacial(dni);
        } else {
            cliente = new ClienteNormal(dni);
        }
        if (scClientes.scRegistrarCliente(cliente)) {
            System.out.print(Constantes.INDICA_EL_NOMBRE_DEL_CLIENTE);
            String nombre = sc.nextLine();
            if (scClientes.scSetNombre(cliente, nombre)) {
                System.out.println(Constantes.REGISTRADO_CORRECTAMENTE);
            }
        } else System.out.println(Constantes.DNI_YA_REGISTRADO);
    }

    private void uiEliminarCliente(Scanner sc) {
        System.out.print(Constantes.INTRODUCE_EL_DNI_DEL_CLIENTE_A_ELIMINAR);
        ClienteNormal c = new ClienteNormal(sc.nextLine());
        List<Cliente> clientes = scClientes.scGetClientListSortDni();
        Cliente cliente = clientes.get(clientes.indexOf(c));
        if (scClientes.scEliminarCliente(cliente)) {
            System.out.println(Constantes.CLIENTE_ELIMINADO);
        } else {
            System.out.println(Constantes.CLIENTE_NO_ENCONTRADO);
        }
    }

    private void uiCambiarNombreCliente(Scanner sc) {
        System.out.println(Constantes.INDICA_DNI_DEL_CLIENTE);
        ClienteNormal c = new ClienteNormal(sc.nextLine());
        List<Cliente> clientes = scClientes.scGetClientListSortDni();
        Cliente cliente = clientes.get(clientes.indexOf(c));
        if (scClientes.scExisteCliente(cliente)) {
            System.out.print(Constantes.INDICA_EL_NOMBRE_DEL_CLIENTE);
            String nombre = sc.nextLine();
            if (scClientes.scSetNombre(cliente, nombre)) {
                System.out.println(Constantes.NOMBRE_CAMBIADO_CORRECTAMENTE);
            } else {
                System.out.println(Constantes.NO_SE_HA_PODIDO_CAMBIAR_EL_NOMBRE);
            }
        }
    }

    private void uiGetProductsWithIngredients() {
        scProductos.scGetProductsWithIngredientsAdmin().forEach(System.out::println);
    }

    private void uiCambiarPorcentajeDescuento(Scanner sc) {
        System.out.print(Constantes.INDICA_UN_NUEVO_PORCENTAJE_DE_DESCUENTO);
        int descuento = sc.nextInt();
        sc.nextLine();
        if (scClientesEspaciales.scSetDescuentoClientesEspaciales(descuento)) {
            System.out.println(Constantes.DESCUENTO_APLICADO_A_TODOS_LOS_CLIENTES_ESPACIALES);
        } else {
            System.out.println(Constantes.DESCUENTO_INTRODUCIDO_NO_VALIDO);
        }
    }

    private void uiGetProductByIngredient(Scanner sc) {
        System.out.print(Constantes.INDICA_NOMBRE_DE_INGREDIENTE);
        Ingrediente ingrediente = new Ingrediente(sc.nextLine().trim().toUpperCase());
        scProductos.scGetProductListIngrediente(ingrediente).forEach(System.out::println);
    }

    private void uiGetMostBuyedProducts() {
        scProductos.scGetListaProductosSortAmountBought().forEach(System.out::println);
    }
}