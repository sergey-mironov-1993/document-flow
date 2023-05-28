package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.CommentDTO;
import org.example.exceptions.EntityAlreadyRemovedException;
import org.example.exceptions.NotFoundWithThisIdException;
import org.example.exceptions.NothingToUpdateException;
import org.example.mappers.CommentMapper;
import org.example.repository.CommentRepository;
import org.example.services.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentDTO> findAll() {
        return commentRepository
                .findByIsDeletedFalse()
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentDTO findOne(Long id) {
        return commentRepository
                .findByIdAndIsDeletedFalse(id)
                .map(commentMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(CommentDTO commentDTO) {
        if (commentRepository.existsCommentByText(commentDTO.getText()))
            return false;
        commentRepository.save(commentMapper.toEntity(commentDTO));
        return true;
    }

    @Override
    public void update(Long id, CommentDTO commentDTO) {
        if (!commentRepository.existsCommentById(id))
            throw new NotFoundWithThisIdException();
        if (commentDTO.getText() == null)
            throw new NothingToUpdateException();
        commentRepository.setNewTextAndModifyDate(id, commentDTO.getText(), LocalDateTime.now());
    }

    @Override
    public void delete(Long id) {
        if (!commentRepository.existsCommentById(id))
            throw new NotFoundWithThisIdException();
        if (commentRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        commentRepository.setIsRemovedTrueAndRemoveDate(id, LocalDateTime.now());
    }
}