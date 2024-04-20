package di;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import java.util.Scanner;

public class ProducerScanner {

    @Produces
    @Singleton
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}
