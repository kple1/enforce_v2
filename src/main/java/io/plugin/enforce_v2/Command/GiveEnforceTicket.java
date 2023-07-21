package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveEnforceTicket implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                return true;
            }

            if (args.length > 1) {
                ItemBuild.giveEnforceItem(Enchantment.getByName(args[0]), args[1], args[2], player);
            }
        }
        return false;
    }
}
