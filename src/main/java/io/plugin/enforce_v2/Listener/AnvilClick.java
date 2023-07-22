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

import static io.plugin.enforce_v2.Main.plugin;

public class AnvilClick implements Listener {

    private final Inventory customInventory;

    public AnvilClick() {
        this.customInventory = Bukkit.createInventory(null, 36, "강화");
        for (int i = 0; i < 36; i++) {
            customInventory.setItem(i, ItemBuild.blackGlass);
        }
        customInventory.setItem(10 , ItemBuild.AIR);
        customInventory.setItem(12 , ItemBuild.AIR);
        customInventory.setItem(14 , ItemBuild.diamond);
        customInventory.setItem(16 , ItemBuild.AIR);
    }

    @EventHandler
    public void onAnvilClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getClickedBlock().getType() != Material.ANVIL) return;

        if (event.getClickedBlock().getX() != plugin.getConfig().getInt("모루인식.X") ||
                event.getClickedBlock().getY() != plugin.getConfig().getInt("모루인식.Y") ||
                event.getClickedBlock().getZ() != plugin.getConfig().getInt("모루인식.Z")) return;

        event.setCancelled(true); // 기본 모루 인터페이스가 열리지 않도록 이벤트 취소
        player.openInventory(customInventory);
    }
}
