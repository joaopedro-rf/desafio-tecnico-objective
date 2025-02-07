package br.com.joaopedro.objective.service;

import br.com.joaopedro.objective.dto.ContaRequestDTO;
import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.exception.InvalidAccountException;
import br.com.joaopedro.objective.exception.UserNotFoundException;
import br.com.joaopedro.objective.mapper.ContaMapper;
import br.com.joaopedro.objective.model.Conta;
import br.com.joaopedro.objective.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    @Mock
    private ContaRepository contaRepository;
    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaMapper contaMapper;

    private Conta conta;
    private ContaRequestDTO contaRequestDTO;
    private ContaResponseDTO contaResponseDTO;

    @BeforeEach
    void setUp(){
        conta = new Conta(1L, BigDecimal.valueOf(100.00));
        contaRequestDTO = new ContaRequestDTO(1L, BigDecimal.valueOf(100.00));
        contaResponseDTO = new ContaResponseDTO(1L, BigDecimal.valueOf(100.00));
    }

    @Nested
    @DisplayName("Criar Conta Tests")
    class CriarContaTests {

        @Test
        @DisplayName("Deve criar conta com sucesso")
        void deveCriarContaComSucesso() {
            when(contaRepository.existsById(1L)).thenReturn(false);
            when(contaRepository.save(any(Conta.class))).thenReturn(conta);
            when(contaMapper.toDTO(any(Conta.class))).thenReturn(contaResponseDTO);

            ContaResponseDTO result = contaService.criarConta(contaRequestDTO);

            assertThat(result).isNotNull();
            assertThat(result.numero_conta()).isEqualTo(contaRequestDTO.numero_conta());
            assertThat(result.saldo()).isEqualTo(contaRequestDTO.saldo());
            verify(contaRepository).save(any(Conta.class));
            verify(contaMapper).toDTO(any(Conta.class));
        }

        @Test
        @DisplayName("Deve lançar exceção quando conta já existe")
        void deveLancarExcecaoQuandoContaJaExiste() {
            when(contaRepository.existsById(1L)).thenReturn(true);

            assertThatThrownBy(() -> contaService.criarConta(contaRequestDTO))
                    .isInstanceOf(InvalidAccountException.class)
                    .hasMessage("Conta já existe");

            verify(contaRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Obter Conta Tests")
    class ObterContaTests {

        @Test
        @DisplayName("Deve obter conta com sucesso")
        void deveObterContaComSucesso() {
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
            when(contaMapper.toDTO(conta)).thenReturn(contaResponseDTO);

            ContaResponseDTO result = contaService.obterConta(1L);

            assertThat(result).isNotNull();
            assertThat(result.numero_conta()).isEqualTo(conta.getNumero_conta());
            assertThat(result.saldo()).isEqualTo(conta.getSaldo());
        }

        @Test
        @DisplayName("Deve lançar exceção quando conta não existe")
        void deveLancarExcecaoQuandoContaNaoExiste() {
            when(contaRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> contaService.obterConta(1L))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("Conta não encontrada: 1");
        }
    }

    @Nested
    @DisplayName("Atualizar Conta Tests")
    class AtualizarContaTests {

        @Test
        @DisplayName("Deve atualizar conta com sucesso")
        void deveAtualizarContaComSucesso() {
            Conta contaAtualizada = new Conta(1L, BigDecimal.valueOf(200.00));
            when(contaRepository.findById(1L)).thenReturn(Optional.of(conta));
            when(contaRepository.save(any(Conta.class))).thenReturn(contaAtualizada);
            when(contaMapper.toDTO(any(Conta.class))).thenReturn(new ContaResponseDTO(1L, BigDecimal.valueOf(200.00)));

            contaService.atualizarConta(contaAtualizada);

            verify(contaRepository).save(any(Conta.class));
            verify(contaMapper).toDTO(any(Conta.class));
        }

        @Test
        @DisplayName("Deve lançar exceção ao atualizar conta inexistente")
        void deveLancarExcecaoAoAtualizarContaInexistente() {
            Conta contaAtualizada = new Conta(1L, BigDecimal.valueOf(200.00));
            when(contaRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> contaService.atualizarConta(contaAtualizada))
                    .isInstanceOf(UserNotFoundException.class)
                    .hasMessage("Conta não encontrada: 1");

            verify(contaRepository, never()).save(any());
        }
    }
}
