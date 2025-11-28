package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Mercadoria;
import com.autobots.automanager.repositorio.MercadoriaRepositorio;

@Service
public class MercadoriaServico {

	@Autowired
	private MercadoriaRepositorio repositorio;

	public List<Mercadoria> listar() {
		return repositorio.findAll();
	}

	public Optional<Mercadoria> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Mercadoria salvar(Mercadoria mercadoria) {
		return repositorio.save(mercadoria);
	}

	public Mercadoria atualizar(Mercadoria mercadoria) {
		return repositorio.save(mercadoria);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}

