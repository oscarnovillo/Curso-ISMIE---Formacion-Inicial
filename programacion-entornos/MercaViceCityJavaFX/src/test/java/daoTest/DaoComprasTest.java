package daoTest;

import dao.impl.DaoComprasImpl;
import dao.impl.DataBaseImpl;
import domain.modelo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoComprasTest {

    @InjectMocks
    DaoComprasImpl daoCompras;

    @Mock
    DataBaseImpl database;

    @Captor
    ArgumentCaptor<Map<String, Cliente>> captorCLientes;


    @Test
    @DisplayName("Añadir linea de compra")
    void addLineaCompra() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
        LineaCompra lineaCompra = new LineaCompra(producto, 10);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
        Map<String, Cliente> map = new HashMap<>();
        doAnswer(invocation -> {
            map.putAll(invocation.getArgument(0));
            return true;
        }).when(database).writeJSONClientes(anyMap());

        //When
        boolean respuesta = daoCompras.addProductoCompra(lineaCompra, cliente);

        //Then
        assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                () -> assertThat(map.get("dni").getCompraActual()).contains(lineaCompra),
                () -> assertThat(respuesta).isTrue(),
                () -> {
                    verify(database).writeJSONClientes(captorCLientes.capture());
                    Map<String, Cliente> clientes = captorCLientes.getValue();
                    assertThat(clientes).containsEntry("dni", cliente);
                    assertThat(clientes.get("dni").getCompraActual()).contains(lineaCompra);
                }
        );
    }


    @Nested
    @DisplayName("Obtener coste compra")
    class GetCosteCompra {


        @Test
        @DisplayName("Obtener coste compra")
        void getCosteCompra() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Producto producto = new ProductoNormal("Producto", 2.0, 2, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 10);
            cliente.getCompraActual().add(lineaCompra);
            when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

            //When
            double respuesta = daoCompras.getCosteCompra(cliente);

            //Then
            assertThat(respuesta).isEqualTo(20.0);
        }

        @Test
        @DisplayName("Obtener coste linea compra")
        void getCosteLineaCompra() {
            //Given
            Producto producto = new ProductoNormal("Producto", 2.0, 2, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 10);

            //When
            double respuesta = daoCompras.getCosteLineaCompra(lineaCompra);

            //Then
            assertThat(respuesta).isEqualTo(20.0);
        }
    }


    @Test
    @DisplayName("Guardar compra y limpiar carrito")
    void saveCompraCleanCarrito() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
        LineaCompra lineaCompra = new LineaCompra(producto, 10);
        cliente.getCompraActual().add(lineaCompra);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));
        Map<String, Cliente> map = new HashMap<>();
        doAnswer(invocation -> {
            map.putAll(invocation.getArgument(0));
            return true;
        }).when(database).writeJSONClientes(anyMap());

        //When
        boolean respuesta = daoCompras.guardarCompraLimpiarCarrito(cliente);

        //Then
        assertAll(() -> assertThat(map).containsEntry("dni", cliente),
                () -> assertThat(map.get("dni").getComprasCliente()
                        .stream().flatMap(Collection::stream)
                        .collect(Collectors.toList())).contains(lineaCompra),
                () -> assertThat(map.get("dni").getCompraActual()).isEmpty(),
                () -> assertThat(respuesta).isTrue(),
                () -> {
                    verify(database).writeJSONClientes(captorCLientes.capture());
                    Map<String, Cliente> clientes = captorCLientes.getValue();
                    assertThat(clientes).containsEntry("dni", cliente);
                    assertThat(clientes.get("dni").getCompraActual()).isEmpty();
                    assertThat(clientes.get("dni").getComprasCliente()
                            .stream().flatMap(Collection::stream)
                            .collect(Collectors.toList()).contains(lineaCompra));
                }
        );
    }


    @Test
    @DisplayName("Listar lineas carrito")
    void gelListaCarrito() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
        LineaCompra lineaCompra = new LineaCompra(producto, 10);
        cliente.getCompraActual().add(lineaCompra);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

        //When
        List<LineaCompra> respuesta = daoCompras.getCarrito(cliente);

        //Then
        assertThat(respuesta).containsExactly(lineaCompra);
    }


    @Test
    @DisplayName("Obtener compras antiguas")
    void gelListaComprasAntiguas() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
        LineaCompra lineaCompra = new LineaCompra(producto, 10);
        List<LineaCompra> compra = new ArrayList<>(List.of(lineaCompra));
        cliente.getComprasCliente().add(compra);
        when(database.readJSONClientes()).thenReturn(new HashMap<>(Map.of("dni", cliente)));

        //When
        List<List<LineaCompra>> respuesta = daoCompras.getCompras(cliente);

        //Then
        assertThat(respuesta).containsExactly(compra);
    }

}