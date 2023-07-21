package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import static io.plugin.enforce_v2.Main.plugin;

public class AnvilClick implements Listener {

    private Inventory inv;

    @EventHandler
    public void onAnvilClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getClickedBlock().getType() != Material.ANVIL) return;

        if (
                event.getClickedBlock().getX() != plugin.getConfig().getInt("X") &&
                        event.getClickedBlock().getY() != plugin.getConfig().getInt("Y") &&
                        event.getClickedBlock().getZ() != plugin.getConfig().getInt("Z")
        ) return;

        this.inv = Bukkit.createInventory(null, 36, "강화");
        for (int i = 0; i < 36; i++) {
            inv.setItem(i, ItemBuild.blackGlass);
        }
        inv.setItem(12 , ItemBuild.AIR);
        inv.setItem(14 , ItemBuild.AIR);
        inv.setItem(16 , ItemBuild.diamond);
        inv.setItem(18 , ItemBuild.AIR);
    }
}
