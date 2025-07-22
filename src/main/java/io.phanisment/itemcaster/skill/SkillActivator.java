package io.phanisment.itemcaster.skill;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.mobs.GenericCaster;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.players.PlayerData;
import io.lumine.mythic.core.adapters.VirtualEntity;
import io.lumine.mythic.core.mobs.MobExecutor;
import io.lumine.mythic.core.skills.MetaSkill;

import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import de.tr7zw.nbtapi.iface.NBTHandler;

import io.phanisment.itemcaster.util.DebugUtil;
import io.phanisment.itemcaster.util.NbtUtil;
import io.phanisment.itemcaster.ItemCaster;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SkillActivator {
	private static final Map<CasterPlayerData, Integer> skill_interval = new HashMap<>();
	private AbilityMeta meta;
	
	private final ItemStack item;
	private final Player player;
	private final Activator activator;
	private NBTCompound inst;
	private String signal;
	private boolean cancel_event;
	
	public SkillActivator(Player player, ItemStack item, Activator activator) {
		this.item = item;
		this.player = player;
		this.activator = activator;
		if (!validateItem(this.item)) return;
		this.inst = new NBTItem(item).getCompound("ItemCaster");
		
		if (this.inst == null) return;
		
		NBTCompoundList abilities = inst.getCompoundList("abilities");
		abilities.forEach(this::readAbilityAttributes);
	}
	
	/** 
	 * Check if item is not empty.
	 */
	public static boolean validateItem(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static SkillCaster toCaster(Entity entity) {
		if (entity == null) {
			VirtualEntity virtual = new VirtualEntity(BukkitAdapter.adapt(entity.getLocation()));
			return new GenericCaster(virtual);
		}
		
		if (((MobExecutor)ItemCaster.getMobManager()).isActiveMob(entity.getUniqueId())) {
			return ((MobExecutor)ItemCaster.getMobManager()).getMythicMobInstance(entity);
		}
		
		Optional<PlayerData> profile = ItemCaster.getPlayerManager().getProfile(entity.getUniqueId());
		if (profile.isPresent()) {
			return (SkillCaster)profile.get();
		}
		return new GenericCaster(BukkitAdapter.adapt(entity));
	}
	
	private void readAbilityAttributes(ReadableNBT ability) {
		String skill = ability.getString("skill");
		String event = ability.getString("activator").toUpperCase();
		
		if (skill.isEmpty()) return;
		if (event.equals(activator.toString())) {
			SkillCaster sc = toCaster(player);
			CasterPlayerData pc = new CasterPlayerData(player.getUniqueId(), skill);
			Optional<Skill> ms = pc.getSkill();
			if (!ms.isPresent()) return;
			MetaSkill sk = (MetaSkill)ms.get();
			
			if (ability.hasTag("cancel_event")) cancel_event = ability.getBoolean("cancel_event");
			if (sk.onCooldown(sc)) return;
			if (this.isSneak(ability, player)) return;
			if (event.equals("SIGNAL") && !this.isSignalEquals(ability)) return;
			if (event.equals("TICK") && !this.canTriggerTick(pc, ability)) return;
			
			this.meta = new AbilityMeta(sk, player)
				.setPower(this.getNbtPower(ability))
				.setCooldown(this.getNbtCooldown(ability))
				.setVariables(ability.getCompound("variables"));
			this.meta.execute();
		}
	}
	
	private float getNbtPower(ReadableNBT nbt) {
		return NbtUtil.getFloatSafe(nbt, "power", 1.0F);
	}
	
	private double getNbtCooldown(ReadableNBT nbt) {
		return NbtUtil.getDoubleSafe(nbt, "cooldown", 0D);
	}
	
	private int getNbtInterval(ReadableNBT nbt) {
		return NbtUtil.getIntSafe(nbt, "interval", 0);
	}
	
	private boolean isSneak(ReadableNBT nbt, Player player) {
		if (!nbt.hasTag("sneaking")) return false;
		return nbt.getBoolean("sneaking") != player.isSneaking();
	}
	
	private boolean isSignalEquals(ReadableNBT nbt) {
		if (!nbt.hasTag("signal", NBTType.NBTTagString)) return false;
		String i_signal = nbt.getString("signal");
		return i_signal.equals(this.signal);
	}
	
	private boolean canTriggerTick(CasterPlayerData caster, ReadableNBT nbt) {
		int interval = this.getNbtInterval(nbt);
		int count = skill_interval.getOrDefault(caster, 0);
		if (count > 0) {
			skill_interval.put(caster, count - 1);
			return false;
		}
		skill_interval.put(caster, interval);
		return true;
	}
	
	public void setSignal(String signal) {
		this.signal = signal;
	}
	
	public boolean getCancelEvent() {
		return this.cancel_event;
	}
	
	public static record CasterPlayerData(UUID uuid, String skill) {
		public Optional<Skill> getSkill() {
			return ItemCaster.getSkillManager().getSkill(skill);
		}
	}
	
	public static enum Activator {
		LEFT_CLICK,
		RIGHT_CLICK,
		INTERACT_ENTITY,
		DROP,
		PICKUP,
		DAMAGED,
		ATTACK,
		TOGGLE_SNEAK,
		SNEAK,
		UNSNEAK,
		CONSUME,
		BOW_SHOOT,
		DEATH,
		LOGIN,
		QUIT,
		CHANGE_SLOT,
		ITEM_BREAK,
		FISHING,
		TOGGLE_SPRINT,
		SPRINT,
		UNSPRINT,
		BLOCK_PLACE,
		BLOCK_BREAK,
		BLOCK_DAMAGED,
		BLOCK_STOP_DAMAGED,
		TELEPORT,
		TICK,
		SIGNAL;
		
		public static Activator value(String name) {
			return Enum.valueOf(Activator.class, name);
		}
	}
}