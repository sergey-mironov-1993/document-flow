package org.example.exceptionHandler;

import org.example.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAPIHandler {

    @ExceptionHandler(NotFoundWithThisIdException.class)
    public ResponseEntity<NotFoundWithThisIdException> notFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundWithThisIdException());
    }

    @ExceptionHandler(EntityAlreadyRemovedException.class)
    public ResponseEntity<EntityAlreadyRemovedException> alreadyRemovedException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityAlreadyRemovedException());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<EntityAlreadyExistsException> alreadyExistsException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EntityAlreadyExistsException());
    }

    @ExceptionHandler(PassportOrITNAlreadyExistsException.class)
    public ResponseEntity<PassportOrITNAlreadyExistsException> passportAndItnException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PassportOrITNAlreadyExistsException());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<EmptyDocumentListException> emptyDocumentListException() {
        return ResponseEntity.status(600).body(new EmptyDocumentListException());
    }

    @ExceptionHandler(DocumentAlreadySavedException.class)
    public ResponseEntity<DocumentAlreadySavedException> documentAlreadySavedException() {
        return ResponseEntity.status(700).body(new DocumentAlreadySavedException());
    }

    @ExceptionHandler(NotFoundObjectWithThisNameException.class)
    public ResponseEntity<NotFoundObjectWithThisNameException> notFoundObjectWithThisNameException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundObjectWithThisNameException());
    }

    @ExceptionHandler(DataEnteredIncorrectlyException.class)
    public ResponseEntity<DataEnteredIncorrectlyException> dataEnteredIncorrectlyException() {
        return ResponseEntity.status(800)
                .body(new DataEnteredIncorrectlyException());
    }

    @ExceptionHandler(IncorrectITNException.class)
    public ResponseEntity<IncorrectITNException> incorrectITNException() {
        return ResponseEntity.status(801)
                .body(new IncorrectITNException());
    }

    @ExceptionHandler(ITNNotFoundException.class)
    public ResponseEntity<ITNNotFoundException> itnNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ITNNotFoundException());
    }

    @ExceptionHandler(ITNIsNotValidException.class)
    public ResponseEntity<ITNIsNotValidException> notSelfEmployedOrEntrepreneurException() {
        return ResponseEntity.status(803)
                .body(new ITNIsNotValidException());
    }

    @ExceptionHandler(PassportIsNotValidException.class)
    public ResponseEntity<PassportIsNotValidException> passportIsNotValidException() {
        return ResponseEntity.status(802)
                .body(new PassportIsNotValidException());
    }

    @ExceptionHandler(FieldWithThisNameNotFoundException.class)
    public ResponseEntity<FieldWithThisNameNotFoundException> fieldWithThisNameNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FieldWithThisNameNotFoundException());
    }

    @ExceptionHandler(NotSelfEmploymentOrIndividualException.class)
    public ResponseEntity<NotSelfEmploymentOrIndividualException> notSelfEmploymentOrIndividualException() {
        return ResponseEntity.status(900)
                .body(new NotSelfEmploymentOrIndividualException());
    }

    @ExceptionHandler(FieldNotFillException.class)
    public ResponseEntity<FieldNotFillException> fieldNotFillException() {
        return ResponseEntity.status(601)
                .body(new FieldNotFillException());
    }

    @ExceptionHandler(NotFoundDocumentTemplateWithThisName.class)
    public ResponseEntity<NotFoundDocumentTemplateWithThisName> notFoundDocumentTemplateWithThisName() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new NotFoundDocumentTemplateWithThisName());
    }

    @ExceptionHandler(NotFoundUserWithThisTelegram.class)
    public ResponseEntity<NotFoundUserWithThisTelegram> notFoundUserWithThisTelegram() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new NotFoundUserWithThisTelegram());
    }

    @ExceptionHandler(FileNotFoundInStorageException.class)
    public ResponseEntity<FileNotFoundInStorageException> fileNotFoundInStorageException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FileNotFoundInStorageException());
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<DocumentNotFoundException> documentNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new DocumentNotFoundException());
    }
}
