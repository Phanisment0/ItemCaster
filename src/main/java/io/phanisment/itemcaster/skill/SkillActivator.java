package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractLocation;
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

import io.phanisment.itemcaster.util.NbtSafe;
import io.phanisment.itemcaster.util.ItemUtil;
import io.phanisment.itemcaster.ItemCaster;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class SkillActivator {
	private static final Map<CasterPlayerData, Integer> skill_interval = new HashMap<>();
	
	private final ItemStack item;
	private final Player player;
	private final Activator activator;
	private NBTCompound inst;
	private String signal;
	private boolean cancel_event;
	
	@SuppressWarnings("deprecation")
	public SkillActivator(Player player, ItemStack item, Activator activator) {
		this.item = item;
		this.player = player;
		this.activator = activator;
		if (!ItemUtil.validateItem(this.item)) return;
		this.inst = new NBTItem(item).getCompound("ItemCaster");
		
		if (this.inst == null) return;
		
		NBTCompoundList abilities = inst.getCompoundList("abilities");
		abilities.forEach(this::readAbilityAttributes);
	}
	
	public static SkillCaster toCaster(Entity entity) {
		if (entity == null) {
			VirtualEntity virtual = new VirtualEntity(new AbstractLocation());
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
		
		if (!skill.isEmpty() && event.equals(activator.toString())) {
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
			
			var exc = new SkillExecutor(sk, player);
			exc.setPower(this.getNbtPower(ability));
			exc.setCooldown(this.getNbtCooldown(ability));
			exc.setVariables(ability.getCompound("variables"));
			exc.execute();
		}
	}
	
	private float getNbtPower(ReadableNBT nbt) {
		return NbtSafe.getFloatSafe(nbt, "power", 1.0F);
	}
	
	private double getNbtCooldown(ReadableNBT nbt) {
		return NbtSafe.getDoubleSafe(nbt, "cooldown", 0D);
	}
	
	private int getNbtInterval(ReadableNBT nbt) {
		return NbtSafe.getIntSafe(nbt, "interval", 0);
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
		PICKUP_EXP,
		
		ATTACK,
		DAMAGED,
		DAMAGED_BY_BLOCK_EXPLOSION,
		DAMAGED_BY_CAMPFIRE,
		DAMAGED_BY_CONTACT,
		DAMAGED_BY_CRAMMING,
		DAMAGED_BY_CUSTOM,
		DAMAGED_BY_DRAGON_BREATH,
		DAMAGED_BY_DROWNING,
		DAMAGED_BY_ENTITY_ATTACK,
		DAMAGED_BY_ENTITY_EXPLOSION,
		DAMAGED_BY_ENTITY_SWEEP_ATTACK,
		DAMAGED_BY_FALL,
		DAMAGED_BY_FALLING_BLOCK,
		DAMAGED_BY_FIRE,
		DAMAGED_BY_FIRE_TICK,
		DAMAGED_BY_FLY_INTO_WALL,
		DAMAGED_BY_FREEZE,
		DAMAGED_BY_HOT_FLOOR,
		DAMAGED_BY_KILL,
		DAMAGED_BY_LAVA,
		DAMAGED_BY_LIGHTNING,
		DAMAGED_BY_MAGIC,
		DAMAGED_BY_POISON,
		DAMAGED_BY_PROJECTILE,
		DAMAGED_BY_SONIC_BOOM,
		DAMAGED_BY_STARVATION,
		DAMAGED_BY_SUFFOCATION,
		DAMAGED_BY_SUICIDE,
		DAMAGED_BY_THORNS,
		DAMAGED_BY_VOID,
		DAMAGED_BY_WITHER,
		DAMAGED_BY_WORLD_BORDER,
		
		TOGGLE_SNEAK,
		SNEAK,
		UNSNEAK,
		
		SHIELD_DISABLED,
		STOP_USE_ITEM,
		CONSUME,
		
		BOW_SHOOT,
		PROJECTILE_HIT,
		
		DEATH,
		
		LOGIN,
		QUIT,
		
		CHANGE_SLOT,
		SWAP_HAND,
		
		ITEM_BREAK,
		
		FISHING,
		FISH_BITE,
		FISH_CAUGHT_ENTITY,
		FISH_CAUGHT_FISH,
		FISH_FAILED_ATTEMPT,
		FISH_FISHING,
		FISH_IN_GROUND,
		FISH_REEL_IN,
		
		TOGGLE_SPRINT,
		SPRINT,
		UNSPRINT,
		
		BLOCK_PLACE,
		BLOCK_BREAK,
		BLOCK_DAMAGED,
		BLOCK_STOP_DAMAGED,
		
		ARMOR_CHANGE,
		ELYRA_BOOST,
		
		TELEPORT,
		TELEPORTED_BY_CHORUS_FRUIT,
		TELEPORTED_BY_COMMAND,
		TELEPORTED_BY_DISMOUNT,
		TELEPORTED_BY_END_GATEWAY,
		TELEPORTED_BY_END_PORTAL,
		TELEPORTED_BY_ENDER_PEARL,
		TELEPORTED_BY_EXIT_BED,
		TELEPORTED_BY_NETHER_PORTAL,
		TELEPORTED_BY_PLUGIN,
		TELEPORTED_BY_SPECTATE,
		TELEPORTED_BY_UNKNOWN,
		
		CHANGE_WORLD,
		
		TICK,
		SIGNAL;
	}
}