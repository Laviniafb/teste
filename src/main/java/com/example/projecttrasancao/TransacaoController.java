package com.example.projecttrasancao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoRepository transacaoRepository;

    @Autowired
    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    //Cadastrar uma nova transação
    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Transacao transacao ){
        try {
            transacaoRepository.save(transacao);
            return ResponseEntity.ok("Sua transação foi inserida com sucesso!! :)");
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + ex);
        }
    }

    //Listar todas as transações
    @GetMapping("/listar")
    public List<Transacao> listarTransacoes(){
        return transacaoRepository.findAll();
    }


    //Buscar transações por tipoTransacao
    @GetMapping("/buscar/{tipo}")
    public Transacao buscar(@PathVariable String tipo) {
       return transacaoRepository.findByTipoTransacao(tipo);
    }

  //Atualizar as informações descricao, valor ou tipo_transacao de uma transação por id
  @PutMapping("/atualizar/{id}")
  public ResponseEntity<String> atualizarTransacao(@PathVariable Long id,
                                                              @RequestBody Transacao transacaoAtualizada) {

      Optional<Transacao> transacaoExistente = transacaoRepository.findById(id);

      if (transacaoExistente.isPresent()) {

          Transacao transacao = transacaoExistente.get();
          transacao.setDescricao(transacaoAtualizada.getDescricao());
          transacao.setValor(transacaoAtualizada.getValor());
          transacao.setTipoTransacao(transacaoAtualizada.getTipoTransacao());
          transacaoRepository.save(transacao);
          return ResponseEntity.ok("Transação atualizada com sucesso!! :)");

      } else {

          return ResponseEntity.notFound().build();
      }
  }


  //Excluir transação pelo id
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable long id) {
        transacaoRepository.deleteById(id);
        return ResponseEntity.ok("Sua transação foi excluída com sucesso!! :)");
    }

}





