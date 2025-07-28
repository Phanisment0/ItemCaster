package io.phanisment.itemcaster.skill.mechanic;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.phanisment.itemcaster.util.CasterLogger;

public class GetItemMechanic extends ItemMechanic {

    public GetItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
        super(manager, file, line, mlc);
    }

    @Override
    public ItemStack getItem() {
        CasterLogger.info("Change to mace");
        return new ItemStack(Material.MACE);
    }

}
