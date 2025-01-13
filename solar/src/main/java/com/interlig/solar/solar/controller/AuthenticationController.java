package com.interlig.solar.solar.controller;

import com.interlig.solar.solar.infra.security.TokenService;
import com.interlig.solar.solar.model.AuthenticationDTO;
import com.interlig.solar.solar.model.LoginResponseDTO;
import com.interlig.solar.solar.model.Usuario;
import com.interlig.solar.solar.model.UsuarioRegistroDTO;
import com.interlig.solar.solar.repository.usuarioRepository;
import com.interlig.solar.solar.service.usuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private usuarioRepository repository;

    @Autowired
    private usuarioService userService;
    @Autowired
    private TokenService tokenService;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((Usuario) auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));
    }





    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UsuarioRegistroDTO data){
        if(this.userService.BuscarPorCPF(data.cpf()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        Usuario User = new Usuario(data.nome(), data.cpf(), data.email(),encryptedPassword,data.role(), data.telefone());

        this.repository.save(User);

        return ResponseEntity.ok().build();
    }




}
