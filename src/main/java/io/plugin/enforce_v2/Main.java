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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

    public void startTimer(Inventory inv, InventoryClickEvent event, Player player) {
        taskId = new BukkitRunnable() {
            int time = 1;

            @Override
            public void run() {
                if (time >= 1 && time <= 9) {
                    inv.setItem(26 + time, ItemBuild.yellowGlass);
                }

                if (time > 9) {
                    this.cancel();
                    return;
                }
                time++;
            }
        }.runTaskTimer(this, 0, 20).getTaskId(); // 1초마다 실행
    }
}
