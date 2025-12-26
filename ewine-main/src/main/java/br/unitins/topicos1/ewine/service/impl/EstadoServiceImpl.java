package br.unitins.topicos1.ewine.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.ewine.resource.location.dto.input.EstadoDTO;
import br.unitins.topicos1.ewine.resource.location.dto.response.EstadoDTOResponse;
import br.unitins.topicos1.ewine.model.location.Estado;
import br.unitins.topicos1.ewine.model.produto.vinho.Pais;
import br.unitins.topicos1.ewine.model.location.Regiao;
import br.unitins.topicos1.ewine.infrastructure.persistence.EstadoRepository;
import br.unitins.topicos1.ewine.infrastructure.persistence.PaisRepository;
import br.unitins.topicos1.ewine.service.EstadoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {
    
    @Inject
    EstadoRepository estadoRepository;

    @Inject
    PaisRepository paisRepository; 

    @Override
    @Transactional
    public EstadoDTOResponse create(EstadoDTO estadoDTO) {
        Estado novoEstado = new Estado();
        novoEstado.setNome(estadoDTO.nome());
        novoEstado.setSigla(estadoDTO.sigla());
        novoEstado.setRegiao(Regiao.valueOf(estadoDTO.idRegiao()));

        estadoRepository.persist(novoEstado);
        return EstadoDTOResponse.valueOf(novoEstado);
    }

    @Override
    @Transactional
    public EstadoDTOResponse update(Long id, EstadoDTO estadoDTO) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null) {
            throw new NotFoundException("Estado não encontrado para atualização. ID: " + id);
        }

        estado.setNome(estadoDTO.nome());
        estado.setSigla(estadoDTO.sigla());

        return EstadoDTOResponse.valueOf(estado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!estadoRepository.deleteById(id)) {
            throw new NotFoundException("Estado não encontrado para exclusão. ID: " + id);
        }
    }

    @Override
    public EstadoDTOResponse findById(Long id) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null) {
            throw new NotFoundException("Estado não encontrado com ID: " + id);
        }
        return EstadoDTOResponse.valueOf(estado);
    }

    @Override
    public List<EstadoDTOResponse> findAll() {
        return estadoRepository.listAll().stream()
                .map(EstadoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstadoDTOResponse> findByNome(String nome) {
        return estadoRepository.findByNome(nome).stream()
                .map(EstadoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<EstadoDTOResponse> findByPaisId(Long idPais) {
        buscarPaisOuFalhar(idPais);
        return estadoRepository.findByPais(idPais).stream()
                .map(EstadoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    // 🔹 método privado para evitar duplicação
    private Pais buscarPaisOuFalhar(Long idPais) {
        Pais pais = paisRepository.findById(idPais);
        if (pais == null) {
            throw new NotFoundException("País não encontrado para o ID: " + idPais);
        }
        return pais;
    }
}
