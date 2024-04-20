package servicesTest;

import dao.DaoClientes;
import dao.DaoCompras;
import dao.DaoMonederos;
import domain.modelo.*;
import domain.services.ServicesProductos;
import domain.services.ServicesProductosPerecederos;
import domain.services.impl.ServicesComprasImpl;
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
class ServicesComprasTest {

    @InjectMocks
    ServicesComprasImpl servicesCompras;

    @Mock
    DaoMonederos daoMonederos;

    @Mock
    DaoClientes daoClientes;

    @Mock
    DaoCompras daoCompras;

    @Mock
    ServicesProductos servicesProductos;

    @Mock
    ServicesProductosPerecederos servicesProductosPerecederos;


    @Nested
    @DisplayName("Añadir producto al carrito")
    class AddProductToCart {

        @Test
        @DisplayName("Añadir producto normal con stock disponible y sin alergenos al carrito")
        void addProductNormalToCart() {
            //Given
            Producto producto = new ProductoNormal("P002", 2, 4, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(true);
            when(servicesProductos.scGetProductStock(lineaCompra.getProducto())).thenReturn(producto.getStock());
            when(servicesProductos.scContieneAlergenos(lineaCompra.getProducto(), cliente)).thenReturn(false);
            when(servicesProductos.scDisminuirStock(lineaCompra.getProducto(), lineaCompra.getCantidad())).thenReturn(true);
            when(daoCompras.addProductoCompra(lineaCompra, cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir producto perecedero no caducado con stock disponible y sin alergenos al carrito")
        void addProductPerecederoToCart() {
            //Given
            ProductoPerecedero producto = new ProductoPerecedero("P002", 2, 4, new ArrayList<>(), LocalDate.now().plusDays(1));
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductosPerecederos.productoCaducado(producto)).thenReturn(false);
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(true);
            when(servicesProductos.scGetProductStock(lineaCompra.getProducto())).thenReturn(producto.getStock());
            when(servicesProductos.scContieneAlergenos(lineaCompra.getProducto(), cliente)).thenReturn(false);
            when(servicesProductos.scDisminuirStock(lineaCompra.getProducto(), lineaCompra.getCantidad())).thenReturn(true);
            when(daoCompras.addProductoCompra(lineaCompra, cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir producto perecedero caducado con stock disponible y sin alergenos al carrito")
        void addProductPerecederoCaducadoToCart() {
            //Given
            ProductoPerecedero producto = new ProductoPerecedero("P002", 2, 4, new ArrayList<>(), LocalDate.now().plusDays(1));
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductosPerecederos.productoCaducado(producto)).thenReturn(true);
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(true);
            when(servicesProductos.scGetProductStock(lineaCompra.getProducto())).thenReturn(producto.getStock());
            when(servicesProductos.scContieneAlergenos(lineaCompra.getProducto(), cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto sin stock disponible y sin alergenos al carrito")
        void addProductSinStockToCart() {
            //Given
            Producto producto = new ProductoNormal("P002", 2, 1, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(true);
            when(servicesProductos.scGetProductStock(lineaCompra.getProducto())).thenReturn(producto.getStock());

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto normal con stock disponible y siendo alergico al carrito")
        void addProductConALergenosToCart() {
            //Given
            Producto producto = new ProductoNormal("P002", 2, 4, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(true);
            when(servicesProductos.scGetProductStock(lineaCompra.getProducto())).thenReturn(producto.getStock());
            when(servicesProductos.scContieneAlergenos(lineaCompra.getProducto(), cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir producto no registrado")
        void addProductNoRegistradoToCart() {
            //Given
            Producto producto = new ProductoNormal("P002", 2, 4, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(servicesProductos.scExisteProducto(lineaCompra.getProducto())).thenReturn(false);

            //When
            boolean respuesta = servicesCompras.scAddProductoCompraCliente(cliente, lineaCompra);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Pagar compra")
    class PagarCompra {

        @Test
        @DisplayName("Pagar compra cliente normal con dinero suficiente")
        void payCompraClienteNormal() {
            //Given
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);
            when(daoCompras.getCosteCompra(cliente)).thenReturn(10.0);
            when(daoMonederos.getSaldoTotal(cliente)).thenReturn(20.0);
            when(daoMonederos.restarDineroMonederos(cliente, 10.0)).thenReturn(true);
            when(daoCompras.guardarCompraLimpiarCarrito(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesCompras.scPagarCompra(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Pagar compra cliente espacial con dinero suficiente")
        void payCompraClienteEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 50);
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);
            when(daoCompras.getCosteCompra(cliente)).thenReturn(10.0);
            when(daoMonederos.getSaldoTotal(cliente)).thenReturn(20.0);
            when(daoMonederos.restarDineroMonederos(cliente, 5.0)).thenReturn(true);
            when(daoCompras.guardarCompraLimpiarCarrito(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesCompras.scPagarCompra(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Pagar compra cliente normal sin dinero suficiente")
        void payCompraClienteNormalSinFondos() {
            //Given
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);
            when(daoCompras.getCosteCompra(cliente)).thenReturn(10.0);
            when(daoMonederos.getSaldoTotal(cliente)).thenReturn(5.0);

            //When
            boolean respuesta = servicesCompras.scPagarCompra(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Pagar compra cliente espacial sin dinero suficiente")
        void payCompraClienteEspacialSinFondos() {
            //Given
            Cliente cliente = new ClienteEspacial("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 50);
            when(daoClientes.getCliente(cliente)).thenReturn(cliente);
            when(daoCompras.getCosteCompra(cliente)).thenReturn(10.0);
            when(daoMonederos.getSaldoTotal(cliente)).thenReturn(4.0);


            //When
            boolean respuesta = servicesCompras.scPagarCompra(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Test
    @DisplayName("Obtener carrito")
    void getCarrito() {
        //Given
        LineaCompra lineaCompra = new LineaCompra(new ProductoNormal("P002", 2, 2, new ArrayList<>()), 2);
        Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(List.of(lineaCompra)), new ArrayList<>(), new ArrayList<>());
        when(daoCompras.getCarrito(cliente)).thenReturn(cliente.getCompraActual());

        //When
        List<LineaCompra> respuesta = servicesCompras.scGetCarrito(cliente);

        //Then
        assertThat(respuesta).isEqualTo(cliente.getCompraActual());
    }

    @Test
    @DisplayName("Obtener compras")
    void getCompras() {
        //Given
        LineaCompra lineaCompra = new LineaCompra(new ProductoNormal("P002", 2, 2, new ArrayList<>()), 2);
        List<LineaCompra> compra = new ArrayList<>(List.of(lineaCompra));
        Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(List.of(compra)), new ArrayList<>());
        when(daoCompras.getCompras(cliente)).thenReturn(cliente.getComprasCliente());

        //When
        List<List<LineaCompra>> respuesta = servicesCompras.scGetCompras(cliente);

        //Then
        assertThat(respuesta).isEqualTo(cliente.getComprasCliente());
    }
}