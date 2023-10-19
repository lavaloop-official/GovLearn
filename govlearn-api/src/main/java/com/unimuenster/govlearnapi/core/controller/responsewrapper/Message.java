package com.unimuenster.govlearnapi.core.controller.responsewrapper;

import lombok.*;

@AllArgsConstructor @ToString
@NoArgsConstructor
@Getter
@Setter
public class Message {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    public String message;
}
