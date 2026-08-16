package fr.rext.retropcadvisor.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Représentation générique d'un composant qui ne possède pas encore de modèle métier spécialisé.
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
      throw new IllegalArgumentException(
          "manufacturer must not be blank"
      );
    }

    if (model.isBlank()) {
      throw new IllegalArgumentException(
          "model must not be blank"
      );
    }

    // Les composants disposant d'un modèle métier spécialisé
    // ne peuvent plus être représentés comme composants génériques.
    switch (type) {
      case MEMORY -> throw new IllegalArgumentException(
          "memory components must use MemoryModule"
      );

      case CPU -> throw new IllegalArgumentException(
          "cpu components must use Cpu"
      );

      case STORAGE -> throw new IllegalArgumentException(
          "storage components must use StorageDevice"
      );

      default -> {
        // Ce type ne possède pas encore de modèle métier spécialisé.
      }
    }
  }
}