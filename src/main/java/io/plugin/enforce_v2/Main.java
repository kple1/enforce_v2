package io.plugin.enforce_v2;

import io.plugin.enforce_v2.Command.EnforceAnvilCreate;
import io.plugin.enforce_v2.Command.EnforceResetTicket;
import io.plugin.enforce_v2.Command.GiveEnforceTicket;
import io.plugin.enforce_v2.Command.StatEnforceTicketCreate;
import io.plugin.enforce_v2.Listener.AnvilClick;
import io.plugin.enforce_v2.Listener.AnvilSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    public void Commands() {
        Bukkit.getPluginCommand("enforceTicket").setExecutor(new GiveEnforceTicket());
        Bukkit.getPluginCommand("statEnforceTicket").setExecutor(new StatEnforceTicketCreate());
        Bukkit.getPluginCommand("enforceResetTicket").setExecutor(new EnforceResetTicket());
        Bukkit.getPluginCommand("enforceAnvilCreate").setExecutor(new EnforceAnvilCreate());
    }

    public void Listener() {
        Bukkit.getPluginManager().registerEvents(new AnvilSet(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilClick(), this);
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
}
