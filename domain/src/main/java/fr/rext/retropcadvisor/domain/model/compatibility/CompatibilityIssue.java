package fr.rext.retropcadvisor.domain.model.compatibility;

import java.util.Objects;

public record CompatibilityIssue(
    String code,
    String message
) {

  public CompatibilityIssue {
    Objects.requireNonNull(code, "code must not be null");
    Objects.requireNonNull(message, "message must not be null");

    if (code.isBlank()) {
      throw new IllegalArgumentException("code must not be blank");
    }

    if (message.isBlank()) {
      throw new IllegalArgumentException("message must not be blank");
    }
  }
}
