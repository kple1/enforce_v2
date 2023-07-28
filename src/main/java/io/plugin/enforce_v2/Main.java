package io.plugin.enforce_v2;

import io.plugin.enforce_v2.Command.EnforceAnvilCreate;
import io.plugin.enforce_v2.Command.EnforceResetTicket;
import io.plugin.enforce_v2.Command.GiveEnforceTicket;
import io.plugin.enforce_v2.Command.StatEnforceTicketCreate;
import io.plugin.enforce_v2.Listener.AnvilClick;
import io.plugin.enforce_v2.Listener.AnvilSet;
import io.plugin.enforce_v2.Listener.InvClickEvent;
import io.plugin.enforce_v2.Listener.InvCloseEvent;
import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.plugin.enforce_v2.Data.UserData.config;
import static io.plugin.enforce_v2.Data.UserData.playerFile;

public final class Main extends JavaPlugin {

    public static Main plugin;
    private int taskId;
    private File uuidFolder;
    String title = Color.chat("&f[ &c&l강화 &f] ");

    public void Commands() {
        Bukkit.getPluginCommand("enforceTicket").setExecutor(new GiveEnforceTicket());
        Bukkit.getPluginCommand("statEnforceTicket").setExecutor(new StatEnforceTicketCreate());
        Bukkit.getPluginCommand("enforceResetTicket").setExecutor(new EnforceResetTicket());
        Bukkit.getPluginCommand("enforceAnvilCreate").setExecutor(new EnforceAnvilCreate());
    }

    public void Listener() {
        Bukkit.getPluginManager().registerEvents(new AnvilSet(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilClick(), this);
        Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InvCloseEvent(), this);
    }

    @Override
    public void onEnable() {
        plugin = this;
        Commands();
        Listener();
    }

    @Override
    public void onDisable() {

    }

    public static Main getPlugin() {
        return plugin;
    }

    public void startTimer(Inventory inv, InventoryClickEvent event) {
        taskId = new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time >= 1 && time <= 9) {
                    inv.setItem(26 + time, ItemBuild.yellowGlass);
                }

                if (time == 9) {
                    setItemIsEnd(event);
                    this.cancel();
                    return;
                }
                time++;
            }
        }.runTaskTimer(this, 0, 20).getTaskId(); // 1초마다 실행
    }

    public void setItemIsEnd(InventoryClickEvent event) {
        //displayName Change
        int num = 0;
        ItemStack itemStack = event.getView().getItem(10);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String displayName = itemMeta.getDisplayName();

        String pattern = "\\d+";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(displayName);

        if (matcher.find()) {
            String numericPart = matcher.group();
            num = Integer.parseInt(numericPart);
            num = num + 1;
        } else {
            num = 1;
        }

        //enchantLevel Change
        int enchantLevel = 0;
        Map<Enchantment, Integer> enchantments = event.getView().getItem(12).getEnchantments();
        if (!enchantments.isEmpty()) {
            enchantLevel = enchantments.entrySet().iterator().next().getValue();
        }

        //enchant Change
        itemMeta.setDisplayName(Color.chat("&f[&d&l" + num + "강&f] 다이아몬드 검"));
        Map<Enchantment, Integer> getEnchantments = event.getView().getItem(12).getEnchantments();

        for (Map.Entry<Enchantment, Integer> entry : getEnchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            int level = entry.getValue();
            itemMeta.addEnchant(enchantment, level, false);
        }
        itemStack.setItemMeta(itemMeta);
        //결과 도출
        event.getView().setItem(16, itemStack);
        //강화가 완료됨. 10, 12슬롯 삭제
        if (event.getView().getItem(16) != null) {
            event.getView().setItem(10, ItemBuild.AIR);
            event.getView().setItem(12, ItemBuild.AIR);
        }
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
