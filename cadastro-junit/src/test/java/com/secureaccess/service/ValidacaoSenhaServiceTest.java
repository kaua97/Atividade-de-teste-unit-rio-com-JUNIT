package com.secureaccess.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes de Validação de Senha - Caixa Branca")
public class ValidacaoSenhaServiceTest {

    private ValidacaoSenhaService service;

    @BeforeEach
    void setUp() {
        service = new ValidacaoSenhaService();
    }

    @Test
    @DisplayName("CT01/CT09 - Deve aceitar senha válida com 10 caracteres (limite inferior)")
    void deveAceitarSenhaValida10Caracteres() {
        assertTrue(service.validarSenha("Java@12345"));
    }

    @Test
    @DisplayName("CT10 - Deve aceitar senha válida com 12 caracteres (limite superior)")
    void deveAceitarSenhaValida12Caracteres() {
        assertTrue(service.validarSenha("Java@1234567"));
    }

    @Test
    @DisplayName("CT02/CT13 - Deve rejeitar senha com 9 caracteres (abaixo do limite)")
    void deveRejeitarSenhaMenorQue10Caracteres() {
        assertFalse(service.validarSenha("Java@1234"));
    }

    @Test
    @DisplayName("CT03 - Deve rejeitar senha com 13 caracteres (acima do limite)")
    void deveRejeitarSenhaMaiorQue12Caracteres() {
        assertFalse(service.validarSenha("Java@12345678"));
    }

    @Test
    @DisplayName("CT04/CT16 - Deve rejeitar senha sem números")
    void deveRejeitarSenhaSemNumero() {
        assertFalse(service.validarSenha("Java@Testes"));
    }

    @Test
    @DisplayName("CT05/CT15 - Deve rejeitar senha sem letras")
    void deveRejeitarSenhaSemLetra() {
        assertFalse(service.validarSenha("123456@7890"));
    }

    @Test
    @DisplayName("CT06/CT14 - Deve rejeitar senha sem caractere especial")
    void deveRejeitarSenhaSemCaractereEspecial() {
        assertFalse(service.validarSenha("Java1234567"));
    }

    @Test
    @DisplayName("CT07 - Deve rejeitar senha nula")
    void deveRejeitarSenhaNula() {
        assertFalse(service.validarSenha(null));
    }

    @Test
    @DisplayName("CT08 - Deve rejeitar senha vazia")
    void deveRejeitarSenhaVazia() {
        assertFalse(service.validarSenha(""));
    }
}