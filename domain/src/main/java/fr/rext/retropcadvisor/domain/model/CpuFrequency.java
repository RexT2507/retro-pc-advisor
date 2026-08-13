package fr.rext.retropcadvisor.domain.model;

/**
 * Fréquence d'un processeur exprimée en mégahertz.
 * <p>
 * Ce "Value Object" évite de manipuler une valeur numérique sans connaître son unité.
 */
public record CpuFrequency(long megahertz) implements Comparable<CpuFrequency> {

  public CpuFrequency {
    if (megahertz <= 0) {
      throw new IllegalArgumentException("megahertz must be greater than zero");
    }
  }

  public static CpuFrequency ofMegahertz(long megahertz) {
    return new CpuFrequency(megahertz);
  }

  public static CpuFrequency ofGigahertz(long gigahertz) {
    if (gigahertz <= 0) {
      throw new IllegalArgumentException("gigahertz must be greater than zero");
    }

    return new CpuFrequency(Math.multiplyExact(gigahertz, 1000));
  }

  @Override
  public int compareTo(CpuFrequency other) {
    if (other == null) {
      throw new NullPointerException("other must not be null");
    }

    return Long.compare(megahertz, other.megahertz);
  }
}
