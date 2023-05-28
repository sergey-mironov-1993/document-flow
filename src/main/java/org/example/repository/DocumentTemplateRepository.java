package org.example.repository;

import org.example.models.DocumentField;
import org.example.models.DocumentTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentTemplateRepository extends JpaRepository<DocumentTemplate, Long> {

    Optional<DocumentTemplate> findByIdAndIsDeletedFalse(Long id);

    List<DocumentTemplate> findByIsDeletedFalse();

    Boolean existsDocumentTemplateByTemplateName(String name);

    Boolean existsDocumentTemplateById(Long id);

    @Query("SELECT dt.isDeleted FROM #{#entityName} dt WHERE dt.id = ?1")
    Boolean isRemoved(Long id);

    @Query("SELECT dt.documentFields FROM #{#entityName} dt WHERE dt.id = ?1")
    List<DocumentField> getAllDTFields(Long id);

    @Modifying
    @Query("update #{#entityName} dt set dt.isDeleted = true, dt.removedAt = ?2 where dt.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} dt set " +
            "dt.modifiedAt = ?2," +
            "dt.templateName = ?3," +
            "dt.version = ?4 where dt.id = ?1")
    void updateDocumentTemplateAndModifyDate(Long id,
                                       LocalDateTime modifyDate,
                                       String templateName,
                                       String version);
}
