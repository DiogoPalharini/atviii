package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Servico;
import com.autobots.automanager.repositorio.ServicoRepositorio;

@Service
public class ServicoServico {

	@Autowired
	private ServicoRepositorio repositorio;

	public List<Servico> listar() {
		return repositorio.findAll();
	}

	public Optional<Servico> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Servico salvar(Servico servico) {
		return repositorio.save(servico);
	}

	public Servico atualizar(Servico servico) {
		return repositorio.save(servico);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}

