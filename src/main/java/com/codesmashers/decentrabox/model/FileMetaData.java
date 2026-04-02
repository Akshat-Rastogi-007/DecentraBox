package com.codesmashers.decentrabox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FileMetaData {

    @Id
    private String id;

}
