package fr.rext.retropcadvisor.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Repérente un composant matériel connu du référentiel.
 */
public record HardwareComponent(
    UUID id,
    HardwareComponentType type,
    String manufacturer,
    String model
) {

  public HardwareComponent {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(type, "type must not be null");
    Objects.requireNonNull(manufacturer, "manufacturer must not be null");
    Objects.requireNonNull(model, "model must not be null");

    if (manufacturer.isBlank()) {
      throw new IllegalArgumentException("manufacturer must not be blank");
    }

    if (model.isBlank()) {
      throw new IllegalArgumentException("model must not be blank");
    }
  }
}
