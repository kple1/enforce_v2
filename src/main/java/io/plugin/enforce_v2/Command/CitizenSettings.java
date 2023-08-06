package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Main;
import io.plugin.enforce_v2.Utils.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.plugin.enforce_v2.Main.plugin;

public class CitizenSettings implements CommandExecutor, TabExecutor {

    String title = Color.chat("&f[ &c&l강화 &f] ");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {

            int nextAvailableIndex = Main.getPlugin().getNextAvailableIndex();
            ConfigurationSection configSection = plugin.getConfig().getConfigurationSection("오픈 강화목록");
            if (!args[0].equals("NPC")) {
                return true;
            }

            if (args[1].equals("추가")) {
                String name = args[2];
                plugin.getConfig().set("오픈 강화목록." + nextAvailableIndex, name);
                plugin.saveConfig();
                player.sendMessage(title + Color.chat("&a" + args[2] + "&f이(가) 추가되었습니다."));
            }

            if (args[1].equals("삭제")) {
                String targetName = args[2];

                if (configSection == null) {
                    player.sendMessage(title + "오픈 강화목록이 비어있습니다.");
                    return true;
                }

                boolean found = false;
                for (String key : configSection.getKeys(false)) {
                    String getConfigKey = plugin.getConfig().getString("오픈 강화목록." + key);
                    if (getConfigKey != null && getConfigKey.equals(targetName)) {
                        configSection.set(key, null);
                        found = true;
                    }
                }

                if (found) {
                    plugin.saveConfig();
                    player.sendMessage(title + Color.chat("&a" + targetName + "&f이(가) 삭제되었습니다."));
                } else {
                    player.sendMessage(title + "해당 이름은 존재하지 않습니다.");
                }
                return true;
            }

            if (args[1].equals("목록")) {
                if (configSection == null) {
                    player.sendMessage(title + "오픈 강화목록이 비어있습니다.");
                    return true;
                }

                player.sendMessage(Color.chat("&f&l[ &c&l오픈 강화목록 &f&l]&f"));
                for (String key : configSection.getKeys(false)) {
                    String getConfigKey = plugin.getConfig().getString("오픈 강화목록." + key);
                    if (getConfigKey != null) {
                        player.sendMessage(getConfigKey);
                    }
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        final List<String> tabList = new ArrayList<>();

        if (args.length == 1) {
            tabList.add("NPC");
            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }

        if (args.length == 2) {
            tabList.add("추가");
            tabList.add("삭제");
            tabList.add("목록");
            return StringUtil.copyPartialMatches(args[1], tabList, new ArrayList<>());
        }

        if (args.length == 3) {
            tabList.add("<NPC 이름>");
        }
        return tabList;
    }
}
