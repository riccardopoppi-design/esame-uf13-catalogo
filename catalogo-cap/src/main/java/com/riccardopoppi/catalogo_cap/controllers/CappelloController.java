package com.riccardopoppi.catalogo_cap.controllers;

import com.riccardopoppi.catalogo_cap.domain.Cappello;
import com.riccardopoppi.catalogo_cap.dto.APIResponse;
import com.riccardopoppi.catalogo_cap.dto.CappelloRequestDTO;
import com.riccardopoppi.catalogo_cap.dto.CappelloResponseDTO;
import com.riccardopoppi.catalogo_cap.services.CappelloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cappelli")
public class CappelloController {

    @Autowired
    private CappelloService cappelloService;

    private CappelloResponseDTO mapToDTO(Cappello cappello) {
        CappelloResponseDTO dto = new CappelloResponseDTO();
        dto.setId(cappello.getId());
        dto.setCodice(cappello.getCodice());
        dto.setNome(cappello.getNome());
        dto.setMarca(cappello.getMarca());
        dto.setTaglia(cappello.getTaglia());
        dto.setAnno(cappello.getAnno());
        dto.setPrezzo(cappello.getPrezzo());
        dto.setImmagine(cappello.getImmagine());
        return dto;
    }

    private Cappello mapToEntity(CappelloRequestDTO dto) {
        Cappello cappello = new Cappello();
        cappello.setCodice(dto.getCodice());
        cappello.setNome(dto.getNome());
        cappello.setMarca(dto.getMarca());
        cappello.setTaglia(dto.getTaglia());
        cappello.setAnno(dto.getAnno());
        cappello.setPrezzo(dto.getPrezzo());
        cappello.setImmagine(dto.getImmagine());
        return cappello;
    }

    @GetMapping
    public APIResponse<List<CappelloResponseDTO>> getAll() {
        List<CappelloResponseDTO> lista = cappelloService.findAll(Sort.by("nome").ascending())
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return APIResponse.success(lista);
    }

    @GetMapping("/{id}")
    public APIResponse<CappelloResponseDTO> getById(@PathVariable UUID id) {
        Cappello cappello = cappelloService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cappello non trovato"));
        return APIResponse.success(mapToDTO(cappello));
    }

    @PostMapping
    public APIResponse<CappelloResponseDTO> create(@Valid @RequestBody CappelloRequestDTO dto) {
        Cappello entity = mapToEntity(dto);
        Cappello salvato = cappelloService.save(entity);
        return APIResponse.success(mapToDTO(salvato));
    }

    @PutMapping("/{id}")
    public APIResponse<CappelloResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody CappelloRequestDTO dto) {
        Cappello esistente = cappelloService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cappello non trovato"));
        
        esistente.setCodice(dto.getCodice());
        esistente.setNome(dto.getNome());
        esistente.setMarca(dto.getMarca());
        esistente.setTaglia(dto.getTaglia());
        esistente.setAnno(dto.getAnno());
        esistente.setPrezzo(dto.getPrezzo());
        esistente.setImmagine(dto.getImmagine());

        Cappello aggiornato = cappelloService.save(esistente);
        return APIResponse.success(mapToDTO(aggiornato));
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> delete(@PathVariable UUID id) {
        cappelloService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cappello non trovato"));
        
        cappelloService.deleteById(id);
        return APIResponse.success("Cappello eliminato con successo");
    }
}