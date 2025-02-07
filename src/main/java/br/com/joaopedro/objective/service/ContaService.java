package br.com.joaopedro.objective.service;

import br.com.joaopedro.objective.dto.ContaRequestDTO;
import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.exception.InvalidAccountException;
import br.com.joaopedro.objective.exception.UserNotFoundException;
import br.com.joaopedro.objective.mapper.ContaMapper;
import br.com.joaopedro.objective.model.Conta;
import br.com.joaopedro.objective.repository.ContaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;

    public ContaService(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }

    public ContaResponseDTO criarConta(ContaRequestDTO contaRequestDTO){
        if (contaRepository.existsById(contaRequestDTO.numeroConta())) {
            throw new InvalidAccountException("Conta já existe");
        }
        Conta conta = new Conta();
        conta.setNumeroConta(contaRequestDTO.numeroConta());
        conta.setSaldo(contaRequestDTO.saldo());
        return contaMapper.toDTO(contaRepository.save(conta));
    }

    public ContaResponseDTO obterConta(Long numeroConta){
        return encontrarContaOuThrowException(numeroConta);
    }

    public ContaResponseDTO encontrarContaOuThrowException(Long numeroConta) {
        return contaRepository.findById(numeroConta)
                .map(contaMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("Conta não encontrada: " + numeroConta));
    }

    @Transactional
    public void atualizarConta(Conta contaAtualizada) {
        Conta contaExistente = contaRepository.findById(contaAtualizada.getNumeroConta())
                .orElseThrow(() -> new UserNotFoundException("Conta não encontrada: " + contaAtualizada.getNumeroConta()));

        contaExistente.setSaldo(contaAtualizada.getSaldo());
        contaMapper.toDTO(contaRepository.save(contaExistente));
    }
}
