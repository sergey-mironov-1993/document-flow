package org.example.services;

import org.example.dto.DocumentTemplateDTO;

import java.util.List;

public interface DocumentTemplateService extends CrudOperationService<DocumentTemplateDTO, Long>{

    @Override
    List<DocumentTemplateDTO> findAll();

    @Override
    DocumentTemplateDTO findOne(Long id);

    @Override
    Boolean save(DocumentTemplateDTO documentTemplateDTO);

    @Override
    void update(Long id, DocumentTemplateDTO documentTemplateDTO);

    @Override
    void delete(Long id);
}
