package com.autobots.automanager.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.modelo.Veiculo;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {
	List<Veiculo> findByProprietario(Usuario proprietario);
	List<Veiculo> findByProprietarioId(Long usuarioId);
}

