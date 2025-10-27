package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.Cliente;
import com.servicios.web2.ec1.models.Persona;
import com.servicios.web2.ec1.repositories.ClienteRepository;
import com.servicios.web2.ec1.repositories.PersonaRepository;
import com.servicios.web2.ec1.repositories.VentaRepository;
import com.servicios.web2.ec1.services.interfaces.IClienteService;
import com.servicios.web2.ec1.utils.dtos.request.ClienteRequest;
import com.servicios.web2.ec1.utils.dtos.response.ClienteResponse;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final VentaRepository ventaRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          PersonaRepository personaRepository,
                          VentaRepository ventaRepository) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.ventaRepository = ventaRepository;
    }

    @Override
    public ClienteResponse obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::ClienteToClienteResponse)
                .orElse(null);
    }

    @Override
    public List<ClienteResponse> listarClientes() {
        return clienteRepository.findAll()
                .stream().map(ClienteResponse::ClienteToClienteResponse)
                .toList();
    }

    @Transactional
    @Override
    public MensajeResponse crearCliente(ClienteRequest cliente) {
        Persona personaToSave = Persona.builder()
                .nombres(cliente.getNombres())
                .apellidos(cliente.getApellidos())
                .email(cliente.getEmail())
                .build();

        personaToSave = personaRepository.save(personaToSave);

        Cliente clienteToSave = Cliente.builder()
                .persona(personaToSave)
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .build();

        clienteRepository.save(clienteToSave);

        return new MensajeResponse("Cliente creado satisfactoriamente.");
    }

    @Override
    public MensajeResponse modificarCliente(Long id, ClienteRequest cliente) {
        Optional<Cliente> clienteToEdit = clienteRepository.findById(id);

        if (clienteToEdit.isEmpty()) {
            return new MensajeResponse("Cliente con id " + id + " no existe.");
        }

        clienteToEdit.get().getPersona().setNombres(cliente.getNombres());
        clienteToEdit.get().getPersona().setApellidos(cliente.getApellidos());
        clienteToEdit.get().getPersona().setEmail(cliente.getEmail());
        clienteToEdit.get().setDireccion(cliente.getDireccion());
        clienteToEdit.get().setTelefono(cliente.getTelefono());

        clienteRepository.save(clienteToEdit.get());

        return new MensajeResponse("Cliente modificado satisfactoriamente.");
    }

    @Override
    public MensajeResponse eliminarClientePorId(Long id) {
        Optional<Cliente> clienteToDelete = clienteRepository.findById(id);

        if (clienteToDelete.isEmpty())
            return new MensajeResponse("Cliente con id " + id + " no existe.");

        if (!ventaRepository.findByCliente(clienteToDelete.get()).isEmpty())
            return new MensajeResponse("Cliente con id " + id + " tiene ventas asociadas. No puede eliminarse.");

        clienteRepository.delete(clienteToDelete.get());

        return new MensajeResponse("Cliente eliminado satisfactoriamente.");
    }

}
