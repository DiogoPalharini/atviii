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

import com.autobots.automanager.assembler.ServicoAssembler;
import com.autobots.automanager.modelo.Servico;
import com.autobots.automanager.servico.ServicoServico;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {

	@Autowired
	private ServicoServico servico;

	@Autowired
	private ServicoAssembler assembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Servico>>> listar() {
		List<Servico> servicos = servico.listar();
		CollectionModel<EntityModel<Servico>> collectionModel = assembler.toCollectionModel(servicos);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Servico>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(servico -> ResponseEntity.ok(assembler.toModel(servico)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Servico>> criar(@Valid @RequestBody Servico servico) {
		Servico servicoSalvo = this.servico.salvar(servico);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(servicoSalvo.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(servicoSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Servico>> atualizar(@PathVariable Long id,
			@Valid @RequestBody Servico servicoAtualizado) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		servicoAtualizado.setId(id);
		Servico servico = this.servico.atualizar(servicoAtualizado);
		return ResponseEntity.ok(assembler.toModel(servico));
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

