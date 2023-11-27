package com.ulises.crudapi.response;

import java.util.HashMap;
import java.util.Map;

public class FailResponse {

    public static Map<String, Object> build(
            String mensaje
    ){
        Map<String, String> meta = new HashMap<>();
        meta.put("Status", "Failure");

        Map<String, String> idMensaje = new HashMap<>();
        idMensaje.put("IDMensaje", mensaje);

        Map<String, Object> mensajeMap = new HashMap<>();
        mensajeMap.put("Mensaje", idMensaje);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("Meta", meta);
        response.put("Data", mensajeMap);

        return response;
    }

}
