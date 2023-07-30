package io.plugin.enforce_v2.Utils;

import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Click14SlotStatEnforce {

    String title = Color.chat("&f[ &c&l강화 &f] ");
    private int taskId;

    public void statEnforce(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        //스텟권 감지
        ItemStack Slot12Item = event.getView().getItem(12);
        for (int i = 0; i < 7; i++) {
            if (!(Slot12Item != null && Slot12Item.hasItemMeta() && Slot12Item.getItemMeta().getDisplayName().equals("스텟강화권"))) {
                return;
            }
        }

        //2차 강화 여부
        ItemStack item = event.getView().getItem(10);
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasEnchants()) {
            Map<Enchantment, Integer> enchantments = item.getItemMeta().getEnchants();
            if (enchantments.size() < 2) {
                player.sendMessage(title + "마법부여가 완료되지 않았습니다.");
                return;
            }
        } else {
            player.sendMessage(title + "마법부여가 완료되지 않았습니다.");
            return;
        }
        player.sendMessage("clear");
        startTimerClick14SlotNormalEnchant(event.getInventory(), event, player);
    }

    public void startTimerClick14SlotNormalEnchant(Inventory inv, InventoryClickEvent event, Player player) {
        taskId = new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time >= 1 && time <= 9) {
                    inv.setItem(26 + time, ItemBuild.yellowGlass);
                }

                if (time == 9) {
                    YamlConfiguration config = UserData.getPlayerConfig(player);
                    config.set("Lock", 0);
                    Main.getPlugin().saveYamlConfiguration();
                    for (int i = 27; i < 36; i++) {
                        inv.setItem(i, ItemBuild.blackGlass);
                    }

                    if (event.getView().getItem(16) != null) {
                        event.getView().setItem(10, ItemBuild.AIR);
                        event.getView().setItem(12, ItemBuild.AIR);
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20).getTaskId(); // 1초마다 실행
    }

    public void probabilities(InventoryClickEvent event) {

    }
}