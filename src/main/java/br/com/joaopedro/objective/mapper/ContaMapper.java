package br.com.joaopedro.objective.mapper;

import br.com.joaopedro.objective.dto.ContaResponseDTO;
import br.com.joaopedro.objective.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

    public Conta toConta(ContaResponseDTO dto){
        return new Conta(
                dto.numero_conta(),
                dto.saldo()
        );
    }

    public ContaResponseDTO toDTO(Conta entity) {
        return new ContaResponseDTO(
                entity.getNumero_conta(),
                entity.getSaldo()
        );
    }
}
