package com.vetnova.inventarioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vetnova.inventarioservice.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

}