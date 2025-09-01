package com.pettour.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pettour.api.dto.LoginRequestDTO;
import com.pettour.api.dto.TokenResponseDTO;
import com.pettour.api.dto.UsuarioDTO;
import com.pettour.api.model.Usuario;
import com.pettour.api.service.TokenService;
import com.pettour.api.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired // Injetar o AuthenticationManager
    private AuthenticationManager authenticationManager;

    @Autowired // Injetar o TokenService
    private TokenService tokenService;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody UsuarioDTO dados) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dados.getNome());
        novoUsuario.setEmail(dados.getEmail());
        novoUsuario.setSenha(dados.getSenha());
        Usuario usuarioSalvo = usuarioService.criarUsuario(novoUsuario);
        return ResponseEntity.ok(usuarioSalvo);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO dados) {
        // 1. Cria um objeto de autenticação com email e senha
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
        
        // 2. O Spring Security autentica o usuário (verifica email e senha criptografada)
        var authentication = authenticationManager.authenticate(authenticationToken);

        // 3. Se a autenticação for bem-sucedida, gera o token
        var usuario = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(usuario);

        // 4. Retorna o token em um DTO
        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
    }
}