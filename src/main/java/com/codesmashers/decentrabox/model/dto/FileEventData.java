package com.codesmashers.decentrabox.model.dto;

import lombok.Data;

@Data
public class FileEventData {

    private String cid;
    private String action;
    private String actor;
    private Long timestamp;
    private String txHash;

}
