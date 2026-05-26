package com.vetnova.inventarioservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vetnova.inventarioservice.model.Inventario;
import com.vetnova.inventarioservice.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> obtenerInventarios() {
        return inventarioRepository.findAll();
    }

    public Inventario obtenerInventarioPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarInventario(Long id, Inventario inventarioActualizado) {
        Optional<Inventario> inventarioExistente = inventarioRepository.findById(id);

        if (inventarioExistente.isPresent()) {
            Inventario inventario = inventarioExistente.get();

            inventario.setProductoId(inventarioActualizado.getProductoId());
            inventario.setStockActual(inventarioActualizado.getStockActual());
            inventario.setStockMinimo(inventarioActualizado.getStockMinimo());
            inventario.setUbicacion(inventarioActualizado.getUbicacion());
            inventario.setEstado(inventarioActualizado.getEstado());
            inventario.setFechaActualizacion(inventarioActualizado.getFechaActualizacion());

            return inventarioRepository.save(inventario);
        }

        return null;
    }

    public boolean eliminarInventario(Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return true;
        }

        return false;
    }
}