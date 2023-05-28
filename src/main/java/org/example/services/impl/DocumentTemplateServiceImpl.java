package org.example.services.impl;

import lombok.AllArgsConstructor;
import org.example.dto.DocumentTemplateDTO;
import org.example.enums.FieldType;
import org.example.exceptions.*;
import org.example.mappers.DocumentTemplateMapper;
import org.example.models.DocumentField;
import org.example.repository.DocumentTemplateRepository;
import org.example.services.DocumentTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.utils.AnotherUtils.NO_SUCH_FIELD_IN_THE_TEMPLATE;
import static org.example.utils.AnotherUtils.THE_FIELD_HAS_BEEN_DELETED;

@Service
@Transactional
@AllArgsConstructor
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

    private final DocumentTemplateRepository documentTemplateRepository;
    private final DocumentTemplateMapper documentTemplateMapper;

    public List<DocumentTemplateDTO> findAll() {
        return documentTemplateRepository
                .findByIsDeletedFalse()
                .stream()
                .map(documentTemplateMapper::toDto).toList();
    }

    @Override
    public DocumentTemplateDTO findOne(Long id) {
        return documentTemplateRepository
                .findByIdAndIsDeletedFalse(id)
                .map(documentTemplateMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(DocumentTemplateDTO documentTemplateDTO) {
        if (documentTemplateRepository.existsDocumentTemplateByTemplateName(documentTemplateDTO.getTemplateName()))
            return false;
        documentTemplateRepository.save(documentTemplateMapper.toEntity(documentTemplateDTO));
        return true;
    }

    @Override
    public void update(Long id, DocumentTemplateDTO documentTemplateDTO) {
        if (!documentTemplateRepository.existsDocumentTemplateById(id))
            throw new NotFoundWithThisIdException();

        if (hasDocumentFieldDTOEmptyProperties(documentTemplateDTO))
            throw new NothingToUpdateException();

        if (documentTemplateRepository.existsDocumentTemplateByTemplateName(
                        documentTemplateDTO.getTemplateName()))
            throw new EntityAlreadyExistsException();

        documentTemplateRepository.updateDocumentTemplateAndModifyDate(
                id,
                LocalDateTime.now(),
                documentTemplateDTO.getTemplateName(),
                documentTemplateDTO.getVersion()
        );
    }

    @Override
    public void delete(Long id) {
        if (!documentTemplateRepository.existsDocumentTemplateById(id))
            throw new NotFoundWithThisIdException();
        if (documentTemplateRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        documentTemplateRepository.setIsRemovedTrueAndRemoveDate(id, LocalDateTime.now());
    }

    @Transactional
    public String deleteFieldFromDocumentTemplate(Long template_id, String fieldName) {
        if (!documentTemplateRepository.existsDocumentTemplateById(template_id))
            throw new NotFoundWithThisIdException();
        if (documentTemplateRepository.findById(template_id).get().getDocumentFields()
                .removeIf(documentField -> documentField.getFieldName().equals(fieldName)))
            return THE_FIELD_HAS_BEEN_DELETED;
        return NO_SUCH_FIELD_IN_THE_TEMPLATE;
    }

    @Transactional
    public String addNewField(Long doc_template_id, String documentFieldName) {
        if (!documentTemplateRepository.existsDocumentTemplateById(doc_template_id))
            throw new NotFoundWithThisIdException();
        documentTemplateRepository.findById(doc_template_id).get().getDocumentFields()
                .add(DocumentField.builder()
                        .createdAt(LocalDateTime.now())
                        .defaultValue("Some new field")
                        .fieldName(documentFieldName)
                        .fieldType(FieldType.NUMERIC)
                        .placeholder("Удачно добавилось")
                        .isDeleted(false)
                        .build());
        return "Поле успешно добавлено";
    }

    public Boolean existsDocumentTemplateByName(String name) {
        if (documentTemplateRepository.existsDocumentTemplateByTemplateName(name))
            return true;
        throw new NotFoundObjectWithThisNameException();
    }

    private Boolean hasDocumentFieldDTOEmptyProperties(DocumentTemplateDTO documentTemplateDTO) {
        return documentTemplateDTO.getTemplateName() == null &&
                documentTemplateDTO.getVersion() == null;
    }
}
