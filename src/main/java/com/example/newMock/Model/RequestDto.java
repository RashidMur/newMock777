package com.example.newMock.Model;

import lombok.Data;

@Data
public class RequestDto {
    private String rqUID;
    private String clientId;
    private String account;
    private String openDate;
    private String closeDate;

}
