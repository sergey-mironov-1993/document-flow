package org.example.models;

import org.example.enums.FieldType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DocumentField extends AbstractModel{
    private String defaultValue;
    private String fieldName;
    private FieldType fieldType;
    private String placeholder;
}
