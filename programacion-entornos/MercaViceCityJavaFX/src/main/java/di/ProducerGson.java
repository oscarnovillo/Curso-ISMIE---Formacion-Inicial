package di;

import com.google.gson.*;
import domain.modelo.*;
import gsonUtils.RuntimeTypeAdapterFactory;
import jakarta.enterprise.inject.Produces;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProducerGson {

    @Produces
    public Gson getGson() {
        RuntimeTypeAdapterFactory<Cliente> adapterCliente =
                RuntimeTypeAdapterFactory
                        .of(Cliente.class, "clientType", true)
                        .registerSubtype(ClienteEspacial.class)
                        .registerSubtype(ClienteNormal.class);

        RuntimeTypeAdapterFactory<Producto> adapterProducto =
                RuntimeTypeAdapterFactory
                        .of(Producto.class, "productType", true)
                        .registerSubtype(ProductoPerecedero.class)
                        .registerSubtype(ProductoNormal.class);

        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapterFactory(adapterCliente)
                .registerTypeAdapterFactory(adapterProducto)
                .create();
    }
}
