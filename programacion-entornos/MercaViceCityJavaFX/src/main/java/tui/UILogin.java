package tui;

import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import domain.services.ServicesClientes;
import jakarta.inject.Inject;
import tui.common.Constantes;

import java.util.List;
import java.util.Scanner;

public class UILogin {


    private final ServicesClientes scClientes;
    private final UIMenuCliente uiMenuCliente;
    private final UIAdmin uiAdmin;
    private final Scanner sc;

    @Inject
    public UILogin(ServicesClientes scClientes,
                   UIMenuCliente uiMenuCliente, UIAdmin uiAdmin, Scanner sc) {
        this.scClientes = scClientes;
        this.uiMenuCliente = uiMenuCliente;
        this.uiAdmin = uiAdmin;
        this.sc = sc;
    }
    public void run() {
        menuLogin(sc);
    }

    private void menuLogin(Scanner sc) {
        int opLogin;
        do {
            System.out.println();
            System.out.println(Constantes.SALIR);
            System.out.println(Constantes.REGISTRARSE);
            System.out.println(Constantes.INICIAR_SESION);
            System.out.print(Constantes.ELIGE_UNA_OPCION);
            opLogin = sc.nextInt();
            sc.nextLine();
            if (opLogin == 1) this.uiRegistrarCliente(sc);
            if (opLogin == 2) this.uiIniciarCliente(sc);
        } while (opLogin != 0);
    }


    private void uiRegistrarCliente(Scanner sc) {
        String dni;
        System.out.println();
        System.out.print(Constantes.INDICA_TU_DNI);
        dni = sc.nextLine();
        ClienteNormal cliente = new ClienteNormal(dni);
        if (scClientes.scRegistrarCliente(cliente)) {
            uiSetNombreCliente(sc, cliente);
        } else System.out.println(Constantes.DNI_YA_REGISTRADO);
    }

    private void uiIniciarCliente(Scanner sc) {
        System.out.println();
        System.out.print(Constantes.INDICA_TU_DNI);
        String dni = sc.nextLine();
        ClienteNormal c = new ClienteNormal(dni);
        if (scClientes.scIsAdmin(c)) {
            uiAdmin.menuAdmin();
        } else {
            if (scClientes.scRegistrarCliente(c)) {
                System.out.println(Constantes.REGISTRARSE_COMO_CLIENTE);
                uiSetNombreCliente(sc, c);
            }
            System.out.println(Constantes.SESION_INICIADA);
            List<Cliente> clientes = scClientes.scGetClientListSortDni();
            Cliente cliente = clientes.get(clientes.indexOf(c));
            uiMenuCliente.menuCliente(cliente);
        }
    }

    private void uiSetNombreCliente(Scanner sc, Cliente cliente) {
        String nombre;
        System.out.print(Constantes.PORFAVOR_INDICA_TU_NOMBRE);
        nombre = sc.nextLine();
        if (scClientes.scSetNombre(cliente, nombre)) {
            System.out.println(Constantes.REGISTRADO_CORRECTAMENTE);
        }
    }
}
