package fr.rext.retropcadvisor.domain.model.hardware.cpu;

import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponent;
import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponentType;
import java.util.Objects;
import java.util.UUID;

public record Cpu(
    UUID id,
    String manufacturer,
    String model,
    CpuFrequency frequency
) implements HardwareComponent {

  public Cpu {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(manufacturer, "manufacturer must not be null");
    Objects.requireNonNull(model, "model must not be null");
    Objects.requireNonNull(frequency, "frequency must not be null");

    if (manufacturer.isBlank()) {
      throw new IllegalArgumentException("manufacturer must not be blank");
    }

    if (model.isBlank()) {
      throw new IllegalArgumentException("model must not be blank");
    }
  }

  @Override
  public HardwareComponentType type() {
    return HardwareComponentType.CPU;
  }
}
