package org.example.repository;

import org.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndIsDeletedFalse(Long id);

    List<User> findByIsDeletedFalse();

    Optional<User> findByLogin(String login);

    Boolean existsUserById(Long id);

    Boolean existsUserByTelegram(String telegram);

    Boolean existsUserByLogin(String login);

    @Query("SELECT u.isDeleted FROM #{#entityName} u WHERE u.id = ?1")
    Boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} u set u.isDeleted = true, u.removedAt = ?2 where u.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} u set u.email = ?2, u.modifiedAt = ?3 where u.id = ?1")
    void setNewEmailAndModifyDate(Long id, String newEmail, LocalDateTime modifyDate);

}
