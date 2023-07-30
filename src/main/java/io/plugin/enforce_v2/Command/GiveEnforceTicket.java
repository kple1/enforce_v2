package io.plugin.enforce_v2.Command;

import io.plugin.enforce_v2.Utils.Color;
import io.plugin.enforce_v2.Utils.ItemBuild;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GiveEnforceTicket implements CommandExecutor, TabExecutor {

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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        final List<String> tabList = new ArrayList<>();

        if (args.length == 1) {
            tabList.add("PROTECTION_ENVIRONMENTAL");
            tabList.add("PROTECTION_FIRE");
            tabList.add("PROTECTION_FALL");
            tabList.add("PROTECTION_EXPLOSIONS");
            tabList.add("PROTECTION_PROJECTILE");
            tabList.add("OXYGEN");
            tabList.add("WATER_WORKER");
            tabList.add("THORNS");
            tabList.add("DEPTH_STRIDER");
            tabList.add("FROST_WALKER");
            tabList.add("BINDING_CURSE");
            tabList.add("DAMAGE_ALL");
            tabList.add("DAMAGE_UNDEAD");
            tabList.add("DAMAGE_ARTHROPODS");
            tabList.add("KNOCKBACK");
            tabList.add("FIRE_ASPECT");
            tabList.add("LOOT_BONUS_MOBS");
            tabList.add("SWEEPING_EDGE");
            tabList.add("DIG_SPEED");
            tabList.add("SILK_TOUCH");
            tabList.add("DURABILITY");
            tabList.add("LOOT_BONUS_BLOCKS");
            tabList.add("ARROW_DAMAGE");
            tabList.add("ARROW_KNOCKBACK");
            tabList.add("ARROW_FIRE");
            tabList.add("ARROW_INFINITE");
            tabList.add("LUCK");
            tabList.add("LURE");
            tabList.add("LOYALTY");
            tabList.add("IMPALING");
            tabList.add("RIPTIDE");
            tabList.add("CHANNELING");
            tabList.add("MULTISHOT");
            tabList.add("QUICK_CHARGE");
            tabList.add("PIERCING");
            tabList.add("MENDING");
            tabList.add("VANISHING_CURSE");
            tabList.add("SOUL_SPEED");
            tabList.add("SWIFT_SNEAK");

            return StringUtil.copyPartialMatches(args[0], tabList, new ArrayList<>());
        }

        if (args.length == 2) {
            for (int i = 5; i < 9; i++) {
                tabList.add(String.valueOf(i));
            }
        }

        if (args.length == 3) {
            tabList.add("<확률>");
        }

        if (args.length == 4) {
            for (int i = 1; i < 10; i++) {
                tabList.add(String.valueOf(i));
            }
        }
        return tabList;
    }
}
