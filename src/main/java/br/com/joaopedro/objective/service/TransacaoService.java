package br.com.joaopedro.objective.service;

import br.com.joaopedro.objective.dto.TransacaoRequestDTO;
import br.com.joaopedro.objective.dto.TransacaoResponseDTO;
import br.com.joaopedro.objective.exception.InsufficientBalanceException;
import br.com.joaopedro.objective.mapper.ContaMapper;
import br.com.joaopedro.objective.mapper.TransacaoMapper;
import br.com.joaopedro.objective.model.Conta;
import br.com.joaopedro.objective.model.PagamentoTipo;
import br.com.joaopedro.objective.model.Transacao;
import br.com.joaopedro.objective.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService;
    private final TransacaoMapper transacaoMapper;
    private final ContaMapper contaMapper;

    public TransacaoService(TransacaoRepository transacaoRepository, ContaService contaService, TransacaoMapper transacaoMapper, ContaMapper contaMapper) {
        this.transacaoRepository = transacaoRepository;
        this.contaService = contaService;
        this.transacaoMapper = transacaoMapper;
        this.contaMapper = contaMapper;
    }

    public TransacaoResponseDTO processarTransacao(TransacaoRequestDTO request){
        Conta conta = buscarConta(request.numero_conta());

        BigDecimal taxa = calculaValorTaxa(request.valor(), request.forma_pagamento());
        BigDecimal valorTotal = request.valor().add(taxa);

        validarSaldo(conta, valorTotal);
        atualizarSaldoConta(conta, valorTotal);

        Transacao transacao = criarTransacao(request, conta, taxa);

        return transacaoMapper.toDTO(transacao);
    }

    private void validarSaldo(Conta conta, BigDecimal valorTotal) {
        if (conta.getSaldo().compareTo(valorTotal) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    public BigDecimal calculaValorTaxa(BigDecimal valor, PagamentoTipo pagamentoTipo) {
        return valor.multiply(BigDecimal.valueOf(pagamentoTipo.getTaxa()));
    }

    private Transacao criarTransacao(TransacaoRequestDTO request, Conta conta, BigDecimal taxa) {
        return transacaoRepository.save(
                transacaoMapper.toEntity(request, conta, taxa)
        );
    }

    private void atualizarSaldoConta(Conta conta, BigDecimal valorTotal) {
        conta.setSaldo(conta.getSaldo().subtract(valorTotal));
        contaService.atualizarConta(conta);
    }

    public Conta buscarConta(Long numeroConta){
        return contaMapper.toConta(contaService.encontrarContaOuThrowException(numeroConta));
    }
}
