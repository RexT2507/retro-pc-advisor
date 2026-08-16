package fr.rext.retropcadvisor.domain.model.hardware.cpu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CpuFrequencyTest {

  @Test
  void should_create_frequency_in_megahertz() {
    var frequency = CpuFrequency.ofMegahertz(800);

    assertThat(frequency.megahertz()).isEqualTo(800);
  }

  @Test
  void should_convert_gigahertz_to_megahertz() {
    var frequency = CpuFrequency.ofGigahertz(2);

    assertThat(frequency.megahertz()).isEqualTo(2000);
  }

  @Test
  void should_compare_cpu_frequencies() {
    var pentiumFrequency = CpuFrequency.ofMegahertz(800);
    var athlonFrequency = CpuFrequency.ofMegahertz(1000);

    assertThat(pentiumFrequency)
        .isLessThan(athlonFrequency);

    assertThat(athlonFrequency)
        .isGreaterThan(pentiumFrequency);
  }

  @Test
  void should_reject_zero_megahertz() {
    assertThatThrownBy(() ->
        CpuFrequency.ofMegahertz(0)
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "megahertz must be greater than zero"
        );
  }

  @Test
  void should_reject_negative_megahertz() {
    assertThatThrownBy(() ->
        CpuFrequency.ofMegahertz(-1)
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "megahertz must be greater than zero"
        );
  }
}
