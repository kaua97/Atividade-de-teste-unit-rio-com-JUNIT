package com.secureaccess.service;

public class ContaBloqueadaException extends RuntimeException {
    public ContaBloqueadaException(String mensagem) {
        super(mensagem);
    }
}