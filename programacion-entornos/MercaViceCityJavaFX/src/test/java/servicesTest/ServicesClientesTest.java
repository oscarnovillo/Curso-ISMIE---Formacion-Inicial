package servicesTest;

import dao.DaoClientes;
import dao.DaoCompras;
import domain.modelo.*;
import domain.services.impl.ServicesClientesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesClientesTest {

    @InjectMocks
    ServicesClientesImpl servicesClients;

    @Mock
    DaoClientes daoClientes;

    @Mock
    DaoCompras daoCompras;

    @Nested
    @DisplayName("Añadir cliente")
    class AddCliente {


        @Test
        @DisplayName("Registrar cliente normal no admin nuevo")
        void registerClientNormalOk() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);
            when(daoClientes.addCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scRegistrarCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Registrar cliente espacial no admin nuevo")
        void registerClientespacialOk() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);
            when(daoClientes.addCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scRegistrarCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Registrar cliente admin nuevo")
        void registerClientAdmin() {
            //Given
            Cliente cliente = new ClienteNormal("admin");

            //When
            boolean respuesta = servicesClients.scRegistrarCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Registrar cliente normal no admin ya registrado")
        void registerClientNormalYaRegistrado() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scRegistrarCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Registrar cliente espacial no admin ya registrado")
        void registerClientEspacialYaRegistrado() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scRegistrarCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Existe cliente")
    class ExisteCliente {

        @Test
        @DisplayName("Cliente normal existe")
        void existeClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scExisteCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Cliente espacial existe")
        void existeClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scExisteCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Cliente normal no existe")
        void noExisteClientNormal() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scExisteCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Cliente espacial no existe")
        void noExisteClientEspacial() {
            //Given
            Cliente cliente = new ClienteEspacial("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scExisteCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Es admin")
    class EsAdmin {

        @Test
        @DisplayName("Cliente es admin")
        void isAdmin() {
            //Given
            Cliente cliente = new ClienteNormal("admin");

            //When
            boolean respuesta = servicesClients.scIsAdmin(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Cliente no es admin")
        void isNotAdmin() {
            //Given
            Cliente cliente = new ClienteNormal("cliente");

            //When
            boolean respuesta = servicesClients.scIsAdmin(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Cambiar nombre cliente")
    class SetNombreCliente {

        @Test
        @DisplayName("Cliente registrado cambia nombre valido")
        void setNombreOkClient() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            String nuevoNombre = "nuevoNombre";
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.setNombreCliente(cliente, nuevoNombre)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scSetNombre(cliente, nuevoNombre);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Cliente registrado cambia nombre no valido")
        void setEmptyNombreClient() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            String nuevoNombre = "";
            when(daoClientes.existeCliente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scSetNombre(cliente, nuevoNombre);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Cliente no registrado cambia nombre valido")
        void setNombreOkClientNoRegistrado() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            String nuevoNombre = "nuevoNombre";
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scSetNombre(cliente, nuevoNombre);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Cliente registrado cambia nombre no valido")
        void setEmptyNombreClientNoRegistrado() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            String nuevoNombre = "";
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scSetNombre(cliente, nuevoNombre);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Obtener nombre cliente")
    void setNombreOkClient() {
        //Given
        Cliente cliente = new ClienteNormal("dni");
        when(daoClientes.getNombreCliente(cliente)).thenReturn("nombre");

        //When
        String respuesta = servicesClients.scGetNombre(cliente);

        //Then
        assertThat(respuesta).isEqualTo("nombre");
    }


    @Nested
    @DisplayName("Eliminar cliente")
    class EliminarCliente {

        @Test
        @DisplayName("Eliminar cliente registrado")
        void deleteClientOk() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.deleteCLiente(cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scEliminarCliente(cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Eliminar cliente no registrado")
        void deleteClientNoRegistrado() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scEliminarCliente(cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Obtener lista clientes ordenada por DNI")
    void sortClientesByDni() {
        //Given
        Cliente cliente1 = new ClienteNormal("1");
        Cliente cliente2 = new ClienteNormal("2");
        when(daoClientes.getClientList()).thenReturn(new ArrayList<>(List.of(cliente2, cliente1)));

        //When
        List<Cliente> respuesta = servicesClients.scGetClientListSortDni();

        //Then
        assertThat(respuesta).containsExactly(cliente1, cliente2);
    }


    @Nested
    @DisplayName("Añadir alergeno a cliente")
    class AddAlergenoCliente {

        @Test
        @DisplayName("Añadir alergeno a cliente existente")
        void addAlergenoClientOk() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.anadirAlergeno(alergeno, cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scAnadirAlergeno(alergeno, cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir alergeno a cliente no existente")
        void addAlergenoClientNoRegistrado() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scAnadirAlergeno(alergeno, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Obtener coste compras cliente")
    void getCosteCompra() {
        //Given
        Cliente cliente1 = new ClienteNormal("1");
        LineaCompra lineaCompra1 = new LineaCompra(new ProductoNormal("nombre", 1.0, 2, new ArrayList<>()), 10);
        cliente1.getComprasCliente().add(new ArrayList<>(List.of(lineaCompra1)));
        when(daoCompras.getCosteLineaCompra(lineaCompra1)).thenReturn(10.0);

        //When
        Double respuesta = servicesClients.getCosteCompras(cliente1);

        //Then
        assertThat(respuesta).isEqualTo(10.0);
    }


    @Test
    @DisplayName("Obtener lista clientes ordenada por gasto")
    void sortClientesByExpense() {
        //Given
        Cliente cliente1 = new ClienteNormal("1");
        LineaCompra lineaCompra1 = new LineaCompra(new ProductoNormal("nombre", 1.0, 2, new ArrayList<>()), 10);
        cliente1.getComprasCliente().add(new ArrayList<>(List.of(lineaCompra1)));
        Cliente cliente2 = new ClienteNormal("2");
        LineaCompra lineaCompra2 = new LineaCompra(new ProductoNormal("nombre", 1.0, 2, new ArrayList<>()), 20);
        cliente2.getComprasCliente().add(new ArrayList<>(List.of(lineaCompra2)));
        when(daoClientes.getClientList()).thenReturn(new ArrayList<>(List.of(cliente1, cliente2)));
        when(daoCompras.getCosteLineaCompra(lineaCompra1)).thenReturn(10.0);
        when(daoCompras.getCosteLineaCompra(lineaCompra2)).thenReturn(20.0);
        //When
        List<Cliente> respuesta = servicesClients.scGetClientListSortGasto();

        //Then
        assertThat(respuesta).containsExactly(cliente2, cliente1);
    }


    @Nested
    @DisplayName("Obtener lista alergenos cliente")
    class GetListaAlergenoCliente {

        @Test
        @DisplayName("Obtener lista alergenos cliente existente")
        void getAlergenosClienteExiste() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.getAlergenos(cliente)).thenReturn(new ArrayList<>(List.of(alergeno)));

            //When
            List<Ingrediente> respuesta = servicesClients.scGetAlergenos(cliente);

            //Then
            assertThat(respuesta).containsExactly(alergeno);
        }

        @Test
        @DisplayName("Obtener lista alergenos cliente no existente")
        void getAlergenosClienteNoExiste() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            List<Ingrediente> respuesta = servicesClients.scGetAlergenos(cliente);

            //Then
            assertThat(respuesta).isEmpty();
        }
    }

    @Nested
    @DisplayName("Comprobar si cliente es alergico")
    class IsAlergico {

        @Test
        @DisplayName("Es alergico cliente existente")
        void isAlergicoClienteExiste() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.tieneAlergia(alergeno, cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesClients.scEsAlergico(alergeno, cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No es alergico cliente existente")
        void isNotAlergicoClienteExiste() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(true);
            when(daoClientes.tieneAlergia(alergeno, cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scEsAlergico(alergeno, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Cliente no existe")
        void ClienteNoExiste() {
            //Given
            Cliente cliente = new ClienteNormal("dni");
            Ingrediente alergeno = new Ingrediente("nombre");
            when(daoClientes.existeCliente(cliente)).thenReturn(false);

            //When
            boolean respuesta = servicesClients.scEsAlergico(alergeno, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }
}