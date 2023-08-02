/*
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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Click14SlotMagicEnchant {

    private int taskId;

    public void magicEnchant(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String get12DisplayName = event.getView().getItem(12).getItemMeta().getDisplayName();

        if (!get12DisplayName.equals("§f[§d마법강화권§f]")) {
            return;
        }

        startTimerClick14SlotMagicEnchant(event.getInventory(), event, player);
    }

    public void startTimerClick14SlotMagicEnchant(Inventory inv, InventoryClickEvent event, Player player) {
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
                    end(event, event.getInventory(), player);
                }
                time++;
            }
        }.runTaskTimer(Main.getPlugin(), 0, 20).getTaskId(); // 1초마다 실행
    }

    public void end(InventoryClickEvent event, Inventory inv, Player player) {
        ItemStack itemStack = event.getView().getItem(10);
        ItemMeta itemMeta = itemStack.getItemMeta();

        int num = 0;
        String pattern = "\\d+";
        Pattern p = Pattern.compile(pattern);

        String lore = event.getView().getItem(12).getItemMeta().getLore().toString();
        if (lore == null) {
            return;
        }
        Matcher matcher = p.matcher(lore);

        String numericPartLevel = "";

        if (matcher.find()) {
            numericPartLevel = matcher.group();
        }

        num = Integer.parseInt(numericPartLevel);

        Map<Enchantment, Integer> getEnchantments = event.getView().getItem(12).getEnchantments();
        for (Map.Entry<Enchantment, Integer> entry : getEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            itemMeta.addEnchant(enchantment, num, true);
        }
        itemStack.setItemMeta(itemMeta);
        inv.setItem(16, itemStack);
    }
}
*/
