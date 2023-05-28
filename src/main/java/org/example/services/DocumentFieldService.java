package org.example.services;

import org.example.dto.DocumentFieldDTO;

import java.util.List;

public interface DocumentFieldService extends CrudOperationService<DocumentFieldDTO, Long>{

    @Override
    List<DocumentFieldDTO> findAll();

    @Override
    DocumentFieldDTO findOne(Long id);

    @Override
    Boolean save(DocumentFieldDTO documentFieldDTO);

    @Override
    void update(Long id, DocumentFieldDTO documentFieldDTO);

    @Override
    void delete(Long id);
}
