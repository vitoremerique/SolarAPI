package com.interlig.solar.solar.service;

import com.interlig.solar.solar.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private usuarioRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String CPF) throws UsernameNotFoundException {
        return userRepo.findByCpf(CPF);
    }
}
