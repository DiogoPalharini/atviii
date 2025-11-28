package com.autobots.automanager.modelo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CredencialDTO {
	@NotNull(message = "Data de criação é obrigatória")
	private Date criacao;
	
	private Date ultimoAcesso;
	
	@NotNull(message = "Status inativo é obrigatório")
	private boolean inativo;
	
	@NotBlank(message = "Nome de usuário é obrigatório")
	private String nomeUsuario;
	
	@NotBlank(message = "Senha é obrigatória")
	private String senha;
	
	private Long usuarioId;
}

