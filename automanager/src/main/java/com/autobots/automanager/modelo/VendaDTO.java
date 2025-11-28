package com.autobots.automanager.modelo;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.autobots.automanager.servico.VendaServico.ItemMercadoriaDTO;
import com.autobots.automanager.servico.VendaServico.ItemServicoDTO;

import lombok.Data;

@Data
public class VendaDTO {
	@NotNull(message = "Cliente é obrigatório")
	private Long clienteId;
	
	private Long veiculoId;
	
	private List<ItemMercadoriaDTO> itensMercadoria;
	
	private List<ItemServicoDTO> itensServico;
}

