package gui;

import domain.modelo.Cliente;
import domain.modelo.ClienteEspacial;
import domain.modelo.ClienteNormal;
import domain.modelo.Ingrediente;
import domain.services.ServicesClientes;
import domain.services.impl.ServicesClientesImpl;
import gui.main.common.Constantes;
import gui.pantallas.clientes_admin.ClientesAdminController;
import gui.pantallas.clientes_admin.ClientesAdminViewModel;
import gui.pantallas.common.ConstantesPantallas;
import gui.pantallas.principal.PrincipalController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
class ClientesAdminTest extends ApplicationTest {

    Cliente cliente1 = new ClienteNormal("12345678A");
    Cliente cliente2 = new ClienteEspacial("12345678B");
    Cliente admin = new ClienteNormal("admin");
    Ingrediente lacteos = new Ingrediente("Lacteos");
    Ingrediente carnes = new Ingrediente("Carnes");

    ClientesAdminController controller;
    private PrincipalController principalController; // = mock(PrincipalController.class);;
    private ServicesClientes servicesClientes; // = mock(LoginUseCase.class);
    private ClientesAdminViewModel clientesAdminViewModel;


    @Start
    public void start(Stage stage) throws IOException {

        cliente1.setNombre("Juan");
        cliente2.setNombre("Pedro");
        cliente2.getAlergenos().clear();
        cliente2.getAlergenos().add(lacteos);
        ((ClienteEspacial) cliente2).setPorcentajeDescuento(10);

        principalController = mock(PrincipalController.class);
        servicesClientes = mock(ServicesClientesImpl.class);

        when(servicesClientes.scGetClientListSortDni()).thenReturn(List.of(cliente1, cliente2, admin));

        clientesAdminViewModel = new ClientesAdminViewModel(servicesClientes);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(param -> new ClientesAdminController(clientesAdminViewModel));
        ResourceBundle r = ResourceBundle.getBundle(Constantes.I_18_N_TEXTOS_UI);
        fxmlLoader.setResources(r);
        Parent fxmlParent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesPantallas.FXML_PANTALLA_CLIENTES_ADMIN_FXML));
        controller = fxmlLoader.getController();
        controller.setPrincipalController(principalController);

        stage.setScene(new Scene(fxmlParent));
        stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.show();
    }

    @Test
    @DisplayName("Tabla con datos")
    void tablaCargada(FxRobot robot) {
        //Given
        TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);

        //When

        //Then
        assertAll(() -> assertThat(tabla.getItems()).isNotNull(),
                () -> assertThat(tabla.getItems()).hasSize(3),
                () -> assertThat(tabla.getItems()).containsExactly(cliente1, cliente2, admin)
        );
    }

    @Test
    @DisplayName("Seleccionar cliente")
    void selectCliente(FxRobot robot) {
        //Given
        TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
        MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
        MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
        MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
        MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
        MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

        //When
        robot.interact(() -> tabla.getSelectionModel().select(1));
        robot.clickOn("#tablaClientes");

        //Then
        assertAll(() -> assertThat(textNombre.getText()).isEqualTo("Pedro"),
                () -> assertThat(textDni.getText()).isEqualTo("12345678B"),
                () -> assertThat(textPorcentajeDescuento.getText()).isEqualTo("10"),
                () -> assertThat(tipoCliente.getValue()).isEqualTo("ClienteEspacial"),
                () -> assertThat(listaIngredientes.getItems()).containsExactly(lacteos)
        );
    }

    @Test
    @DisplayName("Anular seleccion cliente")
    void unselectCliente(FxRobot robot) {
        //Given
        TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
        MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
        MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
        MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
        MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
        MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);
        robot.interact(() -> tabla.getSelectionModel().select(1));
        robot.clickOn("#tablaClientes");

        //When
        robot.clickOn("#unselect");

        //Then
        assertAll(() -> assertThat(textNombre.getText()).isEmpty(),
                () -> assertThat(textDni.getText()).isEmpty(),
                () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                () -> assertThat(listaIngredientes.getItems()).isEmpty()
        );
    }

    @Nested
    @DisplayName("Crear cliente")
    class CrearCliente {

        @Test
        @DisplayName("Añadir cliente normal")
        void addClienteNormal(FxRobot robot) {
            //Given
            Cliente cliente3 = new ClienteNormal("9876543C");
            cliente3.setNombre("Javier");
            cliente3.getAlergenos().add(lacteos);
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente3.getDni()))))
                    .thenReturn(true);

            //When
            robot.interact(() -> tipoCliente.setValue("ClienteNormal"));
            robot.clickOn("#txtNombre");
            robot.write(cliente3.getNombre());
            robot.clickOn("#txtDNI");
            robot.write(cliente3.getDni());
            robot.clickOn("#txtIngrediente");
            robot.write(lacteos.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#addCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.INFORMATION,
                            ConstantesPantallas.OPERACION_REALIZADA,
                            ConstantesPantallas.CLIENTE_REGISTRADO),
                    () -> assertThat(textNombre.getText()).isEmpty(),
                    () -> assertThat(textDni.getText()).isEmpty(),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                    () -> assertThat(listaIngredientes.getItems()).isEmpty()
            );
        }

        @Test
        @DisplayName("Añadir cliente espacial")
        void addClienteEspacial(FxRobot robot) {
            //Given
            ClienteEspacial cliente3 = new ClienteEspacial("9876543C");
            cliente3.setNombre("Javier");
            cliente3.getAlergenos().add(lacteos);
            cliente3.setPorcentajeDescuento(10);
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente3.getDni()))))
                    .thenReturn(true);

            //When
            robot.interact(() -> tipoCliente.setValue("ClienteEspacial"));
            robot.clickOn("#txtNombre");
            robot.write(cliente3.getNombre());
            robot.clickOn("#txtDNI");
            robot.write(cliente3.getDni());
            robot.clickOn("#txtDescuento");
            robot.write(String.valueOf(cliente3.getPorcentajeDescuento()));
            robot.clickOn("#txtIngrediente");
            robot.write(lacteos.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#addCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.INFORMATION,
                            ConstantesPantallas.OPERACION_REALIZADA,
                            ConstantesPantallas.CLIENTE_REGISTRADO),
                    () -> assertThat(textNombre.getText()).isEmpty(),
                    () -> assertThat(textDni.getText()).isEmpty(),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                    () -> assertThat(listaIngredientes.getItems()).isEmpty()
            );
        }

        @Test
        @DisplayName("Añadir cliente ya registrado")
        void addClienteRegistrado(FxRobot robot) {
            //Given
            Cliente cliente3 = new ClienteNormal("12345678A");
            cliente3.setNombre("Javier");
            cliente3.getAlergenos().add(lacteos);
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente3.getDni()))))
                    .thenReturn(false);

            //When
            robot.interact(() -> tipoCliente.setValue("ClienteNormal"));
            robot.clickOn("#txtNombre");
            robot.write(cliente3.getNombre());
            robot.clickOn("#txtDNI");
            robot.write(cliente3.getDni());
            robot.clickOn("#txtIngrediente");
            robot.write(lacteos.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#addCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.ERROR,
                    ConstantesPantallas.ERROR,
                    ConstantesPantallas.NO_SE_HA_PODIDO_AÑADIR_EL_CLIENTE)
            );
        }

    }

    @Nested
    @DisplayName("Actualizar cliente")
    class ActualizarCliente {

        @Test
        @DisplayName("Actualizar cliente normal")
        void actualizarClienteNormal(FxRobot robot) {
            //Given
            String nombre = "Javier";
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            robot.interact(() -> tabla.getSelectionModel().select(0));
            robot.clickOn("#tablaClientes");

            when(servicesClientes.scEliminarCliente(argThat(cliente -> cliente.getDni().equals(cliente1.getDni()))))
                    .thenReturn(true);
            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente1.getDni()))))
                    .thenReturn(true);

            //When
            robot.interact(() -> textNombre.setText(""));
            robot.clickOn("#txtNombre");
            robot.write(nombre);
            robot.clickOn("#txtIngrediente");
            robot.write(lacteos.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#updateCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.INFORMATION,
                            ConstantesPantallas.OPERACION_REALIZADA,
                            ConstantesPantallas.CLIENTE_ACTUALIZADO),
                    () -> assertThat(textNombre.getText()).isEmpty(),
                    () -> assertThat(textDni.getText()).isEmpty(),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                    () -> assertThat(listaIngredientes.getItems()).isEmpty()
            );

            cliente1.setNombre(nombre);
            cliente1.getAlergenos().add(lacteos);

            robot.interact(() -> tabla.getSelectionModel().select(0));
            robot.clickOn("#tablaClientes");

            assertAll(() -> assertThat(textNombre.getText()).isEqualTo(cliente1.getNombre()),
                    () -> assertThat(textDni.getText()).isEqualTo(cliente1.getDni()),
                    () -> assertThat(tipoCliente.getValue()).isEqualTo("ClienteNormal"),
                    () -> assertThat(listaIngredientes.getItems()).containsExactly(lacteos)
            );

        }

        @Test
        @DisplayName("Actualizar cliente espacial")
        void actualizarClienteEspacial(FxRobot robot) {
            //Given
            String nombre = "Javier";
            int descuento = 20;
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            robot.interact(() -> tabla.getSelectionModel().select(1));
            robot.clickOn("#tablaClientes");

            when(servicesClientes.scEliminarCliente(argThat(cliente -> cliente.getDni().equals(cliente2.getDni()))))
                    .thenReturn(true);
            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente2.getDni()))))
                    .thenReturn(true);

            //When
            robot.interact(() -> textNombre.setText(""));
            robot.clickOn("#txtNombre");
            robot.write(nombre);
            robot.interact(() -> textPorcentajeDescuento.setText(""));
            robot.clickOn("#txtDescuento");
            robot.write(String.valueOf(descuento));
            robot.clickOn("#txtIngrediente");
            robot.write(carnes.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#updateCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.INFORMATION,
                            ConstantesPantallas.OPERACION_REALIZADA,
                            ConstantesPantallas.CLIENTE_ACTUALIZADO),
                    () -> assertThat(textNombre.getText()).isEmpty(),
                    () -> assertThat(textDni.getText()).isEmpty(),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                    () -> assertThat(listaIngredientes.getItems()).isEmpty()
            );

            cliente2.setNombre(nombre);
            cliente2.getAlergenos().add(carnes);
            ((ClienteEspacial) cliente2).setPorcentajeDescuento(descuento);

            robot.interact(() -> tabla.getSelectionModel().select(1));
            robot.clickOn("#tablaClientes");

            assertAll(() -> assertThat(textNombre.getText()).isEqualTo(cliente2.getNombre()),
                    () -> assertThat(textDni.getText()).isEqualTo(cliente2.getDni()),
                    () -> assertThat(tipoCliente.getValue()).isEqualTo("ClienteEspacial"),
                    () -> assertThat(listaIngredientes.getItems()).containsExactly(lacteos, carnes),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEqualTo(String.valueOf(descuento))
            );
        }

        @Test
        @DisplayName("Actualizar cliente datos no validos")
        void actualizarClienteNotPosible(FxRobot robot) {
            //Given
            String nombre = "";
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXComboBox<String> tipoCliente = robot.lookup("#selectorType").queryAs(MFXComboBox.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);

            robot.interact(() -> tabla.getSelectionModel().select(0));
            robot.clickOn("#tablaClientes");

            when(servicesClientes.scEliminarCliente(argThat(cliente -> cliente.getDni().equals(cliente1.getDni()))))
                    .thenReturn(true);
            when(servicesClientes.scRegistrarCliente(argThat(cliente -> cliente.getDni().equals(cliente1.getDni()))))
                    .thenReturn(false);

            //When
            robot.interact(() -> textNombre.setText(""));
            robot.clickOn("#txtNombre");
            robot.write(nombre);
            robot.clickOn("#txtIngrediente");
            robot.write(lacteos.getNombre());
            robot.clickOn("#addIngrediente");
            robot.clickOn("#updateCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.ERROR,
                    ConstantesPantallas.ERROR,
                    ConstantesPantallas.NO_SE_HA_PODIDO_ACTUALIZAR_EL_CLIENTE)
            );

            cliente1.setNombre(nombre);
            cliente1.getAlergenos().add(lacteos);

            robot.interact(() -> tabla.getSelectionModel().select(0));
            robot.clickOn("#tablaClientes");

            assertAll(() -> assertThat(textNombre.getText()).isEqualTo(cliente1.getNombre()),
                    () -> assertThat(textDni.getText()).isEqualTo(cliente1.getDni()),
                    () -> assertThat(tipoCliente.getValue()).isEqualTo("ClienteNormal"),
                    () -> assertThat(listaIngredientes.getItems()).containsExactly(lacteos)
            );

        }
    }

    @Nested
    @DisplayName("Borrar cliente")
    class BorrarCliente {

        @Test
        @DisplayName("Borrar cliente ok")
        void deleteCliente(FxRobot robot) {
            //Given
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            MFXTextField textNombre = robot.lookup("#txtNombre").queryAs(MFXTextField.class);
            MFXTextField textDni = robot.lookup("#txtDNI").queryAs(MFXTextField.class);
            MFXTextField textPorcentajeDescuento = robot.lookup("#txtDescuento").queryAs(MFXTextField.class);
            MFXListView<Ingrediente> listaIngredientes = robot.lookup("#listIngredientes").queryAs(MFXListView.class);
            robot.interact(() -> tabla.getSelectionModel().select(1));
            robot.clickOn("#tablaClientes");

            when(servicesClientes.scEliminarCliente(cliente2)).thenReturn(true);

            //When
            robot.clickOn("#deleteCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.INFORMATION,
                            ConstantesPantallas.OPERACION_REALIZADA,
                            ConstantesPantallas.CLIENTE_ELIMINADO),
                    () -> assertThat(textNombre.getText()).isEmpty(),
                    () -> assertThat(textDni.getText()).isEmpty(),
                    () -> assertThat(textPorcentajeDescuento.getText()).isEmpty(),
                    () -> assertThat(listaIngredientes.getItems()).isEmpty()
            );
        }

        @Test
        @DisplayName("Borrar cliente con error")
        void deleteClienteError(FxRobot robot) {
            //Given
            TableView<Cliente> tabla = robot.lookup("#tablaClientes").queryAs(TableView.class);
            robot.interact(() -> tabla.getSelectionModel().select(1));
            robot.clickOn("#tablaClientes");

            when(servicesClientes.scEliminarCliente(cliente2)).thenReturn(false);

            //When
            robot.clickOn("#deleteCliente");

            //Then
            assertAll(() -> verify(principalController).showAlert(Alert.AlertType.ERROR,
                    ConstantesPantallas.ERROR,
                    ConstantesPantallas.NO_SE_HA_PODIDO_ELIMINAR_EL_CLIENTE)
            );
        }
    }
}