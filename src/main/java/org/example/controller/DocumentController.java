package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.DocumentDTO;
import org.example.repository.DocumentRepository;
import org.example.services.impl.DocumentServiceImpl;
import org.example.services.impl.DocumentTemplateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentServiceImpl documentService;
    private final DocumentTemplateServiceImpl documentTemplateService;
    private final DocumentRepository documentRepository;

    @PostMapping("/saveDocumentAndFile")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN, " +
            "@authorities.ROLE_USER, " +
            "@authorities.ROLE_CONTRACTOR)")
    public String saveDocumentAndFile(
            @RequestPart("documentDTO") DocumentDTO documentDTO,
            @RequestPart("file")MultipartFile file) {
        return documentService.saveDocumentAndFile(documentDTO, file);
    }

    @GetMapping("/getAllDocuments")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN," +
            "@authorities.ROLE_USER," +
            "@authorities.ROLE_CONTRACTOR)")
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        return ResponseEntity.ok().body(documentService.findAll());
    }

    @GetMapping("/getDocument/{id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN," +
            "@authorities.ROLE_USER," +
            "@authorities.ROLE_CONTRACTOR)")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(documentService.findOne(id));
    }

    @PatchMapping("/updateDocument/{id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN)")
    public ResponseEntity<HttpStatus> update(@RequestBody DocumentDTO documentDTO,
                                             @PathVariable("id") Long id) {
        documentService.update(id, documentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/deleteDocument/{id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN)")
    public ResponseEntity<HttpStatus> deleteDocumentAndAllHisFiles(@PathVariable("id") Long id) {
        documentService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/isDeleted/{id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN)")
    public ResponseEntity<Boolean> isDeleted(@PathVariable("id") Long id) {
        return ResponseEntity.ok(documentRepository.isRemoved(id));
    }

    @PutMapping("/{doc_template_id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN," +
            "@authorities.ROLE_USER," +
            "@authorities.ROLE_CONTRACTOR)")
    public ResponseEntity<String> addNewFieldInDocumentTemplate(@PathVariable Long doc_template_id,
                                                                @RequestBody String documentFieldName) {
        return ResponseEntity.ok().body(documentTemplateService.addNewField(doc_template_id, documentFieldName));
    }

    @DeleteMapping("/deleteDocField/{template_id}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN)")
    public ResponseEntity<String> deleteFieldFromDocumentTemplateByName(@PathVariable Long template_id,
                                                                        @RequestBody String fieldName) {
        return ResponseEntity.ok().body(documentTemplateService.deleteFieldFromDocumentTemplate(template_id, fieldName));
    }
}
