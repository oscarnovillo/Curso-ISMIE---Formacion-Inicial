package servicesTest;

import dao.DaoMonederos;
import domain.modelo.Cliente;
import domain.modelo.ClienteNormal;
import domain.modelo.Monedero;
import domain.services.impl.ServicesMonederosImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesMonederosTest {

    @InjectMocks
    ServicesMonederosImpl servicesMonederos;

    @Mock
    DaoMonederos daoMonederos;


    @Nested
    @DisplayName("Existe monedero")
    class ExisteMonedero {

        @Test
        @DisplayName("Existe monedero")
        void existeMonedero() {
            //Given
            Monedero monedero = new Monedero(1, 100);
            when(daoMonederos.existeMonedero(monedero)).thenReturn(true);

            //When
            boolean respuesta = servicesMonederos.scExisteMonedero(monedero);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("No existe monedero")
        void noExisteMonedero() {
            //Given
            Monedero monedero = new Monedero(1, 100);
            when(daoMonederos.existeMonedero(monedero)).thenReturn(false);

            //When
            boolean respuesta = servicesMonederos.scExisteMonedero(monedero);

            //Then
            assertThat(respuesta).isFalse();
        }
    }

    @Nested
    @DisplayName("Añadir monedero")
    class AnadirMonedero {

        @Test
        @DisplayName("Añadir monedero nuevo numero e importe mayor que 0")
        void addMonedero() {
            //Given
            Monedero monedero = new Monedero(1, 100);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoMonederos.existeMonedero(monedero)).thenReturn(false);
            when(daoMonederos.addMonederoCliente(monedero, cliente)).thenReturn(true);

            //When
            boolean respuesta = servicesMonederos.scAnadirMonedero(monedero, cliente);

            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Añadir monedero nuevo numero 0 e importe mayor que 0")
        void addMonederoNumero0() {
            //Given
            Monedero monedero = new Monedero(0, 100);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoMonederos.existeMonedero(monedero)).thenReturn(false);

            //When
            boolean respuesta = servicesMonederos.scAnadirMonedero(monedero, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir monedero nuevo numero mayor de 0 e importe 0")
        void addMonederoImporte0() {
            //Given
            Monedero monedero = new Monedero(1, 0);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoMonederos.existeMonedero(monedero)).thenReturn(false);

            //When
            boolean respuesta = servicesMonederos.scAnadirMonedero(monedero, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir monedero nuevo numero e importe 0")
        void addMonederoNumeroImporte0() {
            //Given
            Monedero monedero = new Monedero(0, 0);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoMonederos.existeMonedero(monedero)).thenReturn(false);

            //When
            boolean respuesta = servicesMonederos.scAnadirMonedero(monedero, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }

        @Test
        @DisplayName("Añadir monedero ya registrado")
        void addMonederoYaRegistrado() {
            //Given
            Monedero monedero = new Monedero(1, 100);
            Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            when(daoMonederos.existeMonedero(monedero)).thenReturn(true);

            //When
            boolean respuesta = servicesMonederos.scAnadirMonedero(monedero, cliente);

            //Then
            assertThat(respuesta).isFalse();
        }
    }


    @Test
    @DisplayName("Listar monedero")
    void getListaMonedero() {
        //Given
        Monedero monedero = new Monedero(1, 100);
        Cliente cliente = new ClienteNormal("C001", "Juan", new HashSet<>(Set.of(monedero)), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        when(daoMonederos.getMonederosClienteList(cliente)).thenReturn(List.of(monedero));

        //When
        List<Monedero> respuesta = servicesMonederos.scGetListaMonederosCliente(cliente);

        //Then
        assertThat(respuesta).containsExactly(monedero);
    }

}

