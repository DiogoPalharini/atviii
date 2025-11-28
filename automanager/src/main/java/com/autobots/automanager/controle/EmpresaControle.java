package com.autobots.automanager.controle;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.assembler.EmpresaAssembler;
import com.autobots.automanager.assembler.UsuarioAssembler;
import com.autobots.automanager.modelo.Empresa;
import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.servico.EmpresaServico;
import com.autobots.automanager.servico.UsuarioServico;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {

	@Autowired
	private EmpresaServico servico;

	@Autowired
	private EmpresaAssembler assembler;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@Autowired
	private UsuarioAssembler usuarioAssembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Empresa>>> listar() {
		List<Empresa> empresas = servico.listar();
		CollectionModel<EntityModel<Empresa>> collectionModel = assembler.toCollectionModel(empresas);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Empresa>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(empresa -> ResponseEntity.ok(assembler.toModel(empresa)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Empresa>> criar(@Valid @RequestBody Empresa empresa) {
		Empresa empresaSalva = servico.salvar(empresa);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(empresaSalva.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(empresaSalva));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Empresa>> atualizar(@PathVariable Long id,
			@Valid @RequestBody Empresa empresaAtualizada) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		empresaAtualizada.setId(id);
		Empresa empresa = servico.atualizar(empresaAtualizada);
		return ResponseEntity.ok(assembler.toModel(empresa));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{empresaId}/usuarios")
	public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listarUsuariosPorEmpresa(@PathVariable Long empresaId) {
		if (!servico.existe(empresaId)) {
			return ResponseEntity.notFound().build();
		}
		List<Usuario> usuarios = usuarioServico.listarPorEmpresa(empresaId);
		CollectionModel<EntityModel<Usuario>> collectionModel = usuarioAssembler.toCollectionModel(usuarios);
		return ResponseEntity.ok(collectionModel);
	}
}
