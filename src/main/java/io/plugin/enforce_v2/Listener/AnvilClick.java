package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

import static io.plugin.enforce_v2.Main.plugin;

public class AnvilClick implements Listener {

    @EventHandler
    public void onAnvilClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.ANVIL) return;

        // 현재 클릭한 블록의 위치
        int clickedBlockX = event.getClickedBlock().getLocation().getBlockX();
        int clickedBlockY = event.getClickedBlock().getLocation().getBlockY();
        int clickedBlockZ = event.getClickedBlock().getLocation().getBlockZ();

        Set<String> installAnvilSet = plugin.getConfig().getConfigurationSection("installAnvil").getKeys(false);
        for (String key : installAnvilSet) {
            if (key == null) {
                continue;
            }
            int X = plugin.getConfig().getInt("installAnvil." + key + ".X");
            int Y = plugin.getConfig().getInt("installAnvil." + key + ".Y");
            int Z = plugin.getConfig().getInt("installAnvil." + key + ".Z");

            if (clickedBlockX == X && clickedBlockY == Y && clickedBlockZ == Z) {
                event.setCancelled(true);
                Main.getPlugin().inventory(player);
                break;
            }
        }
    }
}
