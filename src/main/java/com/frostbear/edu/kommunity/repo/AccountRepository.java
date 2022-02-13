package com.frostbear.edu.kommunity.repo;

import com.frostbear.edu.kommunity.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUsername(@NotNull String username);

}