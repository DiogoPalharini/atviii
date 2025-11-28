package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.MercadoriaControle;
import com.autobots.automanager.modelo.Mercadoria;

@Component
public class MercadoriaAssembler implements RepresentationModelAssembler<Mercadoria, EntityModel<Mercadoria>> {

	@Override
	public EntityModel<Mercadoria> toModel(Mercadoria mercadoria) {
		return EntityModel.of(mercadoria,
				linkTo(methodOn(MercadoriaControle.class).buscarPorId(mercadoria.getId())).withSelfRel(),
				linkTo(methodOn(MercadoriaControle.class).listar()).withRel("mercadorias"));
	}

	@Override
	public CollectionModel<EntityModel<Mercadoria>> toCollectionModel(Iterable<? extends Mercadoria> mercadorias) {
		CollectionModel<EntityModel<Mercadoria>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(mercadorias);
		collectionModel.add(linkTo(methodOn(MercadoriaControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

