package com.pettour.api.dto;

import jakarta.validation.constraints.Email;   
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.Size;     
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de email inválido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 1, message = "A senha deve ter no mínimo 1 caracteres.")
    private String senha;
}