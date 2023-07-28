package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnforceAnvilCreate implements CommandExecutor {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                return true;
            }

            if (!args[0].equals("생성")) {
                return true;
            }

            player.getInventory().addItem(ItemBuild.anvil);
            player.sendMessage(title + "모루가 지급되었습니다.");
        }
        return false;
    }
}
