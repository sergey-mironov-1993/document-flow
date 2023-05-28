package org.example.repository;

import org.example.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndIsDeletedFalse(Long id);

    List<Comment> findByIsDeletedFalse();

    Boolean existsCommentById(Long id);

    Boolean existsCommentByText(String text);

    @Query("SELECT c.isDeleted FROM #{#entityName} c WHERE c.id = ?1")
    Boolean isRemoved(Long id);

    @Modifying
    @Query("update #{#entityName} c set c.isDeleted = true, c.removedAt = ?2 where c.id = ?1")
    void setIsRemovedTrueAndRemoveDate(Long id, LocalDateTime removeDate);

    @Modifying
    @Query("update #{#entityName} c set c.text = ?2, c.modifiedAt = ?3 where c.id = ?1")
    void setNewTextAndModifyDate(Long id, String newCommentText, LocalDateTime modifyDate);
}
