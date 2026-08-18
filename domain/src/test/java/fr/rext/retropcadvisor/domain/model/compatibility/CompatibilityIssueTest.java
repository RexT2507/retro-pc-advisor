package fr.rext.retropcadvisor.domain.model.compatibility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CompatibilityIssueTest {

  @Test
  void should_create_compatibility_issue() {
    var issue = new CompatibilityIssue(
        "INSUFFICIENT_MEMORY",
        "At least 16 MB of memory is required"
    );

    assertThat(issue.code()).isEqualTo("INSUFFICIENT_MEMORY");
    assertThat(issue.message()).isEqualTo("At least 16 MB of memory is required");
  }

  @Test
  void should_reject_null_code() {
    assertThatThrownBy(() -> new CompatibilityIssue(
        null,
        "At least 16 MB of memory is required"
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("code must not be null");
  }

  @Test
  void should_reject_null_message() {
    assertThatThrownBy(() -> new CompatibilityIssue(
        "INSUFFICIENT_MEMORY",
        null
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("message must not be null");
  }

  @Test
  void should_reject_blank_code() {
    assertThatThrownBy(() ->
        new CompatibilityIssue(
            " ",
            "At least 16 MB of memory is required"
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("code must not be blank");
  }

  @Test
  void should_reject_blank_message() {
    assertThatThrownBy(() ->
        new CompatibilityIssue(
            "INSUFFICIENT_MEMORY",
            " "
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("message must not be blank");
  }
}
