package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DeleteDevicesRequest {

    @NotEmpty(message = "Which ones to delete?")
    @NotNull(message = "Why are deviceIds null?")
    private Set<Long> deviceIds;
}
