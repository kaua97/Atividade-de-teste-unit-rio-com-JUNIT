package com.secureaccess.service;

public class CampoObrigatorioException extends RuntimeException {
    public CampoObrigatorioException(String mensagem) {
        super(mensagem);
    }
}