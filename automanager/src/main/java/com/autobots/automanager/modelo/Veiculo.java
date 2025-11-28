package com.autobots.automanager.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "proprietario", "vendas" })
@Entity(name = "VeiculoModelo")
public class Veiculo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Tipo do veículo é obrigatório")
	@Column(nullable = false)
	private TipoVeiculo tipo;
	
	@NotBlank(message = "Modelo é obrigatório")
	@Column(nullable = false)
	private String modelo;
	
	@NotBlank(message = "Placa é obrigatória")
	@Column(nullable = false)
	private String placa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario proprietario;
	
}

