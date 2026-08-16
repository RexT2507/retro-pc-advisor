package fr.rext.retropcadvisor.domain.model.hardware;

import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import java.util.Objects;
import java.util.UUID;

/**
 * Barette de mémoire installable dans une configuration PC.
 */
public record MemoryModule(
    UUID id,
    String manufacturer,
    String model,
    MemoryCapacity capacity
) implements HardwareComponent {

  public MemoryModule {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(manufacturer, "manufacturer must not be null");
    Objects.requireNonNull(model, "model must not be null");
    Objects.requireNonNull(capacity, "capacity must not be null");

    if (manufacturer.isBlank()) {
      throw new IllegalArgumentException("manufacturer must not be blank");
    }

    if (model.isBlank()) {
      throw new IllegalArgumentException("model must not be blank");
    }
  }

  /**
   * Le type est imposé par la nature de l'objet.
   * <p>
   * Il n'est donc pas fourni par l'appelant, ce qui empêche de créer un MemoryModule déclaré comme
   * CPU ou GPU.
   */
  @Override
  public HardwareComponentType type() {
    return HardwareComponentType.MEMORY;
  }
}
