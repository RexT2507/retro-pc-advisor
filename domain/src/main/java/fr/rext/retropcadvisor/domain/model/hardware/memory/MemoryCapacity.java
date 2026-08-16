package fr.rext.retropcadvisor.domain.model.hardware.memory;

/**
 * Quantité de mémoire exprimée en mégaoctets.
 */
public record MemoryCapacity(long megabytes) implements Comparable<MemoryCapacity> {

  public MemoryCapacity {
    if (megabytes <= 0) {
      throw new IllegalArgumentException("megabytes must be greater than zero");
    }
  }

  public static MemoryCapacity ofMegabytes(long megabytes) {
    return new MemoryCapacity(megabytes);
  }

  public static MemoryCapacity ofGigabytes(long gigabytes) {
    if (gigabytes <= 0) {
      throw new IllegalArgumentException("gigabytes must be greater than zero");
    }

    return new MemoryCapacity(Math.multiplyExact(gigabytes, 1024));
  }

  public MemoryCapacity add(MemoryCapacity other) {
    if (other == null) {
      throw new NullPointerException("other must not be null");
    }

    return new MemoryCapacity(Math.addExact(megabytes, other.megabytes));
  }

  @Override
  public int compareTo(MemoryCapacity other) {
    if (other == null) {
      throw new NullPointerException("other must not be null");
    }
    return Long.compare(megabytes, other.megabytes);
  }
}
