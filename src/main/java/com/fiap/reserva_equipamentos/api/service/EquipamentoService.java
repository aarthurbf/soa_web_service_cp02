package com.fiap.reserva_equipamentos.api.service;

import com.fiap.reserva_equipamentos.api.domain.Equipamento;
import com.fiap.reserva_equipamentos.api.dto.EquipamentoRequestDTO;
import com.fiap.reserva_equipamentos.api.dto.EquipamentoResponseDTO;
import com.fiap.reserva_equipamentos.api.exception.ResourceNotFoundException;
import com.fiap.reserva_equipamentos.api.repository.EquipamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócio de Equipamentos.
 * Implementa operações CRUD completas.
 */
@Service
@Transactional
public class EquipamentoService {
    
    private final EquipamentoRepository repo;
    
    public EquipamentoService(EquipamentoRepository repo) {
        this.repo = repo;
    }

    /**
     * Lista todos os equipamentos ativos.
     */
    public List<EquipamentoResponseDTO> listarAtivos() {
        return repo.findByAtivoTrue().stream()
                .map(EquipamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca equipamentos por termo na descrição.
     */
    public List<EquipamentoResponseDTO> buscar(String termo) {
        return repo.findByDescricaoContainingIgnoreCase(termo).stream()
                .map(EquipamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Lista todos os equipamentos (ativos e inativos).
     */
    public List<EquipamentoResponseDTO> listarTodos() {
        return repo.findAll().stream()
                .map(EquipamentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca um equipamento por ID.
     * @throws ResourceNotFoundException se não encontrado
     */
    public EquipamentoResponseDTO buscarPorId(Long id) {
        Equipamento equipamento = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento", id));
        return EquipamentoResponseDTO.fromEntity(equipamento);
    }

    /**
     * Cria um novo equipamento.
     */
    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        Equipamento equipamento = EquipamentoResponseDTO.toEntity(dto);
        Equipamento salvo = repo.save(equipamento);
        return EquipamentoResponseDTO.fromEntity(salvo);
    }

    /**
     * Atualiza um equipamento existente.
     * @throws ResourceNotFoundException se não encontrado
     */
    public EquipamentoResponseDTO atualizar(Long id, EquipamentoRequestDTO dto) {
        Equipamento equipamento = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento", id));
        
        EquipamentoResponseDTO.updateEntity(equipamento, dto);
        Equipamento atualizado = repo.save(equipamento);
        return EquipamentoResponseDTO.fromEntity(atualizado);
    }

    /**
     * Deleta logicamente um equipamento (marca como inativo).
     * @throws ResourceNotFoundException se não encontrado
     */
    public void deletar(Long id) {
        Equipamento equipamento = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipamento", id));
        
        equipamento.setAtivo(false);
        repo.save(equipamento);
    }
}