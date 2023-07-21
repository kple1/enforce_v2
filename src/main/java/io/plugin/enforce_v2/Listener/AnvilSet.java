package io.plugin.enforce_v2.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import static io.plugin.enforce_v2.Main.plugin;

public class AnvilSet implements Listener {

    @EventHandler
    public void anvilSet(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (event.getBlock().getType() != Material.ANVIL) {
            return;
        }

        ItemStack loreValue = event.getItemInHand();
        if (loreValue.equals("[특수판별 코드 : 0a]")) {
            plugin.getConfig().set("모루인식.X", event.getBlock().getX());
            plugin.getConfig().set("모루인식.Y", event.getBlock().getY());
            plugin.getConfig().set("모루인식.Z", event.getBlock().getZ());
            plugin.saveConfig();
        } else {
            player.sendMessage("error code : 2");
        }
    }
}
