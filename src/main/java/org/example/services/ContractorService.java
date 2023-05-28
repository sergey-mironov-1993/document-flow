package org.example.services;

import org.example.dto.ContractorDTO;

import java.util.List;

public interface ContractorService extends CrudOperationService<ContractorDTO, Long>{

    @Override
    List<ContractorDTO> findAll();

    @Override
    ContractorDTO findOne(Long id);

    @Override
    Boolean save(ContractorDTO contractorDTO);

    @Override
    void update(Long id, ContractorDTO contractorDTO);

    @Override
    void delete(Long id);
}
