package com.unimuenster.govlearnapi.core.controller.user.wsto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationWsTo {

    @NotBlank
    String name;
}
