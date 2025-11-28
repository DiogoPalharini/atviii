package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.ServicoControle;
import com.autobots.automanager.modelo.Servico;

@Component
public class ServicoAssembler implements RepresentationModelAssembler<Servico, EntityModel<Servico>> {

	@Override
	public EntityModel<Servico> toModel(Servico servico) {
		return EntityModel.of(servico,
				linkTo(methodOn(ServicoControle.class).buscarPorId(servico.getId())).withSelfRel(),
				linkTo(methodOn(ServicoControle.class).listar()).withRel("servicos"));
	}

	@Override
	public CollectionModel<EntityModel<Servico>> toCollectionModel(Iterable<? extends Servico> servicos) {
		CollectionModel<EntityModel<Servico>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(servicos);
		collectionModel.add(linkTo(methodOn(ServicoControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

