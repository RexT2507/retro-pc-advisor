package fr.rext.retropcadvisor.domain.model;

/**
 * Capacité de stockage exprimée en mégabyte.
 * <p>
 * Ce "Value Object" évite de manipuler une valeur numérique sans connaître son unité.
 */
public record StorageCapacity(long megabytes) implements Comparable<StorageCapacity> {

  public StorageCapacity {
    if (megabytes <= 0) {
      throw new IllegalArgumentException("megabytes must be greater than zero");
    }
  }

  public static StorageCapacity ofMegabytes(long megabytes) {
    return new StorageCapacity(megabytes);
  }

  public static StorageCapacity ofGigabytes(long gigabytes) {
    if (gigabytes <= 0) {
      throw new IllegalArgumentException("gigabytes must be greater than zero");
    }

    return new StorageCapacity(Math.multiplyExact(gigabytes, 1024));
  }

  @Override
  public int compareTo(StorageCapacity other) {
    if (other == null) {
      throw new NullPointerException("other must not be null");
    }

    return Long.compare(megabytes, other.megabytes);
  }
}
