package com.sciencematch.sciencematch.DTO.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordDto {

    @NotNull
    @Schema(example = "password")
    String password;

}
