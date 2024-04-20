package tui;

import domain.modelo.Cliente;
import jakarta.inject.Inject;
import tui.common.Constantes;

import java.util.Scanner;

public class UIMenuCliente {

    private UICliente uiCliente;
    private Scanner sc;

    @Inject
    public UIMenuCliente(UICliente uiCliente, Scanner sc) {
        this.uiCliente = uiCliente;
        this.sc = sc;
    }

    public void menuCliente(Cliente cliente) {
        System.out.println();
        System.out.print(Constantes.BIENVENIDO_DE_NUEVO);
        uiCliente.uiClientName(cliente);
        int opCliente;
        do {
            System.out.println();
            System.out.println(Constantes.SALIR);
            System.out.println(Constantes.ANADIR_MONEDERO);
            System.out.println(Constantes.ANADIR_PRODUCTO_AL_CARRITO_POR_ID);
            System.out.println(Constantes.VER_PRODUCTOS_DISPONIBLES);
            System.out.println(Constantes.BUSCAR_PRODUCTOS_DISPONIBLES_POR_NOMBRE);
            System.out.println(Constantes.VER_LISTA_DE_LA_COMPRA);
            System.out.println(Constantes.VER_MONEDEROS);
            System.out.println(Constantes.REALIZAR_COMPRA);
            System.out.println(Constantes.CAMBIAR_NOMBRE);
            System.out.println(Constantes.ANADIR_ALERGENO);
            System.out.println(Constantes.VER_GASTO_TOTAL_DE_TODAS_LAS_COMPRAS);
            System.out.println(Constantes.VER_PRODUCTOS_CON_INGREDIENTES_CLIENT);
            System.out.println(Constantes.ELIGE_UNA_OPCION);
            opCliente = sc.nextInt();
            sc.nextLine();
            switch (opCliente) {
                case 0:
                    System.out.println(Constantes.SALIENDO);
                    break;
                case 1:
                    uiCliente.uiAnadirMonedero(cliente);
                    break;
                case 2:
                    uiCliente.uiAnadirProductoCarrito(cliente);
                    break;
                case 3:
                    uiCliente.uiMostrarProductosDisponibles(cliente);
                    break;
                case 4:
                    uiCliente.uiBuscarProductoDisponible(cliente);
                    break;
                case 5:
                    uiCliente.uiVerCarrito(cliente);
                    break;
                case 6:
                    uiCliente.uiMostrarMonederosCliente(cliente);
                    break;
                case 7:
                    uiCliente.uiPagarCompra(cliente);
                    break;
                case 8:
                    uiCliente.uiCambiarNombre(cliente);
                    break;
                case 9:
                    uiCliente.uiAnadirAlergeno(cliente);
                    break;
                case 10:
                    uiCliente.uiVerGastoTotal(cliente);
                    break;
                case 11:
                    uiCliente.uiVerProductosConIngredientes(cliente);
                    break;
                default:
                    System.out.println(Constantes.ERROR_ENTRADA_DE_MENU_NO_VALIDA);
            }
        } while (opCliente != 0);
    }

}
