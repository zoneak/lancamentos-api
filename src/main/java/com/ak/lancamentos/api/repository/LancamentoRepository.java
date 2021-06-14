package com.ak.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.lancamentos.api.model.Lancamento;
import com.ak.lancamentos.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
