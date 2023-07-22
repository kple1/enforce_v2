package io.plugin.enforce_v2.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static io.plugin.enforce_v2.Main.plugin;

public class AnvilSet implements Listener {

    @EventHandler
    public void anvilSet(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.ANVIL) {
            return;
        }

        ItemStack loreValue = event.getItemInHand();
        ItemMeta itemMeta = loreValue.getItemMeta();
        List<String> getLore = itemMeta.getLore();

        if (getLore == null) {
            return;
        }

        if (getLore.size() > 0 && getLore.get(0).equals("특수판별 코드 : 0a")) {
            plugin.getConfig().set("모루인식.X", event.getBlock().getX());
            plugin.getConfig().set("모루인식.Y", event.getBlock().getY());
            plugin.getConfig().set("모루인식.Z", event.getBlock().getZ());
            plugin.saveConfig();
        }
    }
}
