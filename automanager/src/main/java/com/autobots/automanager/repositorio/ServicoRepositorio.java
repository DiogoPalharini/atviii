package com.autobots.automanager.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Servico;

public interface ServicoRepositorio extends JpaRepository<Servico, Long> {
}

