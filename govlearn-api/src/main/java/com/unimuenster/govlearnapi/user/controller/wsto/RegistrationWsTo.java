package com.unimuenster.govlearnapi.user.controller.wsto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationWsTo {

    @NotBlank
    String name;
}
