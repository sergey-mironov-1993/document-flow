package org.example.services;

import org.example.dto.CommentDTO;

import java.util.List;

public interface CommentService extends CrudOperationService<CommentDTO, Long>{

    @Override
    List<CommentDTO> findAll();

    @Override
    CommentDTO findOne(Long id);

    @Override
    Boolean save(CommentDTO commentDTO);

    @Override
    void update(Long id, CommentDTO commentDTO);

    @Override
    void delete(Long id);
}
