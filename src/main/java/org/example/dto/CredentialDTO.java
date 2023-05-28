package org.example.dto;

import org.example.enums.SubjectType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CredentialDTO extends AbstractDTO{
    private String text;
    private String version;
    private SubjectType subjectType;
    private String passport;
    private String birthdayDate;
    private String itn;
}
