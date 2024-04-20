package tui;

import domain.modelo.*;
import domain.services.ServicesClientes;
import domain.services.ServicesCompras;
import domain.services.ServicesMonederos;
import domain.services.ServicesProductos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tui.common.Constantes;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemIn;
import uk.org.webcompere.systemstubs.stream.SystemOut;
import uk.org.webcompere.systemstubs.stream.input.LinesAltStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(SystemStubsExtension.class)
@ExtendWith(MockitoExtension.class)
class UIClienteTest {

    @Mock
    ServicesClientes scClientes;

    @Mock
    ServicesMonederos scMonederos;

    @Mock
    ServicesCompras scCompras;

    @Mock
    ServicesProductos scProductos;

    @Mock
    UIIngredientes uiIngredientes;

    Scanner sc;

    private UICliente uiCliente;
    private Producto producto;

    @SystemStub
    private SystemOut systemOut;

    @SystemStub
    private SystemIn systemIn;


    @Test
    @DisplayName("GetNombre")
    void getNombre() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A", "Juan", null, null, null, null);
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        when(scClientes.scGetNombre(cliente)).thenReturn(cliente.getNombre());

        //When
        uiCliente.uiClientName(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains(cliente.getNombre());
    }

    @Test
    @DisplayName("Mostrar productos disponibles")
    void mostrarProductosDisponibles() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        List<Producto> productos = new ArrayList<>(List.of(new ProductoNormal("CACAO", 2, 2, new ArrayList<>())));
        when(scProductos.scGetProductosDisponiblesNoCaducadosAlergenos(cliente)).thenReturn(productos);

        //When
        uiCliente.uiMostrarProductosDisponibles(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("0. Cacao (2.0€) Stock: 2");
    }

    @Test
    @DisplayName("Buscar productos disponibles")
    void buscarProductosDisponibles() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        sc = new Scanner(new LinesAltStream("CACAO"));
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        List<Producto> productos = new ArrayList<>(List.of(new ProductoNormal("CACAO", 2, 2, new ArrayList<>())));
        when(scProductos.scBuscarProductoDisponiblesNoCaducados(cliente, "CACAO")).thenReturn(productos);

        //When
        uiCliente.uiBuscarProductoDisponible(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("0. Cacao (2.0€) Stock: 2");
    }

    @Test
    @DisplayName("Mostrar monederos")
    void mostrarMonederos() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        List<Monedero> monederos = new ArrayList<>(List.of(new Monedero(2, 100.0)));
        when(scMonederos.scGetListaMonederosCliente(cliente)).thenReturn(monederos);

        //When
        uiCliente.uiMostrarMonederosCliente(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("Numero monedero: 2, Importe: 100.0");
    }

    @Nested
    @DisplayName("Añadir producto carrito")
    class AnadirProductoCarrito {

