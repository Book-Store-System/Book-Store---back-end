package com.renigomes.api_livraria.user_package.user.DTO;

import jakarta.validation.constraints.NotNull;

public record ReqPasswordRecovery(@NotNull String email) {
}
