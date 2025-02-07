package br.com.joaopedro.objective.mapper;

import br.com.joaopedro.objective.dto.TransacaoRequestDTO;
import br.com.joaopedro.objective.dto.TransacaoResponseDTO;
import br.com.joaopedro.objective.model.Conta;
import br.com.joaopedro.objective.model.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoMapper {

    public Transacao toEntity(TransacaoRequestDTO dto, Conta conta, BigDecimal taxa) {
        return Transacao.builder()
                .valor(dto.valor())
                .taxa(taxa)
                .conta(conta)
                .formaPagamento(dto.forma_pagamento())
                .build();
    }

    public TransacaoResponseDTO toDTO(Transacao entity){
        return new TransacaoResponseDTO(
                entity.getConta().getNumero_conta(),
                entity.getConta().getSaldo()
        );
    }
}
