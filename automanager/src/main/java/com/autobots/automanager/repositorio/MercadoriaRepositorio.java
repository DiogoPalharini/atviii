package com.autobots.automanager.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Mercadoria;

public interface MercadoriaRepositorio extends JpaRepository<Mercadoria, Long> {
}

