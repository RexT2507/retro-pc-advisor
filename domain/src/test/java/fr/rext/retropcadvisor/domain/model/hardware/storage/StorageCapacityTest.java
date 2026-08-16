package fr.rext.retropcadvisor.domain.model.hardware.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class StorageCapacityTest {

  @Test
  void should_create_storage_capacity_in_megabytes() {
    var capacity = StorageCapacity.ofMegabytes(295);

    assertThat(capacity.megabytes()).isEqualTo(295);
  }

  @Test
  void should_convert_gigabytes_to_megabytes() {
    var capacity = StorageCapacity.ofGigabytes(80);

    assertThat(capacity.megabytes()).isEqualTo(81920);
  }

  @Test
  void should_compare_storage_capacities() {
    var smaller = StorageCapacity.ofMegabytes(120);
    var larger = StorageCapacity.ofGigabytes(295);

    assertThat(smaller).isLessThan(larger);
    assertThat(larger).isGreaterThan(smaller);
  }

  @Test
  void should_reject_zero_megabytes() {
    assertThatThrownBy(() -> StorageCapacity.ofMegabytes(0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("megabytes must be greater than zero");
  }

  @Test
  void should_reject_negative_megabytes() {
    assertThatThrownBy(() -> StorageCapacity.ofMegabytes(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("megabytes must be greater than zero");
  }
}
