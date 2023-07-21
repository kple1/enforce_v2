package io.plugin.enforce_v2.Utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemBuild {

    private static ItemStack result_LongType(Material type, int amount, String displayName, String... lore) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);
        return stack;
    }

    private static ItemStack result_ShortType(Material type, int amount) {
        ItemStack stack = new ItemStack(type, amount);
        return stack;
    }

    private static ItemStack result_NameType(Material type, int amount, String displayName) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack result_EnchantType(Material type, int amount, String displayName, Enchantment enchantment) {
        ItemStack stack = new ItemStack(type, amount);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addEnchant(enchantment, 1, false);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack giveEnforceItem(Enchantment enchantment, String level, String percentage, Player player) {
        ItemStack giveItem = result_EnchantType(Material.BOOK, 1, (Color.chat("&b&o&l강화책")), enchantment);
        ItemMeta itemMeta = giveItem.getItemMeta();
        itemMeta.setLore(Arrays.asList(Color.chat("&7최대 강화 : " + level),
                Color.chat("&a강화 성공 확률 : " + percentage + "%")));
        giveItem.setItemMeta(itemMeta);
        player.getInventory().addItem(giveItem);
        return null;
    }

    public final static ItemStack anvil = result_NameType(Material.ANVIL, 1, (Color.chat("&7[ 강화전용 모루 ]")));
}
