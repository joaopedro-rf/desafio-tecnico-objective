package br.com.joaopedro.objective.controller;

import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.dto.TransacaoRequestDTO;
import br.com.joaopedro.objective.dto.TransacaoResponseDTO;
import br.com.joaopedro.objective.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @PostMapping()
    public ResponseEntity<TransacaoResponseDTO> processarTransacao(@Valid @RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.processarTransacao(transacaoRequestDTO));
    }
}
