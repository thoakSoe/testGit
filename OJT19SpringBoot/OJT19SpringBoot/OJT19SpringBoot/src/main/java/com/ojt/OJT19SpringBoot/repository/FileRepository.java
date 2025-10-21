package com.ojt.OJT19SpringBoot.repository;

import com.ojt.OJT19SpringBoot.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
