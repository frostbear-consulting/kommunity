package com.frostbear.edu.kommunity.form;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateCommentForm {

    @NotBlank
    private String comment;

}