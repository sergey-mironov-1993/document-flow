package org.example.repository;

import org.example.models.Contractor;
import org.example.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContractorRepository extends JpaRepository<Contractor, Long> {

    Optional<Contractor> findByIdAndIsDeletedFalse(Long id);

    List<Contractor> findByIsDeletedFalse();

    Boolean existsContractorById(Long id);

    @Query("SELECT c.isDeleted FROM #{#entityName} c WHERE c.id = ?1")
    Boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} c set c.isDeleted = true, c.removedAt = ?2 where c.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} c set " +
            "c.modifiedAt = ?2," +
            "c.firstName = ?3," +
            "c.lastName = ?4," +
            "c.patronymic = ?5," +
            "c.phoneNumber = ?6," +
            "c.telegram = ?7," +
            "c.email = ?8," +
            "c.country = ?9 where c.id = ?1")
    void updateContractorAndModifyDate(Long id,
                                       LocalDateTime modifyDate,
                                       String firstName,
                                       String lastName,
                                       String patronymic,
                                       String phoneNumber,
                                       String telegram,
                                       String email,
                                       String country);
}
