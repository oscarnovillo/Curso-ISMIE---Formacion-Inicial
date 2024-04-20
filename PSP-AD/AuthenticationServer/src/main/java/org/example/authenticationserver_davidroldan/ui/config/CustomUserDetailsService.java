package org.example.authenticationserver_davidroldan.ui.config;

import org.example.authenticationserver_davidroldan.common.Constantes;
import org.example.authenticationserver_davidroldan.data.modelo.CredentialsEntity;
import org.example.authenticationserver_davidroldan.data.repositories.CredentialsRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final CredentialsRepository userRepository;

    public CustomUserDetailsService(CredentialsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CredentialsEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(Constantes.USER_NOT_FOUND);
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }
}
