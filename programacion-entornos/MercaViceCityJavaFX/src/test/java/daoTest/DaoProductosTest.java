package daoTest;

import dao.impl.DaoProductosImpl;
import dao.impl.DataBaseImpl;
import domain.modelo.Producto;
import domain.modelo.ProductoNormal;
import domain.modelo.ProductoPerecedero;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DaoProductosTest {

    @InjectMocks
    DaoProductosImpl daoProductos;

    @Mock
    DataBaseImpl database;

    @Captor
    ArgumentCaptor<List<Producto>> captorProductos;

    @Nested
    @DisplayName("Añadir producto")
    class AddProducto {


        @Test
        @DisplayName("Añadir producto normal")
        void addProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.addProduct(producto);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(p -> p.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getId()).isEqualTo(1),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(p -> p.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getId()).isEqualTo(1);
                    }
            );
        }

        @Test
        @DisplayName("Añadir producto perecedero")
        void addProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.addProduct(producto);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(p -> p.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getId()).isEqualTo(1),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(p -> p.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getId()).isEqualTo(1);
                    }
            );
        }
    }

    @Nested
    @DisplayName("Eliminar producto")
    class DeleteProducto {


        @Test
        @DisplayName("Eliminar producto normal")
        void deleteProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.deleteProduct(producto);

            //Then
            assertAll(() -> assertThat(listaParametro).doesNotContain(producto),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).doesNotContain(producto);
                    }
            );
        }

        @Test
        @DisplayName("Eliminar producto perecedero")
        void deleteProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.deleteProduct(producto);

            //Then
            assertAll(() -> assertThat(listaParametro).doesNotContain(producto),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).doesNotContain(producto);
                    }
            );
        }
    }

    @Nested
    @DisplayName("Existe producto")
    class ExisteProducto {

        @Test
        @DisplayName("Producto normal existe")
        void existeProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));

            //When
            boolean respuesta = daoProductos.existeProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Producto normal no existe")
        void noExisteProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            boolean respuesta = daoProductos.existeProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Producto perecedero existe")
        void existeProductoPerecedero() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));

            //When
            boolean respuesta = daoProductos.existeProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Producto perecedero no existe")
        void noExisteProductoPerecedero() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            boolean respuesta = daoProductos.existeProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Cambiar precio producto")
    class SetPrecioProducto {


        @Test
        @DisplayName("Cambiar precio producto normal")
        void setPrecioProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.setProductPrize(producto, 20.0);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getPrecio()).isEqualTo(20.0),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getPrecio()).isEqualTo(20.0);
                    }
            );
        }

        @Test
        @DisplayName("Cambiar precio producto perecedero")
        void setPrecioProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.setProductPrize(producto, 20.0);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getPrecio()).isEqualTo(20.0),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getPrecio()).isEqualTo(20.0);
                    }
            );
        }

        @Test
        @DisplayName("Cambiar precio producto no existente")
        void setPrecioProductoNoExiste() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            boolean respuesta = daoProductos.setProductPrize(producto, 20.0);

            //Then
            assertThat(respuesta).isFalse();

        }
    }

    @Nested
    @DisplayName("Aumentar stock producto")
    class AumentarStockProducto {


        @Test
        @DisplayName("Aumentar stock producto normal")
        void aumentarStockProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.addProductStock(producto, 2);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getStock()).isEqualTo(4),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getStock()).isEqualTo(4);
                    }
            );
        }

        @Test
        @DisplayName("Aumentar stock producto perecedero")
        void aumentarStockProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.addProductStock(producto, 2);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getStock()).isEqualTo(4),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getStock()).isEqualTo(4);
                    }
            );
        }

        @Test
        @DisplayName("Aumentar stock producto no existente")
        void aumentarStockProductoNoExiste() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            boolean respuesta = daoProductos.addProductStock(producto, 2);

            //Then
            assertThat(respuesta).isFalse();

        }
    }

    @Nested
    @DisplayName("Reducir stock producto")
    class ReducirStockProducto {


        @Test
        @DisplayName("Reducir stock producto normal")
        void reducirStockProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.reduceProductStock(producto, 2);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getStock()).isZero(),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getStock()).isZero();
                    }
            );
        }

        @Test
        @DisplayName("Reducir stock producto perecedero")
        void reducirStockProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));
            List<Producto> listaParametro = new ArrayList<>();
            doAnswer(invocation -> {
                listaParametro.addAll(invocation.getArgument(0));
                return true;
            }).when(database).writeJSONProductos(anyList());

            //When
            boolean respuesta = daoProductos.reduceProductStock(producto, 2);

            //Then
            assertAll(() -> assertThat(listaParametro).contains(producto),
                    () -> assertThat(listaParametro.stream()
                            .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                            .findFirst().get().getStock()).isZero(),
                    () -> assertThat(respuesta).isTrue(),
                    () -> {
                        verify(database).writeJSONProductos(captorProductos.capture());
                        List<Producto> productos = captorProductos.getValue();
                        assertThat(productos).contains(producto);
                        assertThat(productos.stream()
                                .filter(producto1 -> producto1.getNombre().equals(producto.getNombre()))
                                .findFirst().get().getStock()).isZero();
                    }
            );
        }

        @Test
        @DisplayName("Reducir stock producto no existente")
        void reducirStockProductoNoExiste() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            boolean respuesta = daoProductos.addProductStock(producto, 2);

            //Then
            assertThat(respuesta).isFalse();

        }
    }

    @Nested
    @DisplayName("Obtener stock producto")
    class GetStockProducto {


        @Test
        @DisplayName("Obtener stock producto normal")
        void getStockProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));

            //When
            int respuesta = daoProductos.getStockProduct(producto);

            //Then
            assertThat(respuesta).isEqualTo(2);
        }

        @Test
        @DisplayName("Obtener stock producto perecedero")
        void getStockProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto)));

            //When
            int respuesta = daoProductos.getStockProduct(producto);

            //Then
            assertThat(respuesta).isEqualTo(2);
        }


        @Test
        @DisplayName("Obtener stock producto no existente")
        void reducirStockProductoNoExiste() {
            //Given
            Producto producto = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            int respuesta = daoProductos.getStockProduct(producto);

            //Then
            assertThat(respuesta).isZero();

        }
    }


    @Test
    @DisplayName("Obtener lista de productos")
    void getListaProductos() {
        //Given
        Producto producto1 = new ProductoNormal("Producto", 22.0, 2, new ArrayList<>());
        Producto producto2 = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), LocalDate.now());
        when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(producto1, producto2)));

        //When
        List<Producto> respuesta = daoProductos.getProductList();

        //Then
        assertThat(respuesta).containsExactly(producto1, producto2);
    }


    @Nested
    @DisplayName("Obtener caducidad producto perecedero")
    class GetCaducidadProducto {


        @Test
        @DisplayName("Obtener caducidad producto BD")
        void getStockProductoBD() {
            //Given
            LocalDate fecha = LocalDate.now();
            ProductoPerecedero productoBD = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), fecha);
            ProductoPerecedero productoTemporal = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), null);
            when(database.readJSONProductos()).thenReturn(new ArrayList<>(List.of(productoBD)));

            //When
            LocalDate respuesta = daoProductos.getCaducidad(productoTemporal);

            //Then
            assertThat(respuesta).isEqualTo(fecha);
        }

        @Test
        @DisplayName("Obtener caducidad producto temporal")
        void getStockProductoTemporal() {
            //Given
            LocalDate fecha = LocalDate.now();
            ProductoPerecedero productoTemporal = new ProductoPerecedero("Producto", 22.0, 2, new ArrayList<>(), fecha);
            when(database.readJSONProductos()).thenReturn(new ArrayList<>());

            //When
            LocalDate respuesta = daoProductos.getCaducidad(productoTemporal);

            //Then
            assertThat(respuesta).isEqualTo(fecha);
        }
    }
}