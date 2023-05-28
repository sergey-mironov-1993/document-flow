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
public class DocumentDTO extends AbstractDTO{
    private String documentName;
    private String documentNumber;
    private List<CommentDTO> comments;
    private ContractorDTO contractor;
    private DocumentTemplateDTO documentTemplate;
    private UserDTO user;
}
