package com.ulises.crudapi.response;

import com.ulises.crudapi.dto.EmpleadoConsultaDto;
import com.ulises.crudapi.entity.Empleado;
import com.ulises.crudapi.response.data.EmpleadoResponseData;
import com.ulises.crudapi.response.data.PolizaEmpleadoResponseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarEmpleadoResponse {

    public static Map<String, Object> buildFromEntityList(
            List<Empleado> empleadoList
    ){
        var empleadoResponseDataList =
                empleadoList
                .stream()
                .map(
                        (Empleado emp) -> {
                            return EmpleadoConsultaDto.build(
                                    emp.getIdEmpleado(),emp.getNombre().trim(),emp.getApellido().trim());
                        }
                ).toList();

        Map<String, String> statusMeta = new HashMap<>();
        statusMeta.put("Status", "OK");
        //polizaMeta
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> empleadoMeta = new HashMap<>();
        data.put("Empleado", empleadoResponseDataList);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("Meta", statusMeta);
        response.put("Data", data);

        return response;
    }
    public static Map<String, Object> build(
            List<EmpleadoResponseData> empleadoResponseData
    ){
        Map<String, String> statusMeta = new HashMap<>();
        statusMeta.put("Status", "OK");
        //polizaMeta
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> empleadoMeta = new HashMap<>();
        data.put("Empleado", empleadoResponseData);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("Meta", statusMeta);
        response.put("Data", data);

        return response;
    }


}
