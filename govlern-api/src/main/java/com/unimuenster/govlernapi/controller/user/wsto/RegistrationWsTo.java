package com.unimuenster.govlernapi.controller.user.wsto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationWsTo {

    @NotBlank
    String name;
}
