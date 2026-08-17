package fr.rext.retropcadvisor.domain.model.os;

import fr.rext.retropcadvisor.domain.model.hardware.cpu.CpuFrequency;
import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import fr.rext.retropcadvisor.domain.model.hardware.storage.StorageCapacity;
import java.util.Objects;

public record OperatingSystemRequirements(
    OperatingSystem operatingSystem,
    RequirementLevel level,
    CpuFrequency minimumCpuFrequency,
    MemoryCapacity minimumMemoryCapacity,
    StorageCapacity minimumStorageCapacity
) {

  public OperatingSystemRequirements {
    Objects.requireNonNull(operatingSystem, "operating system must not be null");
    Objects.requireNonNull(level, "level must not be null");
    Objects.requireNonNull(minimumCpuFrequency, "minimum cpu frequency must not be null");
    Objects.requireNonNull(minimumMemoryCapacity, "minimum memory capacity must not be null");
    Objects.requireNonNull(minimumStorageCapacity, "minimum storage capacity must not be null");
  }
}