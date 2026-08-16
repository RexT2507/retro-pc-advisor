package fr.rext.retropcadvisor.domain.model.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponent;
import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponentType;
import fr.rext.retropcadvisor.domain.model.hardware.Cpu;
import fr.rext.retropcadvisor.domain.model.hardware.cpu.CpuFrequency;
import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import fr.rext.retropcadvisor.domain.model.hardware.MemoryModule;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PcConfigurationTest {

  @Test
  void should_create_a_pc_configuration() {
    var cpu = cpu(
        "Intel",
        "Pentium III 800",
        800
    );

    var memory = memoryModule(
        "Kingston",
        "SDRAM 128 MB",
        128
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

    components.add(cpu(
        "Intel",
        "Pentium II",
        450
    ));

    assertThat(configuration.components()).isEmpty();

    var immutableComponents = configuration.components();

    assertThatThrownBy(immutableComponents::clear)
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  void should_reject_duplicate_component_ids() {
    var componentId = UUID.randomUUID();

    var firstMemoryModule = new MemoryModule(
        componentId,
        "Kingston",
        "SDRAM 128 MB",
        MemoryCapacity.ofMegabytes(128)
    );

    var secondMemoryModule = new MemoryModule(
        componentId,
        "Kingston",
        "SDRAM 256 MB",
        MemoryCapacity.ofMegabytes(256)
    );

    var configurationId = UUID.randomUUID();
    var components = List.<HardwareComponent>of(
        firstMemoryModule,
        secondMemoryModule
    );

    assertThatThrownBy(() -> new PcConfiguration(
        configurationId,
        "Invalid PC",
        components
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("component ids must be unique");
  }

  @Test
  void should_reject_more_than_one_cpu() {
    var firstCpu = cpu(
        "Intel",
        "Pentium III 800",
        800
    );

    var secondCpu = cpu(
        "AMD",
        "Athlon 800",
        800
    );

    var configurationId = UUID.randomUUID();

    List<HardwareComponent> components =
        List.of(firstCpu, secondCpu);

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
    var firstMemoryModule = memoryModule(
        "Kingston",
        "SDRAM 128 MB",
        128
    );

    var secondMemoryModule = memoryModule(
        "Crucial",
        "SDRAM 256 MB",
        256
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
    var cpu = cpu(
        "Intel",
        "Pentium III 800",
        800
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

  @Test
  void should_calculate_total_memory_capacity() {
    var firstModule = memoryModule(
        "Kingston",
        "SDRAM 128 MB",
        128
    );

    var secondModule = memoryModule(
        "Crucial",
        "SDRAM 256 MB",
        256
    );

    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Windows XP PC",
        List.of(firstModule, secondModule)
    );

    assertThat(configuration.totalMemoryCapacity())
        .contains(MemoryCapacity.ofMegabytes(384));
  }

  @Test
  void should_return_empty_when_no_memory_is_installed() {
    var configuration = new PcConfiguration(
        UUID.randomUUID(),
        "Incomplete PC",
        List.of()
    );

    assertThat(configuration.totalMemoryCapacity()).isEmpty();
  }

  private static Cpu cpu(
      String manufacturer,
      String model,
      long megahertz
  ) {
    return new Cpu(UUID.randomUUID(), manufacturer, model, CpuFrequency.ofMegahertz(megahertz));
  }

  private static MemoryModule memoryModule(
      String manufacturer,
      String model,
      long megabytes
  ) {
    return new MemoryModule(
        UUID.randomUUID(),
        manufacturer,
        model,
        MemoryCapacity.ofMegabytes(megabytes)
    );
  }
}
