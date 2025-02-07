package br.com.joaopedro.objective.model;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ContaModelTest {

    @Test
    void testContaEqualsAndHashCode() {
        Conta conta1 = new Conta(1L, BigDecimal.valueOf(100.00));
        Conta conta2 = new Conta(1L, BigDecimal.valueOf(100.00));
        Conta conta3 = new Conta(2L, BigDecimal.valueOf(100.00));

        assertThat(conta1).isEqualTo(conta2);
        assertThat(conta1).isNotEqualTo(conta3);
        assertThat(conta1).isNotEqualTo(null);
        assertThat(conta1).isNotEqualTo(new Object());
        assertThat(conta1.hashCode()).isEqualTo(conta2.hashCode());
    }

    @Test
    void testContaGettersAndSetters() {
        Conta conta = new Conta();
        conta.setNumero_conta(1L);
        conta.setSaldo(BigDecimal.valueOf(100.00));

        assertThat(conta.getNumero_conta()).isEqualTo(1L);
        assertThat(conta.getSaldo()).isEqualByComparingTo(BigDecimal.valueOf(100.00));
    }

    @Test
    void testContaToString() {
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));
        String toString = conta.toString();

        assertThat(toString)
                .contains("1")
                .contains("100.0");
    }

    @Test
    void testContaConstructor() {
        Conta conta = new Conta(1L, BigDecimal.valueOf(100.00));

        assertThat(conta.getNumero_conta()).isEqualTo(1L);
        assertThat(conta.getSaldo()).isEqualByComparingTo(BigDecimal.valueOf(100.00));
    }
}
