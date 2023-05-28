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
public class DocumentTemplateDTO extends AbstractDTO{
    private String templateName;
    private String version;
    private List<CommentDTO> comments;
    private List<DocumentFieldDTO> documentFields;
}
