package com.autobots.automanager.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Empresa;
import com.autobots.automanager.modelo.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	List<Usuario> findByEmpresa(Empresa empresa);
	List<Usuario> findByEmpresaId(Long empresaId);
}

