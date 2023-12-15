package com.shems.server.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DeleteDevicesRequest {

    @NotEmpty(message = "Which ones to delete?")
    private Set<Long> deviceIds;
}
