package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.DocumentDTO;
import org.example.exceptions.*;
import org.example.mappers.DocumentMapper;
import org.example.models.Document;
import org.example.models.User;
import org.example.repository.DocumentRepository;
import org.example.services.DocumentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.utils.AnotherUtils.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final ContractorServiceImpl contractorService;
    private final UserServiceImpl userServiceImpl;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;

    @Override
    public List<DocumentDTO> findAll() {
        return documentRepository
                .findByIsDeletedFalse()
                .stream()
                .map(documentMapper::toDto)
                .toList();
    }

    @Override
    public DocumentDTO findOne(Long id) {
        return documentRepository
                .findByIdAndIsDeletedFalse(id)
                .map(documentMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(DocumentDTO documentDTO) {
        return null;
    }

    @Transactional
    public String saveDocumentAndFile(DocumentDTO documentToSave, MultipartFile file) {

        String docNumber = documentToSave.getDocumentNumber();

        if (isDouble(docNumber))
            return DOCUMENT_NOT_SAVED;

        String result = contractorService.checkInformationAboutContractor(documentToSave.getContractor());

        if (!result.equals(OK))
            return result;

        Document newDocument = documentMapper.toEntity(documentToSave);
        String userLogin = newDocument.getUser().getLogin();
        Boolean existsUser = userServiceImpl.existsUserByLogin(userLogin);

        fileService.saveFileAndSetDocument(file, newDocument);

        if (existsUser)
            return saveDocumentWithOldUser(newDocument, userLogin, docNumber);

        return saveDocumentWithNewUser(newDocument);
    }

    @Override
    public void update(Long id, DocumentDTO documentDTO) {
        if (!documentRepository.existsDocumentById(id))
            throw new NotFoundWithThisIdException();
        if(hasDocumentDTOEmptyProperties(documentDTO))
            throw new NothingToUpdateException();
        if (documentRepository.existsDocumentByDocumentNumber(documentDTO.getDocumentNumber()))
            throw new EntityAlreadyExistsException();
        documentRepository.setNewNameAndNumberAndModifyDate(
                id,
                documentDTO.getDocumentName(),
                documentDTO.getDocumentNumber(),
                LocalDateTime.now());
    }

    @Override
    public void delete(Long id) {
        if (!documentRepository.existsDocumentById(id))
            throw new NotFoundWithThisIdException();
        if (documentRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        Document docToRemove = documentRepository.findById(id).get();
        docToRemove.setIsDeleted(true);
        docToRemove.getFiles().forEach(file -> file.setIsDeleted(true));
        docToRemove.getDocumentTemplate().getDocumentFields().forEach(field -> field.setIsDeleted(true));
        fileService.deleteFilesFromRepo(docToRemove.getFiles());
    }

    private Boolean isDouble(String docNumber) {
        return documentRepository.existsDocumentByDocumentNumber(docNumber);
    }

    private String saveDocumentWithOldUser(Document newDocument, String userLogin, String docNumber) {
        newDocument.setUser(null);
        documentRepository.save(newDocument);
        User user = userServiceImpl.findUserByLogin(userLogin);
        documentRepository.setUserForDocument(user, docNumber);
        return DOCUMENT_SAVED;
    }

    private String saveDocumentWithNewUser(Document newDocument) {
        User docUser = newDocument.getUser();
        String encodedPassword = passwordEncoder.encode(docUser.getPassword());
        docUser.setPassword(encodedPassword);
        documentRepository.save(newDocument);
        return DOCUMENT_SAVED;
    }

    private Boolean hasDocumentDTOEmptyProperties(DocumentDTO documentDTO) {
        return documentDTO.getDocumentName() == null &&
                documentDTO.getDocumentNumber() == null;
    }
}
