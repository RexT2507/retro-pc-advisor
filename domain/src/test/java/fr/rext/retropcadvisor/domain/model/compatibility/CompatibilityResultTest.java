package fr.rext.retropcadvisor.domain.model.compatibility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CompatibilityResultTest {

  @Test
  void should_create_compatible_result() {
    var result = new CompatibilityResult(
        CompatibilityStatus.COMPATIBLE,
        List.of()
    );

    assertThat(result.status())
        .isEqualTo(CompatibilityStatus.COMPATIBLE);
    assertThat(result.issues())
        .isEmpty();
    assertThat(result.compatible())
        .isTrue();
  }

  @Test
  void should_create_incompatible_result_with_issues() {
    var issue = new CompatibilityIssue(
        "INSUFFICIENT_MEMORY",
        "At least 16 MB of memory is required"
    );

    var result = new CompatibilityResult(
        CompatibilityStatus.INCOMPATIBLE,
        List.of(issue)
    );

    assertThat(result.status()).isEqualTo(CompatibilityStatus.INCOMPATIBLE);
    assertThat(result.issues()).isEqualTo(List.of(issue));
    assertThat(result.compatible()).isFalse();
  }

  @Test
  void should_make_issues_immutable() {
    var issue = new CompatibilityIssue(
        "INSUFFICIENT_MEMORY",
        "At least 16 MB of memory is required"
    );
    var issues = new ArrayList<CompatibilityIssue>();
    issues.add(issue);

    var result = new CompatibilityResult(
        CompatibilityStatus.INCOMPATIBLE,
        issues
    );

    issues.clear();

    assertThat(result.issues()).isEqualTo(List.of(issue));

    var immutableIssues = result.issues();

    assertThatThrownBy(immutableIssues::clear)
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void should_reject_compatible_result_with_issues() {
    var issue = new CompatibilityIssue(
        "INSUFFICIENT_MEMORY",
        "At least 16 MB of memory is required"
    );

    var issues = List.of(issue);

    assertThatThrownBy(() -> new CompatibilityResult(
        CompatibilityStatus.COMPATIBLE,
        issues
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("compatible result must not contain issues");
  }

  @Test
  void should_reject_incompatible_result_without_issues() {
    assertThatThrownBy(() -> new CompatibilityResult(
        CompatibilityStatus.INCOMPATIBLE,
        List.of()
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("incompatible result must contain at least one issue");
  }

  @Test
  void should_reject_null_status() {
    assertThatThrownBy(() -> new CompatibilityResult(
        null,
        List.of()
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("status must not be null");
  }

  @Test
  void should_reject_null_issues() {
    assertThatThrownBy(() -> new CompatibilityResult(
        CompatibilityStatus.COMPATIBLE,
        null
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("issues must not be null");
  }
}
