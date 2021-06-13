package com.ak.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.lancamentos.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
