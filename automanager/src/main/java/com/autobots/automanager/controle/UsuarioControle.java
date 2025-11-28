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

import com.autobots.automanager.assembler.UsuarioAssembler;
import com.autobots.automanager.assembler.VeiculoAssembler;
import com.autobots.automanager.assembler.VendaAssembler;
import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.modelo.UsuarioDTO;
import com.autobots.automanager.modelo.Veiculo;
import com.autobots.automanager.modelo.Venda;
import com.autobots.automanager.servico.UsuarioServico;
import com.autobots.automanager.servico.VeiculoServico;
import com.autobots.automanager.servico.VendaServico;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {

	@Autowired
	private UsuarioServico servico;

	@Autowired
	private UsuarioAssembler assembler;
	
	@Autowired
	private VeiculoServico veiculoServico;
	
	@Autowired
	private VeiculoAssembler veiculoAssembler;
	
	@Autowired
	private VendaServico vendaServico;
	
	@Autowired
	private VendaAssembler vendaAssembler;

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listar() {
		List<Usuario> usuarios = servico.listar();
		CollectionModel<EntityModel<Usuario>> collectionModel = assembler.toCollectionModel(usuarios);
		return ResponseEntity.ok(collectionModel);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Usuario>> buscarPorId(@PathVariable Long id) {
		return servico.buscarPorId(id)
				.map(usuario -> ResponseEntity.ok(assembler.toModel(usuario)))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EntityModel<Usuario>> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioDTO.getNome());
		usuario.setNomeSocial(usuarioDTO.getNomeSocial());
		
		Usuario usuarioSalvo;
		if (usuarioDTO.getEmpresaId() != null) {
			usuarioSalvo = servico.salvarComEmpresa(usuario, usuarioDTO.getEmpresaId());
		} else {
			usuarioSalvo = servico.salvar(usuario);
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(usuarioSalvo.getId()).toUri();
		return ResponseEntity.created(location).body(assembler.toModel(usuarioSalvo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Usuario>> atualizar(@PathVariable Long id,
			@Valid @RequestBody UsuarioDTO usuarioDTO) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Usuario usuario = servico.buscarPorId(id).orElseThrow();
		usuario.setId(id);
		usuario.setNome(usuarioDTO.getNome());
		usuario.setNomeSocial(usuarioDTO.getNomeSocial());
		
		Usuario usuarioAtualizado;
		if (usuarioDTO.getEmpresaId() != null) {
			usuarioAtualizado = servico.atualizarComEmpresa(usuario, usuarioDTO.getEmpresaId());
		} else {
			usuarioAtualizado = servico.atualizar(usuario);
		}
		
		return ResponseEntity.ok(assembler.toModel(usuarioAtualizado));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		servico.excluir(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{usuarioId}/veiculos")
	public ResponseEntity<CollectionModel<EntityModel<Veiculo>>> listarVeiculosPorUsuario(@PathVariable Long usuarioId) {
		if (!servico.existe(usuarioId)) {
			return ResponseEntity.notFound().build();
		}
		List<Veiculo> veiculos = veiculoServico.listarPorUsuario(usuarioId);
		CollectionModel<EntityModel<Veiculo>> collectionModel = veiculoAssembler.toCollectionModel(veiculos);
		return ResponseEntity.ok(collectionModel);
	}
	
	@GetMapping("/{id}/vendas")
	public ResponseEntity<CollectionModel<EntityModel<Venda>>> listarVendasPorUsuario(@PathVariable Long id) {
		if (!servico.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		List<Venda> vendas = vendaServico.listarPorCliente(id);
		CollectionModel<EntityModel<Venda>> collectionModel = vendaAssembler.toCollectionModel(vendas);
		return ResponseEntity.ok(collectionModel);
	}
}

