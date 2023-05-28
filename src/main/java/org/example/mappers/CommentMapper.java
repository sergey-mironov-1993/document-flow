package org.example.mappers;

import org.example.dto.CommentDTO;
import org.example.models.Comment;
import org.mapstruct.Mapper;

@Mapper
public interface CommentMapper {
    CommentDTO toDto(Comment comment);
    Comment toEntity(CommentDTO commentDTO);
}
