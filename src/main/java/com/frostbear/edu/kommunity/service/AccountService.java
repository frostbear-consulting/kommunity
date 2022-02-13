package com.frostbear.edu.kommunity.service;

import com.frostbear.edu.kommunity.domain.Session;
import com.frostbear.edu.kommunity.entity.Account;
import com.frostbear.edu.kommunity.exception.AccountAlreadyExistsException;
import com.frostbear.edu.kommunity.repo.AccountRepository;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repo.findByUsername(username)
                .map(account -> new Session(
                        account.getIdAccount(),
                        account.getUsername(),
                        account.getPassword(),
                        Set.of(new SimpleGrantedAuthority("ROLE_ACCOUNT"))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }

    /**
     * Registers a new user account
     *
     * @param username Username of the new account
     * @param password Plain-text password
     * @return Session
     * @throws AccountAlreadyExistsException If an account with that username already exists
     */
    @Transactional
    public Session register(@NotNull String username, @NotNull String password) {

        var account = new Account(
                UUID.randomUUID(),
                username,
                this.passwordEncoder.encode(password)
        );

        try {
            this.repo.saveAndFlush(account);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException cvex) {
                if (cvex.getConstraintName().equals("account_username_unq")) {
                    throw new AccountAlreadyExistsException("Account for that user name already exists");
                }
            }

            throw ex;
        }

        return (Session) this.loadUserByUsername(username);
    }
}