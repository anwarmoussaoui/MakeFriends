package com.workshop.back.Repository;

import com.workshop.back.Entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepository extends JpaRepository<FileData,Long> {
    Optional<FileData> findFirstByName(String fileName);
}