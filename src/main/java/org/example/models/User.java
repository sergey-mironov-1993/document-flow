package org.example.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.enums.Role;

import java.util.List;

@Entity
@Table(name = "our_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "documents")
public class User extends AbstractModel{

    private String firstName;
    private String lastName;
    private String patronymic;
    private String login;
    private String password;
    private String email;
    private String telegram;
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Document> documents;
}
