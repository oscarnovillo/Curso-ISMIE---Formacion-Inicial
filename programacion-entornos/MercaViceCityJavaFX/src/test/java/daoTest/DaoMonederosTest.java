package daoTest;

import dao.impl.DaoMonederosImpl;
import dao.impl.DataBaseImpl;
import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import domain.modelo.Monedero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoMonederosTest {

    @InjectMocks
    DaoMonederosImpl daoMonederos;

    @Mock
    DataBaseImpl database;

    @Captor
    ArgumentCaptor<Map<String, Cliente>> captorCLientes;


    @Test
    @DisplayName("Añadir monedero")
    void addMonedero() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Monedero monedero = new Monedero(1, 10.0);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
        Map<String, Cliente> map = new HashMap<>();
        doAnswer(invocation -> {
            map.putAll(invocation.getArgument(0));
            return true;
        }).when(database).writeJSONClientes(anyMap());

        //When
        boolean respuesta = daoMonederos.addMonederoCliente(monedero, cliente);

        //Then
        assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                () -> assertThat(map.get("dni").getMonederosCliente()).contains(monedero),
                () -> assertThat(respuesta).isTrue(),
                () -> {
                    verify(database).writeJSONClientes(captorCLientes.capture());
                    Map<String, Cliente> clientes = captorCLientes.getValue();
                    assertThat(clientes).containsEntry("dni", cliente);
                    assertThat(clientes.get("dni").getMonederosCliente()).contains(monedero);
                }
        );
    }


    @Nested
    @DisplayName("Existe monedero")
    class ExisteMonedero {


        @Test
        @DisplayName("Existe monedero")
        void existeMonedero() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Monedero monedero = new Monedero(1, 10.0);
            cliente.getMonederosCliente().add(monedero);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            boolean respuesta = daoMonederos.existeMonedero(monedero);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe monedero")
        void noExisteMonedero() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Monedero monedero = new Monedero(1, 10.0);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            boolean respuesta = daoMonederos.existeMonedero(monedero);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Obtener saldo cliente")
    void getSaldoCliente() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Monedero monedero1 = new Monedero(1, 10.0);
        Monedero monedero2 = new Monedero(2, 10.0);
        cliente.getMonederosCliente().add(monedero1);
        cliente.getMonederosCliente().add(monedero2);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

        //When
        double respuesta = daoMonederos.getSaldoTotal(cliente);

        //Then
        assertThat(respuesta).isEqualTo(20.0);
    }


    @Nested
    @DisplayName("Obtener lista monederos cliente")
    class ObtenerListaMonederosCliente {


        @Test
        @DisplayName("Obtener lista monederos cliente")
        void getListaMonederosCliente() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Monedero monedero1 = new Monedero(1, 10.0);
            Monedero monedero2 = new Monedero(2, 10.0);
            cliente.getMonederosCliente().add(monedero1);
            cliente.getMonederosCliente().add(monedero2);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            List<Monedero> respuesta = daoMonederos.getMonederosClienteList(cliente);

            //Then
            assertThat(respuesta).containsExactly(monedero1, monedero2);
        }
    }

    @Nested
    @DisplayName("Restar importe monederos cliente")
    class RestarImporteMonederosCliente {


        @Test
        @DisplayName("Restar importe monederos cliente con fondos")
        void restarImporteMonederosClienteOk() {
            //Given
            double importe = 15.0;
            Cliente cliente = new ClienteNormal("dni");
            Monedero monedero1 = new Monedero(1, 10.0);
            Monedero monedero2 = new Monedero(2, 10.0);
            cliente.getMonederosCliente().add(monedero1);
            cliente.getMonederosCliente().add(monedero2);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoMonederos.restarDineroMonederos(cliente, importe);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getMonederosCliente()
                            .stream()
                            .mapToDouble(Monedero::getImporte).sum()).isEqualTo(5.0),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorCLientes.capture());
                        Map<String, Cliente> clientes = captorCLientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getMonederosCliente()
                                .stream()
                                .mapToDouble(Monedero::getImporte).sum()).isEqualTo(5.0);
                    }
            );
        }

        @Test
        @DisplayName("Restar importe monederos cliente sin fondos")
        void restarImporteMonederosClienteSinFondos() {
            //Given
            double importe = 25.0;
            Cliente cliente = new ClienteNormal("dni");
            Monedero monedero1 = new Monedero(1, 10.0);
            Monedero monedero2 = new Monedero(2, 10.0);
            cliente.getMonederosCliente().add(monedero1);
            cliente.getMonederosCliente().add(monedero2);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoMonederos.restarDineroMonederos(cliente, importe);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getMonederosCliente()
                            .stream()
                            .mapToDouble(Monedero::getImporte).sum()).isEqualTo(20.0),
                    () -> assertThat(respuesta).isFalse(),
                    () -> {
                        verify(database).writeJSONClientes(captorCLientes.capture());
                        Map<String, Cliente> clientes = captorCLientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getMonederosCliente()
                                .stream()
                                .mapToDouble(Monedero::getImporte).sum()).isEqualTo(20.0);
                    }
            );
        }
    }
}