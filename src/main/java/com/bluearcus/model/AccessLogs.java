package com.bluearcus.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "access_logs")
@Data
public class AccessLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @UpdateTimestamp
    @Column(name = "access_date_time", nullable = false)
    private Date accessDateTime;
    
    @Column(name = "req_payload", columnDefinition = "JSON")
    private String reqPayload;

    @Column(name = "response_payload")
    private String responsePayload;

    @Column(name = "authtoken")
    private String authToken;

    public void setReqPayload(String reqPayload) {
        try {
            this.reqPayload = new ObjectMapper().writeValueAsString(reqPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
