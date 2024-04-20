package daoTest;

import dao.impl.DataBaseImpl;
import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import domain.modelo.Producto;
import domain.modelo.ProductoNormal;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import lombok.extern.log4j.Log4j2;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@ExtendWith(MockitoExtension.class)
@Log4j2
class DataBaseTest {

    private DataBaseImpl dataBase;
    private static SeContainer container;


    @BeforeAll
    static void beforeAll() {
        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        container = initializer.initialize();
    }

    @BeforeEach
    void setUp() {
        try {
            Files.delete(Paths.get("data_test/Clientes.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.delete(Paths.get("data_test/Productos.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataBase = container.select(DataBaseImpl.class).get();
    }

    @AfterAll
    static void afterAll() {
        container.close();
    }

    @Test
    void readJSONClientesFicheroOk() {
        //Given
        Map<String,Cliente> resultado;
        try {
            Files.copy(Paths.get("data_test/ClientesOk.json"), Paths.get("data_test/Clientes.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When
        resultado = dataBase.readJSONClientes();

        //Then
        assertThat(resultado).hasSize(1);
    }

    @Test
    void readJSONClientesFicheroMal() {
        //Given
        Map<String,Cliente> resultado;
        LogCaptor logCaptor = LogCaptor.forClass(DataBaseImpl.class);

        //When
        resultado = dataBase.readJSONClientes();

        //Then
        List<LogEvent> logEvents = logCaptor.getLogEvents();
        assertThat(logEvents).hasSize(1);

        LogEvent logEvent = logEvents.get(0);
        assertThat(logEvent.getLevel()).isEqualTo("ERROR");
        assertThat(logEvent.getThrowable()).isPresent();

        assertThat(logEvent.getThrowable().get())
                .isInstanceOf(IOException.class);

        assertThat(resultado).isEmpty();
    }

    @Test
    void writeJSONClientesFicheroOk() {
        //Given
        Map<String,Cliente> clientes = Map.of("dni",new ClienteNormal("dni"));

        //When
        boolean retorno = dataBase.writeJSONClientes(clientes);

        //Then
        assertAll(() ->assertThat(new File("data_test/Clientes.json")).exists(),
                () ->assertThat(new File("data_test/Clientes.json"))
                        .hasContent("{\"dni\":{\"descuento\":0,\"clientType\":\"ClienteNormal\",\"dni\":\"dni\",\"nombre\":\"\",\"monederoCliente\":[],\"compraActual\":[],\"comprasCliente\":[],\"alergenos\":[]}}"),
                () -> assertThat(retorno).isTrue());
    }

    @Test
    void readJSONProductosFicheroOk() {
        //Given
        List<Producto> resultado;
        try {
            Files.copy(Paths.get("data_test/ProductosOk.json"), Paths.get("data_test/Productos.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When
        resultado = dataBase.readJSONProductos();

        //Then
        assertThat(resultado).hasSize(1);
    }

    @Test
    void readJSONProductosFicheroMal() {
        //Given
        List<Producto> resultado;
        LogCaptor logCaptor = LogCaptor.forClass(DataBaseImpl.class);

        //When
        resultado = dataBase.readJSONProductos();

        //Then
        List<LogEvent> logEvents = logCaptor.getLogEvents();
        assertThat(logEvents).hasSize(1);

        LogEvent logEvent = logEvents.get(0);
        assertThat(logEvent.getLevel()).isEqualTo("ERROR");
        assertThat(logEvent.getThrowable()).isPresent();

        assertThat(logEvent.getThrowable().get())
                .isInstanceOf(IOException.class);

        assertThat(resultado).isEmpty();
    }

    @Test
    void writeJSONProductosFicheroOk() {
        //Given
        List<Producto> productos = List.of(new ProductoNormal(1));

        //When
        boolean retorno = dataBase.writeJSONProductos(productos);


        //Then
        assertAll(() ->assertThat(new File("data_test/Productos.json")).exists(),
                () -> assertThat(new File("data_test/Productos.json")).hasContent("[{\"productType\":\"ProductoNormal\",\"id\":1,\"precio\":0.0,\"stock\":0}]"),
                () -> assertThat(retorno).isTrue());
    }

}