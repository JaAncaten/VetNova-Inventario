package com.vetnova.inventarioservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.vetnova.inventarioservice.model.Inventario;
import com.vetnova.inventarioservice.service.InventarioService;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> obtenerInventarios() {
        return inventarioService.obtenerInventarios();
    }

    @GetMapping("/{id}")
    public Inventario obtenerInventarioPorId(@PathVariable Long id) {
        return inventarioService.obtenerInventarioPorId(id);
    }

    @PostMapping
    public Inventario guardarInventario(@RequestBody Inventario inventario) {
        return inventarioService.guardarInventario(inventario);
    }

    @PutMapping("/{id}")
    public Inventario actualizarInventario(@PathVariable Long id,
                                           @RequestBody Inventario inventario) {
        return inventarioService.actualizarInventario(id, inventario);
    }

    @DeleteMapping("/{id}")
    public String eliminarInventario(@PathVariable Long id) {
        boolean eliminado = inventarioService.eliminarInventario(id);

        if (eliminado) {
            return "Inventario eliminado correctamente";
        }

        return "Inventario no encontrado";
    }

    @PutMapping("/producto/{productoId}/descontar/{cantidad}")
    public Inventario descontarStock(@PathVariable Long productoId,
                                  @PathVariable Integer cantidad) {
             return inventarioService.descontarStock(productoId, cantidad);
}

}