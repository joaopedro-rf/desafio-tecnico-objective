package br.com.joaopedro.objective.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TransacaoModelTests {

    @Test
    void testTransacaoEqualsAndHashCode() {
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));
        Transacao transacao1 = new Transacao(1L, BigDecimal.valueOf(50.00), BigDecimal.valueOf(2.50), conta, PagamentoTipo.C);
        Transacao transacao2 = new Transacao(1L, BigDecimal.valueOf(50.00), BigDecimal.valueOf(2.50), conta, PagamentoTipo.C);
        Transacao transacao3 = new Transacao(2L, BigDecimal.valueOf(50.00), BigDecimal.valueOf(2.50), conta, PagamentoTipo.C);

        assertThat(transacao1).isEqualTo(transacao2);
        assertThat(transacao1).isNotEqualTo(transacao3);
        assertThat(transacao1).isNotEqualTo(null);
        assertThat(transacao1).isNotEqualTo(new Object());
        assertThat(transacao1.hashCode()).isEqualTo(transacao2.hashCode());
    }

    @Test
    void testTransacaoGettersAndSetters() {
        Transacao transacao = new Transacao();
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));

        transacao.setId(1L);
        transacao.setValor(BigDecimal.valueOf(50.00));
        transacao.setTaxa(BigDecimal.valueOf(2.50));
        transacao.setConta(conta);
        transacao.setForma_pagamento(PagamentoTipo.C);

        assertThat(transacao.getId()).isEqualTo(1L);
        assertThat(transacao.getValor()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
        assertThat(transacao.getTaxa()).isEqualByComparingTo(BigDecimal.valueOf(2.50));
        assertThat(transacao.getConta()).isEqualTo(conta);
        assertThat(transacao.getForma_pagamento()).isEqualTo(PagamentoTipo.C);
    }

    @Test
    void testTransacaoBuilder() {
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));

        Transacao transacao = Transacao.builder()
                .valor(BigDecimal.valueOf(50.00))
                .taxa(BigDecimal.valueOf(2.50))
                .conta(conta)
                .formaPagamento(PagamentoTipo.C)
                .build();

        assertThat(transacao.getValor()).isEqualByComparingTo(BigDecimal.valueOf(50.00));
        assertThat(transacao.getTaxa()).isEqualByComparingTo(BigDecimal.valueOf(2.50));
        assertThat(transacao.getConta()).isEqualTo(conta);
        assertThat(transacao.getForma_pagamento()).isEqualTo(PagamentoTipo.C);
    }

    @Test
    void testTransacaoToString() {
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));
        Transacao transacao = new Transacao(1L, BigDecimal.valueOf(50.00), BigDecimal.valueOf(2.50), conta, PagamentoTipo.C);

        String toString = transacao.toString();

        assertThat(toString)
                .contains("1")
                .contains("50.0")
                .contains("2.5")
                .contains("C");
    }
}
