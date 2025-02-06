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
                ", formaPagamento=" + formaPagamento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id) && Objects.equals(valor, transacao.valor) && Objects.equals(taxa, transacao.taxa) && Objects.equals(conta, transacao.conta) && formaPagamento == transacao.formaPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, valor, taxa, conta, formaPagamento);
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

    public PagamentoTipo getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(PagamentoTipo formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Transacao(Long id, BigDecimal valor, BigDecimal taxa, Conta conta, PagamentoTipo formaPagamento) {
        this.id = id;
        this.valor = valor;
        this.taxa = taxa;
        this.conta = conta;
        this.formaPagamento = formaPagamento;
    }

    @Column(nullable = false)
    private BigDecimal taxa;

    @ManyToOne()
    @JoinColumn(name = "numero_conta" , nullable = false)
    private Conta conta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PagamentoTipo formaPagamento;





}
