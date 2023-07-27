package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GiveEnforceTicket implements CommandExecutor {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                return true;
            }

            if (args.length > 1) {
                try {
                    ItemBuild.giveEnforceItem(Enchantment.getByName(args[0]), args[1], args[2], player, Integer.parseInt(args[3]));
                } catch (NumberFormatException e) {
                    player.sendMessage(title + "오류입니다! 다시 한번 확인 해주세요.");
                }
            }
        }
        return false;
    }
}
