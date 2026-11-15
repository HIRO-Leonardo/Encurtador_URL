package com.encurtadorlink.V1.dto;

import java.time.LocalDateTime;


public class ErrorDTO {
    private String status;
    private String message;
    private String lancamento;
    private String times;


    public ErrorDTO(String status, String message, String lancamento) {
        this.status = status;
        this.message = message;
        this.lancamento = lancamento;
        this.times = LocalDateTime.now().toString();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
