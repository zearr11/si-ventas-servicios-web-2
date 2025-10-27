package com.servicios.web2.ec1.repositories;

import com.servicios.web2.ec1.models.DetalleVenta;
import com.servicios.web2.ec1.models.Producto;
import com.servicios.web2.ec1.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {

    List<DetalleVenta> findByVenta(Venta venta);
    List<DetalleVenta> findByProducto(Producto producto);

}
