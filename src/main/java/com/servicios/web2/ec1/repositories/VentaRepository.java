package com.servicios.web2.ec1.repositories;

import com.servicios.web2.ec1.models.Cliente;
import com.servicios.web2.ec1.models.Usuario;
import com.servicios.web2.ec1.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByCliente(Cliente cliente);
    List<Venta> findByUsuario(Usuario usuario);

}
