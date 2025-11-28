package com.autobots.automanager.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Credencial;

public interface CredencialRepositorio extends JpaRepository<Credencial, Long> {
}

