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

import com.autobots.automanager.assembler.VendaAssembler;
import com.autobots.automanager.modelo.Venda;
import com.autobots.automanager.modelo.VendaDTO;
import com.autobots.automanager.servico.VendaServico;
import com.autobots.automanager.servico.VendaServico.ItemMercadoriaDTO;
import com.autobots.automanager.servico.VendaServico.ItemServicoDTO;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

	@Autowired
	private VendaServico servico;

	@Autowired
	private VendaAssembler assembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Venda>>> listar() {
		List<Venda> vendas = servico.listar();
		CollectionModel<EntityModel<Venda>> collectionModel = assembler.toCollectionModel(vendas);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Venda>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(venda -> ResponseEntity.ok(assembler.toModel(venda)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Venda>> criar(@Valid @RequestBody VendaDTO vendaDTO) {
		List<ItemMercadoriaDTO> itensMercadoria = vendaDTO.getItensMercadoria() != null 
				? vendaDTO.getItensMercadoria() : List.of();
		List<ItemServicoDTO> itensServico = vendaDTO.getItensServico() != null 
				? vendaDTO.getItensServico() : List.of();
		
		Venda vendaSalva = servico.criarVendaComItens(
				vendaDTO.getClienteId(),
				vendaDTO.getVeiculoId(),
				itensMercadoria,
				itensServico
		);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(vendaSalva.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(vendaSalva));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Venda>> atualizar(@PathVariable Long id,
			@Valid @RequestBody Venda vendaAtualizada) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		vendaAtualizada.setId(id);
		Venda venda = servico.atualizar(vendaAtualizada);
		return ResponseEntity.ok(assembler.toModel(venda));
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

