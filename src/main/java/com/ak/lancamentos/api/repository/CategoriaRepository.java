package com.ak.lancamentos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ak.lancamentos.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
