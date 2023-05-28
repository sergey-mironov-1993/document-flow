package org.example.services.impl;

import org.example.dto.ContractorDTO;
import org.example.dto.CredentialDTO;
import org.example.mappers.CredentialMapper;
import org.example.repository.CredentialRepository;
import org.example.services.CredentialService;
import lombok.RequiredArgsConstructor;
import org.example.exceptions.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.example.utils.AnotherUtils.*;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;
    private final CredentialMapper credentialMapper;
    private final FTSService ftsService;

    @Override
    public List<CredentialDTO> findAll() {
        return credentialRepository
                .findByIsDeletedFalse()
                .stream()
                .map(credentialMapper::toDto)
                .toList();
    }

    @Override
    public CredentialDTO findOne(Long id) {
        return credentialRepository
                .findByIdAndIsDeletedFalse(id)
                .map(credentialMapper::toDto)
                .orElseThrow(NotFoundWithThisIdException::new);
    }

    @Override
    public Boolean save(CredentialDTO credentialDTO) {
        if (credentialRepository.existsCredentialByPassportOrItn(
                credentialDTO.getPassport(),
                credentialDTO.getItn()))
            throw new PassportOrITNAlreadyExistsException();
        credentialRepository.save(credentialMapper.toEntity(credentialDTO));
        return true;
    }

    @Override
    public void update(Long id, CredentialDTO credentialDTO) {
        if (!credentialRepository.existsCredentialById(id))
            throw new NotFoundWithThisIdException();
        if (credentialRepository.existsCredentialByPassportOrItn(
                credentialDTO.getPassport(), credentialDTO.getItn()))
            throw new PassportOrITNAlreadyExistsException();
        if (hasEmptyCredential(credentialDTO))
            throw new NothingToUpdateException();
        performUpdate(id, credentialDTO);
    }

    @Override
    public void delete(Long id) {
        if (!credentialRepository.existsCredentialById(id))
            throw new NotFoundWithThisIdException();
        if (credentialRepository.isRemoved(id))
            throw new EntityAlreadyRemovedException();
        credentialRepository.setIsRemovedTrueAndRemoveDate(id, LocalDateTime.now());
    }

    public String checkEnteredDataFromFTS(ContractorDTO contractorDTO, CredentialDTO credentialDTO) {
        if (credentialDTO.getPassport().length() != 10)
            throw new PassportIsNotValidException();

        String passportCheckResult = ftsService.getInformationAboutPassport(
                credentialDTO.getPassport(),
                API_KEY);

        String itnFromFts = ftsService.getINN(
                contractorDTO.getLastName(),
                contractorDTO.getFirstName(),
                contractorDTO.getPatronymic(),
                credentialDTO.getBirthdayDate(),
                credentialDTO.getPassport(),
                API_KEY);

        if (passportCheckResult.equals(OK) && credentialDTO.getItn().equals(itnFromFts))
            return OK;

        return NOT_OK;
    }

    public Boolean existsCredentialByPassportOrItn(String passport, String itn) {
        return credentialRepository.existsCredentialByPassportOrItn(passport, itn);
    }

    private void performUpdate(Long id, CredentialDTO credentialDTO) {
        credentialRepository.updateCredentialAndModifyDate(
                        id,
                        LocalDateTime.now(),
                        credentialDTO.getText(),
                        credentialDTO.getVersion(),
                        credentialDTO.getSubjectType(),
                        credentialDTO.getPassport(),
                        credentialDTO.getBirthdayDate(),
                        credentialDTO.getItn());
    }

    private Boolean hasEmptyCredential(CredentialDTO credentialDTO) {
        return credentialDTO.getText() == null &&
                credentialDTO.getVersion() == null &&
                credentialDTO.getSubjectType() == null &&
                credentialDTO.getPassport() == null &&
                credentialDTO.getBirthdayDate() == null &&
                credentialDTO.getItn() == null;
    }
}
