package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.Empresa;
import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.repositorio.EmpresaRepositorio;
import com.autobots.automanager.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

	@Autowired
	private UsuarioRepositorio repositorio;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;

	public List<Usuario> listar() {
		return repositorio.findAll();
	}
	
	public List<Usuario> listarPorEmpresa(Long empresaId) {
		return repositorio.findByEmpresaId(empresaId);
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return repositorio.findById(id);
	}

	public Usuario salvar(Usuario usuario) {
		return repositorio.save(usuario);
	}
	
	public Usuario salvarComEmpresa(Usuario usuario, Long empresaId) {
		Optional<Empresa> empresaOpt = empresaRepositorio.findById(empresaId);
		if (empresaOpt.isPresent()) {
			usuario.setEmpresa(empresaOpt.get());
		}
		return repositorio.save(usuario);
	}

	public Usuario atualizar(Usuario usuario) {
		return repositorio.save(usuario);
	}
	
	public Usuario atualizarComEmpresa(Usuario usuario, Long empresaId) {
		Optional<Empresa> empresaOpt = empresaRepositorio.findById(empresaId);
		if (empresaOpt.isPresent()) {
			usuario.setEmpresa(empresaOpt.get());
		}
		return repositorio.save(usuario);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
}

