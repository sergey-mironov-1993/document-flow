package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"documents", "credential"})
public class Contractor extends AbstractModel{

    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String telegram;
    private String email;
    private String country;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Credential credential;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contractor", fetch = FetchType.EAGER)
    private List<Document> documents;
}
