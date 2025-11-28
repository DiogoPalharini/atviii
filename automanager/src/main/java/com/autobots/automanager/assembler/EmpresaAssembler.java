package com.autobots.automanager.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controle.EmpresaControle;
import com.autobots.automanager.modelo.Empresa;

@Component
public class EmpresaAssembler implements RepresentationModelAssembler<Empresa, EntityModel<Empresa>> {

	@Override
	public EntityModel<Empresa> toModel(Empresa empresa) {
		return EntityModel.of(empresa,
				linkTo(methodOn(EmpresaControle.class).buscarPorId(empresa.getId())).withSelfRel(),
				linkTo(methodOn(EmpresaControle.class).listar()).withRel("empresas"));
	}

	@Override
	public CollectionModel<EntityModel<Empresa>> toCollectionModel(Iterable<? extends Empresa> empresas) {
		CollectionModel<EntityModel<Empresa>> collectionModel = RepresentationModelAssembler.super
				.toCollectionModel(empresas);
		collectionModel.add(linkTo(methodOn(EmpresaControle.class).listar()).withSelfRel());
		return collectionModel;
	}
}
