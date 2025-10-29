package com.servicios.web2.ec1.services.impl;

import com.servicios.web2.ec1.models.*;
import com.servicios.web2.ec1.repositories.*;
import com.servicios.web2.ec1.services.interfaces.IVentaService;
import com.servicios.web2.ec1.services.interfaces.auth.IAuthService;
import com.servicios.web2.ec1.utils.dtos.request.DetalleVentaRequest;
import com.servicios.web2.ec1.utils.dtos.request.VentaRequest;
import com.servicios.web2.ec1.utils.dtos.response.MensajeResponse;
import com.servicios.web2.ec1.utils.dtos.response.VentaResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final IAuthService authService;

    public VentaService(VentaRepository ventaRepository,
                        DetalleVentaRepository detalleVentaRepository,
                        ClienteRepository clienteRepository,
                        UsuarioRepository usuarioRepository,
                        ProductoRepository productoRepository,
                        IAuthService authService) {
        this.ventaRepository = ventaRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.authService = authService;
    }

    @Override
    public VentaResponse obtenerVentaPorId(Long id) {
        Optional<Venta> ventaOptional = ventaRepository.findById(id);

        if (ventaOptional.isEmpty())
            return null;

        List<DetalleVenta> detalleVentas = detalleVentaRepository.findByVenta(ventaOptional.get());

        return VentaResponse.VentaDetalleVentaToVentaResponse(
                ventaOptional.get(), detalleVentas
        );
    }

    @Override
    public List<VentaResponse> listarVentas() {

        List<Venta> ventas = ventaRepository.findAll();

        if (ventas.isEmpty())
            return null;

        List<VentaResponse> response = new ArrayList<>();

        for (Venta venta : ventas) {
            List<DetalleVenta> detalleVentas = detalleVentaRepository.findByVenta(venta);
            response.add(
                    VentaResponse.VentaDetalleVentaToVentaResponse(
                            venta, detalleVentas
                    )
            );
        }

        return response;
    }

    @Transactional
    @Override
    public MensajeResponse crearVenta(VentaRequest venta) {

        Optional<Cliente> cliente = clienteRepository.findById(venta.getIdCliente());
        Usuario usuario = authService.obtenerUsuarioAutenticado();
        List<DetalleVentaRequest> detallesVenta = venta.getDetalleVenta();

        if (cliente.isEmpty())
            return new MensajeResponse("Cliente con id " + venta.getIdCliente() + " no existe.");

        if (detallesVenta.isEmpty())
            return new MensajeResponse("Debe agregarse el detalle de venta.");

        Venta ventaToSave = Venta.builder()
                .cliente(cliente.get())
                .usuario(usuario)
                .fecha(LocalDateTime.now())
                .total(0.00)
                .build();

        ventaToSave = ventaRepository.save(ventaToSave);

        double totalVenta = 0;

        for (DetalleVentaRequest detalleVenta : detallesVenta) {
            Optional<Producto> producto = productoRepository.findById(detalleVenta.getIdProducto());

            if (producto.isEmpty())
                throw new RuntimeException("Producto con id " +
                        detalleVenta.getIdProducto() + " no existe.");

            if (detalleVenta.getCantidad() > producto.get().getStock())
                throw new RuntimeException("La cantidad solicitada en el producto con id "
                        + producto.get().getIdProducto() + " es mayor a la cantidad en stock.");

            double subTotal = producto.get().getPrecio() * detalleVenta.getCantidad();

            DetalleVenta detalleVentaToSave = DetalleVenta.builder()
                    .cantidad(detalleVenta.getCantidad())
                    .subtotal(subTotal)
                    .producto(producto.get())
                    .venta(ventaToSave)
                    .build();

            totalVenta += subTotal;
            detalleVentaRepository.save(detalleVentaToSave);

            producto.get().setStock(producto.get().getStock()-detalleVenta.getCantidad());
            productoRepository.save(producto.get());
        }

        ventaToSave.setTotal(totalVenta);
        ventaRepository.save(ventaToSave);

        return new MensajeResponse("Venta registrada satisfactoriamente.");
    }

    /*
    @Transactional
    @Override
    public MensajeResponse modificarVenta(Long id, VentaRequest venta) {

        Optional<Venta> ventaToUpdate = ventaRepository.findById(id);
        Optional<Cliente> cliente = clienteRepository.findById(venta.getIdCliente());
        Optional<Usuario> usuario = usuarioRepository.findById(venta.getIdUsuario());
        List<DetalleVentaRequest> detallesVenta = venta.getDetalleVenta();

        if (ventaToUpdate.isEmpty())
            return new MensajeResponse("Venta con id " + id + " no existe.");

        if (cliente.isEmpty())
            return new MensajeResponse("Cliente con id " + venta.getIdCliente() + " no existe.");

        if (usuario.isEmpty())
            return new MensajeResponse("Usuario con id " + venta.getIdUsuario() + " no existe.");

        if (detallesVenta.isEmpty())
            return new MensajeResponse("Debe agregarse el detalle de venta.");

        List<DetalleVenta> detalleVentaCurrent = detalleVentaRepository.findByVenta(ventaToUpdate.get());

        for (DetalleVenta elemento : detalleVentaCurrent) {
            elemento.getProducto().setStock(elemento.getCantidad() + elemento.getProducto().getStock());
            productoRepository.save(elemento.getProducto());
            detalleVentaRepository.delete(elemento);
        }

        ventaToUpdate.get().setCliente(cliente.get());
        ventaToUpdate.get().setUsuario(usuario.get());
        ventaToUpdate.get().setFecha(LocalDateTime.now());
        ventaToUpdate.get().setTotal(0.00);

        Venta newVenta = ventaRepository.save(ventaToUpdate.get());

        double totalVenta = 0;

        for (DetalleVentaRequest detalleVenta : detallesVenta) {
            Optional<Producto> producto = productoRepository.findById(detalleVenta.getIdProducto());

            if (producto.isEmpty())
                throw new RuntimeException("Producto con id " +
                        detalleVenta.getIdProducto() + " no existe.");

            if (detalleVenta.getCantidad() > producto.get().getStock())
                throw new RuntimeException("La cantidad solicitada en el producto con id "
                        + producto.get().getIdProducto() + " es mayor a la cantidad en stock.");

            double subTotal = producto.get().getPrecio() * detalleVenta.getCantidad();

            DetalleVenta detalleVentaToSave = DetalleVenta.builder()
                    .cantidad(detalleVenta.getCantidad())
                    .subtotal(subTotal)
                    .producto(producto.get())
                    .venta(newVenta)
                    .build();

            totalVenta += subTotal;
            detalleVentaRepository.save(detalleVentaToSave);

            producto.get().setStock(producto.get().getStock() - detalleVenta.getCantidad());
            productoRepository.save(producto.get());
        }

        newVenta.setTotal(totalVenta);
        ventaRepository.save(newVenta);

        return new MensajeResponse("Venta actualizada satisfactoriamente.");
    }

    @Transactional
    @Override
    public MensajeResponse eliminarVentaPorId(Long id) {

        Optional<Venta> ventaToDelete = ventaRepository.findById(id);

        if (ventaToDelete.isEmpty())
            return new MensajeResponse("Venta con id " + id + " no existe.");

        List<DetalleVenta> detallesVenta = detalleVentaRepository.findByVenta(ventaToDelete.get());

        for (DetalleVenta detalleVenta : detallesVenta) {
            detalleVenta.getProducto().setStock(detalleVenta.getCantidad() + detalleVenta.getProducto().getStock());
            productoRepository.save(detalleVenta.getProducto());
            detalleVentaRepository.delete(detalleVenta);
        }

        ventaRepository.delete(ventaToDelete.get());

        return new MensajeResponse("Venta eliminada satisfactoriamente.");
    }
    */
}
