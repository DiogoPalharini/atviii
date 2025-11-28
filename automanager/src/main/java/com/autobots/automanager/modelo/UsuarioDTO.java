package com.autobots.automanager.modelo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioDTO {
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	private String nomeSocial;
	
	private Long empresaId;
}

