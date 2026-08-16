package fr.rext.retropcadvisor.domain.model.configuration;

import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponent;
import fr.rext.retropcadvisor.domain.model.hardware.HardwareComponentType;
import fr.rext.retropcadvisor.domain.model.hardware.Cpu;
import fr.rext.retropcadvisor.domain.model.hardware.memory.MemoryCapacity;
import fr.rext.retropcadvisor.domain.model.hardware.MemoryModule;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Configuration matérielle soumise au moteur de compatibilité.
 * <p>
 * Une configuration peut être incomplète : le moteur de compatibilité sera chargé d'expliquer les
 * composants manquants.
 */
public record PcConfiguration(
    UUID id,
    String name,
    List<HardwareComponent> components
) {

  public PcConfiguration {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(name, "name must not be null");
    Objects.requireNonNull(components, "components must not be null");

    if (name.isBlank()) {
      throw new IllegalArgumentException("name must not be blank");
    }

    // Empêche le code extérieur de modifier la configuration
    // après sa construction.
    components = List.copyOf(components);

    validateUniqueComponentIds(components);

    validateSingleComponent(components, HardwareComponentType.CPU);
    validateSingleComponent(components, HardwareComponentType.GPU);
    validateSingleComponent(components, HardwareComponentType.MOTHERBOARD);
    validateSingleComponent(components, HardwareComponentType.SOUND_CARD);
  }

  /**
   * Retourne tous les composants correspondant à une catégorie.
   * <p>
   * Cette méthode est utile pour les catégories multiples, comme la mémoire ou le stockage.
   */
  public List<HardwareComponent> componentsOfType(
      HardwareComponentType type
  ) {
    Objects.requireNonNull(type, "type must not be null");

    return components.stream()
        .filter(component -> component.type() == type)
        .toList();
  }

  /**
   * Retourne l'unique composant correspondant à une catégorie.
   * <p>
   * Cette méthode est adaptée au CPU, au GPU, à la carte mère et à la carte son.
   */
  public Optional<HardwareComponent> componentOfType(
      HardwareComponentType type
  ) {
    Objects.requireNonNull(type, "type must not be null");

    return components.stream()
        .filter(component -> component.type() == type)
        .findFirst();
  }

  /**
   * Calcule la quantité totale de mémoire installée.
   */
  public Optional<MemoryCapacity> totalMemoryCapacity() {
    long totalMegabytes = components.stream()
        .filter(MemoryModule.class::isInstance)
        .map(MemoryModule.class::cast)
        .mapToLong(memoryModule ->
            memoryModule.capacity().megabytes()
        )
        .sum();

    if (totalMegabytes == 0) {
      return Optional.empty();
    }

    return Optional.of(
        MemoryCapacity.ofMegabytes(totalMegabytes)
    );
  }

  /**
   * Calcule la fréquence du processeur installée.
   */
  public Optional<Cpu> cpu() {
    return components.stream().filter(Cpu.class::isInstance).map(Cpu.class::cast).findFirst();
  }

  private static void validateUniqueComponentIds(
      List<HardwareComponent> components
  ) {
    Set<UUID> componentIds = components.stream()
        .map(HardwareComponent::id)
        .collect(Collectors.toSet());

    if (componentIds.size() != components.size()) {
      throw new IllegalArgumentException(
          "component ids must be unique"
      );
    }
  }

  private static void validateSingleComponent(
      List<HardwareComponent> components,
      HardwareComponentType type
  ) {
    long count = components.stream()
        .filter(component -> component.type() == type)
        .count();

    if (count > 1) {
      throw new IllegalArgumentException(
          "configuration must contain at most one component of type "
              + type
      );
    }
  }
}
