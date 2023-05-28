package org.example.dto;

import org.example.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends AbstractDTO{
    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private String telegram;
    private Role role;
    private List<CommentDTO> comments;
}
