package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.EmpresaControle;
import com.autobots.automanager.controle.UsuarioControle;
import com.autobots.automanager.modelo.Usuario;

@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

	@Override
	public EntityModel<Usuario> toModel(Usuario usuario) {
		EntityModel<Usuario> entityModel = EntityModel.of(usuario,
				linkTo(methodOn(UsuarioControle.class).buscarPorId(usuario.getId())).withSelfRel());
		
		if (usuario.getEmpresa() != null) {
			entityModel.add(linkTo(methodOn(EmpresaControle.class).buscarPorId(usuario.getEmpresa().getId()))
					.withRel("empresa"));
		}
		
		// Links futuros para veiculos e vendas
		entityModel.add(linkTo(methodOn(UsuarioControle.class).buscarPorId(usuario.getId())).slash("veiculos")
				.withRel("veiculos"));
		entityModel.add(linkTo(methodOn(UsuarioControle.class).buscarPorId(usuario.getId())).slash("vendas")
				.withRel("vendas"));
		
		return entityModel;
	}

	@Override
	public CollectionModel<EntityModel<Usuario>> toCollectionModel(Iterable<? extends Usuario> usuarios) {
		CollectionModel<EntityModel<Usuario>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(usuarios);
		collectionModel.add(linkTo(methodOn(UsuarioControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}

