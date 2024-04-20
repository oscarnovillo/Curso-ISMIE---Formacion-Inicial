package tui;


import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import tui.common.Constantes;


public class Main {

    public static void main(String[] args) {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();

        UILogin uiLogin = container.select(UILogin.class).get();
        System.out.println(Constantes.ASCII_ART);
        System.out.println(Constantes.BIENVENIDO_A_MERCA_VICE_CITY);
        uiLogin.run();

        container.close();
    }
}
