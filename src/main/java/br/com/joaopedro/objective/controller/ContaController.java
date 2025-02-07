package br.com.joaopedro.objective.controller;

import br.com.joaopedro.objective.dto.ContaRequestDTO;
import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<ContaResponseDTO> criarConta(@Valid @RequestBody ContaRequestDTO contaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(contaService.criarConta(contaRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<ContaResponseDTO> obterConta(@RequestParam("numero_conta") Long numeroConta){
        return ResponseEntity.ok(contaService.obterConta(numeroConta));
    }
}
