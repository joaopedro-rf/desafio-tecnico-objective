package br.com.joaopedro.objective.service;

import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.dto.TransacaoRequestDTO;
import br.com.joaopedro.objective.dto.TransacaoResponseDTO;
import br.com.joaopedro.objective.exception.InsufficientBalanceException;
import br.com.joaopedro.objective.exception.UserNotFoundException;
import br.com.joaopedro.objective.mapper.ContaMapper;
import br.com.joaopedro.objective.mapper.TransacaoMapper;
import br.com.joaopedro.objective.model.Conta;
import br.com.joaopedro.objective.model.PagamentoTipo;
import br.com.joaopedro.objective.model.Transacao;
import br.com.joaopedro.objective.repository.TransacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ContaService contaService;

    @Mock
    private TransacaoMapper transacaoMapper;

    @Mock
    private ContaMapper contaMapper;

    @InjectMocks
    private TransacaoService transacaoService;

    private Conta conta;
    private Transacao transacao;
    private TransacaoRequestDTO transacaoRequestDTO;
    private TransacaoResponseDTO transacaoResponseDTO;

    @BeforeEach
    void setUp() {
        conta = new Conta(1L, BigDecimal.valueOf(1000.00));
        transacao = new Transacao(1L, BigDecimal.valueOf(100.00), BigDecimal.valueOf(5.00), conta, PagamentoTipo.C);
        transacaoRequestDTO = new TransacaoRequestDTO(PagamentoTipo.C, 1L, BigDecimal.valueOf(100.00));
        transacaoResponseDTO = new TransacaoResponseDTO(1L, BigDecimal.valueOf(895.00));
    }

    @Nested
    @DisplayName("Processar Transacao Tests")
    class ProcessarTransacaoTests {

        @Test
        @DisplayName("Deve processar transação com sucesso")
        void deveProcessarTransacaoComSucesso() {
            when(contaService.encontrarContaOuThrowException(1L)).thenReturn(new ContaResponseDTO(1L, BigDecimal.valueOf(1000.00)));
            when(contaMapper.toConta(any())).thenReturn(conta);
            when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao);
            when(transacaoMapper.toEntity(any(), any(), any())).thenReturn(transacao);
            when(transacaoMapper.toDTO(any())).thenReturn(transacaoResponseDTO);

            TransacaoResponseDTO result = transacaoService.processarTransacao(transacaoRequestDTO);

            assertThat(result).isNotNull();
            assertThat(result.numero_conta()).isEqualTo(transacaoResponseDTO.numero_conta());
            assertThat(result.saldo()).isEqualTo(transacaoResponseDTO.saldo());
            verify(transacaoRepository).save(any(Transacao.class));
            verify(contaService).atualizarConta(any(Conta.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando saldo é insuficiente")
        void deveLancarExcecaoQuandoSaldoInsuficiente() {
            Conta contaComSaldoBaixo = new Conta(1L, BigDecimal.valueOf(50.00));
            when(contaService.encontrarContaOuThrowException(1L)).thenReturn(new ContaResponseDTO(1L, BigDecimal.valueOf(50.00)));
            when(contaMapper.toConta(any())).thenReturn(contaComSaldoBaixo);

            assertThatThrownBy(() -> transacaoService.processarTransacao(transacaoRequestDTO))
                    .isInstanceOf(InsufficientBalanceException.class);

            verify(transacaoRepository, never()).save(any());
            verify(contaService, never()).atualizarConta(any());
        }
    }

    @Nested
    @DisplayName("Calcula Valor Taxa Tests")
    class CalculaValorTaxaTests {
        @Test
        @DisplayName("Deve calcular taxa corretamente para pagamento com crédito")
        void deveCalcularTaxaCorretamenteParaCredito() {
            BigDecimal valor = BigDecimal.valueOf(100.00);
            PagamentoTipo formaPagamento = PagamentoTipo.C;

            BigDecimal taxa = transacaoService.calculaValorTaxa(valor, formaPagamento);

            assertThat(taxa).isEqualByComparingTo(BigDecimal.valueOf(5.00));
        }

        @Test
        @DisplayName("Deve calcular taxa corretamente para pagamento com débito")
        void deveCalcularTaxaCorretamenteParaDebito() {
            BigDecimal valor = BigDecimal.valueOf(100.00);
            PagamentoTipo formaPagamento = PagamentoTipo.D;

            BigDecimal taxa = transacaoService.calculaValorTaxa(valor, formaPagamento);

            assertThat(taxa).isEqualByComparingTo(BigDecimal.valueOf(3.00));
        }
    }

    @Nested
    @DisplayName("Buscar Conta Tests")
    class BuscarContaTests {

        @Test
        @DisplayName("Deve buscar conta com sucesso")
        void deveBuscarContaComSucesso() {
            when(contaService.encontrarContaOuThrowException(1L)).thenReturn(new ContaResponseDTO(1L, BigDecimal.valueOf(1000.00)));
            when(contaMapper.toConta(any())).thenReturn(conta);

            Conta result = transacaoService.buscarConta(1L);

            assertThat(result).isNotNull();
            assertThat(result.getNumero_conta()).isEqualTo(1L);
            assertThat(result.getSaldo()).isEqualByComparingTo(BigDecimal.valueOf(1000.00));
        }

        @Test
        @DisplayName("Deve propagar exceção quando conta não existe")
        void devePropararExcecaoQuandoContaNaoExiste() {
            when(contaService.encontrarContaOuThrowException(1L)).thenThrow(new UserNotFoundException("Conta não encontrada: 1"));

            assertThatThrownBy(() -> transacaoService.buscarConta(1L))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("Conta não encontrada: 1");
        }
    }
}
