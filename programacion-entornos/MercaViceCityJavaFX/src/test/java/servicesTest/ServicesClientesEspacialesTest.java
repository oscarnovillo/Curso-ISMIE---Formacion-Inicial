package servicesTest;

import dao.DaoClientes;
import domain.services.impl.ServicesClientesEspacialesImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ServicesClientesEspacialesTest {

    @InjectMocks
    ServicesClientesEspacialesImpl servicesClientesEspaciales;

    @Mock
    DaoClientes daoClientes;

    @Test
    @DisplayName("Actualizar descuento valido")
    void setDescuentoOk() {
        //Given
        int descuento = 60;

        //When
        boolean respuesta = servicesClientesEspaciales.scSetDescuentoClientesEspaciales(descuento);

        //Then
        assertThat(respuesta).isTrue();
    }

    @Test
    @DisplayName("Actualizar descuento demasiado alto")
    void setDescuentoDemasiadoAlto() {
        //Given
        int descuento = 90;

        //When
        boolean respuesta = servicesClientesEspaciales.scSetDescuentoClientesEspaciales(descuento);

        //Then
        assertThat(respuesta).isFalse();
    }

    @Test
    @DisplayName("Actualizar descuento demasiado bajo")
    void setDescuentoDemasiadoBajo() {
        //Given
        int descuento = 5;

        //When
        boolean respuesta = servicesClientesEspaciales.scSetDescuentoClientesEspaciales(descuento);

        //Then
        assertThat(respuesta).isFalse();
    }

}

