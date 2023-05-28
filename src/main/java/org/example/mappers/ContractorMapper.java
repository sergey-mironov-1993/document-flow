package org.example.mappers;

import org.example.dto.ContractorDTO;
import org.example.models.Contractor;
import org.mapstruct.Mapper;

@Mapper
public interface ContractorMapper {
    ContractorDTO toDto(Contractor contractor);
    Contractor toEntity(ContractorDTO contractorDto);
}
