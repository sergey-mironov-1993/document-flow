package org.example.mappers;

import org.example.dto.CredentialDTO;
import org.example.models.Credential;
import org.mapstruct.Mapper;

@Mapper
public interface CredentialMapper {
    CredentialDTO toDto(Credential credential);
    Credential toEntity(CredentialDTO credentialDto);
}
