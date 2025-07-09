package com.renigomes.api_livraria.user_package.user.DTO;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record ReqIsCodeRecoveryCode(@NotNull Integer recoveryCode) implements Serializable {
}
