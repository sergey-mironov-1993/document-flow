package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.enums.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentFieldDTO extends AbstractDTO{
    private String defaultValue;
    private String fieldName;
    private FieldType fieldType;
    private String placeholder;
}
