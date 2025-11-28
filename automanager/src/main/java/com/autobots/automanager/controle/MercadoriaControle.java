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

import com.autobots.automanager.assembler.MercadoriaAssembler;
import com.autobots.automanager.modelo.Mercadoria;
import com.autobots.automanager.servico.MercadoriaServico;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {

	@Autowired
	private MercadoriaServico servico;

	@Autowired
	private MercadoriaAssembler assembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Mercadoria>>> listar() {
		List<Mercadoria> mercadorias = servico.listar();
		CollectionModel<EntityModel<Mercadoria>> collectionModel = assembler.toCollectionModel(mercadorias);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Mercadoria>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(mercadoria -> ResponseEntity.ok(assembler.toModel(mercadoria)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Mercadoria>> criar(@Valid @RequestBody Mercadoria mercadoria) {
		Mercadoria mercadoriaSalva = servico.salvar(mercadoria);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(mercadoriaSalva.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(mercadoriaSalva));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Mercadoria>> atualizar(@PathVariable Long id,
			@Valid @RequestBody Mercadoria mercadoriaAtualizada) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		mercadoriaAtualizada.setId(id);
		Mercadoria mercadoria = servico.atualizar(mercadoriaAtualizada);
		return ResponseEntity.ok(assembler.toModel(mercadoria));
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

