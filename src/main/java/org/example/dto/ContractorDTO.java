package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContractorDTO extends AbstractDTO{
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String telegram;
    private String email;
    private String country;
    private List<CommentDTO> comments;
    private CredentialDTO credential;
}
