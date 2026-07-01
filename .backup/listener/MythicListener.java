package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicPostReloadedEvent;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.bukkit.events.MythicPlayerSignalEvent;
import io.lumine.mythic.bukkit.BukkitAdapter;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.skill.Activator;
import io.phanisment.itemcaster.skill.condition.*;
import io.phanisment.itemcaster.skill.mechanic.*;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.ItemUtil;

public class MythicListener implements Listener {

	@EventHandler
	public void onReload(MythicPostReloadedEvent e) {
		CasterItem.clear();
		ItemCaster.inst().reload();
		CasterLogger.log("[<gradient:#69DFFF:#5984CF>ItemCaster</gradient>] <color:#23eb73>Plugin has finished reloading!");
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onItemGenerate(MythicMobItemGenerateEvent e) {
		CasterItem item = new CasterItem(e.getItem());
		item.applyData(e);
	}

	@EventHandler
	public void onSignal(MythicPlayerSignalEvent e) {
		Player player = BukkitAdapter.adapt(e.getProfile().getEntity().asPlayer());
		ItemUtil.runSkill(player, Activator.SIGNAL, e.getSignal());
	}

	@EventHandler
	public void onMechanic(MythicMechanicLoadEvent event) {
		var config = event.getConfig();
		var add_crossbow_projectile = new AddCrossbowProjectileItemMechanic(config);
		var add_enchantment = new AddEnchantmentItemMechanic(config);
		var add_food_level = new AddFoodLevelMechanic(config);
		var add_food_saturation_level = new AddFoodSaturationLevelMechanic(config);
		var clear_enchantment = new ClearEnchantmentItemMechanic(config);
		var give_item = new GiveItemMechanic(config);
		var reduce_food_level = new ReduceFoodLevelMechanic(config);
		var reduce_food_saturation_level = new ReduceFoodSaturationLevelMechanic(config);
		var set_custom_model_data = new SetCustomModelDataItemMechanic(config);
		var set_food_level = new SetFoodLevelMechanic(config);
		var set_food_saturation_level = new SetFoodSaturationLevelMechanic(config);
		var set_item = new SetItemMechanic(config);
		var set_type_item = new SetTypeItemMechanic(config);
		var consume_mana = new ConsumeManaMechanic(config);
		var set_mana = new SetManaMechanic(config);
		var increase_mana = new IncreaseManaMechanic(config);

		switch (event.getMechanicName().toLowerCase()) {
			case "addamountitem" -> event.register(new AddAmountItemMechanic(config));
			
			case "addcrossbowprojectileitem" -> event.register(add_crossbow_projectile);
			case "crossbowprojectile" -> event.register(add_crossbow_projectile);
			
			case "itemcaster:addenchantmentitem" -> event.register(add_enchantment);
			case "itemcaster:enchantitem" -> event.register(add_enchantment);
			case "itemcaster:enchant" -> event.register(add_enchantment);
			case "addenchantmentitem" -> event.register(add_enchantment);
			case "enchantitem" -> event.register(add_enchantment);
			case "enchant" -> event.register(add_enchantment);
			
			case "addfoodlevel" -> event.register(add_food_level);
			case "addfood"  -> event.register(add_food_level);
			case "feed" -> event.register(add_food_level);

			case "addfoodsaturationlevel" -> event.register(add_food_saturation_level);
			case "addfoodsaturation" -> event.register(add_food_saturation_level);

			case "itemcaster:clearenchantmentsitem"-> event.register(clear_enchantment);
			case "itemcaster:clearenchantments"-> event.register(clear_enchantment);
			case "clearenchantmentsitem" -> event.register(clear_enchantment);
			case "clearenchantments"-> event.register(clear_enchantment);
			case "clearenchant"-> event.register(clear_enchantment);

			case "damageitem" -> event.register(new DamageItemMechanic(config));
			
			case "itemcaster:giveitem" -> event.register(give_item);
			case "itemcaster:give" -> event.register(give_item);
			case "itemcaster:itemgive" -> event.register(give_item);

			case "reduceamountitem" -> event.register(new ReduceAmountItemMechanic(config));
			case "reducefoodlevel" -> event.register(reduce_food_level);
			case "reducefood" -> event.register(reduce_food_level);

			case "reducefoodsaturationlevel" -> event.register(reduce_food_saturation_level);
			case "reducefoodsaturation" -> event.register(reduce_food_saturation_level);

			case "removeenchantmentitem" -> event.register(new RemoveEnchantmentItemMechanic(config));

			case "removehandcaster" -> event.register(new RemoveHandCasterMechanic());

			case "repairitem" -> event.register(new RepairItemMechanic(config));
			
			case "setamountitem" -> event.register(new SetAmountItemMechanic(config));

			case "itemcaster:setcustommodeldataitem" -> event.register(set_custom_model_data);
			case "itemcaster:setmodeldataitem" -> event.register(set_custom_model_data);
			case "itemcaster:modeldataitem" -> event.register(set_custom_model_data);
			case "setcustommodeldataitem" -> event.register(set_custom_model_data);
			case "setmodeldataitem" -> event.register(set_custom_model_data);
			case "modeldataitem" -> event.register(set_custom_model_data);

			case "setdurabilityitem" -> event.register(new SetDurabilityItemMechanic(config));

			case "setfoodlevel" -> event.register(set_food_level);
			case "setfood" -> event.register(set_food_level);

			case "setfoodsaturationlevel" -> event.register(set_food_saturation_level);
			case "setfoodsaturation" -> event.register(set_food_saturation_level);

			case "sethandcaster" -> event.register(new SetHandCasterMechanic(config));

			case "itemcaster:replaceitem" -> event.register(set_item);
			case "itemcaster:setitem" -> event.register(set_item);
			case "replaceitem" -> event.register(set_item);
			case "setitem" -> event.register(set_item);

			case "setmaxdurabilityitem" -> event.register(new SetMaxDurabilityItemMechanic(config));

			case "settypeitem" -> event.register(set_type_item);
			case "setitemtype" -> event.register(set_type_item);
			case "itemtype" -> event.register(set_type_item);

			case "itemcaster:consumemana" -> event.register(consume_mana);
			case "itemcaster:decreasemana" -> event.register(consume_mana);
			case "consumemana" -> event.register(consume_mana);
			case "decreasemana" -> event.register(consume_mana);
			
			case "itemcaster:setmana" -> event.register(set_mana);
			case "setmana" -> event.register(set_mana);

			case "itemcaster:increasemana" -> event.register(increase_mana);
			case "increasemana" -> event.register(increase_mana);
		}
	}

	@EventHandler
	public void onCondition(MythicConditionLoadEvent event) {
		var config = event.getConfig();
		var attack_cooldown = new AttackCooldownCondition();
		var has_food_level = new HasFoodLevelCondition(config);
		var has_food_saturation_level = new HasFoodSaturationLevelCondition(config);
		var is_charged_crossbow_charged = new IsChargedCrossbowCondition(config);
		var mana = new ManaCondition(config);
		var max_mana = new MaxManaCondition(config);
		var consume_mana = new ConsumeManaCondition(config);

		switch (event.getConditionName().toLowerCase()) {
			case "isattackoncooldown" -> event.register(attack_cooldown);
			case "isattackcooldown" -> event.register(attack_cooldown);
			case "attackcooldown" -> event.register(attack_cooldown);
			case "atkcd" -> event.register(attack_cooldown);
			
			case "hasfoodlevel" -> event.register(has_food_level);
			case "foodlevel" -> event.register(has_food_level);
			case "hasfood" -> event.register(has_food_level);

			case "hasfoodsaturationlevel" -> event.register(has_food_saturation_level);
			case "foodsaturationlevel" -> event.register(has_food_saturation_level);
			case "hasfoodsaturation" -> event.register(has_food_saturation_level);

			case "hashandcaster" -> event.register(new HasHandCasterCondition(config));

			case "iscrossbowcharged" -> event.register(is_charged_crossbow_charged);
			case "crossbowcharged" -> event.register(is_charged_crossbow_charged);

			case "itemdurability" -> event.register(new ItemDurabilityCondition(config));

			case "itemcaster:mana" -> event.register(mana);
			case "mana" -> event.register(mana);
			
			case "itemcaster:maxmana" -> event.register(max_mana);
			case "maxmana" -> event.register(max_mana);

			case "itemcaster:consumemana" -> event.register(consume_mana);
			case "itemcaster:decreasemana" -> event.register(consume_mana);
			case "consumemana" -> event.register(consume_mana);
			case "decreasemana" -> event.register(consume_mana);
			case "requiredmana" -> event.register(consume_mana);
		}

		if (Storage.has_auraskills) switch (event.getConditionName().toLowerCase()) {
			case "aurastatslevel" -> event.register(new AuraStatsLevelCondition(config));
			case "auraskilllevel" -> event.register(new AuraSkillLevelCondition(config));
		}
	}
}