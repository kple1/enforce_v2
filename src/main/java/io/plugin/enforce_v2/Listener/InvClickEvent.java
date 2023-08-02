package io.plugin.enforce_v2.Listener;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Utils.Click14SlotNormalEnchant;
import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClickEvent implements Listener {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @EventHandler
    public void invClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equals("강화")) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (event.getClickedInventory().equals(player.getInventory())) {
            return;
        }

        if (event.getRawSlot() >= event.getInventory().getSize()) {
            return;
        }

        YamlConfiguration config = UserData.getPlayerConfig(player);
        int getLock = config.getInt("Lock");
        for (int i = 0; i < 36; i++) {
            if (getLock == 0) {
                if (i == 10 || i == 12 || i == 16) {
                    continue;
                }
            } else {
                player.sendMessage(title + "강화도중에는 아이템을 꺼낼 수 없습니다.");
                event.setCancelled(true);
                break;
            }

            if (event.getRawSlot() == i) {
                event.setCancelled(true);
            }
        }

        if (event.getSlot() == 14) {
            if (event.getView().getItem(10) == null || event.getView().getItem(12) == null) {
                player.sendMessage(title + "강화할 아이템을 올려주세요!");
                return;
            }
            //기본 인챈트
            Click14SlotNormalEnchant click14SlotNormalEnchant = new Click14SlotNormalEnchant();
            click14SlotNormalEnchant.normalEnchant(event);
        }
    }
}
