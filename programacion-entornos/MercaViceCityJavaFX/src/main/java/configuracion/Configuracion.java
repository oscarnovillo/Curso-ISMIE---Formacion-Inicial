package configuracion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import configuracion.common.Constantes;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;


    @Getter
    @Log4j2
    @Singleton
    public class Configuracion {

        private String pathJSONClientes;
        private String pathJSONProductos;

        public Configuracion() {

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();

            try {
                JsonNode node = mapper.readTree(
                        getClass().getClassLoader().getResourceAsStream(Constantes.CONFIG_YAML));
                this.pathJSONClientes = node.get(Constantes.PATH_JSON_CLIENTES).asText();
                this.pathJSONProductos = node.get(Constantes.PATH_JSON_PRODUCTOS).asText();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }