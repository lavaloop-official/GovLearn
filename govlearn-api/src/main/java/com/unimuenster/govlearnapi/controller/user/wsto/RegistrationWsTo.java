package com.unimuenster.govlearnapi.controller.user.wsto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationWsTo {

    @NotBlank
    String name;
}
