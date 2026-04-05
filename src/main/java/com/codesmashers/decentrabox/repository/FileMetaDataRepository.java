package com.codesmashers.decentrabox.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codesmashers.decentrabox.model.FileMetaData;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, String> {

}