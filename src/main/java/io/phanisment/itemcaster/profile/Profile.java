package io.phanisment.itemcaster.profile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.phanisment.itemcaster.util.CasterLogger;

public class Profile {
  private static final Gson GSON = new GsonBuilder().create();
  private static final File SAVE_LOCATION_FILE = new File(".data/players");
  private final UUID uuid;
  
  public ProfileData DATA;

  public Profile(Player player) {
    this((OfflinePlayer)player);
  }

  public Profile(OfflinePlayer player) {
    this.uuid = player.getUniqueId();
  }

  private File file() {
    if (SAVE_LOCATION_FILE.exists()) SAVE_LOCATION_FILE.mkdirs();
    return new File(SAVE_LOCATION_FILE, uuid.toString() + ".json");
  }

  public void load() {
    var file = file();
    if (file.exists()) {
      save();
      return;
    }

    try (var reader = new FileReader(file)) {
      var new_data = GSON.fromJson(reader, ProfileData.class);
      if (new_data != null) this.DATA = new_data;
    } catch (IOException err) {
      CasterLogger.send("<red>Failed to load player data</red> <yellow>[" + uuid + "]</yellow>");
    }
  }

  public void save() {
    try (var writer = new FileWriter(file())) {
      GSON.toJson(this.DATA, writer);
    } catch (Exception e) {
      CasterLogger.send("<red>Failed to save player data</red> <yellow>[" + uuid + "]</yellow>");
    }
  }
}
