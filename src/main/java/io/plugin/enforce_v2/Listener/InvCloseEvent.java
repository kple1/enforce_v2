package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvCloseEvent implements Listener {

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("강화")) {
            return;
        }

        Player player = (Player) event.getPlayer();
        YamlConfiguration config = UserData.getPlayerConfig(player);
        config.set("Lock", 0);
        config.set("itemInfo.1.anotherEnchant", null);
        config.set("itemInfo.2.anotherEnchant", null);
        config.set("data", 0);
        Main.getPlugin().saveYamlConfiguration();
    }
}
