package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class StatEnforceTicketCreate implements CommandExecutor {

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

            ItemStack itemStack = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("스텟강화권");
            itemStack.setItemMeta(itemMeta);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemStack);
        }
        return false;
    }
}
