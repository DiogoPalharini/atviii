package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.modelo.Veiculo;
import com.autobots.automanager.repositorio.UsuarioRepositorio;
import com.autobots.automanager.repositorio.VeiculoRepositorio;

@Service
public class VeiculoServico {

	@Autowired
	private VeiculoRepositorio repositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	public List<Veiculo> listar() {
		return repositorio.findAll();
	}
	
	public List<Veiculo> listarPorUsuario(Long usuarioId) {
		return repositorio.findByProprietarioId(usuarioId);
	}

	public Optional<Veiculo> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Veiculo salvar(Veiculo veiculo) {
		return repositorio.save(veiculo);
	}
	
	public Veiculo salvarComUsuario(Veiculo veiculo, Long usuarioId) {
		Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(usuarioId);
		if (usuarioOpt.isPresent()) {
			veiculo.setProprietario(usuarioOpt.get());
		}
		return repositorio.save(veiculo);
	}

	public Veiculo atualizar(Veiculo veiculo) {
		return repositorio.save(veiculo);
	}
	
	public Veiculo atualizarComUsuario(Veiculo veiculo, Long usuarioId) {
		Optional<Usuario> usuarioOpt = usuarioRepositorio.findById(usuarioId);
		if (usuarioOpt.isPresent()) {
			veiculo.setProprietario(usuarioOpt.get());
		}
		return repositorio.save(veiculo);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}

