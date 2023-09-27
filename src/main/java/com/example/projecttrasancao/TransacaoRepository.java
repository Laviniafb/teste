package com.example.projecttrasancao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Transacao findByTipoTransacao(String tipoTransacao);

}
