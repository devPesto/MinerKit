package io.github.pesto.minerkit;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class MinerKit extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Enabling MinerKit v" + getDescription().getVersion());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling MinerKit v" + getDescription().getVersion());
    }

    @EventHandler
    public void onArmorEquip(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getNewItem();

        // Removing armor or changing armor to non-iron armor
        if (item == null || !MINER_KIT.contains(item.getType())) {
            for (PotionEffect e : EFFECTS)
                player.removePotionEffect(e.getType());
        } else
            applyKitEffects(player);
    }

    /**
     * Check if player has all items equipped. If they do,
     * apply effects
     */
    private void applyKitEffects(@NotNull Player player) {
        for (EquipmentSlot slot : SLOTS) {
            ItemStack armor = player.getEquipment().getItem(slot);
            if (!MINER_KIT.contains(armor.getType())) return;
        }
        player.addPotionEffects(EFFECTS);
    }

    private static final Set<Material> MINER_KIT = Set.of(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS);

    private static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    private static final Set<PotionEffect> EFFECTS = Set.of(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false, false), new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false), new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, false, false));
}
