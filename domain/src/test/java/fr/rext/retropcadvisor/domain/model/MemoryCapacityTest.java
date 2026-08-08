package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MemoryCapacityTest {

  @Test
  void should_create_memory_capacity_in_megabytes() {
    var capacity = MemoryCapacity.ofMegabytes(128);

    assertThat(capacity.megabytes()).isEqualTo(128);
  }

  @Test
  void should_convert_gigabytes_to_megabytes() {
    var capacity = MemoryCapacity.ofGigabytes(2);

    assertThat(capacity.megabytes()).isEqualTo(2048);
  }

  @Test
  void should_add_two_memory_capacities() {
    var first = MemoryCapacity.ofMegabytes(128);
    var second = MemoryCapacity.ofMegabytes(256);

    var result = first.add(second);

    assertThat(result)
        .isEqualTo(MemoryCapacity.ofMegabytes(384));
  }

  @Test
  void should_compare_memory_capacities() {
    var smaller = MemoryCapacity.ofMegabytes(64);
    var larger = MemoryCapacity.ofMegabytes(128);

    assertThat(smaller).isLessThan(larger);
    assertThat(larger).isGreaterThan(smaller);
  }

  @Test
  void should_reject_zero_megabytes() {
    assertThatThrownBy(() -> MemoryCapacity.ofMegabytes(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("megabytes must be greater than zero");
  }

  @Test
  void should_reject_negative_megabytes() {
    assertThatThrownBy(() -> MemoryCapacity.ofMegabytes(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("megabytes must be greater than zero");
  }

  @Test
  void should_reject_null_capacity_when_adding() {
    var capacity = MemoryCapacity.ofMegabytes(128);

    assertThatThrownBy(() -> capacity.add(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("other must not be null");
  }
}
