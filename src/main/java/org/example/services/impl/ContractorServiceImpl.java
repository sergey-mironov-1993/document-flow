package org.example.services.impl;

import org.example.dto.ContractorDTO;
import org.example.exceptions.EntityAlreadyRemovedException;
import org.example.exceptions.NotFoundWithThisIdException;
import org.example.exceptions.PassportOrITNAlreadyExistsException;
import org.example.exceptions.EmptyEntityDataException;
import org.example.mappers.ContractorMapper;
import org.example.repository.ContractorRepository;
import org.example.services.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.utils.AnotherUtils.*;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final CredentialServiceImpl credentialServiceImpl;
    private final ContractorRepository contractorRepository;
    private final ContractorMapper contractorMapper;

    @Override
    public Boolean save(ContractorDTO contractorDTO) {
        if (credentialServiceImpl.existsCredentialByPassportOrItn(
                contractorDTO.getCredential().getPassport(),
                contractorDTO.getCredential().getItn()))
            throw new PassportOrITNAlreadyExistsException();
        contractorRepository.save(contractorMapper.toEntity(contractorDTO));
        return true;
    }

    @Override
    public List<ContractorDTO> findAll() {
        return contractorRepository
                .findByIsDeletedFalse()
                .stream()
                .map(contractorMapper::toDto)
                .toList();
    }

    @Override
    public ContractorDTO findOne(Long id) {
        return contractorRepository
                .findByIdAndIsDeletedFalse(id)
                .map(contractorMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public void update(Long id, ContractorDTO contractorDTO) {
        if (!contractorRepository.existsContractorById(id))
            throw new NotFoundWithThisIdException();
        if (hasContractorDTOEmptyProperties(contractorDTO))
            throw new EmptyEntityDataException();
        if (credentialServiceImpl.existsCredentialByPassportOrItn(
                contractorDTO.getCredential().getPassport(),
                contractorDTO.getCredential().getItn()))
            throw new PassportOrITNAlreadyExistsException();
        contractorRepository.updateContractorAndModifyDate(
                id,
                LocalDateTime.now(),
                contractorDTO.getFirstName(),
                contractorDTO.getLastName(),
                contractorDTO.getPatronymic(),
                contractorDTO.getPhoneNumber(),
                contractorDTO.getTelegram(),
                contractorDTO.getEmail(),
                contractorDTO.getCountry());
    }

    @Override
    public void delete(Long id) {
        if (!contractorRepository.existsContractorById(id))
            throw new NotFoundWithThisIdException();
        if (contractorRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        contractorRepository.setIsRemovedTrueAndRemoveDate(id, LocalDateTime.now());
    }

    public String checkInformationAboutContractor(ContractorDTO contractorDTO) {
        String result = credentialServiceImpl.checkEnteredDataFromFTS(
                contractorDTO,
                contractorDTO.getCredential());
        if (result.equals(OK))
            return OK;

        return INVALID_CONTRACTOR_DATA;
    }

    private Boolean hasContractorDTOEmptyProperties(ContractorDTO contractorDTO) {
        return contractorDTO.getFirstName() == null &&
                contractorDTO.getLastName() == null &&
                contractorDTO.getPatronymic() == null &&
                contractorDTO.getPhoneNumber() == null &&
                contractorDTO.getTelegram() == null &&
                contractorDTO.getEmail() == null &&
                contractorDTO.getCountry() == null;
    }
}
