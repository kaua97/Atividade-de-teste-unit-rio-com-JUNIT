package com.secureaccess.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Autenticação e Bloqueio - RF05, RF06 e RF07")
public class AutenticacaoServiceTest {

    private AutenticacaoService autenticacaoService;
    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        autenticacaoService = new AutenticacaoService();
        usuarioValido = new Usuario("Carlos Silva", "carlos.admin", "Java@12345", "carlos@email.com", NivelAcesso.ADMIN);
        autenticacaoService.cadastrarUsuario(usuarioValido);
    }

    @Test
    @DisplayName("AUT01 - Deve autenticar utilizador valido")
    void deveAutenticarUsuarioValido() {
        assertTrue(autenticacaoService.autenticar("carlos.admin", "Java@12345"));
    }

    @Test
    @DisplayName("AUT02 - Deve rejeitar utilizador inexistente")
    void deveLancarExcecaoParaUsuarioInexistente() {
        assertThrows(AutenticacaoException.class, () ->
                autenticacaoService.autenticar("utilizador.fake", "Java@12345"));
    }

    @Test
    @DisplayName("AUT04/AUT05 - Deve rejeitar campos nulos ou vazios")
    void deveLancarExcecaoParaCamposNulosOuVazios() {
        assertThrows(CampoObrigatorioException.class, () ->
                autenticacaoService.autenticar(null, "Java@12345"));
        assertThrows(CampoObrigatorioException.class, () ->
                autenticacaoService.autenticar("carlos.admin", ""));
    }

    @Test
    @DisplayName("RF06 - Deve lancar ConexaoBancoException quando o banco estiver indisponivel")
    void deveLancarExcecaoQuandoBancoFalhar() {
        autenticacaoService.setBancoConectado(false);
        assertThrows(ConexaoBancoException.class, () ->
                autenticacaoService.autenticar("carlos.admin", "Java@12345"));
    }

    @Test
    @DisplayName("AUT09 a AUT12 / RF07 - Teste de Bloqueio apos 3 tentativas incorretas")
    void deveBloquearContaAposTresTentativasIncorretas() {
        // 1a Tentativa invalida
        assertThrows(AutenticacaoException.class, () ->
                autenticacaoService.autenticar("carlos.admin", "Errada1@123"));
        assertFalse(usuarioValido.isBloqueado());
        assertEquals(1, usuarioValido.getTentativasInvalidas());

        // 2a Tentativa invalida
        assertThrows(AutenticacaoException.class, () ->
                autenticacaoService.autenticar("carlos.admin", "Errada2@123"));
        assertFalse(usuarioValido.isBloqueado());
        assertEquals(2, usuarioValido.getTentativasInvalidas());

        // 3a Tentativa invalida (Bloqueia a conta)
        assertThrows(AutenticacaoException.class, () ->
                autenticacaoService.autenticar("carlos.admin", "Errada3@123"));
        assertTrue(usuarioValido.isBloqueado());

        // AUT12 - Tentativa apos bloqueio com a senha CORRETA deve ser rejeitada
        assertThrows(ContaBloqueadaException.class, () ->
                autenticacaoService.autenticar("carlos.admin", "Java@12345"));
    }
}