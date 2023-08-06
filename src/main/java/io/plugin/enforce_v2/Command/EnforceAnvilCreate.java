package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnforceAnvilCreate implements CommandExecutor, TabExecutor {

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

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        final List<String> tabList = new ArrayList<>();

        if (args.length == 1) {
            tabList.add("생성");
            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }
        return tabList;
    }
}
