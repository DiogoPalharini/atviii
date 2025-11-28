package com.autobots.automanager.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Empresa;

public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
}

