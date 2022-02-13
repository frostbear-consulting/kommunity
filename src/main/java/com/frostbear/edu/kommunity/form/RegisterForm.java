package com.frostbear.edu.kommunity.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterForm {
    @NotBlank
    @Length(max = 16)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotBlank
    private String password;
}