package com.autobots.automanager.entitades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// Imports JPA removidos - esta classe não é mais uma entidade JPA

import lombok.Data;

@Data
// @Entity removida para evitar conflito com com.autobots.automanager.modelo.Empresa
// Esta classe não é mais usada como entidade JPA
public class Empresa {
	private Long id;
	private String razaoSocial;
	private String nomeFantasia;
	private Set<Telefone> telefones = new HashSet<>();
	private Endereco endereco;
	private Date cadastro;
	private Set<Usuario> usuarios = new HashSet<>();
	private Set<Mercadoria> mercadorias = new HashSet<>();
	private Set<Servico> servicos = new HashSet<>();
	private Set<Venda> vendas = new HashSet<>();
}