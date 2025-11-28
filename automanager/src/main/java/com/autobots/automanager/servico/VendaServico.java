package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.modelo.ItemMercadoria;
import com.autobots.automanager.modelo.ItemServico;
import com.autobots.automanager.modelo.Mercadoria;
import com.autobots.automanager.modelo.Servico;
import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.modelo.Veiculo;
import com.autobots.automanager.modelo.Venda;
import com.autobots.automanager.repositorio.MercadoriaRepositorio;
import com.autobots.automanager.repositorio.ServicoRepositorio;
import com.autobots.automanager.repositorio.UsuarioRepositorio;
import com.autobots.automanager.repositorio.VeiculoRepositorio;
import com.autobots.automanager.repositorio.VendaRepositorio;

@Service
public class VendaServico {

	@Autowired
	private VendaRepositorio repositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private VeiculoRepositorio veiculoRepositorio;
	
	@Autowired
	private MercadoriaRepositorio mercadoriaRepositorio;
	
	@Autowired
	private ServicoRepositorio servicoRepositorio;

	public List<Venda> listar() {
		List<Venda> vendas = repositorio.findAll();
		// Forçar carregamento das coleções LAZY para evitar LazyInitializationException
		vendas.forEach(v -> {
			v.getItensMercadoria().size();
			v.getItensServico().size();
		});
		return vendas;
	}
	
	public List<Venda> listarPorCliente(Long clienteId) {
		List<Venda> vendas = repositorio.findByClienteId(clienteId);
		vendas.forEach(v -> {
			v.getItensMercadoria().size();
			v.getItensServico().size();
		});
		return vendas;
	}
	
	public List<Venda> listarPorVeiculo(Long veiculoId) {
		List<Venda> vendas = repositorio.findByVeiculoId(veiculoId);
		vendas.forEach(v -> {
			v.getItensMercadoria().size();
			v.getItensServico().size();
		});
		return vendas;
	}

	public Optional<Venda> buscarPorId(Long id) {
		Optional<Venda> vendaOpt = repositorio.findById(id);
		if (vendaOpt.isPresent()) {
			Venda venda = vendaOpt.get();
			// Forçar carregamento das coleções LAZY
			venda.getItensMercadoria().size();
			venda.getItensServico().size();
		}
		return vendaOpt;
	}

	public Venda salvar(Venda venda) {
		calcularTotal(venda);
		return repositorio.save(venda);
	}
	
	public Venda criarVendaComItens(Long clienteId, Long veiculoId, 
			List<ItemMercadoriaDTO> itensMercadoria, List<ItemServicoDTO> itensServico) {
		Venda venda = new Venda();
		venda.setCadastro(new java.util.Date());
		
		// Buscar cliente
		Optional<Usuario> clienteOpt = usuarioRepositorio.findById(clienteId);
		if (clienteOpt.isEmpty()) {
			throw new RuntimeException("Cliente não encontrado");
		}
		venda.setCliente(clienteOpt.get());
		
		// Buscar veículo se fornecido
		if (veiculoId != null) {
			Optional<Veiculo> veiculoOpt = veiculoRepositorio.findById(veiculoId);
			if (veiculoOpt.isPresent()) {
				venda.setVeiculo(veiculoOpt.get());
			}
		}
		
		// Criar itens de mercadoria
		for (ItemMercadoriaDTO itemDTO : itensMercadoria) {
			Optional<Mercadoria> mercadoriaOpt = mercadoriaRepositorio.findById(itemDTO.getMercadoriaId());
			if (mercadoriaOpt.isEmpty()) {
				throw new RuntimeException("Mercadoria não encontrada: " + itemDTO.getMercadoriaId());
			}
			
			ItemMercadoria item = new ItemMercadoria();
			item.setMercadoria(mercadoriaOpt.get());
			item.setQuantidade(itemDTO.getQuantidade());
			item.setVenda(venda);
			venda.getItensMercadoria().add(item);
		}
		
		// Criar itens de serviço
		for (ItemServicoDTO itemDTO : itensServico) {
			Optional<Servico> servicoOpt = servicoRepositorio.findById(itemDTO.getServicoId());
			if (servicoOpt.isEmpty()) {
				throw new RuntimeException("Serviço não encontrado: " + itemDTO.getServicoId());
			}
			
			ItemServico item = new ItemServico();
			item.setServico(servicoOpt.get());
			item.setQuantidade(itemDTO.getQuantidade());
			item.setVenda(venda);
			venda.getItensServico().add(item);
		}
		
		calcularTotal(venda);
		return repositorio.save(venda);
	}
	
	private void calcularTotal(Venda venda) {
		double total = 0.0;
		
		// Soma dos itens de mercadoria
		for (ItemMercadoria item : venda.getItensMercadoria()) {
			if (item.getMercadoria() != null && item.getMercadoria().getPreco() != null) {
				total += item.getMercadoria().getPreco() * item.getQuantidade();
			}
		}
		
		// Soma dos itens de serviço
		for (ItemServico item : venda.getItensServico()) {
			if (item.getServico() != null && item.getServico().getPreco() != null) {
				total += item.getServico().getPreco() * item.getQuantidade();
			}
		}
		
		venda.setTotal(total);
	}

	public Venda atualizar(Venda venda) {
		calcularTotal(venda);
		return repositorio.save(venda);
	}

	public void excluir(Long id) {
		repositorio.deleteById(id);
	}

	public boolean existe(Long id) {
		return repositorio.existsById(id);
	}
	
	// DTOs internos
	public static class ItemMercadoriaDTO {
		private Long mercadoriaId;
		private Integer quantidade;
		
		public Long getMercadoriaId() {
			return mercadoriaId;
		}
		public void setMercadoriaId(Long mercadoriaId) {
			this.mercadoriaId = mercadoriaId;
		}
		public Integer getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
	}
	
	public static class ItemServicoDTO {
		private Long servicoId;
		private Integer quantidade;
		
		public Long getServicoId() {
			return servicoId;
		}
		public void setServicoId(Long servicoId) {
			this.servicoId = servicoId;
		}
		public Integer getQuantidade() {
			return quantidade;
		}
		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
	}
}

