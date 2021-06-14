package com.ak.lancamentos.api.repository.lancamento;

import java.util.List;

import com.ak.lancamentos.api.model.Lancamento;
import com.ak.lancamentos.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);

}
