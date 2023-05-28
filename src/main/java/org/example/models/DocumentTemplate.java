package org.example.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"documents", "documentFields"})
public class DocumentTemplate extends AbstractModel {

    private String templateName;
    private String version;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "documentTemplate")
    private List<Document> documents;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<DocumentField> documentFields;

}
