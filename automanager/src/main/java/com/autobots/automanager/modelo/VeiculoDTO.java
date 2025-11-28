package com.autobots.automanager.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

import lombok.Data;

@Data
public class VeiculoDTO {
	@NotNull(message = "Tipo do veículo é obrigatório")
	private TipoVeiculo tipo;
	
	@NotBlank(message = "Modelo é obrigatório")
	private String modelo;
	
	@NotBlank(message = "Placa é obrigatória")
	private String placa;
	
	private Long usuarioId;
}

