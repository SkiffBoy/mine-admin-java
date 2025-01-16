package com.skiffboy.mine.admin.model.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotNull(message = "{user.id}{jakarta.validation.constraints.NotNull.message}")
    private Long id;
}
