package fr.rext.retropcadvisor.domain.model.os;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.rext.retropcadvisor.domain.model.hardware.cpu.CpuFrequency;
import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import fr.rext.retropcadvisor.domain.model.hardware.storage.StorageCapacity;
import org.junit.jupiter.api.Test;

class OperatingSystemRequirementsTest {

  @Test
  void should_create_operating_system_requirements() {
    var operatingSystem = OperatingSystem.WINDOWS_98_SE;
    var level = RequirementLevel.MINIMUM;
    var minimumCpuFrequency = CpuFrequency.ofMegahertz(66);
    var minimumMemoryCapacity = MemoryCapacity.ofMegabytes(16);
    var minimumStorageCapacity = StorageCapacity.ofMegabytes(195);

    var requirements = new OperatingSystemRequirements(
        operatingSystem, level, minimumCpuFrequency,
        minimumMemoryCapacity, minimumStorageCapacity
    );

    assertThat(requirements.operatingSystem()).isEqualTo(operatingSystem);
    assertThat(requirements.level()).isEqualTo(level);
    assertThat(requirements.minimumCpuFrequency()).isEqualTo(minimumCpuFrequency);
    assertThat(requirements.minimumMemoryCapacity()).isEqualTo(minimumMemoryCapacity);
    assertThat(requirements.minimumStorageCapacity()).isEqualTo(minimumStorageCapacity);
  }

  @Test
  void should_reject_null_operating_system() {
    var level = RequirementLevel.MINIMUM;
    var minimumCpuFrequency = CpuFrequency.ofMegahertz(66);
    var minimumMemoryCapacity = MemoryCapacity.ofMegabytes(16);
    var minimumStorageCapacity = StorageCapacity.ofMegabytes(195);

    assertThatThrownBy(() ->
        new OperatingSystemRequirements(
            null,
            level,
            minimumCpuFrequency,
            minimumMemoryCapacity,
            minimumStorageCapacity
        )
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("operating system must not be null");
  }

  @Test
  void should_reject_null_level() {
    var operatingSystem = OperatingSystem.WINDOWS_98_SE;
    var minimumCpuFrequency = CpuFrequency.ofMegahertz(66);
    var minimumMemoryCapacity = MemoryCapacity.ofMegabytes(16);
    var minimumStorageCapacity = StorageCapacity.ofMegabytes(195);

    assertThatThrownBy(() ->
        new OperatingSystemRequirements(
            operatingSystem,
            null,
            minimumCpuFrequency,
            minimumMemoryCapacity,
            minimumStorageCapacity
        )
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("level must not be null");
  }

  @Test
  void should_reject_null_minimum_cpu_frequency() {
    var operatingSystem = OperatingSystem.WINDOWS_98_SE;
    var level = RequirementLevel.MINIMUM;
    var minimumMemoryCapacity = MemoryCapacity.ofMegabytes(16);
    var minimumStorageCapacity = StorageCapacity.ofMegabytes(195);

    assertThatThrownBy(() ->
        new OperatingSystemRequirements(
            operatingSystem,
            level,
            null,
            minimumMemoryCapacity,
            minimumStorageCapacity
        )
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("minimum cpu frequency must not be null");
  }

  @Test
  void should_reject_null_minimum_memory_capacity() {
    var operatingSystem = OperatingSystem.WINDOWS_98_SE;
    var level = RequirementLevel.MINIMUM;
    var minimumCpuFrequency = CpuFrequency.ofMegahertz(66);
    var minimumStorageCapacity = StorageCapacity.ofMegabytes(195);

    assertThatThrownBy(() ->
        new OperatingSystemRequirements(
            operatingSystem,
            level,
            minimumCpuFrequency,
            null,
            minimumStorageCapacity
        )
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("minimum memory capacity must not be null");
  }

  @Test
  void should_reject_null_minimum_storage_capacity() {
    var operatingSystem = OperatingSystem.WINDOWS_98_SE;
    var level = RequirementLevel.MINIMUM;
    var minimumCpuFrequency = CpuFrequency.ofMegahertz(66);
    var minimumMemoryCapacity = MemoryCapacity.ofMegabytes(16);

    assertThatThrownBy(() ->
        new OperatingSystemRequirements(
            operatingSystem,
            level,
            minimumCpuFrequency,
            minimumMemoryCapacity,
            null
        )
    )
        .isInstanceOf(NullPointerException.class)
        .hasMessage("minimum storage capacity must not be null");
  }
}
