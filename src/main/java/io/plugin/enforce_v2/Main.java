package io.plugin.enforce_v2;

import io.plugin.enforce_v2.Command.EnforceAnvilCreate;
import io.plugin.enforce_v2.Command.EnforceResetTicket;
import io.plugin.enforce_v2.Command.GiveEnforceTicket;
import io.plugin.enforce_v2.Command.StatEnforceTicketCreate;
import io.plugin.enforce_v2.Listener.AnvilClick;
import io.plugin.enforce_v2.Listener.AnvilSet;
import io.plugin.enforce_v2.Listener.InvClickEvent;
import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class Main extends JavaPlugin {

    public static Main plugin;
    private int taskId;
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
                    createNewEnforce(event);
                    this.cancel();
                    return;
                }
                time++;
            }
        }.runTaskTimer(this, 0, 20).getTaskId(); // 1초마다 실행
    }

    public void createNewEnforce(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack itemInSlot10 = event.getView().getItem(10);
        ItemStack itemInSlot12 = event.getView().getItem(12);
        if (itemInSlot10 == null || itemInSlot12 == null) {
            return;
        }

        ItemMeta itemMeta10 = itemInSlot10.getItemMeta();
        ItemMeta itemMeta12 = itemInSlot12.getItemMeta();
        if (itemMeta10 == null || itemMeta12 == null) {
            return;
        }

        List<String> getLore10 = itemMeta10.getLore();
        List<String> getLore12 = itemMeta12.getLore();
        if (getLore10 == null || getLore12 == null) {
            return;
        }

        String loreLine12 = getLoreLineContainingNumber(getLore12);
        if (loreLine12 == null) {
            return; // No line with a number found in gerLore12
        }

        // Extract the number from the lore line in gerLore12
        int extractedNumber12;
        try {
            extractedNumber12 = extractNumberFromLoreLine(loreLine12);
        } catch (NumberFormatException e) {
            return; // The extracted number is not valid
        }

        // Find and compare the "최대 강화 : 숫자" line in gerLore10
        String maxEnchantLinePattern = "최대 강화 : \\d+";
        for (String line : getLore10) {
            if (line.matches(maxEnchantLinePattern)) {
                int maxEnchantValue = extractNumberFromLoreLine(line);
                if (!(extractedNumber12 < maxEnchantValue)) {
                    player.sendMessage(title + "강화의 격차가 맞지 않습니다.");
                    return;
                }
                break; // Break the loop as we found the relevant line
            }
        }
    }

    // Helper method to find the lore line in gerLore12 that contains a number
    private String getLoreLineContainingNumber(List<String> lore) {
        for (String line : lore) {
            if (containsNumber(line)) {
                return line;
            }
        }
        return null;
    }

    // Helper method to check if a string contains a number
    private boolean containsNumber(String str) {
        return str.matches(".*\\d+.*");
    }

    // Helper method to extract the number from a lore line
    private int extractNumberFromLoreLine(String loreLine) throws NumberFormatException {
        String[] parts = loreLine.split(" ");
        for (String part : parts) {
            if (containsNumber(part)) {
                return Integer.parseInt(part);
            }
        }
        throw new NumberFormatException("ERROR | 숫자가 발견되지 않았습니다.");
    }
}
