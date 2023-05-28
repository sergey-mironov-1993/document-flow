package org.example.repository;

import org.example.models.Document;
import org.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByIdAndIsDeletedFalse(Long id);

    Optional<Document> findByDocumentNumber(String documentNumber);

    List<Document> findByIsDeletedFalse();

    Boolean existsDocumentById(Long id);

    Boolean existsDocumentByDocumentNumber(String docNumber);

    @Query("SELECT d.isDeleted FROM #{#entityName} d WHERE d.id = ?1")
    boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} d set d.isDeleted = true, d.removedAt = ?2 where d.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} d set " +
            "d.documentName = ?2, " +
            "d.documentNumber = ?3," +
            "d.modifiedAt = ?4 where d.id = ?1")
    void setNewNameAndNumberAndModifyDate(Long id,
                                          String documentName,
                                          String documentNumber,
                                          LocalDateTime modifyDate);

    @Modifying
    @Query("update #{#entityName} d set d.user = ?1 where d.documentNumber = ?2")
    void setUserForDocument(User user, String docNumber);
}
