package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.controle.VeiculoControle;
import com.autobots.automanager.controle.VendaControle;
import com.autobots.automanager.modelo.Venda;

@Component
public class VendaAssembler implements RepresentationModelAssembler<Venda, EntityModel<Venda>> {

	@Override
	public EntityModel<Venda> toModel(Venda venda) {
		EntityModel<Venda> entityModel = EntityModel.of(venda,
				linkTo(methodOn(VendaControle.class).buscarPorId(venda.getId())).withSelfRel());
		
		if (venda.getCliente() != null) {
			entityModel.add(linkTo(methodOn(UsuarioControle.class).buscarPorId(venda.getCliente().getId()))
					.withRel("cliente"));
		}
		
		if (venda.getVeiculo() != null) {
			entityModel.add(linkTo(methodOn(VeiculoControle.class).buscarPorId(venda.getVeiculo().getId()))
					.withRel("veiculo"));
		}
		
		// Link para itens
		entityModel.add(linkTo(methodOn(VendaControle.class).buscarPorId(venda.getId())).slash("itens")
				.withRel("itens"));
		
		return entityModel;
	}

	@Override
	public CollectionModel<EntityModel<Venda>> toCollectionModel(Iterable<? extends Venda> vendas) {
		CollectionModel<EntityModel<Venda>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(vendas);
		collectionModel.add(linkTo(methodOn(VendaControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

