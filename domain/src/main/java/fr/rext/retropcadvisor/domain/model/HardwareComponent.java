package fr.rext.retropcadvisor.domain.model;

import java.util.UUID;

/**
 * Composant matériel pouvant appartenir à une configuration PC.
 * <p>
 * L'interface est scellée afin que le domaine maîtrise explicitement les différentes
 * représentations possibles d'un composant.
 */
public sealed interface HardwareComponent permits Cpu, GenericHardwareComponent, MemoryModule,
    StorageDevice {

  UUID id();

  HardwareComponentType type();

  String manufacturer();

  String model();
}
