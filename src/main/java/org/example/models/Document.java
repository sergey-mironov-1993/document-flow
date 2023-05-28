package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"contractor", "documentTemplate", "user", "files"})
public class Document extends AbstractModel {

    private String documentName;
    private String documentNumber;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Contractor contractor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private DocumentTemplate documentTemplate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OurFile> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private User user;
}
