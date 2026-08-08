package fr.rext.retropcadvisor.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Représentation générique d'un composant qui ne possède par encore de modèle métier spécialisé.
 */
public record GenericHardwareComponent(
    UUID id,
    HardwareComponentType type,
    String manufacturer,
    String model
) implements HardwareComponent {

  public GenericHardwareComponent {
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

    // Empêche de contourner le modèle en créant une mémoire générique sans capacité.
    if (type == HardwareComponentType.MEMORY) {
      throw new IllegalArgumentException("memory components must use MemoryModule");
    }
  }
}
