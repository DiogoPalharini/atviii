package com.autobots.automanager.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "CredencialModelo")
public class Credencial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Data de criação é obrigatória")
	@Column(nullable = false)
	private Date criacao;
	
	@Column
	private Date ultimoAcesso;
	
	@Column(nullable = false)
	private boolean inativo;
	
	@Column(nullable = false)
	private String nomeUsuario;
	
	@JsonIgnore
	@Column(nullable = false)
	private String senha;
	
	@OneToOne(mappedBy = "credencial", fetch = FetchType.LAZY)
	private Usuario usuario;
}

