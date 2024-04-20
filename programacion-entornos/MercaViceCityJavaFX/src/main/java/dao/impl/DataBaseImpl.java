package dao.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import configuracion.Configuracion;
import dao.DataBase;
import domain.modelo.Cliente;
import domain.modelo.Producto;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class DataBaseImpl implements DataBase {

    private final Gson gson;
    private final Configuracion configuracion;

    @Inject
    public DataBaseImpl(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }

    @Override
    public Map<String, Cliente> readJSONClientes() {

        Type userMapType = new TypeToken<HashMap<String, Cliente>>() {
        }.getType();

        try (FileReader fileReader = new FileReader(configuracion.getPathJSONClientes())) {
            return gson.fromJson(fileReader, userMapType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    @Override
    public boolean writeJSONClientes(Map<String, Cliente> clientes) {

        try (FileWriter fileWriter = new FileWriter(configuracion.getPathJSONClientes())) {
            gson.toJson(clientes, fileWriter);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Producto> readJSONProductos() {

        Type userListType = new TypeToken<List<Producto>>() {
        }.getType();

        try (FileReader fileReader = new FileReader(configuracion.getPathJSONProductos())) {
            return gson.fromJson(fileReader, userListType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public boolean writeJSONProductos(List<Producto> inventario) {

        try (FileWriter fileWriter = new FileWriter(configuracion.getPathJSONProductos())) {
            gson.toJson(inventario, fileWriter);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

}