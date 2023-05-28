package org.example.mappers;

import org.example.dto.DocumentTemplateDTO;
import org.example.models.DocumentTemplate;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentTemplateMapper {
    DocumentTemplateDTO toDto(DocumentTemplate documentTemplate);
    DocumentTemplate toEntity(DocumentTemplateDTO documentTemplateDTO);
}
