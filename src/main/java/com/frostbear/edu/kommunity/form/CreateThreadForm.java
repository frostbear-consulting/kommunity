package com.frostbear.edu.kommunity.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateThreadForm {

    @NotNull
    @Length(max = 128)
    private String title;

    @NotBlank
    private String text;

}