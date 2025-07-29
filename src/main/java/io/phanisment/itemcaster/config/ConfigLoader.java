package io.phanisment.itemcaster.config;

import java.io.File;

import com.google.gson.Gson;
import io.phanisment.itemcaster.ItemCaster;

public class ConfigLoader {
  private static final Gson gson = new Gson();
  private File config;

  public ConfigLoader() {
    this.config = new File(ItemCaster.inst().getDataFolder(), "config.json");
  }

  public void load() {
    if (!config.exists()) ItemCaster.inst().saveResource("configs.json", false);
    
  }
}
