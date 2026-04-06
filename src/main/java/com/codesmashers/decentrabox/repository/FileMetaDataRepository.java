package com.codesmashers.decentrabox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codesmashers.decentrabox.model.FileMetaData;
import com.codesmashers.decentrabox.model.User;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, String> {

    List<FileMetaData> findByUser(User user);

    Optional<FileMetaData> findByCid(String cid);

}