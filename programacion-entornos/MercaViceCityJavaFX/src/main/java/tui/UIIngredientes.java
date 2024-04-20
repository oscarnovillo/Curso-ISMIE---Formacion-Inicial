package tui;

import domain.modelo.Ingrediente;
import tui.common.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UIIngredientes {

    public List<Ingrediente> uiListaIngredientes(Scanner sc) {
        int op;
        List<Ingrediente> ingredientes = new ArrayList<>();
        do {
            System.out.println(Constantes.NO_ANADIR_MAS_INGREDIENTES);
            System.out.println(Constantes.ANADIR_INGREDIENTE);
            System.out.print(Constantes.INTRODUCE_UNA_OPCION);
            op = sc.nextInt();
            sc.nextLine();
            if (op == 1) {
                System.out.print(Constantes.INDICA_NOMBRE_DE_INGREDIENTE);
                Ingrediente ingrediente = new Ingrediente(sc.nextLine());
                if (!ingredientes.contains(ingrediente)) ingredientes.add(ingrediente);
            }
        } while (op != 0);
        return ingredientes;
    }
}
