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

import com.autobots.automanager.assembler.VeiculoAssembler;
import com.autobots.automanager.assembler.VendaAssembler;
import com.autobots.automanager.modelo.Veiculo;
import com.autobots.automanager.modelo.VeiculoDTO;
import com.autobots.automanager.modelo.Venda;
import com.autobots.automanager.servico.VeiculoServico;
import com.autobots.automanager.servico.VendaServico;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {

	@Autowired
	private VeiculoServico servico;

	@Autowired
	private VeiculoAssembler assembler;
	
	@Autowired
	private VendaServico vendaServico;
	
	@Autowired
	private VendaAssembler vendaAssembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Veiculo>>> listar() {
		List<Veiculo> veiculos = servico.listar();
		CollectionModel<EntityModel<Veiculo>> collectionModel = assembler.toCollectionModel(veiculos);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Veiculo>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(veiculo -> ResponseEntity.ok(assembler.toModel(veiculo)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Veiculo>> criar(@Valid @RequestBody VeiculoDTO veiculoDTO) {
		Veiculo veiculo = new Veiculo();
		veiculo.setTipo(veiculoDTO.getTipo());
		veiculo.setModelo(veiculoDTO.getModelo());
		veiculo.setPlaca(veiculoDTO.getPlaca());
		
		Veiculo veiculoSalvo;
		if (veiculoDTO.getUsuarioId() != null) {
			veiculoSalvo = servico.salvarComUsuario(veiculo, veiculoDTO.getUsuarioId());
		} else {
			veiculoSalvo = servico.salvar(veiculo);
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(veiculoSalvo.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(veiculoSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Veiculo>> atualizar(@PathVariable Long id,
			@Valid @RequestBody VeiculoDTO veiculoDTO) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Veiculo veiculo = servico.buscarPorId(id).orElseThrow();
		veiculo.setId(id);
		veiculo.setTipo(veiculoDTO.getTipo());
		veiculo.setModelo(veiculoDTO.getModelo());
		veiculo.setPlaca(veiculoDTO.getPlaca());
		
		Veiculo veiculoAtualizado;
		if (veiculoDTO.getUsuarioId() != null) {
			veiculoAtualizado = servico.atualizarComUsuario(veiculo, veiculoDTO.getUsuarioId());
		} else {
			veiculoAtualizado = servico.atualizar(veiculo);
		}
		
		return ResponseEntity.ok(assembler.toModel(veiculoAtualizado));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/vendas")
	public ResponseEntity<CollectionModel<EntityModel<Venda>>> listarVendasPorVeiculo(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		List<Venda> vendas = vendaServico.listarPorVeiculo(id);
		CollectionModel<EntityModel<Venda>> collectionModel = vendaAssembler.toCollectionModel(vendas);
		return ResponseEntity.ok(collectionModel);
	}
}

