package com.codesmashers.decentrabox.model.dto;

import lombok.Data;

@Data
public class AccessGrantData {
    private String grantedTo;
    private Long grantedAt;
    private Long expiresAt;
    private boolean active;

}
