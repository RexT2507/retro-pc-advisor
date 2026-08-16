package fr.rext.retropcadvisor.domain.model.os;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OperatingSystemTest {

  @Test
  void should_expose_all_supported_operating_systems() {
    assertThat(OperatingSystem.values()).containsExactly(OperatingSystem.WINDOWS_95,
        OperatingSystem.WINDOWS_98, OperatingSystem.WINDOWS_98_SE, OperatingSystem.WINDOWS_XP);
  }
}
