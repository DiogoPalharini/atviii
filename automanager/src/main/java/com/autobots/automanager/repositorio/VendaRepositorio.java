package com.autobots.automanager.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobots.automanager.modelo.Usuario;
import com.autobots.automanager.modelo.Veiculo;
import com.autobots.automanager.modelo.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
	List<Venda> findByCliente(Usuario cliente);
	List<Venda> findByClienteId(Long clienteId);
	List<Venda> findByVeiculo(Veiculo veiculo);
	List<Venda> findByVeiculoId(Long veiculoId);
}

