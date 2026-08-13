package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CpuTest {

  @Test
  void should_create_a_cpu() {
    var id = UUID.randomUUID();
    var frequency = CpuFrequency.ofMegahertz(800);

    var cpu = new Cpu(
        id,
        "Intel",
        "Pentium III 800",
        frequency
    );

    assertThat(cpu.id()).isEqualTo(id);
    assertThat(cpu.type()).isEqualTo(HardwareComponentType.CPU);
    assertThat(cpu.manufacturer()).isEqualTo("Intel");
    assertThat(cpu.model()).isEqualTo("Pentium III 800");
    assertThat(cpu.frequency()).isEqualTo(frequency);
  }

  @Test
  void should_reject_null_frequency() {
    var id = UUID.randomUUID();

    assertThatThrownBy(() -> new Cpu(id, "Intel", "Pentium III 800", null)).isInstanceOf(
        NullPointerException.class).hasMessage("frequency must not be null");
  }

  @Test
  void should_reject_blank_manufacturer() {
    var id = UUID.randomUUID();
    var frequency = CpuFrequency.ofMegahertz(800);

    assertThatThrownBy(() -> new Cpu(id, " ", "Pentium III 800", frequency)).isInstanceOf(
        IllegalArgumentException.class).hasMessage("manufacturer must not be blank");
  }

  @Test
  void should_reject_blank_model() {
    var id = UUID.randomUUID();
    var frequency = CpuFrequency.ofMegahertz(800);

    assertThatThrownBy(() ->
        new Cpu(id, "Intel", " ", frequency)
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model must not be blank");
  }

  @Test
  void should_reject_null_id() {
    var frequency = CpuFrequency.ofMegahertz(800);

    assertThatThrownBy(() ->
        new Cpu(null, "Intel", "Pentium III 800", frequency)
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("id must not be null");
  }
}
