package com.frostbear.edu.kommunity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @Type(type = "pg-uuid")
    @Column(name = "idAccount")
    private UUID idAccount;

    @Column(nullable = false, unique = true, length = 16)
    private String username;

    @Column(nullable = false)
    private String password;
}