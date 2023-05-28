package org.example.repository;

import org.example.enums.SubjectType;
import org.example.models.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> findByIdAndIsDeletedFalse(Long id);
    List<Credential> findByIsDeletedFalse();
    Boolean existsCredentialById(Long id);
    Boolean existsCredentialByPassportOrItn(String passport, String itn);

    @Query("SELECT c.isDeleted FROM #{#entityName} c WHERE c.id = ?1")
    Boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} c set " +
            "c.modifiedAt = ?2," +
            "c.text = ?3," +
            "c.version = ?4," +
            "c.subjectType = ?5," +
            "c.passport = ?6," +
            "c.birthdayDate = ?7," +
            "c.itn = ?8 where c.id = ?1")
    void updateCredentialAndModifyDate(Long id,
                                       LocalDateTime modifyDate,
                                       String text,
                                       String version,
                                       SubjectType subjectType,
                                       String passportNumberAndSeries,
                                       String birthdayDate,
                                       String itn);

    @Modifying
    @Query("update #{#entityName} c set c.isDeleted = true, c.removedAt = ?2 where c.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);
}
