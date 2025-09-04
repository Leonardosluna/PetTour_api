package com.pettour.api.dto;

import com.pettour.api.model.Usuario;

public record LoginResponseDTO(
    String token,
    Long id,
    String nome,
    String fotoUrl
) {
    // Construtor para facilitar a criação do DTO a partir do token e do usuário
    public LoginResponseDTO(String token, Usuario usuario) {
        this(token, usuario.getId(), usuario.getNome(), usuario.getFotoUrl());
    }
}