package com.autobots.automanager.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
@Entity
public class ItemServico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Serviço é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servico_id", nullable = false)
	private Servico servico;
	
	@NotNull(message = "Quantidade é obrigatória")
	@Positive(message = "Quantidade deve ser positiva")
	@Column(nullable = false)
	private Integer quantidade;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venda_id")
	private Venda venda;
}

