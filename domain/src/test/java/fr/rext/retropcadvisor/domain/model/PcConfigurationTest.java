package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PcConfigurationTest {

  @Test
  void should_create_a_pc_configuration() {
    var cpu = component(
        HardwareComponentType.CPU,
        "Intel",
        "Pentium III 800"
    );

    var memory = component(
        HardwareComponentType.MEMORY,
        "Kingston",
        "SDRAM 128 MB"
    );

    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Windows 98 Gaming PC",
        List.of(cpu, memory)
    );

    assertThat(configuration.name())
        .isEqualTo("Windows 98 Gaming PC");

    assertThat(configuration.components())
        .containsExactly(cpu, memory);
  }

  @Test
  void should_allow_an_incomplete_configuration() {
    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Incomplete PC",
        List.of()
    );

    assertThat(configuration.components()).isEmpty();
  }

  @Test
  void should_make_the_component_list_immutable() {
    var components = new ArrayList<HardwareComponent>();

    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Immutable PC",
        components
    );

    components.add(component(
        HardwareComponentType.CPU,
        "Intel",
        "Pentium II"
    ));

    assertThat(configuration.components()).isEmpty();

    var immutableComponents = configuration.components();

    assertThatThrownBy(immutableComponents::clear).isInstanceOf(
        UnsupportedOperationException.class);
  }

  @Test
  void should_reject_duplicate_component_ids() {
    var componentId = UUID.randomUUID();

    var firstMemoryModule = new HardwareComponent(
        componentId,
        HardwareComponentType.MEMORY,
        "Kingston",
        "SDRAM 128 MB"
    );

    var secondMemoryModule = new HardwareComponent(
        componentId,
        HardwareComponentType.MEMORY,
        "Kingston",
        "SDRAM 256 MB"
    );

    var configurationId = UUID.randomUUID();
    var components = List.of(firstMemoryModule, secondMemoryModule);

    assertThatThrownBy(() -> new PcConfiguration(
        configurationId,
        "Invalid PC",
        components
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("component ids must be null");
  }

  @Test
  void should_reject_more_than_one_cpu() {
    var firstCpu = component(
        HardwareComponentType.CPU,
        "Intel",
        "Pentium III 800"
    );

    var secondCpu = component(
        HardwareComponentType.CPU,
        "AMD",
        "Athlon 800"
    );

    var configurationId = UUID.randomUUID();
    var components = List.of(firstCpu, secondCpu);

    assertThatThrownBy(() -> new PcConfiguration(
        configurationId,
        "Invalid dual CPU PC",
        components
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(
            "configuration must contain at most one component of type CPU"
        );
  }

  @Test
  void should_allow_multiple_memory_modules() {
    var firstMemoryModule = component(
        HardwareComponentType.MEMORY,
        "Kingston",
        "SDRAM 128 MB"
    );

    var secondMemoryModule = component(
        HardwareComponentType.MEMORY,
        "Crucial",
        "SDRAM 256 MB"
    );

    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Windows XP PC",
        List.of(firstMemoryModule, secondMemoryModule)
    );

    assertThat(configuration.componentsOfType(
        HardwareComponentType.MEMORY
    )).containsExactly(firstMemoryModule, secondMemoryModule);
  }

  @Test
  void should_find_the_cpu() {
    var cpu = component(
        HardwareComponentType.CPU,
        "Intel",
        "Pentium III 800"
    );

    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Windows 98 PC",
        List.of(cpu)
    );

    assertThat(configuration.componentOfType(
        HardwareComponentType.CPU
    )).contains(cpu);
  }

  @Test
  void should_return_empty_when_component_type_is_missing() {
    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Windows 95 PC",
        List.of()
    );

    assertThat(configuration.componentOfType(
        HardwareComponentType.GPU
    )).isEmpty();
  }

  private static HardwareComponent component(
      HardwareComponentType type,
      String manufacturer,
      String model
  ) {
    return new HardwareComponent(
        UUID.randomUUID(),
        type,
        manufacturer,
        model
    );
  }
}
