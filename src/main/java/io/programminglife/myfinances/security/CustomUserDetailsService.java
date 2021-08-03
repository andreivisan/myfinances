package io.programminglife.myfinances.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.programminglife.myfinances.entity.Client;
import io.programminglife.myfinances.exception.MyFinancesException;
import io.programminglife.myfinances.repository.ClientRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Client client = clientRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new MyFinancesException(
                String.format("Client not found for username or email %s", usernameOrEmail)
            ));

        return UserPrincipal.create(client);
    }

    public UserDetails loadUserById(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new MyFinancesException(
            String.format("Client not found for id %s", id)
        ));

        return UserPrincipal.create(client);
    }
    
}