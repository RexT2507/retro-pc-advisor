package fr.rext.retropcadvisor.domain.model.hardware;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fr.rext.retropcadvisor.domain.model.hardware.storage.StorageCapacity;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StorageDeviceTest {

  @Test
  void should_create_a_storage_device() {
    var id = UUID.randomUUID();
    var capacity = StorageCapacity.ofMegabytes(295);

    var storageDevice = new StorageDevice(
        id,
        "Seagate",
        "ST3290A",
        capacity
    );

    assertThat(storageDevice.id()).isEqualTo(id);
    assertThat(storageDevice.type()).isEqualTo(HardwareComponentType.STORAGE);
    assertThat(storageDevice.manufacturer()).isEqualTo("Seagate");
    assertThat(storageDevice.model()).isEqualTo("ST3290A");
    assertThat(storageDevice.capacity()).isEqualTo(capacity);
  }

  @Test
  void should_reject_null_capacity() {
    var id = UUID.randomUUID();

    assertThatThrownBy(() -> new StorageDevice(
        id,
        "Seagate",
        "ST3290A",
        null
    ))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("capacity must not be null");
  }

  @Test
  void should_reject_blank_manufacturer() {
    var id = UUID.randomUUID();
    var capacity = StorageCapacity.ofMegabytes(128);

    assertThatThrownBy(() -> new StorageDevice(
        id,
        " ",
        "ST3290A",
        capacity
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("manufacturer must not be blank");
  }

  @Test
  void should_reject_storage_as_generic_hardware_component() {
    var componentId = UUID.randomUUID();

    assertThatThrownBy(() ->
        new GenericHardwareComponent(
            componentId,
            HardwareComponentType.STORAGE,
            "Seagate",
            "ST3290A"
        )
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("storage components must use StorageDevice");
  }

  @Test
  void should_reject_blank_model() {
    var id = UUID.randomUUID();
    var capacity = StorageCapacity.ofMegabytes(295);

    assertThatThrownBy(() -> new StorageDevice(
        id,
        "Seagate",
        " ",
        capacity
    ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model must not be blank");
  }
}
