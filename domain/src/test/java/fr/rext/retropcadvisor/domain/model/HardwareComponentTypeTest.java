package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HardwareComponentTypeTest {

  @Test
  void should_expose_all_supported_hardware_component_types() {
    assertThat(HardwareComponentType.values()).containsExactly(HardwareComponentType.CPU,
        HardwareComponentType.GPU, HardwareComponentType.MEMORY, HardwareComponentType.MOTHERBOARD,
        HardwareComponentType.SOUND_CARD, HardwareComponentType.STORAGE);
  }
}
