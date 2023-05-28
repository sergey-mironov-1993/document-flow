package org.example.mappers;

import org.example.dto.DocumentDTO;
import org.example.models.Document;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    DocumentDTO toDto(Document document);
    Document toEntity(DocumentDTO documentDTO);
}
