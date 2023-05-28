package org.example.services;

import org.example.dto.DocumentDTO;

import java.util.List;

public interface DocumentService extends CrudOperationService<DocumentDTO, Long>{

    @Override
    List<DocumentDTO> findAll();

    @Override
    DocumentDTO findOne(Long id);

    @Override
    Boolean save(DocumentDTO documentDTO);

    @Override
    void update(Long id, DocumentDTO documentDTO);

    @Override
    void delete(Long id);
}
