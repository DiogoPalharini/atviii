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

import com.autobots.automanager.assembler.CredencialAssembler;
import com.autobots.automanager.modelo.Credencial;
import com.autobots.automanager.modelo.CredencialDTO;
import com.autobots.automanager.servico.CredencialServico;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {

	@Autowired
	private CredencialServico servico;

	@Autowired
	private CredencialAssembler assembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Credencial>>> listar() {
		List<Credencial> credenciais = servico.listar();
		CollectionModel<EntityModel<Credencial>> collectionModel = assembler.toCollectionModel(credenciais);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Credencial>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(credencial -> ResponseEntity.ok(assembler.toModel(credencial)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Credencial>> criar(@Valid @RequestBody CredencialDTO credencialDTO) {
		Credencial credencial = new Credencial();
		credencial.setCriacao(credencialDTO.getCriacao());
		credencial.setUltimoAcesso(credencialDTO.getUltimoAcesso());
		credencial.setInativo(credencialDTO.isInativo());
		credencial.setNomeUsuario(credencialDTO.getNomeUsuario());
		credencial.setSenha(credencialDTO.getSenha());
		
		Credencial credencialSalva;
		if (credencialDTO.getUsuarioId() != null) {
			credencialSalva = servico.salvarComUsuario(credencial, credencialDTO.getUsuarioId());
		} else {
			credencialSalva = servico.salvar(credencial);
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(credencialSalva.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(credencialSalva));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Credencial>> atualizar(@PathVariable Long id,
			@Valid @RequestBody CredencialDTO credencialDTO) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Credencial credencial = servico.buscarPorId(id).orElseThrow();
		credencial.setId(id);
		credencial.setCriacao(credencialDTO.getCriacao());
		credencial.setUltimoAcesso(credencialDTO.getUltimoAcesso());
		credencial.setInativo(credencialDTO.isInativo());
		credencial.setNomeUsuario(credencialDTO.getNomeUsuario());
		credencial.setSenha(credencialDTO.getSenha());
		
		Credencial credencialAtualizada = servico.atualizar(credencial);
		return ResponseEntity.ok(assembler.toModel(credencialAtualizada));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
}

