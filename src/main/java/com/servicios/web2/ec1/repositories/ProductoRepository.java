package com.servicios.web2.ec1.repositories;

import com.servicios.web2.ec1.models.Categoria;
import com.servicios.web2.ec1.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(Categoria categoria);

}
