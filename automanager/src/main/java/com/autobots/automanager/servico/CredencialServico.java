package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Credencial;
import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.repositorio.CredencialRepositorio;
import com.autobots.automanager.repositorio.UsuarioRepositorio;

@Service
public class CredencialServico {

	@Autowired
	private CredencialRepositorio repositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public List<Credencial> listar() {
		return repositorio.findAll();
	}

	public Optional<Credencial> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Credencial salvar(Credencial credencial) {
		return repositorio.save(credencial);
	}
	
	public Credencial salvarComUsuario(Credencial credencial, Long usuarioId) {
		Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(usuarioId);
		if (usuarioOpt.isPresent()) {
			Usuario usuario = usuarioOpt.get();
			credencial.setUsuario(usuario);
			usuario.setCredencial(credencial);
			return repositorio.save(credencial);
		}
		return repositorio.save(credencial);
	}

	public Credencial atualizar(Credencial credencial) {
		return repositorio.save(credencial);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}

