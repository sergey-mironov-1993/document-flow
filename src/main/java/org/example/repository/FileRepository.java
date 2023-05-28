package org.example.repository;

import org.example.models.OurFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<OurFile, Long> { }
