package io.phanisment.itemcaster.profile;

import java.util.UUID;

import org.bukkit.NamespacedKey;

public class ProfileData {
  public transient UUID uuid;
  public NamespacedKey hand_ability;
  public ProfileData(UUID uuid) {
    this.uuid = uuid;
  }
}
