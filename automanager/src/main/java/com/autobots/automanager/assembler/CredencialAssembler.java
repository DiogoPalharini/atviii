package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.CredencialControle;
import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.modelo.Credencial;

@Component
public class CredencialAssembler implements RepresentationModelAssembler<Credencial, EntityModel<Credencial>> {

	@Override
	public EntityModel<Credencial> toModel(Credencial credencial) {
		EntityModel<Credencial> entityModel = EntityModel.of(credencial,
				linkTo(methodOn(CredencialControle.class).buscarPorId(credencial.getId())).withSelfRel());
		
		if (credencial.getUsuario() != null) {
			entityModel.add(linkTo(methodOn(UsuarioControle.class).buscarPorId(credencial.getUsuario().getId()))
					.withRel("usuario"));
		}
		
		return entityModel;
	}

	@Override
	public CollectionModel<EntityModel<Credencial>> toCollectionModel(Iterable<? extends Credencial> credenciais) {
		CollectionModel<EntityModel<Credencial>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(credenciais);
		collectionModel.add(linkTo(methodOn(CredencialControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

