package org.example.mappers;

import org.example.dto.DocumentFieldDTO;
import org.example.models.DocumentField;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentFieldMapper {
    DocumentFieldDTO toDto(DocumentField documentField);
    DocumentField toEntity(DocumentFieldDTO documentFieldDTO);
}
