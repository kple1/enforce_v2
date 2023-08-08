package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Main;
import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.plugin.enforce_v2.Main.plugin;

public class InvCloseEvent implements Listener {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @EventHandler
    public void invCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        YamlConfiguration config = UserData.getPlayerConfig(player);

        //데이터 초기화
        if (!event.getView().getTitle().equals("강화")) return;
        if (config.getInt("Lock") == 1) return;

        config.set("Lock", 0);
        config.set("itemInfo.1.anotherEnchant", null);
        config.set("itemInfo.2.anotherEnchant", null);
        config.set("data", 0);
        Main.getPlugin().saveYamlConfiguration();

        ItemStack getItem10Slot = event.getView().getItem(10);
        ItemStack getItem12Slot = event.getView().getItem(12);
        ItemStack getItem16Slot = event.getView().getItem(16);

        if (getItem10Slot != null) {
            player.getInventory().addItem(getItem10Slot);
        }

        if (getItem12Slot != null) {
            player.getInventory().addItem(getItem12Slot);
        }

        if (getItem16Slot != null) {
            player.getInventory().addItem(getItem16Slot);
        }
    }

    @EventHandler
    public void loadToEnforceCancelledCloseInventory(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        YamlConfiguration config = UserData.getPlayerConfig(player);

        if (event.getView().getTitle().equals("강화")) {
            if (config.getInt("Lock") != 1) return;

            Inventory inventory = event.getInventory();

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                event.getPlayer().openInventory(inventory);
            }, 1);
            player.sendMessage(title + "강화진행 중에는 인벤토리를 닫을 수 없습니다!");
        }
    }
}
