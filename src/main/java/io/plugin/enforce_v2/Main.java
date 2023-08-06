package io.plugin.enforce_v2;

import io.plugin.enforce_v2.Command.*;
import io.plugin.enforce_v2.Data.UserData;
import io.plugin.enforce_v2.Listener.*;
import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.plugin.enforce_v2.Data.UserData.config;
import static io.plugin.enforce_v2.Data.UserData.playerFile;

public final class Main extends JavaPlugin {

    public static Main plugin;
    private File uuidFolder;
    String title = Color.chat("&f[ &c&l강화 &f] ");

    public void Commands() {
        Bukkit.getPluginCommand("enforceTicket").setExecutor(new GiveEnforceTicket());
        Bukkit.getPluginCommand("enforceAnvilCreate").setExecutor(new EnforceAnvilCreate());
    }

    public void Listener() {
        Bukkit.getPluginManager().registerEvents(new AnvilClick(), this);
        Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InvCloseEvent(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilSet(plugin), this);
    }

    @Override
    public void onEnable() {
        plugin = this;
        Commands();
        Listener();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public void startTimerClick14SlotNormalEnchant(Inventory inv, InventoryClickEvent event, Player player) {
        int taskId = new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time == 1) {
                    inv.setItem(26 + time, ItemBuild.redGlass);
                    inv.setItem(27 + time, ItemBuild.redGlass);
                    inv.setItem(28 + time, ItemBuild.redGlass);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 1);
                }

                if (time == 2) {
                    inv.setItem(28 + time, ItemBuild.yellowGlass);
                    inv.setItem(29 + time, ItemBuild.yellowGlass);
                    inv.setItem(30 + time, ItemBuild.yellowGlass);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 1);
                }

                if (time == 3) {
                    inv.setItem(30 + time, ItemBuild.greenGlass);
                    inv.setItem(31 + time, ItemBuild.greenGlass);
                    inv.setItem(32 + time, ItemBuild.greenGlass);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 1);
                }

                if (time == 4) {
                    YamlConfiguration config = UserData.getPlayerConfig(player);
                    config.set("Lock", 0);
                    saveYamlConfiguration();
                    for (int i = 27; i < 36; i++) {
                        inv.setItem(i, ItemBuild.blackGlass);
                    }
                    setItemIsEnd(event, player);
                    this.cancel();
                    return;
                }
                time++;
            }
        }.runTaskTimer(this, 0, 20).getTaskId(); // 1초마다 실행
    }

    private String getItemName(ItemStack item) {
        String itemName = item.getType().toString().toLowerCase();
        return itemName.replace("", "");
    }

    public void setItemIsEnd(InventoryClickEvent event, Player player) {
        ItemStack itemStack = event.getView().getItem(10);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(Color.chat("&f[&d&l마법&f] " + getItemName(itemStack)));

        Map<Enchantment, Integer> getEnchantments = event.getView().getItem(12).getEnchantments();

        for (Map.Entry<Enchantment, Integer> entry : getEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            itemMeta.addEnchant(enchantment, level, true);
        }
        itemStack.setItemMeta(itemMeta);

        //결과 도출
        double probability = config.getDouble("itemInfo.2.percent") / 100.0; // 0부터 1 사이의 값으로 변환
        if (Math.random() < probability) {
            event.getView().setItem(16, itemStack);
            player.sendMessage(title + "강화에 성공 하셨습니다!");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
        } else {
            player.sendMessage(title + "강화에 실패 하셨습니다");
            event.getView().setItem(16, ItemBuild.backUp(event));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            return;
        }

        //강화가 완료됨. 10, 12슬롯 삭제
        if (event.getView().getItem(16) != null) {
            event.getView().setItem(10, ItemBuild.AIR);
            removeItemsFromMainHand(event, 1);
            return;
        }
    }

    public void removeItemsFromMainHand(InventoryClickEvent event, int amountToRemove) {
        ItemStack itemToRemove = event.getInventory().getItem(12).clone();
        itemToRemove.setAmount(amountToRemove);
        event.getInventory().removeItem(itemToRemove);
    }

    public File getUuidFolder() {
        return uuidFolder;
    }

    public void createPlayerDefaults() {
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.options().copyDefaults(true);
        saveYamlConfiguration();
    }

    public void saveYamlConfiguration() {
        try {
            config.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
