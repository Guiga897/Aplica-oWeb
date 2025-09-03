package br.com.aweb.escola.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.aweb.escola.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    
}