        @Test
        @DisplayName("Añadir producto al carrito")
        void anadirProductoAlCarrito() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            sc = new Scanner(new LinesAltStream("1", "2"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Producto producto = new ProductoNormal(1, "CACAO", 2, 2, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            when(scProductos.scExisteProducto(producto)).thenReturn(true);
            when(scProductos.scGetProductList()).thenReturn(List.of(producto));
            when(scCompras.scAddProductoCompraCliente(cliente, lineaCompra)).thenReturn(true);

            //When
            uiCliente.uiAnadirProductoCarrito(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.PRODUCTO_ANADIDO_AL_CARRITO);
        }

        @Test
        @DisplayName("Añadir producto al carrito")
        void anadirProductoNoExistenteAlCarrito() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            sc = new Scanner(new LinesAltStream("1", "2"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Producto producto = new ProductoNormal(1, "CACAO", 2, 2, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            when(scProductos.scExisteProducto(producto)).thenReturn(false);

            //When
            uiCliente.uiAnadirProductoCarrito(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.PRODUCTO_NO_ANADIDO_AL_CARRITO);
        }

        @Test
        @DisplayName("Añadir producto al carrito")
        void anadirProductoSinStockAlCarrito() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            sc = new Scanner(new LinesAltStream("1", "2"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Producto producto = new ProductoNormal(1, "CACAO", 2, 2, new ArrayList<>());
            LineaCompra lineaCompra = new LineaCompra(producto, 2);
            when(scProductos.scExisteProducto(producto)).thenReturn(true);
            when(scProductos.scGetProductList()).thenReturn(List.of(producto));
            when(scCompras.scAddProductoCompraCliente(cliente, lineaCompra)).thenReturn(false);

            //When
            uiCliente.uiAnadirProductoCarrito(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.PRODUCTO_NO_ANADIDO_AL_CARRITO);
        }
    }

    @Test
    @DisplayName("Mostrar carrito")
    void mostrarCarrito() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        List<LineaCompra> lineasCompra = new ArrayList<>(List.of(new LineaCompra(new ProductoNormal(1, "CACAO", 2, 2, new ArrayList<>()), 2)));
        when(scCompras.scGetCarrito(cliente)).thenReturn(lineasCompra);

        //When
        uiCliente.uiVerCarrito(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("1. Cacao (2.0€) Cantidad: 2");
    }

    @Nested
    @DisplayName("Comprar")
    class Comprar {

        @Test
        @DisplayName("Pagar compra")
        void pagarCompra() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            when(scCompras.scPagarCompra(cliente)).thenReturn(true);

            //When
            uiCliente.uiPagarCompra(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.COMPRA_REALIZADA);
        }

        @Test
        @DisplayName("Pagar compra no puede pagar")
        void pagarCompraNotPosible() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            when(scCompras.scPagarCompra(cliente)).thenReturn(false);

            //When
            uiCliente.uiPagarCompra(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.NO_SE_HA_PODIDO_REALIZAR_LA_COMPRA);
        }
    }

    @Nested
    @DisplayName("Cambiar nombre")
    class CambiarNombre {

        @Test
        @DisplayName("Cambiar nombre")
        void cambiarNombre() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            String nombre = "Nombre";
            sc = new Scanner(new LinesAltStream(nombre));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            when(scClientes.scSetNombre(cliente, nombre)).thenReturn(true);

            //When
            uiCliente.uiCambiarNombre(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.NOMBRE_CAMBIADO_CORRECTAMENTE);
        }

        @Test
        @DisplayName("Cambiar nombre no valido")
        void cambiarNombreNotValid() {
            //Given
            Cliente cliente = new ClienteNormal("12345678A");
            String nombre = "Nombre";
            sc = new Scanner(new LinesAltStream(nombre));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            when(scClientes.scSetNombre(cliente, nombre)).thenReturn(false);

            //When
            uiCliente.uiCambiarNombre(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.NO_SE_HA_PODIDO_CAMBIAR_EL_NOMBRE);
        }
    }

    @Test
    @DisplayName("Añadir alergia")
    void anadirAlergia() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        Ingrediente ingrediente = new Ingrediente("CACAO");
        List<Ingrediente> alergias = new ArrayList<>(List.of(ingrediente));
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        when(uiIngredientes.uiListaIngredientes(sc)).thenReturn(alergias);
        when(scClientes.scEsAlergico(ingrediente, cliente)).thenReturn(false);
        when(scClientes.scAnadirAlergeno(ingrediente, cliente)).thenReturn(true);

        //When
        uiCliente.uiAnadirAlergeno(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains(Constantes.ALERGENO + ingrediente + Constantes.ANADIDO);
    }

    @Test
    @DisplayName("Ver gasto total")
    void verGastoTotal() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        when(scClientes.getCosteCompras(cliente)).thenReturn(2.0);

        //When
        uiCliente.uiVerGastoTotal(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("Su gasto total es de: 2.0€");
    }

    @Test
    @DisplayName("Ver productos con ingredientes")
    void verProductosConIngredientes() {
        //Given
        Cliente cliente = new ClienteNormal("12345678A");
        uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
        List<Producto> productos = new ArrayList<>(List.of(new ProductoNormal("CACAO", 2, 2, new ArrayList<>())));
        when(scProductos.scGetProductsWithIngredientsClient(cliente)).thenReturn(productos);

        //When
        uiCliente.uiVerProductosConIngredientes(cliente);

        //Then
        assertThat(systemOut.getOutput().getLines()).contains("0. Cacao (2.0€) Stock: 2");
    }

    @Nested
    @DisplayName("AddMonedero")
    class AddMonedero {

        @Test
        @DisplayName("AddMonedero")
        void addMonedero() {
            //Given
            sc = new Scanner(new LinesAltStream("2", "100"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Cliente cliente = new ClienteNormal("12345678A");
            when(scMonederos.scExisteMonedero(new Monedero(2))).thenReturn(false);
            when(scMonederos.scAnadirMonedero(new Monedero(2, 100.0), cliente)).thenReturn(true);

            //When
            uiCliente.uiAnadirMonedero(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.MONEDERO_ANADIDO);
        }

        @Test
        @DisplayName("AddMonederoExistente")
        void addMonederoExistente() {
            //Given
            sc = new Scanner(new LinesAltStream("2", "100"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Cliente cliente = new ClienteNormal("12345678A");
            when(scMonederos.scExisteMonedero(new Monedero(2))).thenReturn(true);

            //When
            uiCliente.uiAnadirMonedero(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.MONEDRO_NO_ANADIDO);
        }

        @Test
        @DisplayName("AddMonederoNoPosible")
        void addMonederoNotPosible() {
            //Given
            sc = new Scanner(new LinesAltStream("2", "100"));
            uiCliente = new UICliente(scClientes, scCompras, scProductos, scMonederos, uiIngredientes, sc);
            Cliente cliente = new ClienteNormal("12345678A");
            when(scMonederos.scExisteMonedero(new Monedero(2))).thenReturn(false);
            when(scMonederos.scAnadirMonedero(new Monedero(2, 100.0), cliente)).thenReturn(false);

            //When
            uiCliente.uiAnadirMonedero(cliente);

            //Then
            assertThat(systemOut.getOutput().getLines()).contains(Constantes.MONEDRO_NO_ANADIDO);
        }

    }
}