package com.secureaccess.service;

import java.util.HashMap;
import java.util.Map;

public class AutenticacaoService {

    private final Map<String, Usuario> repositorio = new HashMap<>();
    private boolean bancoConectado = true;

    public void setBancoConectado(boolean bancoConectado) {
        this.bancoConectado = bancoConectado;
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (!bancoConectado) {
            throw new ConexaoBancoException("Erro de conexao com o banco de dados.");
        }
        repositorio.put(usuario.getLogin(), usuario);
    }

    public boolean autenticar(String login, String senha) {
        if (!bancoConectado) {
            throw new ConexaoBancoException("Erro de conexao com o banco de dados.");
        }

        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            throw new CampoObrigatorioException("Login e senha sao obrigatorios.");
        }

        Usuario usuario = repositorio.get(login);

        if (usuario == null) {
            throw new AutenticacaoException("Utilizador nao encontrado.");
        }

        if (usuario.isBloqueado()) {
            throw new ContaBloqueadaException("Conta bloqueada devido a multiplas tentativas incorretas.");
        }

        if (!usuario.getSenha().equals(senha)) {
            usuario.incrementarTentativas();
            throw new AutenticacaoException("Senha incorreta.");
        }

        usuario.zerarTentativas();
        return true;
    }
}