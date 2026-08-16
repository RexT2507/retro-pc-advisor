package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.rext.retropcadvisor.domain.model.hardware.GenericHardwareComponent;
import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponentType;
import fr.rext.retropcadvisor.domain.model.hardware.Cpu;
import fr.rext.retropcadvisor.domain.model.hardware.cpu.CpuFrequency;
import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import fr.rext.retropcadvisor.domain.model.hardware.MemoryModule;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HardwareComponentTest {

  @Test
  void should_create_a_valid_hardware_component() {
    var id = UUID.randomUUID();

    var cpuFrequency = CpuFrequency.ofMegahertz(800);
    var cpuComponent = new Cpu(id, "Intel",
        "Pentium III 800", cpuFrequency);

    var memoryCapacity = MemoryCapacity.ofMegabytes(1024);
    var memoryComponent = new MemoryModule(id, "Kingston",
        "KVR133X64C3", memoryCapacity);

    assertThat(cpuComponent.id()).isEqualTo(id);
    assertThat(cpuComponent.type()).isEqualTo(HardwareComponentType.CPU);
    assertThat(cpuComponent.manufacturer()).isEqualTo("Intel");
    assertThat(cpuComponent.model()).isEqualTo("Pentium III 800");

    assertThat(memoryComponent.id()).isEqualTo(id);
    assertThat(memoryComponent.type()).isEqualTo(HardwareComponentType.MEMORY);
    assertThat(memoryComponent.manufacturer()).isEqualTo("Kingston");
    assertThat(memoryComponent.model()).isEqualTo("KVR133X64C3");
  }

  @Test
  void should_reject_cpu_as_generic_hardware_component() {
    var componentId = UUID.randomUUID();

    assertThatThrownBy(() ->
        new GenericHardwareComponent(
            componentId,
            HardwareComponentType.CPU,
            "Intel",
            "Pentium III 800"
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("cpu components must use Cpu");
  }

  @Test
  void should_reject_null_id() {
    assertThatThrownBy(() ->
        new GenericHardwareComponent(
            null,
            HardwareComponentType.GPU,
            "Nvidia",
            "GeForce 2 MX"
        )
    )
        .isInstanceOf(NullPointerException.class)
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
