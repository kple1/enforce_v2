package io.plugin.enforce_v2.Listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilSet implements Listener {

    private final JavaPlugin plugin;

    public AnvilSet(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerBlockPlaceEvent(BlockPlaceEvent event) {
        if (!(event.getBlock().getType() == Material.ANVIL)) {
            return;
        }

        ItemStack item = event.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§7[ 강화전용 모루 ]")) {
            int nextAvailableIndex = getNextAvailableIndex();
            plugin.getConfig().set("installAnvil." + nextAvailableIndex + ".X", event.getBlock().getX());
            plugin.getConfig().set("installAnvil." + nextAvailableIndex + ".Y", event.getBlock().getY());
            plugin.getConfig().set("installAnvil." + nextAvailableIndex + ".Z", event.getBlock().getZ());
            plugin.saveConfig();
        }
    }

    private int getNextAvailableIndex() {
        int nextAvailableIndex = 0;
        while (plugin.getConfig().getString("installAnvil." + nextAvailableIndex) != null) {
            nextAvailableIndex++;
        }
        return nextAvailableIndex;
    }
}
