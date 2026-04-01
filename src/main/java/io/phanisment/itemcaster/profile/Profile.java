package io.phanisment.itemcaster.profile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.adapter.IdentiferAdapter;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Identifier;

public class Profile {
	private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Identifier.class, new IdentiferAdapter()).create();
	private static final File SAVE_PATH_FILE = new File(ItemCaster.inst().getDataFolder(), ".data/players");
	private final UUID uuid;

	private ProfileData data;

	public Profile(Player player) {
		this((OfflinePlayer)player);
	}

	public Profile(OfflinePlayer player) {
		this.uuid = player.getUniqueId();
		this.data = new ProfileData(uuid);
	}

	public Optional<ProfileData> getData() {
		return data != null ? Optional.of(data) : Optional.empty();
	}

	public void setAbility(Identifier id) {
		this.data.hand_ability = id;
		save();
	}

	private File file() {
		if (!SAVE_PATH_FILE.exists()) SAVE_PATH_FILE.mkdirs();
		return new File(SAVE_PATH_FILE, uuid.toString() + ".json");
	}

	public void load() {
		var file = file();
		if (!file.exists()) {
			save();
			return;
		}

		try (var reader = new FileReader(file)) {
			var new_data = GSON.fromJson(reader, ProfileData.class);
			if (new_data != null) this.data = new_data;
		} catch (IOException err) {
			CasterLogger.error("[{0}] Failed to load player data", uuid);
		}
	}

	public void save() {
		try (var writer = new FileWriter(file())) {
			GSON.toJson(this.data, writer);
		} catch (Exception e) {
			CasterLogger.error("[{0}] Failed to save player data", uuid);
		}
	}
}