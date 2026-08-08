package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class HardwareComponentTest {

  @Test
  void should_create_a_valid_hardware_component() {
    var id = UUID.randomUUID();

    var component = new GenericHardwareComponent(id, HardwareComponentType.CPU, "Intel",
        "Pentium III 800");

    assertThat(component.id()).isEqualTo(id);
    assertThat(component.type()).isEqualTo(HardwareComponentType.CPU);
    assertThat(component.manufacturer()).isEqualTo("Intel");
    assertThat(component.model()).isEqualTo("Pentium III 800");
  }

  @Test
  void should_reject_null_id() {
    assertThatThrownBy(() -> new GenericHardwareComponent(null, HardwareComponentType.CPU, "Intel",
        "Pentium III 800")).isInstanceOf(NullPointerException.class)
        .hasMessage("id must not be null");
  }

  @Test
  void should_reject_blank_manufacturer() {
    var componentId = UUID.randomUUID();
    assertThatThrownBy(() -> new GenericHardwareComponent(
        componentId,
        HardwareComponentType.GPU,
        " ",
        "GeForce 2 MX"
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("manufacturer must not be blank");
  }

  @Test
  void should_reject_blank_model() {
    var componentId = UUID.randomUUID();
    assertThatThrownBy(() -> new GenericHardwareComponent(
        componentId,
        HardwareComponentType.SOUND_CARD,
        "Creative",
        ""
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model must not be blank");
  }
}
