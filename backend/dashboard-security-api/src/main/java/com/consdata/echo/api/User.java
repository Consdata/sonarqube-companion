package com.consdata.echo.api;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record User(@NotBlank String username, @NotBlank String password, List<String> roles) {
}
