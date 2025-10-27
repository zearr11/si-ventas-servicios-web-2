package com.servicios.web2.ec1.repositories;

import com.servicios.web2.ec1.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
