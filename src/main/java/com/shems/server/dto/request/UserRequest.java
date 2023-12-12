package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {


    @NotNull
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private String billingAddress;
}
