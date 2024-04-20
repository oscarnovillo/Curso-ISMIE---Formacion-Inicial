package daoTest;

import dao.impl.DaoClientesImpl;
import dao.impl.DataBaseImpl;
import domain.modelo.Cliente;
import domain.modelo.ClienteEspacial;
import domain.modelo.ClienteNormal;
import domain.modelo.Ingrediente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoClientesTest {

    @InjectMocks
    DaoClientesImpl daoClientes;

    @Mock
    DataBaseImpl database;

    @Captor
    ArgumentCaptor<Map<String, Cliente>> captorClientes;

    @Nested
    @DisplayName("Añadir cliente")
    class AddCliente {


        @Test
        @DisplayName("Añadir cliente normal")
        void addClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>());
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.addCliente(cliente);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                    }
            );
        }

        @Test
        @DisplayName("Añadir cliente espacial")
        void addClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>());
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.addCliente(cliente);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                    }
            );
        }
    }

    @Nested
    @DisplayName("Eliminar cliente")
    class deleteCliente {


        @Test
        @DisplayName("Eliminar cliente normal")
        void deleteClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.deleteCLiente(cliente);

            //Then
            assertAll(() -> assertThat(map).doesNotContainEntry("dni", cliente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).doesNotContainEntry("dni", cliente);
                    }
            );
        }

        @Test
        @DisplayName("Eliminar cliente espacial")
        void deleteClientEspecial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.deleteCLiente(cliente);

            //Then
            assertAll(() -> assertThat(map).doesNotContainEntry("dni", cliente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).doesNotContainEntry("dni", cliente);
                    }
            );
        }
    }

    @Nested
    @DisplayName("Poner nombre cliente")
    class setNombreCliente {


        @Test
        @DisplayName("Poner nombre cliente normal")
        void setNombreClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            String nombre = "nombre";
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.setNombreCliente(cliente, nombre);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getNombre()).isEqualTo(nombre),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getNombre()).isEqualTo(nombre);
                    }
            );
        }

        @Test
        @DisplayName("Poner nombre cliente espacial")
        void setNombreClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            String nombre = "nombre";
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.setNombreCliente(cliente, nombre);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getNombre()).isEqualTo(nombre),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getNombre()).isEqualTo(nombre);
                    }
            );
        }
    }

    @Test
    @DisplayName("Poner nombre cliente")
    void getNombreCliente() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        String nombre = "nombre";
        cliente.setNombre(nombre);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

        //When
        String respuesta = daoClientes.getNombreCliente(cliente);

        //Then
        assertThat(respuesta).isEqualTo(nombre);
    }

    @Nested
    @DisplayName("Existe cliente")
    class existeCliente {

        @Test
        @DisplayName("Existe cliente normal")
        void existeClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            boolean respuesta = daoClientes.existeCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe cliente normal")
        void noExisteClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>());

            //When
            boolean respuesta = daoClientes.existeCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Existe cliente espacial")
        void existeClienteEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            boolean respuesta = daoClientes.existeCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe cliente especial")
        void noExisteClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(database.readJSONClientes()).thenReturn(new HashMap<>());

            //When
            boolean respuesta = daoClientes.existeCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("devolver lista de clientes")
    void getClientes() {

        //Given
        Map<String, Cliente> base = Map.of("123", new ClienteNormal("123"));
        when(database.readJSONClientes()).thenReturn(base);

        //When
        Collection<Cliente> respuesta = daoClientes.getClientList();

        //Then
        assertAll(() -> assertThat(respuesta).containsAll(base.values()),
                () -> assertThat(respuesta).hasSize(1));

    }


    @Nested
    @DisplayName("Añadir alergeno cliente")
    class addAlergenoCliente {

        @Test
        @DisplayName("Añadir alergeno cliente normal")
        void addAlergenoClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.anadirAlergeno(ingrediente, cliente);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getAlergenos()).contains(ingrediente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getAlergenos()).contains(ingrediente);
                    }
            );
        }

        @Test
        @DisplayName("Añadir alergeno cliente espacial")
        void addAlergenoClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
            Map<String, Cliente> map = new HashMap<>();
            doAnswer(invocation -> {
                map.putAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONClientes(anyMap());

            //When
            boolean respuesta = daoClientes.anadirAlergeno(ingrediente, cliente);

            //Then
            assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                    () -> assertThat(map.get("dni").getAlergenos()).contains(ingrediente),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONClientes(captorClientes.capture());
                        Map<String, Cliente> clientes = captorClientes.getValue();
                        assertThat(clientes).containsEntry("dni", cliente);
                        assertThat(clientes.get("dni").getAlergenos()).contains(ingrediente);
                    }
            );
        }
    }

    @Nested
    @DisplayName("Es alergico cliente")
    class isAlergicCliente {

        @Test
        @DisplayName("Es alergico cliente normal")
        void isAlergicClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            cliente.getAlergenos().add(ingrediente);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));


            //When
            boolean respuesta = daoClientes.tieneAlergia(ingrediente, cliente);

            //Then
            assertThat(respuesta).isTrue();

        }

        @Test
        @DisplayName("Es alergico cliente espacial")
        void isAlergicClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            cliente.getAlergenos().add(ingrediente);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));


            //When
            boolean respuesta = daoClientes.tieneAlergia(ingrediente, cliente);

            //Then
            assertThat(respuesta).isTrue();
        }
    }

    @Nested
    @DisplayName("No es alergico cliente")
    class isNotAlergicCliente {

        @Test
        @DisplayName("Es alergico cliente normal")
        void isNotAlergicClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            boolean respuesta = daoClientes.tieneAlergia(ingrediente, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("No es alergico cliente espacial")
        void isNotAlergicClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            Ingrediente ingrediente = new Ingrediente("nombre");
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));


            //When
            boolean respuesta = daoClientes.tieneAlergia(ingrediente, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Media de descuento clientes espaciales")
    void getMediaDescuentoClientesEspaciales() {
        //Given
        ClienteEspacial cliente1 = new ClienteEspacial("dni1");
        cliente1.setPorcentajeDescuento(10);
        ClienteEspacial cliente2 = new ClienteEspacial("dni2");
        cliente2.setPorcentajeDescuento(20);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni1", cliente1, "dni2", cliente2)));

        //When
        int respuesta = daoClientes.getMediaDescuento();

        //Then
        assertThat(respuesta).isEqualTo(15);
    }


    @Test
    @DisplayName("Cambiar descuento clientes espaciales")
    void setMediaDescuentoClientesEspaciales() {
        //Given
        ClienteEspacial cliente1 = new ClienteEspacial("dni1");
        ClienteEspacial cliente2 = new ClienteEspacial("dni2");
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni1", cliente1, "dni2", cliente2)));
        Map<String, Cliente> map = new HashMap<>();
        doAnswer(invocation -> {
            map.putAll(invocation.getArgument(0));
            return true;
        }).when(database).writeJSONClientes(anyMap());
        //When
        daoClientes.setDescuento(10);

        //Then
        assertAll(() -> assertThat(map).containsEntry("dni1", cliente1),
                () -> assertThat(map).containsEntry("dni2", cliente2),
                () -> assertThat(((ClienteEspacial) map.get("dni1")).getPorcentajeDescuento()).isEqualTo(10),
                () -> assertThat(((ClienteEspacial) map.get("dni2")).getPorcentajeDescuento()).isEqualTo(10),
                () -> {
                    verify(database).writeJSONClientes(captorClientes.capture());
                    Map<String, Cliente> clientes = captorClientes.getValue();
                    assertThat(clientes).containsEntry("dni1", cliente1);
                    assertThat(clientes).containsEntry("dni2", cliente2);
                    assertThat(((ClienteEspacial) clientes.get("dni1")).getPorcentajeDescuento()).isEqualTo(10);
                    assertThat(((ClienteEspacial) clientes.get("dni2")).getPorcentajeDescuento()).isEqualTo(10);
                }
        );
    }


    @Test
    @DisplayName("Listar alergenos cliente")
    void getAlergenosCliente() {
        //Given
        ClienteNormal cliente = new ClienteNormal("dni");
        Ingrediente gluten = new Ingrediente("gluten");
        Ingrediente cacao = new Ingrediente("cacao");
        cliente.getAlergenos().add(gluten);
        cliente.getAlergenos().add(cacao);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

        //When
        List<Ingrediente> respuesta = daoClientes.getAlergenos(cliente);

        //Then
        assertThat(respuesta).containsExactly(gluten, cacao);
    }


    @Nested
    @DisplayName("Obtener cliente de bd")
    class getCliente {

        @Test
        @DisplayName("Obtener cliente de bd existe")
        void getClienteExiste() {
            //Given
            ClienteNormal clienteTemporal = new ClienteNormal("dni");
            ClienteNormal clienteBD = new ClienteNormal("dni");
            clienteBD.setNombre("nombre");

            HashMap<String, Cliente> clientesBase = new HashMap<>(Map.of("dni", clienteBD));
            when(database.readJSONClientes()).thenReturn(clientesBase);

            //When
            Cliente respuesta = daoClientes.getCliente(clienteTemporal);

            //Then
            assertAll(
                    () -> assertThat(respuesta.getDni()).isEqualTo(clienteBD.getDni()),
                    () -> assertThat(respuesta.getNombre()).isEqualTo(clienteBD.getNombre())
            );
        }

        @Test
        @DisplayName("Obtener cliente de bd no existe")
        void getClienteNoExiste() {
            //Given
            ClienteNormal clienteTemporal = new ClienteNormal("dni");

            HashMap<String, Cliente> clientesBase = new HashMap<>();
            when(database.readJSONClientes()).thenReturn(clientesBase);

            //When
            Cliente respuesta = daoClientes.getCliente(clienteTemporal);

            //Then
            assertThat(respuesta).isNull();

        }
    }
}

