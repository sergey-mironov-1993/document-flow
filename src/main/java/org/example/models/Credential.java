package org.example.models;

import org.example.enums.SubjectType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = "contractor")
public class Credential extends AbstractModel{

    private String text;
    private String version;
    private SubjectType subjectType;
    private String passport;
    private String birthdayDate;
    private String itn;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "credential")
    private Contractor contractor;

}
