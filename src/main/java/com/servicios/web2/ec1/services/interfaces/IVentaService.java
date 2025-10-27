package com.servicios.web2.ec1.services.interfaces;

import com.servicios.web2.ec1.utils.dtos.request.VentaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.VentaResponse;
import java.util.List;

public interface IVentaService {

    VentaResponse obtenerVentaPorId(Long id);
    List<VentaResponse> listarVentas();
    MensajeResponse crearVenta(VentaRequest venta);
    MensajeResponse modificarVenta(Long id, VentaRequest venta);
    MensajeResponse eliminarVentaPorId(Long id);

}
