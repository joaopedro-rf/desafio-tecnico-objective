package br.com.joaopedro.objective.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", message = "O valor da transação deve ser positivo")
    private BigDecimal valor;

    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", taxa=" + taxa +
                ", conta=" + conta +
                ", formaPagamento=" + forma_pagamento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id) && Objects.equals(valor, transacao.valor) && Objects.equals(taxa, transacao.taxa) && Objects.equals(conta, transacao.conta) && forma_pagamento == transacao.forma_pagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, taxa, conta, forma_pagamento);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public PagamentoTipo getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(PagamentoTipo forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public Transacao(Long id, BigDecimal valor, BigDecimal taxa, Conta conta, PagamentoTipo forma_pagamento) {
        this.id = id;
        this.valor = valor;
        this.taxa = taxa;
        this.conta = conta;
        this.forma_pagamento = forma_pagamento;
    }

    public static TransacaoBuilder builder() {
        return new TransacaoBuilder();
    }

    public static class TransacaoBuilder {
        private BigDecimal valor;
        private BigDecimal taxa;
        private Conta conta;
        private PagamentoTipo forma_pagamento;

        public TransacaoBuilder valor(BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        public TransacaoBuilder taxa(BigDecimal taxa) {
            this.taxa = taxa;
            return this;
        }

        public TransacaoBuilder conta(Conta conta) {
            this.conta = conta;
            return this;
        }

        public TransacaoBuilder formaPagamento(PagamentoTipo formaPagamento) {
            this.forma_pagamento = formaPagamento;
            return this;
        }

        public Transacao build() {
            return new Transacao(null, valor, taxa, conta, forma_pagamento);
        }
    }

    @Column(nullable = false)
    private BigDecimal taxa;

    @ManyToOne()
    @JoinColumn(name = "numero_conta" , nullable = false)
    private Conta conta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PagamentoTipo forma_pagamento;





}
