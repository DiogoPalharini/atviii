package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.controle.VeiculoControle;
import com.autobots.automanager.modelo.Veiculo;

@Component
public class VeiculoAssembler implements RepresentationModelAssembler<Veiculo, EntityModel<Veiculo>> {

	@Override
	public EntityModel<Veiculo> toModel(Veiculo veiculo) {
		EntityModel<Veiculo> entityModel = EntityModel.of(veiculo,
				linkTo(methodOn(VeiculoControle.class).buscarPorId(veiculo.getId())).withSelfRel());
		
		if (veiculo.getProprietario() != null) {
			entityModel.add(linkTo(methodOn(UsuarioControle.class).buscarPorId(veiculo.getProprietario().getId()))
					.withRel("usuario"));
		}
		
		// Link futuro para vendas
		entityModel.add(linkTo(methodOn(VeiculoControle.class).buscarPorId(veiculo.getId())).slash("vendas")
				.withRel("vendas"));
		
		return entityModel;
	}

	@Override
	public CollectionModel<EntityModel<Veiculo>> toCollectionModel(Iterable<? extends Veiculo> veiculos) {
		CollectionModel<EntityModel<Veiculo>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(veiculos);
		collectionModel.add(linkTo(methodOn(VeiculoControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

