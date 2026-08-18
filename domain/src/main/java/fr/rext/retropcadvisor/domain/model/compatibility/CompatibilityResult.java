package fr.rext.retropcadvisor.domain.model.compatibility;

import java.util.List;
import java.util.Objects;

public record CompatibilityResult(
    CompatibilityStatus status,
    List<CompatibilityIssue> issues
) {

  public CompatibilityResult {
    Objects.requireNonNull(status, "status must not be null");
    Objects.requireNonNull(issues, "issues must not be null");

    issues = List.copyOf(issues);

    if (status == CompatibilityStatus.COMPATIBLE && !issues.isEmpty()) {
      throw new IllegalArgumentException("compatible result must not contain issues");
    }

    if (status == CompatibilityStatus.INCOMPATIBLE && issues.isEmpty()) {
      throw new IllegalArgumentException("incompatible result must contain at least one issue");
    }
  }

  public boolean compatible() {
    return status == CompatibilityStatus.COMPATIBLE;
  }
}
