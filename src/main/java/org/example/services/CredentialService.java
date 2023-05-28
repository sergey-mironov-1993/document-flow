package org.example.services;

import org.example.dto.CredentialDTO;

import java.util.List;

public interface CredentialService extends CrudOperationService<CredentialDTO, Long>{

    @Override
    List<CredentialDTO> findAll();

    @Override
    CredentialDTO findOne(Long id);

    @Override
    Boolean save(CredentialDTO credentialDTO);

    @Override
    void update(Long id, CredentialDTO credentialDTO);

    @Override
    void delete(Long id);
}
