package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class AnvilClick implements Listener {

    private Inventory customInventory;

    @EventHandler
    public void onAnvilClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getClickedBlock().getType() != Material.ANVIL) return;

        event.setCancelled(true);
        this.customInventory = Bukkit.createInventory(null, 36, "강화");
        for (int i = 0; i < 36; i++) {
            customInventory.setItem(i, ItemBuild.blackGlass);
        }
        customInventory.setItem(10, ItemBuild.AIR);
        customInventory.setItem(12, ItemBuild.AIR);
        customInventory.setItem(14, ItemBuild.diamond);
        customInventory.setItem(16, ItemBuild.AIR);
        player.openInventory(customInventory);
    }
}
