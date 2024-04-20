package servicesTest;

import dao.DaoProductos;
import domain.modelo.ProductoPerecedero;
import domain.services.impl.ServicesProductosPerecederosImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicesProductosPerecederosTest {

    @InjectMocks
    ServicesProductosPerecederosImpl servicesProductosPerecederos;

    @Mock
    DaoProductos daoProductos;


    @Nested
    @DisplayName("Caducado")
    class IsCaducado {

        @Test
        @DisplayName("Si el producto esta caducado")
        void siElProductoEstaCaducado() {
            //Given
            ProductoPerecedero productoPerecedero = new ProductoPerecedero("P004", 2, 2, new ArrayList<>(), LocalDate.now().minusDays(2));
            when(daoProductos.getCaducidad(productoPerecedero)).thenReturn(productoPerecedero.getCaducidad());
            //When
            boolean respuesta = servicesProductosPerecederos.productoCaducado(productoPerecedero);
            //Then
            assertThat(respuesta).isTrue();
        }

        @Test
        @DisplayName("Si el producto no esta caducado")
        void siElProductoNoEstaCaducado() {
            //Given
            ProductoPerecedero productoPerecedero = new ProductoPerecedero("P004", 2, 2, new ArrayList<>(), LocalDate.now().plusDays(2));
            when(daoProductos.getCaducidad(productoPerecedero)).thenReturn(productoPerecedero.getCaducidad());
            //When
            boolean respuesta = servicesProductosPerecederos.productoCaducado(productoPerecedero);
            //Then
            assertThat(respuesta).isFalse();
        }
    }
}
