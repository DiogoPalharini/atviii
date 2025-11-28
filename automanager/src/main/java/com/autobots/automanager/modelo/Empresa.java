package com.autobots.automanager.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.entitades.Telefone;

import lombok.Data;

@Data
@Entity
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Razão social é obrigatória")
	@Column(nullable = false)
	private String razaoSocial;
	
	@Column
	private String nomeFantasia;
	
	@Column(unique = true)
	private String cnpj;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	
	@NotNull(message = "Data de cadastro é obrigatória")
	@Column(nullable = false)
	private Date cadastro;
	
}

