package io.plugin.enforce_v2;

import io.plugin.enforce_v2.Command.EnforceAnvilCreate;
import io.plugin.enforce_v2.Command.EnforceResetTicket;
import io.plugin.enforce_v2.Command.GiveEnforceTicket;
import io.plugin.enforce_v2.Command.StatEnforceTicketCreate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public void Commands() {
        Bukkit.getPluginCommand("enforceTicket").setExecutor(new GiveEnforceTicket());
        Bukkit.getPluginCommand("statEnforceTicket").setExecutor(new StatEnforceTicketCreate());
        Bukkit.getPluginCommand("enforceResetTicket").setExecutor(new EnforceResetTicket());
        Bukkit.getPluginCommand("enforceAnvilCreate").setExecutor(new EnforceAnvilCreate());
    }

    @Override
    public void onEnable() {
        Commands();
    }

    @Override
    public void onDisable() {

    }
}
