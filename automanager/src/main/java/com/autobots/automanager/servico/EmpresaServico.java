package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Empresa;
import com.autobots.automanager.repositorio.EmpresaRepositorio;

@Service
public class EmpresaServico {

	@Autowired
	private EmpresaRepositorio repositorio;

	public List<Empresa> listar() {
		return repositorio.findAll();
	}

	public Optional<Empresa> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Empresa salvar(Empresa empresa) {
		return repositorio.save(empresa);
	}

	public Empresa atualizar(Empresa empresa) {
		return repositorio.save(empresa);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}
