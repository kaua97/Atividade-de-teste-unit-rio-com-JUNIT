package com.secureaccess.service;

public class Usuario {
    private String nome;
    private String login;
    private String senha;
    private String email;
    private NivelAcesso nivelAcesso;
    private int tentativasInvalidas;
    private boolean bloqueado;

    // Construtor simplificado (para manter compatibilidade com testes mais simples)
    public Usuario(String login, String senha) {
        this("Utilizador Teste", login, senha, "teste@email.com", NivelAcesso.CLIENTE);
    }

    // Construtor completo
    public Usuario(String nome, String login, String senha, String email, NivelAcesso nivelAcesso) {
        if (nome == null || nome.isBlank() || login == null || login.isBlank() ||
                senha == null || senha.isBlank() || email == null || email.isBlank()) {
            throw new CampoObrigatorioException("Todos os campos do cadastro sao obrigatorios.");
        }
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.nivelAcesso = nivelAcesso != null ? nivelAcesso : NivelAcesso.CLIENTE;
        this.tentativasInvalidas = 0;
        this.bloqueado = false;
    }

    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getEmail() { return email; }
    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public int getTentativasInvalidas() { return tentativasInvalidas; }
    public boolean isBloqueado() { return bloqueado; }

    public void incrementarTentativas() {
        this.tentativasInvalidas++;
        if (this.tentativasInvalidas >= 3) {
            this.bloqueado = true;
        }
    }

    public void zerarTentativas() {
        this.tentativasInvalidas = 0;
    }
}