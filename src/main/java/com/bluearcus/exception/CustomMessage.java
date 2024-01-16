package com.bluearcus.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {
    @JsonProperty("status_code")
    private Integer statusCode;

    @JsonProperty("message")
    private String msg;
}
