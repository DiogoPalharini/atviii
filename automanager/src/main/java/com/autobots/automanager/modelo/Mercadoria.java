package com.autobots.automanager.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
public class Mercadoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;
	
	@Column
	private String descricao;
	
	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser positivo")
	@Column(nullable = false)
	private Double preco;
	
	@Column(unique = true)
	private String codigoBarra;
}

