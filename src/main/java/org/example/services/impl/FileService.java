package org.example.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.exceptions.FileNotFoundInStorageException;
import org.example.models.Document;
import org.example.models.OurFile;
import org.example.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.example.utils.AnotherUtils.DIRECTORY_PATH;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public void saveFileAndSetDocument(MultipartFile file, Document document) {
        String uuidFile = UUID.randomUUID().toString();
        Path path = Paths.get(DIRECTORY_PATH + uuidFile + "_" + file.getOriginalFilename());
        try {
            OurFile ourFile = buildFile(file, uuidFile);
            fileRepository.save(ourFile);
            document.setFiles(Collections.singletonList(ourFile));
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFilesFromRepo (List<OurFile> docsToDelete) {
        docsToDelete.forEach(doc -> {
            Path fileToDelete = Paths.get(DIRECTORY_PATH + doc.getNameForDirectory());
            try {
                System.out.println(doc.getNameForDirectory());
                Files.delete(fileToDelete);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public File getFile(String fileName) {
        Path path = Paths.get(DIRECTORY_PATH + "/" + fileName);
        File fileFromStorage = new File(path.toUri());
        if (fileFromStorage.exists())
            return fileFromStorage;
        else
            throw new FileNotFoundInStorageException();
    }

    private static OurFile buildFile(MultipartFile multipartFile, String uuidFile) throws IOException {
        String originalNameForDB = uuidFile + "_" + multipartFile.getOriginalFilename();
        return OurFile.builder()
                .nameForDirectory(originalNameForDB)
                .originalFileName(multipartFile.getOriginalFilename())
                .contentType(multipartFile.getContentType())
                .size(multipartFile.getSize())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }
}
