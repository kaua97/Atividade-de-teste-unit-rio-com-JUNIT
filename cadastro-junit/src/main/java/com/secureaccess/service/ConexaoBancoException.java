package com.secureaccess.service;

public class ConexaoBancoException extends RuntimeException {
    public ConexaoBancoException(String mensagem) {
        super(mensagem);
    }
}