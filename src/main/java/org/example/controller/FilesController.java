package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.services.impl.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FilesController {

    private final FileService fileService;

//    @PutMapping(value = "/saveFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.MULTIPART_MIXED_VALUE})
//    @PreAuthorize("hasAnyAuthority(" +
//            "@authorities.ROLE_ADMIN," +
//            "@authorities.ROLE_SUPER_ADMIN," +
//            "@authorities.ROLE_USER," +
//            "@authorities.ROLE_CONTRACTOR)")
//    public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {
//        fileService.saveFile(file);
//        return ResponseEntity.ok().body("File saved successfully.");
//    }

    @GetMapping("/getFile/{file_name}")
    @PreAuthorize("hasAnyAuthority(" +
            "@authorities.ROLE_ADMIN," +
            "@authorities.ROLE_SUPER_ADMIN," +
            "@authorities.ROLE_USER," +
            "@authorities.ROLE_CONTRACTOR)")
    public ResponseEntity<File> getFile(@PathVariable String file_name) {
        return ResponseEntity.ok().body(fileService.getFile(file_name));
    }
}
