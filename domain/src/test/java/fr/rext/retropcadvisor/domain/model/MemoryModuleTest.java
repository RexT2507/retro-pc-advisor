package fr.rext.retropcadvisor.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class MemoryModuleTest {

  @Test
  void should_create_a_memory_module() {
    var capacity = MemoryCapacity.ofMegabytes(128);

    var memoryModule = new MemoryModule(
        UUID.randomUUID(),
        "Kingston",
        "KVR133X64C3",
        capacity
    );

    assertThat(memoryModule.type())
        .isEqualTo(HardwareComponentType.MEMORY);

    assertThat(memoryModule.capacity())
        .isEqualTo(capacity);
  }

  @Test
  void should_reject_null_capacity() {
    var id = UUID.randomUUID();

    assertThatThrownBy(() -> new MemoryModule(
        id,
        "Kingston",
        "KVR133X64C3",
        null
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("capacity must not be null");
  }

  @Test
  void should_reject_blank_manufacturer() {
    var id = UUID.randomUUID();
    var capacity = MemoryCapacity.ofMegabytes(128);

    assertThatThrownBy(() -> new MemoryModule(
        id,
        " ",
        "KVR133X64C3",
        capacity
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("manufacturer must not be blank");
  }
}
