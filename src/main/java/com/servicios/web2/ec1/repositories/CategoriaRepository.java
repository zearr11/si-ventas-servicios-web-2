package com.servicios.web2.ec1.repositories;

import com.servicios.web2.ec1.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
