package org.example.repository;

import org.example.enums.FieldType;
import org.example.models.DocumentField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentFieldRepository extends JpaRepository<DocumentField, Long> {

    Optional<DocumentField> findByIdAndIsDeletedFalse(Long id);

    List<DocumentField> findByIsDeletedFalse();

    Optional<DocumentField> findByFieldName(String documentFieldName);

    Boolean existsDocumentFieldById(Long id);

    Boolean existsDocumentFieldByFieldName(String documentFieldName);

    @Query("SELECT df.isDeleted FROM #{#entityName} df WHERE df.id = ?1")
    Boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} df set " +
            "df.isDeleted = true, " +
            "df.removedAt = ?2 where df.id = ?1")
    void setIsRemovedTrueAndRemoveDateById(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} df set " +
            "df.isDeleted = true, " +
            "df.removedAt = ?2 where df.fieldName = ?1")
    void setIsRemovedTrueAndRemoveDateByFieldName(String fieldName, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} df set " +
            "df.modifiedAt = ?2," +
            "df.defaultValue = ?3," +
            "df.fieldName = ?4," +
            "df.fieldType = ?5," +
            "df.placeholder = ?6 where df.id = ?1")
    void setNewPropertiesAndModifyDate(Long id,
                                 LocalDateTime modifyDate,
                                 String defaultValue,
                                 String fieldName,
                                 FieldType fieldType,
                                 String placeholder);
}
