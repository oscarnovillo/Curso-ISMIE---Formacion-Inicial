package servicesTest;

import dao.DaoClientes;
import dao.DaoProductos;
import domain.modelo.*;
import domain.services.impl.ServicesProductosImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesProductosTest {

    @InjectMocks
    ServicesProductosImpl servicesProductos;

    @Mock
    DaoProductos daoProductos;

    @Mock
    DaoClientes daoClientes;

    @Nested
    @DisplayName("Añadir producto")
    class AnadirProducto {

        @Test
        @DisplayName("Añadir producto normal nuevo precio y stock mayor de 0")
        void anadirProductoNormalValido() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(false);
            when(daoProductos.addProduct(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir producto normal nuevo precio 0 y stock mayor de 0")
        void anadirProductoNormalPrecio0() {
            //Given
            Producto producto = new ProductoNormal("P001", 0, 1, new ArrayList<>());

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto normal nuevo precio mayor de 0 y stock 0")
        void anadirProductoNormalStock0() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 0, new ArrayList<>());

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto normal nuevo precio y stock 0")
        void anadirProductoNormalPrecioYStock0() {
            //Given
            Producto producto = new ProductoNormal("P001", 0, 0, new ArrayList<>());

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto ya registrado")
        void anadirProductoYaRegistrado() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto perecible nuevo no caducado")
        void anadirProductoPerecibleValido() {
            //Given
            ProductoPerecedero producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().plusDays(1));
            when(daoProductos.existeProducto(producto)).thenReturn(false);
            when(daoProductos.addProduct(producto)).thenReturn(true);
            when(daoProductos.getCaducidad(producto)).thenReturn(producto.getCaducidad());

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir producto perecible nuevo caducado")
        void anadirProductoPerecibleCaducado() {
            //Given
            ProductoPerecedero producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().minusDays(1));
            when(daoProductos.existeProducto(producto)).thenReturn(false);
            when(daoProductos.getCaducidad(producto)).thenReturn(producto.getCaducidad());

            //When
            boolean respuesta = servicesProductos.scAnadirProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Eliminar producto")
    class EliminarProducto {

        @Test
        @DisplayName("Eliminar producto normal")
        void eliminarProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.deleteProduct(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scEliminarProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Eliminar producto perecible")
        void eliminarProductoPerecible() {
            //Given
            ProductoPerecedero producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().plusDays(1));
            when(daoProductos.deleteProduct(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scEliminarProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }
    }

    @Nested
    @DisplayName("Existe producto")
    class ExisteProducto {

        @Test
        @DisplayName("Existe producto normal")
        void existeProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scExisteProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe producto normal")
        void noExisteProductoNormal() {
            //Given
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(false);

            //When
            boolean respuesta = servicesProductos.scExisteProducto(producto);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Existe producto perecedero")
        void existeProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().plusDays(1));
            when(daoProductos.existeProducto(producto)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scExisteProducto(producto);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe producto perecedero")
        void noExisteProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().plusDays(1));
            when(daoProductos.existeProducto(producto)).thenReturn(false);

            //When
            boolean respuesta = servicesProductos.scExisteProducto(producto);

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
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.getStockProduct(producto)).thenReturn(producto.getStock());

            //When
            int respuesta = servicesProductos.scGetProductStock(producto);

            //Then
            assertThat(respuesta).isEqualTo(producto.getStock());
        }

        @Test
        @DisplayName("Obtener stock producto perecedero")
        void getStockProductoPerecedero() {
            //Given
            Producto producto = new ProductoPerecedero("P001", 1, 1, new ArrayList<>(), LocalDate.now().plusDays(1));
            when(daoProductos.getStockProduct(producto)).thenReturn(producto.getStock());

            //When
            int respuesta = servicesProductos.scGetProductStock(producto);

            //Then
            assertThat(respuesta).isEqualTo(producto.getStock());
        }
    }

    @Nested
    @DisplayName("Cambiar precio producto")
    class SetPrecioProducto {

        @Test
        @DisplayName("Cambiar precio mayor de 0 producto existente")
        void setPrecioOkProductoOk() {
            //Given
            double nuevoPrecio = 2.0;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);
            when(daoProductos.setProductPrize(producto, nuevoPrecio)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scSetPrecioProducto(producto, nuevoPrecio);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Cambiar precio 0 producto existente")
        void setPrecio0ProductoOk() {
            //Given
            double nuevoPrecio = 0;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());

            //When
            boolean respuesta = servicesProductos.scSetPrecioProducto(producto, nuevoPrecio);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Cambiar precio mayor de 0 producto no existente")
        void setPrecioOkProductoNoExiste() {
            //Given
            double nuevoPrecio = 2.0;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(false);

            //When
            boolean respuesta = servicesProductos.scSetPrecioProducto(producto, nuevoPrecio);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Aumentar stock producto")
    class AddStockProducto {

        @Test
        @DisplayName("Aumentar stock mayor de 0 producto existente")
        void addStockOkProductoOk() {
            //Given
            int stock = 10;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);
            when(daoProductos.addProductStock(producto, stock)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scAumentarStock(producto, stock);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Aumentar stock 0 producto existente")
        void addStock0ProductoOk() {
            //Given
            int stock = 0;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());

            //When
            boolean respuesta = servicesProductos.scAumentarStock(producto, stock);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Aumentar stock mayor de 0 producto no existente")
        void addStockOkProductoNoExiste() {
            //Given
            int stock = 10;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(false);

            //When
            boolean respuesta = servicesProductos.scAumentarStock(producto, stock);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Reducir stock producto")
    class ReduceStockProducto {

        @Test
        @DisplayName("Reducir stock mayor de 0 producto existente con stock suficiente")
        void reduceStockOkProductoOk() {
            //Given
            int stock = 10;
            Producto producto = new ProductoNormal("P001", 1, 11, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);
            when(daoProductos.getStockProduct(producto)).thenReturn(producto.getStock());
            when(daoProductos.reduceProductStock(producto, stock)).thenReturn(true);

            //When
            boolean respuesta = servicesProductos.scDisminuirStock(producto, stock);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Reducir stock mayor de 0 producto existente con stock insuficiente")
        void reduceStockOkProductoSinStockSuficiente() {
            //Given
            int stock = 10;
            Producto producto = new ProductoNormal("P001", 1, 9, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);
            when(daoProductos.getStockProduct(producto)).thenReturn(producto.getStock());

            //When
            boolean respuesta = servicesProductos.scDisminuirStock(producto, stock);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Reducir stock 0 producto existente")
        void reduceStock0ProductoOk() {
            //Given
            int stock = 0;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(true);
            when(daoProductos.getStockProduct(producto)).thenReturn(producto.getStock());

            //When
            boolean respuesta = servicesProductos.scDisminuirStock(producto, stock);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Reducir stock mayor de 0 producto no existente")
        void reduceStockOkProductoNoExiste() {
            //Given
            int stock = 10;
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoProductos.existeProducto(producto)).thenReturn(false);

            //When
            boolean respuesta = servicesProductos.scDisminuirStock(producto, stock);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Listar productos")
    class ListarProductos {

        @Test
        @DisplayName("Listar productos")
        void listarProductos() {
            //Given
            List<Producto> productos = new ArrayList<>();
            productos.add(new ProductoNormal("P001", 1, 1, new ArrayList<>()));
            productos.add(new ProductoNormal("P002", 2, 2, new ArrayList<>()));
            productos.add(new ProductoNormal("P003", 3, 3, new ArrayList<>()));
            when(daoProductos.getProductList()).thenReturn(productos);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductList();

            //Then
            assertThat(respuesta).isEqualTo(productos);
        }

        @Test
        @DisplayName("Listar productos ordenados por nombre")
        void listarProductosPorNombre() {
            //Given
            Producto producto1 = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            Producto producto2 = new ProductoNormal("P002", 2, 2, new ArrayList<>());
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3));
            when(daoProductos.getProductList()).thenReturn(productos);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductListSortName();

            //Then
            assertThat(respuesta).containsExactly(producto1, producto2, producto3);
        }

        @Test
        @DisplayName("Listar productos que contienen un ingrediente")
        void listarProductosContieneIngrediente() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Producto producto1 = new ProductoNormal("P001", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("P002", 2, 2, new ArrayList<>());
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3));
            when(daoProductos.getProductList()).thenReturn(productos);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductListIngrediente(ingredinete);

            //Then
            assertThat(respuesta).containsExactly(producto1);
        }

        @Test
        @DisplayName("Listar productos disponibles no caducados que no producen alergia al cliente")
        void listarProductosDisponiblesAlergias() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Producto producto1 = new ProductoNormal("P001", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("P002", 2, 2, new ArrayList<>());
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            Producto producto4 = new ProductoNormal("P004", 2, 0, new ArrayList<>());
            Producto producto5 = new ProductoPerecedero("P004", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(ingredinete)));
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3, producto4, producto5));
            when(daoProductos.getProductList()).thenReturn(productos);
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductosDisponiblesNoCaducadosAlergenos(cliente);

            //Then
            assertThat(respuesta).containsExactlyInAnyOrder(producto2, producto3);
        }

        @Test
        @DisplayName("Listar productos disponibles no caducados que no producen alergia al cliente y tengan ingredientes")
        void listarProductosDisponiblesAlergiasConIngredientes() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Ingrediente ingredinete2 = new Ingrediente("I002");
            Producto producto1 = new ProductoNormal("P001", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("P002", 2, 2, new ArrayList<>(List.of(ingredinete2)));
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            Producto producto4 = new ProductoNormal("P004", 2, 0, new ArrayList<>());
            Producto producto5 = new ProductoPerecedero("P004", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(ingredinete)));
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3, producto4, producto5));
            when(daoProductos.getProductList()).thenReturn(productos);
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductsWithIngredientsClient(cliente);

            //Then
            assertThat(respuesta).containsExactlyInAnyOrder(producto2);
        }

        @Test
        @DisplayName("Listar productos que tengan ingredientes")
        void listarProductosConIngredientes() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Ingrediente ingredinete2 = new Ingrediente("I002");
            Producto producto1 = new ProductoNormal("P001", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("P002", 2, 2, new ArrayList<>(List.of(ingredinete2)));
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            Producto producto4 = new ProductoNormal("P004", 2, 0, new ArrayList<>());
            Producto producto5 = new ProductoPerecedero("P004", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3, producto4, producto5));
            when(daoProductos.getProductList()).thenReturn(productos);

            //When
            List<Producto> respuesta = servicesProductos.scGetProductsWithIngredientsAdmin();

            //Then
            assertThat(respuesta).containsExactlyInAnyOrder(producto1, producto2);
        }

        @Test
        @DisplayName("Buscar productos por nombre")
        void buscarProductosPorNombre() {
            //Given
            String nombre = "abc";
            Ingrediente ingredinete = new Ingrediente("I001");
            Ingrediente ingredinete2 = new Ingrediente("I002");
            Producto producto1 = new ProductoNormal("abcde", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("abcdef", 2, 2, new ArrayList<>(List.of(ingredinete2)));
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            Producto producto4 = new ProductoNormal("P004", 2, 0, new ArrayList<>());
            Producto producto5 = new ProductoPerecedero("abcd", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3, producto4, producto5));
            when(daoProductos.getProductList()).thenReturn(productos);

            //When
            List<Producto> respuesta = servicesProductos.scBuscarProducto(nombre);

            //Then
            assertThat(respuesta).containsExactlyInAnyOrder(producto1, producto2, producto5);
        }

        @Test
        @DisplayName("Buscar productos por nombre mostrar disponibles, no caducado, sin alergia para el cliente")
        void buscarProductosPorNombreDisponiblesAlergenos() {
            //Given
            String nombre = "abc";
            Ingrediente ingredinete = new Ingrediente("I001");
            Ingrediente ingredinete2 = new Ingrediente("I002");
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(ingredinete)));
            Producto producto1 = new ProductoNormal("abcde", 1, 1, new ArrayList<>(List.of(ingredinete)));
            Producto producto2 = new ProductoNormal("abcdef", 2, 2, new ArrayList<>(List.of(ingredinete2)));
            Producto producto3 = new ProductoNormal("P003", 3, 3, new ArrayList<>());
            Producto producto4 = new ProductoNormal("P004", 2, 0, new ArrayList<>());
            Producto producto5 = new ProductoPerecedero("abcd", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            Producto producto6 = new ProductoPerecedero("abcdd", 2, 2, new ArrayList<>(), LocalDate.now().plusDays(2));
            List<Producto> productos = new ArrayList<>(List.of(producto1, producto2, producto3, producto4, producto5, producto6));
            when(daoProductos.getProductList()).thenReturn(productos);
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);

            //When
            List<Producto> respuesta = servicesProductos.scBuscarProductoDisponiblesNoCaducados(cliente, nombre);

            //Then
            assertThat(respuesta).containsExactlyInAnyOrder(producto2, producto6);
        }

        @Test
        @DisplayName("Listar productos ordenados por veces comprados")
        void listarProductosOrdenadosPorVecesComprados() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            LineaCompra l1 = new LineaCompra(new ProductoNormal("1", 1, 1, new ArrayList<>()), 2);
            LineaCompra l2 = new LineaCompra(new ProductoNormal("1", 1, 1, new ArrayList<>()), 2);
            LineaCompra l3 = new LineaCompra(new ProductoNormal("2", 1, 1, new ArrayList<>()), 5);
            LineaCompra l4 = new LineaCompra(new ProductoNormal("3", 1, 1, new ArrayList<>()), 3);
            Cliente cliente1 = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(new ArrayList<>(List.of(List.of(l1, l2)))), new ArrayList<>(List.of(ingredinete)));
            Cliente cliente2 = new ClienteNormal("C002", "Pedro", new HashSet<>(), new ArrayList<>(), new ArrayList<>(new ArrayList<>(List.of(List.of(l3, l4)))), new ArrayList<>(List.of(ingredinete)));
            when(daoClientes.getClientList()).thenReturn(new ArrayList<>(List.of(cliente1, cliente2)));

            //When
            List<String> respuesta = servicesProductos.scGetListaProductosSortAmountBought();

            //Then
            String producto1 = "0. 1 (1.0€) Cantidad comprada: 4";
            String producto2 = "0. 2 (1.0€) Cantidad comprada: 5";
            String producto3 = "0. 3 (1.0€) Cantidad comprada: 3";
            assertThat(respuesta).containsExactlyInAnyOrder(producto1, producto2, producto3);
        }
    }

    @Nested
    @DisplayName("Contiene alergenos")
    class ContieneAlergenos {

        @Test
        @DisplayName("Producto contiene alergenos para el cliente")
        void contieneAlergenos() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(ingredinete)));
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>(List.of(ingredinete)));
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);

            //When
            boolean respuesta = servicesProductos.scContieneAlergenos(producto, cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Producto no contiene alergenos para el cliente")
        void noContieneAlergenos() {
            //Given
            Ingrediente ingredinete = new Ingrediente("I001");
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(List.of(ingredinete)));
            Producto producto = new ProductoNormal("P001", 1, 1, new ArrayList<>());
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);

            //When
            boolean respuesta = servicesProductos.scContieneAlergenos(producto, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }
}