package br.com.joaopedro.objective.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "contas")
public class Conta {

    @Id
    @Min(value = 1, message = "O número da conta deve ser um número positivo")
    private Long numero_conta;

    public Conta(Long numero_conta, BigDecimal saldo) {
        this.numero_conta = numero_conta;
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numeroConta=" + numero_conta +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(numero_conta, conta.numero_conta) && Objects.equals(saldo, conta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero_conta, saldo);
    }

    public Long getNumero_conta() {
        return numero_conta;
    }

    public void setNumero_conta(Long numero_conta) {
        this.numero_conta = numero_conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Conta() {
    }

    @Column(nullable = false)
    @DecimalMin(value = "0.00", message = "O saldo da conta deve ser positivo")
    private BigDecimal saldo;

}
