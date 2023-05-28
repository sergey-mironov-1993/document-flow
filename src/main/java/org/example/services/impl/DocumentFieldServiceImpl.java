package org.example.services.impl;

import lombok.AllArgsConstructor;
import org.example.dto.DocumentFieldDTO;
import org.example.exceptions.EntityAlreadyRemovedException;
import org.example.exceptions.NotFoundObjectWithThisNameException;
import org.example.exceptions.NotFoundWithThisIdException;
import org.example.exceptions.NothingToUpdateException;
import org.example.mappers.DocumentFieldMapper;
import org.example.repository.DocumentFieldRepository;
import org.example.services.DocumentFieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class DocumentFieldServiceImpl implements DocumentFieldService {

    private final DocumentFieldRepository documentFieldRepository;
    private final DocumentFieldMapper documentFieldMapper;

    @Override
    public List<DocumentFieldDTO> findAll() {
        return documentFieldRepository
                .findByIsDeletedFalse()
                .stream()
                .map(documentFieldMapper::toDto)
                .toList();
    }

    @Override
    public DocumentFieldDTO findOne(Long id) {
        return documentFieldRepository
                .findByIdAndIsDeletedFalse(id)
                .map(documentFieldMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(DocumentFieldDTO documentFieldDTO) {
        if (documentFieldRepository.existsDocumentFieldByFieldName(documentFieldDTO.getFieldName()))
            return false;
        documentFieldRepository.save(documentFieldMapper.toEntity(documentFieldDTO));
        return true;
    }

    @Override
    public void update(Long id, DocumentFieldDTO documentFieldDTO) {
        if (!documentFieldRepository.existsDocumentFieldById(id))
            throw new NotFoundWithThisIdException();

        if (hasEmptyProperties(documentFieldDTO))
            throw new NothingToUpdateException();

        documentFieldRepository.setNewPropertiesAndModifyDate(
                id,
                LocalDateTime.now(),
                documentFieldDTO.getDefaultValue(),
                documentFieldDTO.getFieldName(),
                documentFieldDTO.getFieldType(),
                documentFieldDTO.getPlaceholder());
    }

    @Override
    public void delete(Long id) {
        if (!documentFieldRepository.existsDocumentFieldById(id))
            throw new NotFoundWithThisIdException();
        if (documentFieldRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        documentFieldRepository.setIsRemovedTrueAndRemoveDateById(id, LocalDateTime.now());
    }

    public void deleteFieldByName(String fieldName) {
        if (!documentFieldRepository.existsDocumentFieldByFieldName(fieldName))
            throw new NotFoundObjectWithThisNameException();
        documentFieldRepository.setIsRemovedTrueAndRemoveDateByFieldName(fieldName, LocalDateTime.now());
    }

    public DocumentFieldDTO getFieldByName(String documentFieldName) {
        if (documentFieldRepository.existsDocumentFieldByFieldName(documentFieldName))
            return documentFieldMapper.toDto(documentFieldRepository.findByFieldName(documentFieldName).get());
        throw new NotFoundObjectWithThisNameException();
    }

    private Boolean hasEmptyProperties(DocumentFieldDTO documentFieldDTO) {
        return documentFieldDTO.getDefaultValue() == null &&
                documentFieldDTO.getFieldName() == null &&
                documentFieldDTO.getFieldType() == null &&
                documentFieldDTO.getPlaceholder() == null;
    }
}
