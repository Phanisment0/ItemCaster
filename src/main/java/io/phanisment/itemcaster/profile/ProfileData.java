package io.phanisment.itemcaster.profile;

import java.util.UUID;

import io.phanisment.itemcaster.util.Identifier;

public class ProfileData {
	public transient UUID uuid;
	
	public Identifier hand_ability;
	public boolean hand_toggle = true;
	public ProfileData(UUID uuid) {
		this.uuid = uuid;
	}
}
