package com.example.chatserver.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class CommonResDto {
    private int status_code;
    private String status_message;
    private Object result;
    public CommonResDto(HttpStatus httpStatus, String message, Object result){
        this.status_code = httpStatus.value();
        this.status_message = message;
        this.result = result;
    }
}
